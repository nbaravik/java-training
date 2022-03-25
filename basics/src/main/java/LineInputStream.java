import java.io.*;

public class LineInputStream extends InputStream implements AutoCloseable {

    private InputStream inputStream;

    public LineInputStream(InputStream is) {
        this.inputStream = new BufferedInputStream(is);
    }

    public String nextLine() throws IOException {

        StringBuilder line = new StringBuilder();
        int oneByte;

        while ((oneByte = inputStream.read()) != -1) {
            switch ((char) oneByte) {
                case '\r' -> { break; }                      //skip \r
                case '\n' -> { return line.toString(); }     //end of line
                default   -> line.append((char) oneByte);
            }
        }

        if (line.length() == 0) {
            return null;
        }
        return line.toString();
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }

    public void close() throws IOException {
        inputStream.close();
    }
}
