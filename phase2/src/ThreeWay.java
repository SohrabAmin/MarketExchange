import java.util.*;

public class ThreeWay extends Transaction{


    private User firstTrader;

    private User secondTrader;

    private User thirdTrader;

    private List<Item> items;


    public ThreeWay(Item item1, Item item2, Item item3, boolean temp, boolean virtual){
        super(temp, virtual);
        this.firstTrader = item1.getOwner();
        this.secondTrader = item2.getOwner();
        this.thirdTrader = item3.getOwner();
        this.items = new ArrayList<>();
        this.items.add(item1);
        this.items.add(item2);
        this.items.add(item3);
    }

    public User getFirstTrader() {
        return this.firstTrader;
    }

    /**
     * Setter for the secondTrader of a TwoWay; the User who accepts the TwoWay.
     *
     * @return The secondTrader; the User who accepts the TwoWay.
     */
    public User getSecondTrader() {
        return this.secondTrader;
    }


    public User getThirdTrader(){return this.thirdTrader;}

    /**
     * Getter for the Item the firstTrader is giving.
     *
     * @return The Item the firstTrader is giving.
     */
    public Item getFirstItem() {
        return this.items.get(0);
    }

    /**
     * Getter for the Item the secondTrader is giving.
     *
     * @return The Item the secondTrader is giving.
     */
    public Item getSecondItem() {
        return this.items.get(1);
    }

    public Item getThirdItem(){return this.items.get(2);}
}