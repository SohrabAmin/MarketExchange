import java.io.Serializable;

public class Item implements Serializable {
    private String name;
    private final User owner;
    private User currentHolder;
    private String description;
    

    public Item(String a, User b, String description){
        this.name = a;
        this.owner = b;
        this.description = description;
        this.currentHolder = null;
    }
    public String getName(){return name;
    }

    public void setName(String x) {
        name = x;
    }

    public User getOwner(){
        return this.owner;
    }
    
    public String getDescription() {
        return description; 
    }
}
