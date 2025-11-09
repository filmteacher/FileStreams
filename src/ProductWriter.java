import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class ProductWriter
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

        Path target = new File(System.getProperty("user.dir")).toPath();
        target = target.resolve("src");
        //getOutPutPath doesn't work on my Mac
        Path fileLocation = target.resolve("ProductTestData.txt");

        try
        {
            // Typical java pattern of inherited classes
            // we wrap a BufferedWriter around a lower level BufferedOutputStream
            // BufferedOutputStream doesn't work on my Mac
            BufferedWriter writer =
                    Files.newBufferedWriter(fileLocation, Charset.forName("UTF-8"));

            // Finally can write the file LOL!

            for(Product rec : products)
            {
                writer.write(rec.toCSV());  // writer won't write the Product object unless we convert it to a string'
                writer.newLine();  // adds the new line

            }
            writer.close(); // must close the file to seal it and flush buffer
            System.out.println("Data file written!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
