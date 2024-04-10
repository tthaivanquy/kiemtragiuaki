package ktgk;
import java.util.concurrent.BlockingQueue;
public class CheckPrimeThread extends Thread {
    private BlockingQueue<String> inputQueue;
    private BlockingQueue<String> outputQueue;

    public CheckPrimeThread(BlockingQueue<String> inputQueue, BlockingQueue<String> outputQueue) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    public void run() {
        try {
            while (true) {
                String encodedAge = inputQueue.take();
                if (encodedAge == null) {
                    break;
                }
                int sumOfDigits = calculateSumOfDigits(encodedAge);
                boolean isPrime = isPrime(sumOfDigits);
                outputQueue.put(Boolean.toString(isPrime));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private int calculateSumOfDigits(String encodedAge) {
        int sum = 0;
        for (int i = 0; i < encodedAge.length(); i++) {
            char digitChar = encodedAge.charAt(i);
            int digit = Character.getNumericValue(digitChar);
            sum += digit;
        }
        return sum;
    }
    private boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}