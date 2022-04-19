import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Main {

    public static void main(String[] args) throws IOException {
        // write
        InputStream isTxt = Main.class.getResourceAsStream("file.txt");
        OutputStream osZip = new GZIPOutputStream(new FileOutputStream("file.zip"));
        IOUtils.copy(isTxt, osZip);
        isTxt.close();
        osZip.close();

        // read
        InputStream isZip = new GZIPInputStream(new FileInputStream(System.getProperty("user.dir") + File.separator + "file.zip"));
        String nextLine;
        while ((nextLine = IOUtils.nextLine(isZip)) != null) {
            System.out.println(nextLine);
        }
        isZip.close();
    }
}
