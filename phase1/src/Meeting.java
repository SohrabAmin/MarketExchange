import java.util.Calendar;
import java.util.HashMap;

public class Meeting {

    private Calendar date;
    private String place;
    private Boolean confirmed = false;
    private int edits;
    private HashMap<String, Integer> editHistory;
    private String lastEdit;

    /**
     *
     * @param date date of Meeting
     * @param place place of Meeting
     */
    public Meeting(Calendar date, String place) {
        this.date = date;
        this.place = place;
        editHistory = new HashMap<>();

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
        return "Date: " + getDate().getTime().toString() + ", Place: " + place;
    }
}
