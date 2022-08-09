package bot;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.UnknownHostException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

public class WeatherBot {

    private static final String NAME = "@BelarusWeatherBot";
    private static final String HOST_URL = "http://api.weatherstack.com/current?access_key=";

    private static final Logger LOGGER = Logger.getLogger(WeatherBot.class);
    private static final OkHttpClient CLIENT = new OkHttpClient();

    private static WeatherDB weatherDatabase = null;

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> LOGGER.info("Application has stopped. Telegram bot " + NAME + " is not available.")));

        if (args.length == 0) {
            LOGGER.error("Telegram bot cannot be launched. Token is undefined.");
            System.exit(0);
        }
        if (args.length == 1) {
            LOGGER.error("Telegram bot cannot work correctly. Access key for weatherstack.com is undefined.");
            System.exit(0);
        }

        if (args.length == 2) {
            LOGGER.warn("Database access password is undefined. Connection to the database is impossible.");
            LOGGER.info("Weather forecast search will be executed only through API weatherstack.com");
        }

        final String token = args[0];
        final String accessKey = args[1];
        if (args.length == 3) {
            weatherDatabase = new WeatherDB(args[2]);
        }

        TelegramBot bot = new TelegramBot(token);
        LOGGER.info("Telegram bot " + NAME + " started");

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
                        Weather weather;
                        // try to get weather information from database
                        if (weatherDatabase != null) {
                            weather = manageWeatherFromDatabase(chatMessage);
                            if (weather != null) {
                                message = new SendMessage(chatId, formatMessage(weather)).parseMode(ParseMode.HTML);
                                LOGGER.debug("Valid information about the weather in " + chatMessage + " is found in the database.");
                                LOGGER.debug("Current weather in " + weather.getLocation().getCity() + ", " + weather.getLocation().getCountry() +
                                        " were sent to chat#" + chatId);
                                bot.execute(message);
                                break;
                            }
                        }

                        // try to get weather information from API
                        weather = manageWeatherFromAPI(chatMessage, accessKey);
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

    private static Weather manageWeatherFromDatabase(String cityName) {

        Weather weather = weatherDatabase.getWeatherByCityName(cityName);
        // case 1: there is no info in DB
        if (weather == null) {
            LOGGER.debug("There is no information about the weather in " + cityName + " in the database.");
            return null;
        }
        // case 2: weather info from DB is expired, it must be deleted
        if (!weather.isRelevant()) {
            LOGGER.debug("Information about the weather in " + cityName + " from the database is expired.");
            weatherDatabase.deleteExpiredWeatherInfo(cityName);
            return null;
        }
        // case 3: valid weather info from DB
        return weather;
    }

    private static Weather manageWeatherFromAPI(String cityName, String accessKey) {
        Weather currentWeather = null;

        Request request = new Request.Builder()
                .url(HOST_URL + accessKey + "&query=" + cityName)
                .addHeader("Accept-Encoding", "gzip, deflate")
                .build();

        LOGGER.debug("Request: " + request.url());

        try {
            Response response = CLIENT.newCall(request).execute();

            int responseCode = response.code();
            LOGGER.debug("Request executed. Response code: " + responseCode);
            if (responseCode >= 100 && responseCode < 200) {
                LOGGER.warn("Error. Response code: " + responseCode + "; " + response.body());
                return null;
            }
            if (responseCode >= 200 && responseCode < 300) {
                LOGGER.info("Request was executed successful. Response code: " + responseCode + "; " + response.body());
            }
            if (responseCode >= 400 && responseCode < 500) {
                LOGGER.warn("Client error, Response code: " + responseCode + "; " + response.body());
                return null;
            }
            if (responseCode >= 500 && responseCode < 600) {
                LOGGER.warn("Server error, Response code: " + responseCode + "; " + response.body());
                return null;
            }

            currentWeather = WeatherObjectMapper.getInstance().getMapper().
                    readValue(new GZIPInputStream(response.body().byteStream()), Weather.class);

            if (currentWeather.hasError()) {
                LOGGER.error("Request error was detected. " + currentWeather.getError().getErrorDescription());
                return null;
            }
            // insert weather information into database, if it is available
            if (weatherDatabase != null) {
                weatherDatabase.insertWeatherInfo(currentWeather);
            }

        } catch (JsonProcessingException e) {
            LOGGER.error("Json processing exception. " + e);
        } catch (UnknownHostException e) {
            LOGGER.error("Unknown host exception. " + e);
        } catch (IOException e) {
            LOGGER.error("Unexpected exception: " + e);
        }
        return currentWeather;
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