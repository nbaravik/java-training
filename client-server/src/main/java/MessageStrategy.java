import java.io.*;

public interface MessageStrategy {

    void send(ObjectOutputStream oos, Message msg) throws IOException;

    Message receive(ObjectInputStream ois) throws IOException;
}
