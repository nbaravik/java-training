package mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @GetMapping("weather/{city}")
    public CurrentWeather getWeather(@PathVariable String city) {
        return new CurrentWeather(city, 23);
    }
}
