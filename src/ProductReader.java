import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class ProductReader
{
    public static void main(String[] args)
    {
        try
        {
            JFileChooser chooser = new JFileChooser();
            Scanner inFile;
            String line;

            // set the chooser to the project src directory
            Path target = new File(System.getProperty("user.dir")).toPath();
            target = target.resolve("src");
            chooser.setCurrentDirectory(target.toFile());

            // Create a File object with the desired default filename
            File defaultFile = new File("ProductTestData.txt");

            // Set the default selected file in the JFileChooser
            chooser.setSelectedFile(defaultFile);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                target = chooser.getSelectedFile().toPath();  // this is a File object not a String filename
                inFile = new Scanner(target);

                System.out.println("ID#         Name                    Description                                              Cost");
                System.out.println("====================================================================================================");

                while (inFile.hasNextLine())
                {
                    line = inFile.nextLine();
                    String[] items = line.split(",");
                    items[3] = items[3].trim();
                    double cost = Double.parseDouble(items[3]);
                    System.out.printf("%-11s%-25s%-57s%-7s\n", items[0],items[1],items[2],items[3]);
                }

                inFile.close();

            } else {  // User did not pick a file, closed the chooser
                System.out.println("Sorry, you must select a file! Terminating!");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found Error");
            ex.printStackTrace();
        } catch (IOException ex) // code to handle this exception
        {
            System.out.println("IOException Error");
            ex.printStackTrace();
        }
    }
}
