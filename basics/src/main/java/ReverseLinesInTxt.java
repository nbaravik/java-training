import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ReverseLinesInTxt {

    public static String reverse(String string) {
        if (string.length() == 0) {
            return "";
        }
        char[] chars = string.toCharArray();
        char tmp;
        for (int i = 0; i < chars.length / 2; i++) {
            tmp = chars[i];
            chars[i] = chars[chars.length - i - 1];
            chars[chars.length - i - 1] = tmp;
        }
        return String.valueOf(chars);
    }

    public static void main(String[] args) throws IOException {

        Path inPath = Path.of("basics", "input.txt");
        Path outPath = Path.of("basics", "output.txt");
        List<String> reversedLines = new LinkedList<>();
        try (FileReader fileReader = new FileReader(inPath.toString())) {
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()) {
                reversedLines.add(reverse(scanner.nextLine()));
            }
        }
        try (FileWriter fileWriter = new FileWriter(outPath.toString())) {
            for (String nextLine : reversedLines)
                fileWriter.write(nextLine + "\n");
        }
    }
}
