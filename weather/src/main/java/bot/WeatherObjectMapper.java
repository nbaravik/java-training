package bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherObjectMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final WeatherObjectMapper INSTANCE = new WeatherObjectMapper();

    private ObjectMapper mapper;

    private WeatherObjectMapper() {
        mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();

        LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(FORMATTER);
        module.addSerializer(LocalDateTime.class, localDateTimeSerializer);

        LocalDateTimeDeserializer localDateTimeDeserializer =  new LocalDateTimeDeserializer(FORMATTER);
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);

        mapper.registerModule(module);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static WeatherObjectMapper getInstance() {
        return INSTANCE;
    }

    public DateTimeFormatter getDateTimeFormat() {
        return FORMATTER;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}
