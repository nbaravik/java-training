package bot;

import org.apache.log4j.Logger;

public class WeatherCache implements WeatherClient {

    private static final Logger LOGGER = Logger.getLogger(WeatherCache.class);

    private WeatherClient weatherClient;
    private WeatherDB weatherDatabase;

    public WeatherCache(WeatherClient weatherClient, String accessKey) {
        this.weatherClient = weatherClient;
        weatherDatabase = new WeatherDB(accessKey);
    }

    @Override
    public Weather byCity(String cityName) {
        Weather weather = weatherDatabase.getWeatherByCityName(cityName);
        // case 1: valid weather info from DB
        if (weather != null && weather.isRelevant()) {
            LOGGER.debug("Valid information about the weather in " + cityName + " is found in the database.");
            return weather;
        }
        // case 2: weather info from DB is expired, it must be deleted
        if (weather != null && !weather.isRelevant()) {
            LOGGER.debug("Information about the weather in " + cityName + " from the database is expired.");
            weatherDatabase.deleteExpiredWeatherInfo(cityName);
        }
        // case 3: there is no info in DB
        if (weather == null) {
            LOGGER.debug("There is no information about the weather in " + cityName + " in the database.");
        }
        // search weather info in API and store it in database
        weather = weatherClient.byCity(cityName);
        if (weather != null) {
            weatherDatabase.insertWeatherInfo(weather);
        }
        return weather;
    }
}
