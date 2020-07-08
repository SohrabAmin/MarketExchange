import java.io.Serializable;

public class Item implements Serializable {
    private String name;
    private final User owner;

    public Item(String a, User b){
        this.name = a;
        this.owner = b;
    }
    public String getName(){return name;
    }

    public void setName(String x) {
        name = x;
    }

    public User getOwner(){
        return this.owner;
    }
}
