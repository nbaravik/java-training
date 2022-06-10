import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class JsonStrategy implements MessageStrategy {

    private Gson GSON;

    public JsonStrategy() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        GSON = gsonBuilder.setPrettyPrinting().create();
    }

    @Override
    public void send(ObjectOutputStream oos, Message msg) throws IOException {
        String jsonString = GSON.toJson(msg);
        oos.writeObject(jsonString);
        oos.flush();
    }

    @Override
    public Message receive(ObjectInputStream ois) throws IOException {

        try {
            String jsonString = (String) ois.readObject();
            Message msg = GSON.fromJson(jsonString, Message.class);
            return msg;
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }

    }
}

class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(localDateTime));
    }
}

class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalDateTime.parse(json.getAsString(),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME.withLocale(Locale.ENGLISH));
    }
}