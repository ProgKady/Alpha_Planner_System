
package alpha.planner;

//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//public class CopyBinaryFile {
//    public static void main(String[] args) {
//        String source = "C:\\Users\\Ahmed.ElKady\\Desktop\\test\\old.png";
//        String target = "C:\\Users\\Ahmed.ElKady\\Desktop\\test\\new.png";
//
//        try (FileInputStream fis = new FileInputStream(source);
//             FileOutputStream fos = new FileOutputStream(target)) {
//
//            byte[] buffer = new byte[4096]; // 4KB buffer
//            int bytesRead;
//
//            while ((bytesRead = fis.read(buffer)) != -1) {
//                fos.write(buffer, 0, bytesRead);
//            }
//
//            System.out.println("Done!");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadTextFile {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Ahmed.ElKady\\Desktop\\test\\file.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String content = sb.toString();
        System.out.println(content);
    }
}











