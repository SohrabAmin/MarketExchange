import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Meeting {

    private Calendar date;
    private String place;
    private Boolean confirmed = false;
    private int edits;
    private HashMap<String, Integer> editHistory;
    private String lastEdit;
    private HashMap<String, Integer> confirm; //0 for not confirmed - 1 for confirmed that meeting took place

    /**
     *
     * @param date date of Meeting
     * @param place place of Meeting
     */
    public Meeting(Calendar date, String place) {
        this.date = date;
        this.place = place;
        editHistory = new HashMap<>();
        confirm = new HashMap<>();

    }
    public boolean confirmedByBothSides (){
        //Used https://stackoverflow.com/questions/27254302/counting-duplicate-values-in-hashmap as reference
        int counter = 0;
        Integer countingFor = 1;
        for(String key : confirm.keySet()) {            // iterate through all the keys in this HashMap
            if(confirm.get(key).equals(countingFor)) {  // if a key maps to the string you need, increment the counter
                counter++;
            }
        }
    if (counter == 2)
        return true;
    else
        return false;
    }

    public void initialconfirm (String side1, String side2){
        confirm.put(side1, 0);
        confirm.put(side2, 0);
    }

    public void meetingConfirmed (String name){
        confirm.replace(name, 1);
    }

    public Integer userconfirmed (String name){
        return confirm.get(name);
    }

    public String getOtherSide (String name){

    for ( String key : confirm.keySet() ) {
        if (!key.equals(name))
            return key;
    }
    return "error";
    }

    public void initialHistory (String name, int num ){
        editHistory.put(name, num);
    }

    public void changeLastEdit (String name){
        lastEdit = name;
    }

    public String viewLastEdit (){
        return lastEdit;
    }

    public void changeHistory (User user, int i){
        editHistory.replace(user.name, i);
    }

    public int geteditHistory (String user){
        return editHistory.get(user);
    }

    /**
     * gets date for Meeting
     * @return this Meetings date
     */
    public Calendar getDate() {
        return date;
    }

    /**
     * gets place of Meeting
     * @return this Meeting place
     */
    public String getPlace() {
        return place;
    }

    /**
     * gets confirmed value of Meeting
     * @return this Meetings confirmed value
     */
    public Boolean getConfirmed() {
        return confirmed;
    }

    /**
     * gets number of times meeting was edited
     * @return number of meeting edits
     */
    public int getEdits() {
        return edits;
    }

    /**
     * sets date for Meeting
     * @param date date to be set for Meeting
     */
    public void setDate(Calendar date) {
        this.date = date;
    }

    /**
     * sets place for Meeting
     * @param place place to be set for Meeting
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * sets value of confirmed to true
     */
    public void setConfirmedTrue() {
        this.confirmed = true;
    }

    /**
     * sets value of edits
     * @param edits new number of edits
     */
    public void setEdits(int edits) {
        this.edits = edits;
    }

    /**
     * Returns a string representation of this Meeting.
     * @return String of this Meetings date and place.
     */
    @Override
    public String toString() {

        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy hh:mma");
        return dateFormat.format(getDate().getTime()) + " at " + getPlace();
    }
}
