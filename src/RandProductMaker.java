import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author Matt Bennett
 * Some code refactored from SerialDemoWrite by Tom Wulf
 */
public class RandProductMaker extends JFrame
{
    JPanel mainPnl;
    JPanel titlePnl;
    JPanel entryPnl;
    JPanel infoPnl;
    JPanel footerPnl;

    JLabel title;

    JTextField nameFld;
    JTextField descFld;
    JTextField costFld;
    JTextField recordsFld;

    JLabel nameLbl;
    JLabel descLbl;
    JLabel costLbl;

    JButton addBtn;
    JButton quitBtn;

    JTextArea infoArea;

    JFrame frame;

    ArrayList<Product> products = new ArrayList<>();

    String Name = "";
    String Desc = "";
    String CostStr = "";
    double Cost = 0;
    String fullProduct = "";
    int numRecords = 0;

    public RandProductMaker() {
        mainPnl = new JPanel();
        mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));

        createTitlePanel();
        createEntryPanel();
        createInfoPanel();
        createFooterPanel();

        add(mainPnl);
        setTitle("Assignment 2 - Advanced IO");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createTitlePanel()
    {
        titlePnl = new JPanel();
        titlePnl.setPreferredSize(new Dimension(500,30));
        titlePnl.setBackground(Color.BLACK);
        title = new JLabel("Enter Your Product Below");
        title.setFont(new Font("Verdana", Font.PLAIN, 20));
        title.setForeground(Color.WHITE);
        titlePnl.add(title);

        mainPnl.add(titlePnl);
    }

    private void createEntryPanel()
    {
        entryPnl = new JPanel();
        entryPnl.setPreferredSize(new Dimension(500,120));
        entryPnl.setLayout(new GridLayout(4,2));

        nameFld = new JTextField(20);
        descFld = new JTextField(20);
        costFld = new JTextField(20);
        recordsFld = new JTextField(20);
        recordsFld.setText(numRecords + " records.");

        Font labelFont = new Font("Verdana", Font.BOLD, 16);

        nameLbl = new JLabel("                        Name:");
        nameLbl.setFont(labelFont);
        descLbl = new JLabel("               Description:");
        descLbl.setFont(labelFont);
        costLbl = new JLabel("                           Cost:");
        costLbl.setFont(labelFont);

        addBtn = new JButton("Add Product");
        addBtn.addActionListener(
                (ActionEvent ae) ->
                {
                    Name = nameFld.getText();
                    Desc = descFld.getText();
                    CostStr = costFld.getText();

                    if (Name.equals("") || Desc.equals("") || CostStr.equals("")) {
                        infoArea.setText("You must enter complete product information above.");
                        return;
                    } else {
                        Cost = Double.parseDouble(CostStr);

                        Product productRec = new Product(Name, Desc, Cost);
                        products.add(productRec);

                        File workingDirectory = new File(System.getProperty("user.dir"));
                        Path path = Paths.get(workingDirectory.getPath(), "src", "ProductData.bin");

                        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path.toFile())))
                        {
                            out.writeObject(products);
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }

                        fullProduct = productRec.toString();
                        infoArea.setText(fullProduct + " added to database. Enter another or click Quit!");
                        numRecords = products.size();

                        nameFld.setText("");
                        Name = "";
                        descFld.setText("");
                        Desc = "";
                        costFld.setText("");
                        CostStr = "";
                        Cost = 0;
                        recordsFld.setText(numRecords + " records.");
                    }
                });

        entryPnl.add(nameLbl);
        entryPnl.add(nameFld);

        entryPnl.add(descLbl);
        entryPnl.add(descFld);

        entryPnl.add(costLbl);
        entryPnl.add(costFld);

        entryPnl.add(recordsFld);
        entryPnl.add(addBtn);

        mainPnl.add(entryPnl);
    }

    private void createInfoPanel()
    {
        infoPnl = new JPanel();
        infoPnl.setPreferredSize(new Dimension(400, 220));

        infoArea = new JTextArea(13,60);
        infoArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setEditable(false);
        infoPnl.add(infoArea);

        mainPnl.add(infoPnl);
    }

    private void createFooterPanel()
    {
        footerPnl = new JPanel();
        footerPnl.setPreferredSize(new Dimension(500,30));
        footerPnl.setBackground(Color.BLACK);

        quitBtn = new JButton("Quit!");
        quitBtn.addActionListener((ActionEvent ae) ->
        {
            frame = new JFrame("Confirmation");

            int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (result == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
            else
            {
                frame.dispose();
            }
        });
        footerPnl.add(quitBtn);

        mainPnl.add(footerPnl);
    }
}
