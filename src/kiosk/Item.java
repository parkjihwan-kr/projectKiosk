package kiosk;

public class Item extends Menu implements MenuItem{
    private int price;
    //private String size;
    
    public Item(String name, int price, String description) {
        super(name, description);
        this.price = price;
    }
    
    /*public Item(String name, int price, String description) {
        super(name, description);
        this.price = price;
    }*/
    
    @Override
    public int getPrice() {
        return this.price;
    }
    
    @Override
    public void setPrice(int price) {
    	this.price = price;
    }
}