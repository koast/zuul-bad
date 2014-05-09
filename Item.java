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
    private boolean canTakeIt;

    /**
     * Constructor for objects of class Item
     */
    public Item(String itemDescription,double itemSize,boolean canTakeIt)
    {
        this.itemDescription = itemDescription;
        this.itemSize = itemSize;
        this.canTakeIt = canTakeIt;
    }

    public double getSize()
    {
        return itemSize;
    }

    public String getDescription()
    {       
        return itemDescription;
    }

    public boolean getCanTakeIt()
    {
        return canTakeIt;
    }
}
