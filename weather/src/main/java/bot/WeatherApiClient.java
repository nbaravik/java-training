package bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.zip.GZIPInputStream;

public class WeatherApiClient implements WeatherClient {

    private static final String HOST_URL = "http://api.weatherstack.com/current?access_key=";

    private static final Logger LOGGER = Logger.getLogger(WeatherApiClient.class);
    private static final OkHttpClient CLIENT = new OkHttpClient();

    private String accessKey;

    public WeatherApiClient(String accessKey) {
        this.accessKey = accessKey;
    }

    @Override
    public Weather byCity(String cityName) {
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
        } catch (JsonProcessingException e) {
            LOGGER.error("Json processing exception. " + e);
        } catch (UnknownHostException e) {
            LOGGER.error("Unknown host exception. " + e);
        } catch (IOException e) {
            LOGGER.error("Unexpected exception: " + e);
        }
        return currentWeather;
    }
}
