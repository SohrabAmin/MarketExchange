import java.io.Serializable;

public class Item implements Serializable {
    private String name;
    private User owner;
    private User currentHolder;
    private String description;
    private String category;
    private boolean virtual; //add setter/getter
    private boolean tradable;
    private boolean sellable;
    private boolean rentable;
    private double sellPrice;
    private double rentPrice;


    public Item(String name, User owner, String description, String category, boolean virtual, boolean tradable, boolean sellable, boolean rentable,
                double sellPrice, double rentPrice){
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.currentHolder = null;
        this.category = category;
        this.sellPrice = sellPrice;
        this.rentPrice = rentPrice;
        this.rentable = rentable;
        this.sellable = sellable;
        this.tradable = tradable;
        this.virtual = virtual;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return this.owner;
    }

    public User getCurrentHolder() {
        return this.currentHolder;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setCurrentHolder(User holder) {
        this.currentHolder = holder;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory(){
    return category;
        }

    public boolean getVirtual(){
        return this.virtual;
    }

    public void changeTradable(boolean tradable){
        this.tradable = tradable;
    }

    public void changeSellable(boolean sellable){
        this.sellable = sellable;
    }

    public void changeRentable(boolean rentable){
        this.rentable = rentable;
    }

    public void setSellPrice(double price){
        this.sellPrice = price;
    }

    public void setRentPrice(double price){
    }

    public boolean getSellable(){return this.sellable;}
    public boolean getRentable(){return this.rentable;}
    public boolean getTradable(){return this.tradable;}
    public double getSellPrice(){return this.sellPrice;}
    public double getRentPrice(){return this.rentPrice;}
}
