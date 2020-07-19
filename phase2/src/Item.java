import java.io.Serializable;

public class Item implements Serializable {
    private String name;
    private User owner;
    private User currentHolder;
    private String description;


    public Item(String name, User owner, String description) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.currentHolder = null;
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
}
