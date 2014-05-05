import java.util.Random;

/**
 * Write a description of class Item here.
 * 
 * @author Alejandro Aller Diez
 * @version (a version number or a date)
 */
public class Item
{

    private String itemDescription;
    private double itemSize;
    
    /**
     * Constructor for objects of class Item
     */
    public Item(String itemDescription,double itemSize)
    {
        this.itemDescription = itemDescription;
        this.itemSize = itemSize;
    }
    
    public double getSize()
    {
         return itemSize;
    }

    public String getDescription()
    {       
        return itemDescription;
    }
}
