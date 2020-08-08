import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AvailabilityChart {

    private HashMap <Integer, Boolean> chart;


  public AvailabilityChart (){
        chart = new HashMap<>();



        //initializing the chart so that the person is completely unavailable
        for (int i = 1; i < 8; i++)
        chart.put((Integer) i,false);

    }

    public void editChart (Integer day, boolean available){
      chart.replace(day, available);
    }

    public HashMap <Integer, Boolean> getChart(){
      return chart;
    }



}
