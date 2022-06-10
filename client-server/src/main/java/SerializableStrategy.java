import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializableStrategy implements MessageStrategy {
    @Override
    public void send(ObjectOutputStream oos, Message msg) throws IOException {
        oos.writeObject(msg);
        oos.flush();
    }

    @Override
    public Message receive(ObjectInputStream ois) throws IOException {
        try {
            return (Message) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}
