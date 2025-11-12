import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author Matt Bennett
 * Some code refactored from SerialDemoRead by Tom Wulf
 */
public class RandProductSearch extends JFrame
{
    JPanel mainPnl;
    JPanel titlePnl;
    JPanel searchPnl;
    JPanel infoPnl;
    JPanel footerPnl;

    JLabel title;

    JTextField searchFld;

    JLabel searchLbl;
    JLabel emptyLbl;

    String searchPhrase = "";

    JButton searchBtn;
    JButton quitBtn;

    JTextArea infoArea;

    JFrame frameSearch;

    ArrayList<Product> products = new ArrayList<>();
    String nameTest;
    int resultsCount = 0;
    String resultsString = "";

    public RandProductSearch() {
        mainPnl = new JPanel();
        mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));

        createTitlePanel();
        createSearchPanel();
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
        title = new JLabel("Enter Your Search Phrase Below");
        title.setFont(new Font("Verdana", Font.PLAIN, 20));
        title.setForeground(Color.WHITE);
        titlePnl.add(title);

        mainPnl.add(titlePnl);
    }

    private void createSearchPanel()
    {
        searchPnl = new JPanel();
        searchPnl.setPreferredSize(new Dimension(500,60));
        searchPnl.setLayout(new GridLayout(2,2));

        searchFld = new JTextField(20);

        Font labelFont = new Font("Verdana", Font.BOLD, 16);

        searchLbl = new JLabel("                  Search for:");
        searchLbl.setFont(labelFont);
        emptyLbl = new JLabel("");

        searchBtn = new JButton("Search");
        searchBtn.addActionListener(
                (ActionEvent ae) ->
                {
                    searchPhrase = searchFld.getText();

                    if (searchPhrase.equals("")) {
                        infoArea.setText("You must enter a search phrase above.");
                        return;
                    } else {

                        File workingDirectory = new File(System.getProperty("user.dir"));
                        Path path = Paths.get(workingDirectory.getPath(), "src", "ProductData.bin");

                        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path.toFile())))
                        {
                            resultsString = "Results:\n";
                            resultsCount = 0;

                            products = (ArrayList<Product>) in.readObject();
                            for (Product p : products)
                            {
                                nameTest = p.getName();
                                if (nameTest.toLowerCase().contains(searchPhrase.toLowerCase()))
                                {
                                    resultsCount ++;
                                    resultsString = resultsString + p.toString() + "\n";
                                }
                            }
                            resultsString = resultsString + resultsCount + " records found.";
                            infoArea.setText(resultsString);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

        searchPnl.add(searchLbl);
        searchPnl.add(searchFld);

        searchPnl.add(emptyLbl);
        searchPnl.add(searchBtn);

        mainPnl.add(searchPnl);
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
            frameSearch = new JFrame("Confirmation");

            int result = JOptionPane.showConfirmDialog(frameSearch, "Are you sure you want to quit?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (result == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
            else
            {
                frameSearch.dispose();
            }
        });
        footerPnl.add(quitBtn);

        mainPnl.add(footerPnl);
    }
}
