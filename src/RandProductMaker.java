import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author Matt Bennett
 * Some code refactored from SerialDemoWrite by Tom Wulf
 */
public class RandProductMaker
{
    public static void main(String[] args)
    {
        ArrayList<Product> products = new ArrayList<>();
        boolean done = false;

        String ID = "";
        String Name = "";
        String Desc = "";
        double Cost = 0;

        // Create an instance of SafeInputObject
        SafeInputObj SI = new SafeInputObj();

        do {
            Name = SI.getNonZeroLenString("Enter the product name");
            Desc = SI.getNonZeroLenString("Enter the product description");
            Cost = SI.getDouble("Enter the product cost");

            Product productRec = new Product(Name, Desc, Cost);
            products.add(productRec);

            done = SI.getYNConfirm("Are you done?");

        } while(!done);

        for (Product p : products) {
            System.out.println(p);
        }

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path path = Paths.get(workingDirectory.getPath() + "\\src\\ProjectData.bin");

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path.toFile())))
        {
            out.writeObject(products);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
