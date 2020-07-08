import java.util.List;

public class TwoWay extends Transaction {


    User firstTrader;

    User secondTrader;

    List<Item> items;


    public TwoWay(Item item1, Item item2, boolean temp) {
        this.temp = temp;
        this.firstTrader = item1.getOwner();
        this.secondTrader = item2.getOwner();
        this.items.add(item1);
        this.items.add(item2);

    }

    public User getFirstTrader(){
        return this.firstTrader;
    }

    public User getSecondTrader() {
        return this.secondTrader;
    }

    public Item getFirstItem(){
        return this.items.get(0);
    }

    public Item getSecondItem() {
        return this.items.get(1);
    }
}
