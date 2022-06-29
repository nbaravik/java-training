import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

public class WeatherBot {

    private static final String NAME = "@BelarusWeatherBot";
    private static final String HOST_URL = "http://api.weatherstack.com/current?access_key=";

    private static final Logger LOGGER = Logger.getLogger(WeatherBot.class);

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> LOGGER.info("Application has stopped. Telegram bot " + NAME + " is not available.")));

        if (args.length == 0) {
            LOGGER.error("Telegram bot cannot be launched. Token is undefined.");
            System.exit(0);
        }
        if (args.length == 1) {
            // TODO
        }

        final String token = args[0];
        final String accessKey = args[1];

        TelegramBot bot = new TelegramBot(token);
        LOGGER.info("Telegram bot " + NAME + " started");

        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {

                long chatId = update.message().chat().id();
                String cityName = update.message().text();

                LOGGER.info("Update from chat#" + chatId + ". Message: " + cityName);

                Weather weather = getWeather(cityName, accessKey);
                SendMessage message;
                if (weather == null) {
                    message = new SendMessage(chatId,
                            "Something went wrong. We are unable to provide you with data of the weather in "
                                    + cityName + ".\nTry again later.");
                } else {
                    message = new SendMessage(chatId, formatMessage(weather)).parseMode(ParseMode.HTML);
                }
                bot.execute(message);

                if (weather == null) {
                    LOGGER.debug("Unable to send current weather in " + cityName + " to chat#" + chatId);
                } else {
                    LOGGER.debug("Current weather in " + weather.getLocation().getCityAndCountry() + " were sent to chat#" + chatId);
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private static Weather getWeather(String cityName, String accessKey) {
        Weather currentWeather = null;
        Request request = new Request.Builder()
                .url(HOST_URL +  accessKey + "&query=" + cityName)
                .addHeader("Accept-Encoding", "gzip, deflate")
                .build();

        LOGGER.debug("Request: " + request.url());

        try {
            Response response = client.newCall(request).execute();

            int responseCode = response.code();
            LOGGER.debug("Request executed. Response code: " + responseCode);
            if (responseCode >= 200 && responseCode < 300) {
                LOGGER.info("Request was executed successful. Response code: " + responseCode);
            }
            if (responseCode >= 400 && responseCode < 500) {
                LOGGER.warn("Client error, Response code: " + responseCode + "; " + response.body());
                return null;
            }
            if (responseCode >= 500 && responseCode < 600) {
                LOGGER.warn("Server error, Response code: " + responseCode + "; " + response.body());
                return null;
            }
            currentWeather = mapper.readValue(new GZIPInputStream(response.body().byteStream()), Weather.class);

        } catch (StreamReadException e) {
            LOGGER.error("Stream read exception " + e);
        } catch (DatabindException e) {
            LOGGER.error("Databind exception: " + e);
        } catch (UnknownHostException e) {
            LOGGER.error("Unknown host exception: " + e);
        } catch (IOException e) {
            LOGGER.error("Unexpected exception: " + e);
        }
        return currentWeather;
    }

    private static String formatMessage(Weather weather) {
        StringBuilder sb = new StringBuilder();
        sb.append(weather.getLocation().getLocaltime() + "\n");
        sb.append("<b>Weather in " + weather.getLocation().getCityAndCountry() + "</b>\n");
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