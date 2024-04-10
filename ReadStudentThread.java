package ktgk;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.concurrent.BlockingQueue;

public class ReadStudentThread extends Thread {
    private BlockingQueue<Student> queue;

    public ReadStudentThread(BlockingQueue<Student> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            File file = new File("D:\\codejavaver2\\baitapgiuaki\\src\\ktgk\\student.xml");
            InputStream inputStream = new FileInputStream(file);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);
            NodeList studentList = doc.getElementsByTagName("student");
            for (int i = 0; i < studentList.getLength(); i++) {
                Element studentElement = (Element) studentList.item(i);
                int id = Integer.parseInt(studentElement.getAttribute("id"));
                String name = studentElement.getElementsByTagName("name").item(0).getTextContent();
                String address = studentElement.getElementsByTagName("address").item(0).getTextContent();
                String dateOfBirthStr = studentElement.getElementsByTagName("dateOfBirth").item(0).getTextContent();
                LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr, DateTimeFormatter.ISO_DATE);

                Student student = new Student(id, name, address, dateOfBirth);

                queue.put(student);
            }

            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
