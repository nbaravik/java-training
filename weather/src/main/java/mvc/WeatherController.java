package mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@ConfigurationProperties(prefix = "weather")
public class WeatherController {

    @Autowired
    private ApplicationContext ctx;

    private String accessKey;
    private String hostUrl;

    public void setAccessKey(String accessKey) { this.accessKey = accessKey; }

    public void setHostUrl(String hostUrl) { this.hostUrl = hostUrl; }

    @GetMapping("weather/{city}")
    @ResponseBody
    public Weather getWeather(@PathVariable String city) {

        Weather currentWeather = null;
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        Request request = new Request.Builder()
                .url(hostUrl + "?access_key=" + accessKey + "&query=" + city)
                .build();
        try {
            Response response = client.newCall(request).execute();
            currentWeather = mapper.readValue(response.body().byteStream(), Weather.class);
        } catch (IOException e) {
            System.out.println("Unexpected exception: " + e);
        }
        return currentWeather;
    }
}
