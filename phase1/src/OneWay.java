
public class OneWay extends Transaction {

    private final User borrower;

    private final User lender;

    private final Item item;


    public OneWay(User borrower, Item item, boolean temp) {
        super(temp);
        this.borrower = borrower;
        this.lender = item.getOwner();
        this.item = item;
    }

    public User getBorrower(){
        return this.borrower;
    }

    public User getLender() {
        return this.lender;
    }

    public Item getLenderItem(){
        return this.item;
    }

}