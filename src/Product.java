import java.io.Serializable;
import java.util.Objects;

/**
 * @author Matt Bennett based on work by Tom Wulf
 * This class creates a Product object with all the fields and methods.
 */
public class Product implements Serializable
{
    private String IDNum;
    private String Name;
    private String Description;
    private double Cost;

    static private int IDSeed = 1;
    public static int getIDSeed() { return IDSeed; }
    public static void setIDSeed(int IDSeed) { Product.IDSeed = IDSeed; }

    public Product(String IDNum, String Name, String Description, double Cost)
    {
        this.IDNum = IDNum;
        this.Name = Name;
        this.Description = Description;
        this.Cost = Cost;
    }

    public Product(String Name, String Description, double Cost)
    {
        this.IDNum = this.genIDNum();
        this.Name = Name;
        this.Description = Description;
        this.Cost = Cost;
    }

    public String genIDNum()
    {
        String newID = "" + IDSeed;
        while (newID.length() < 6) {
            newID = "0" + newID;
        }
        IDSeed++;
        return newID;
    }

    public String getIDNum()
    {
        return IDNum;
    }

    public void setIDNum(String IDNum)
    {
        this.IDNum = IDNum;
    }

    public String getName()
    {
        return Name.trim();
    }

    public void setName(String Name)
    {
        while (Name.length() < 35) {
            Name = " " + Name;
        }
        this.Name = Name;
    }

    public String getDescription()
    {
        return Description.trim();
    }

    public void setDescription(String Description)
    {
        while (Description.length() < 75) {
            Description = " " + Description;
        }
        this.Description = Description;
    }

    public double getCost()
    {
        return (double) Cost;
    }

    public void setCost(double Cost)
    {
        this.Cost = Cost;
    }

    public String toString()
    {
        return getIDNum() + ": " + getName() + "-- " + getDescription() + " ($" + getCost() + ")";
    }

    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Cost == product.Cost &&
                Objects.equals(IDNum, product.IDNum) &&
                Objects.equals(Name, product.Name) &&
                Objects.equals(Description, product.Description);
    }
}
