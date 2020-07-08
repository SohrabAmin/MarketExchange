import java.io.Serializable;

public class Item implements Serializable {
    private String Name;

    public String getName(){return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
