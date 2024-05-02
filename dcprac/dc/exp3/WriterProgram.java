import java.io.BufferedWriter; import java.io.FileWriter; import java.io.IOException; public class WriterProgram {
public static void main(String[] args) { String filePath = "message.txt";
String message = "Hello from Vinod ";

try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) { writer.write(message);

                                       System.out.println("Message written to " + filePath);

} catch (IOException e) { e.printStackTrace();
}
}
}
