import java.util.*;

public class OneWayMonetized extends OneWay {


    private final double cost;
    private int rentDuration;


    public OneWayMonetized(User user1, Item item, boolean temp, boolean virtual){
        super(user1, item, temp, virtual);
        if(this.getTemp()){
            this.cost = item.getRentPrice();
        }else{
            this.cost = item.getSellPrice();
        }
    }

    public double getCost(){
        return this.cost;
    }

    public void setRentDuration(int days){
        this.rentDuration = days;
    }

    public int getRentDuration(){
        return this.rentDuration;
    }
}
