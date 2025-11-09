import javax.swing.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author Matt Bennett
 * Some code refactored from SerialDemoRead by Tom Wulf
 */
public class RandProductSearch
{
    public static void main(String[] args)
    {
        ArrayList<Product> products;

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path path = Paths.get(workingDirectory.getPath() + "\\src\\ProductData.bin");

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path.toFile()))) {
            products = (ArrayList<Product>) in.readObject();
            for (Product p : products) {
                System.out.println(p);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
