package ktgk;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ReadResult {
    public static void main(String[] args) {
        String fileName = "kq.xml";
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
