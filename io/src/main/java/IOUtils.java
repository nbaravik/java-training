import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {
    public static String nextLine(InputStream inputStream) throws IOException {

        StringBuilder line = new StringBuilder();
        int oneByte;
        while ((oneByte = inputStream.read()) != -1) {
            switch ((char) oneByte) {
                case '\r' -> {
                    break;
                }
                case '\n' -> {
                    return line.toString();
                }
                default -> line.append((char) oneByte);
            }
        }
        if (line.length() == 0) {
            return null;
        }
        return line.toString();
    }

    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {

        byte buffer[] = new byte[16000];
        int count;

        while ((count = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, count);
        }
    }
}
