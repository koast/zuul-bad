public class Door
{
    private boolean open;
    private Item llave;
    private String position;
    
    public Door(boolean open, Item llave, String position)
    {
        this.open = open;
        this.llave = llave;
        this.position = position;
    }
    
    public boolean getOpen()
    {
        return open;
    }
    
    public void open()
    {
        open = true;
    }
    
    public Item llave()
    {
        return llave;
    }
    
    public String getPosition()
    {
        return position;
    }
}