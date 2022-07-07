package bot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

    private final String city;
    private final String country;
    private final String localtime;

    public Location(@JsonProperty("name") String cityName,
                    @JsonProperty("country") String countryName,
                    @JsonProperty("localtime") String localtime) {
        this.city = cityName;
        this.country = countryName;
        this.localtime = localtime;
    }

    public String getLocaltime() {
        return localtime;
    }

    public String getCityAndCountry() {
        return city + ", " + country;
    }
}