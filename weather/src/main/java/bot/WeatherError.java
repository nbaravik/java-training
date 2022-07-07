package bot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherError {

    private final int code;
    private final String type;
    private final String info;

    public WeatherError(@JsonProperty("code") int code,
                    @JsonProperty("type") String type,
                    @JsonProperty("info") String info) {
        this.code = code;
        this.type = type;
        this.info = info;
    }

    public String getErrorDescription() {
        return "Code: " + code + ", type: " + type + ". " + info;
    }
}
