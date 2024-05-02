import java.io.BufferedReader; import java.io.FileReader; import java.io.IOException; public class ReaderProgram {
public static void main(String[] args) { String filePath = "message.txt";
try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) { String line;
while ((line = reader.readLine()) != null) { System.out.println("Message read from " + filePath + ": " + line);
}

} catch (IOException e) { e.printStackTrace();
}

}

}
