import java.io.Serializable;

public class Item implements Serializable {
    private String name;
    private User owner;
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

    public User getCurrentHolder(){
        return this.currentHolder;
    }

    public void setOwner(User x){
        this.owner = x;
    }

    public void setCurrentHolder(User x){
        this.currentHolder = x;
    }

    public void updateDescription(String s){
        this.description = s;
    }
    public String getDescription() {
        return description; 
    }
}
