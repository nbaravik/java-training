package mvc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

    @Autowired
    @JsonProperty("name")
    private String city;

    @Autowired
    @JsonProperty("country")
    private String country;

    @Autowired
    @JsonProperty("localtime")
    private String localtime;
}