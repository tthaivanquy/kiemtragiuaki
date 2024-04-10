package ktgk;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
public class Main {
    public static void main(String[] args) {
        BlockingQueue<Student> studentQueue = new LinkedBlockingQueue<>();
        BlockingQueue<String> encodedAgeQueue = new LinkedBlockingQueue<>();
        BlockingQueue<String> primeCheckQueue = new LinkedBlockingQueue<>();
        ReadStudentThread readThread = new ReadStudentThread(studentQueue);
        CalculateAgeThread calculateAgeThread = new CalculateAgeThread(studentQueue, encodedAgeQueue);
        CheckPrimeThread checkPrimeThread = new CheckPrimeThread(encodedAgeQueue, primeCheckQueue);

        readThread.start();
        calculateAgeThread.start();
        checkPrimeThread.start();

        try {
            readThread.join();
            calculateAgeThread.join();
            checkPrimeThread.join();
            createResultFile(primeCheckQueue);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static void createResultFile(BlockingQueue<String> primeCheckQueue) {
        try {
            File file = new File("D:\\codejavaver2\\baitapgiuaki\\src\\ktgk\\kq.xml");
            FileWriter writer = new FileWriter(file);

            writer.write("<Students>\n");
            while (!primeCheckQueue.isEmpty()) {
                String result = primeCheckQueue.take();
                writer.write("\t<Student>\n");
                writer.write("\t\t<AgeSum>" + result + "</AgeSum>\n");
                writer.write("\t\t<IsDigit>" + result + "</IsDigit>\n");
                writer.write("\t</Student>\n");
            }
            writer.write("</Students>");
            writer.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}