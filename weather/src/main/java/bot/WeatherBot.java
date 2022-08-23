package bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import org.apache.log4j.Logger;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.stream.Collectors;

public class WeatherBot {

    private static final String NAME = "@BelarusWeatherBot";
    private static final Logger LOGGER = Logger.getLogger(WeatherBot.class);

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> LOGGER.info("Application has stopped. Telegram bot " + NAME + " is not available.")));

        if (args.length == 0) {
            LOGGER.error("Telegram bot cannot be launched. Token is undefined.");
            System.exit(0);
        }
        final String TOKEN = args[0];

        if (args.length == 1) {
            LOGGER.error("Telegram bot cannot work correctly. Access key for weatherstack.com is undefined.");
            System.exit(0);
        }
        final String API_ACCESS_KEY = args[1];

        // if database is available, try to find weather info in cache first
        final WeatherClient weatherClient;

        if (args.length == 3) {
            final String DATABASE_PASS = args[2];
            weatherClient = new WeatherCache(new WeatherApiClient(API_ACCESS_KEY), DATABASE_PASS);
        } else {
            LOGGER.warn("Database access password is undefined. Connection to the database is impossible.");
            LOGGER.info("Weather forecast search will be executed only through API weatherstack.com");
            weatherClient = new WeatherApiClient(API_ACCESS_KEY);
        }

        TelegramBot bot = new TelegramBot(TOKEN);
        LOGGER.info("Telegram bot " + NAME + " started");

        final WeatherClient finalWeatherClient = weatherClient;
        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {

                long chatId = update.message().chat().id();
                String chatMessage = update.message().text();

                LOGGER.info("Update from chat#" + chatId + ". Message: " + chatMessage);

                switch (chatMessage.toLowerCase(Locale.ROOT)) {
                    case "/start": {
                        SendMessage welcomeMessage = new SendMessage(chatId, "<b>Hello! I'm Weather Telegram Bot!</b>\n" +
                                "Enter the name of the city and I will send you the current weather in it.").parseMode(ParseMode.HTML);
                        bot.execute(welcomeMessage);
                        LOGGER.debug("Welcome message for chat#" + chatId + " is sent");
                        break;
                    }
                    default: {
                        SendMessage message;
                        Weather weather = weatherClient.byCity(chatMessage);

                        if (weather == null) {
                            message = new SendMessage(chatId,
                                    "<b>Sorry! Something went wrong.</b>\nWe are unable to provide you with data of the weather in " +
                                            chatMessage + ".\nTry again later.").parseMode(ParseMode.HTML);
                            LOGGER.debug("Unable to send current weather in " + chatMessage + " to chat#" + chatId);
                        } else {
                            message = new SendMessage(chatId, formatMessage(weather)).parseMode(ParseMode.HTML);
                            LOGGER.debug("Current weather in " + weather.getLocation().getCity() + ", " + weather.getLocation().getCountry() +
                                    " were sent to chat#" + chatId);
                        }
                        bot.execute(message);
                    }
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> {
            if (e.response() != null) {
                // bad response from telegram
                LOGGER.error("Telegram bot cannot work correctly. Error code: " + e.response().errorCode() +
                        ", description: " + e.response().description());
                System.exit(0);
            } else {
                // probably network error
                LOGGER.error("Unexpected error: " + e);
            }
        });
    }

    private static String formatMessage(Weather weather) {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter dtf = WeatherObjectMapper.getInstance().getDateTimeFormat();
        sb.append(weather.getLocation().getLocaltime().format(dtf) + "\n");
        sb.append("<b>Weather in " + weather.getLocation().getCity() + ", " + weather.getLocation().getCountry() + "</b>\n");
        sb.append("Temperature: " + weather.getCurrent().getTemperature() + "°C, feels like " + weather.getCurrent().getFeelslike() + "°C\n");
        sb.append("Humidity: " + weather.getCurrent().getHumidity() + "%\n");
        sb.append("Cloudcover: " + weather.getCurrent().getCloudcover() + "%\n");
        if (weather.getCurrent().getWeatherDescriptions() != null) {
            sb.append("Description: " + weather.getCurrent().getWeatherDescriptions().stream().map(Object::toString)
                    .collect(Collectors.joining(", ")).toLowerCase() + "\n");
        }
        if (weather.getCurrent().getWeatherIcons() != null) {
            for (String pictureUrl : weather.getCurrent().getWeatherIcons()) {
                pictureUrl.replaceAll("\"", "/");
                sb.append(pictureUrl + "\n");
            }
        }
        return sb.toString();
    }
}