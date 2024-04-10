package ktgk;
import java.time.LocalDate;
import java.time.Period;
import java.util.concurrent.BlockingQueue;

public class CalculateAgeThread extends Thread {
    private BlockingQueue<Student> inputQueue;
    private BlockingQueue<String> outputQueue;

    public CalculateAgeThread(BlockingQueue<Student> inputQueue, BlockingQueue<String> outputQueue) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    public void run() {
        try {
            while (true) {
                Student student = inputQueue.take();
                if (student == null) {
                    break;
                }
                int age = calculateAge(student.getDateOfBirth());
                String encodedAge = encodeAge(age);
                outputQueue.put(encodedAge);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private int calculateAge(LocalDate dateOfBirth) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(dateOfBirth, currentDate).getYears();
    }
    private String encodeAge(int age) {
        return Integer.toString(age);
    }
}
