package meetings;

import accounts.users.User;

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
     * @param date  date of meetings.Meeting
     * @param place place of meetings.Meeting
     */
    public Meeting(Calendar date, String place) {
        this.date = date;
        this.place = place;
        editHistory = new HashMap<>();
        confirm = new HashMap<>();
    }

    /**
     * Checks if both accounts.users have confimred the meetings.Meeting
     *
     * @return True if both accounts.users have confirmed meetings.Meeting, false otherwise.
     */
    public boolean confirmedByBothSides() {
        //Used https://stackoverflow.com/questions/27254302/counting-duplicate-values-in-hashmap as reference
        int counter = 0;
        Integer countingFor = 1;
        for (String key : confirm.keySet()) {            // iterate through all the keys in this HashMap
            if (confirm.get(key).equals(countingFor)) {  // if a key maps to the string you need, increment the counter
                counter++;
            }
        }
        return counter == 2 || counter == 3;
    }

    public boolean confirmByThreeSides(){

        //Used https://stackoverflow.com/questions/27254302/counting-duplicate-values-in-hashmap as reference
        int counter = 0;
        Integer countingFor = 1;
        for (String key : confirm.keySet()) {            // iterate through all the keys in this HashMap
            if (confirm.get(key).equals(countingFor)) {  // if a key maps to the string you need, increment the counter
                counter++;
            }
        }
        return counter == 3;


    }

    /**
     * Set confirmation values of both accounts.users to 0
     *
     * @param side1 name of first user
     * @param side2 name of second user
     */
    public void initialconfirm(String side1, String side2) {
        confirm.put(side1, 0);
        confirm.put(side2, 0);
    }


    public void initial3confirm(String side1, String side2, String side3) {
        confirm.put(side1, 0);
        confirm.put(side2, 0);
        confirm.put(side3, 0);
    }

    /**
     * Confirms accounts.users confirmation value of a meetings.Meeting
     *
     * @param name name of user
     */
    public void meetingConfirmed(String name) {
        confirm.replace(name, 1);
    }



    /**
     * Gets accounts.users confirmation value of a meetings.Meeting
     *
     * @param name name of user
     * @return Integer representing if user confirmed meetings.Meeting
     */
    public Integer userconfirmed(String name) {
        return confirm.get(name);
    }

    /**
     * Get name of other user in meetings.Meeting
     *
     * @param name name of original user in meetings.Meeting
     * @return name of other user in meetings.Meeting
     */
    public String getOtherSide(String name) {
        for (String key : confirm.keySet()) {
            if (!key.equals(name))
                return key;
        }
        return "error";
    }

    /**
     * Puts a key-value pair in edit history hashmap
     *
     * @param name key to be put in
     * @param num  value to be put in
     */
    public void initialHistory(String name, int num) {
        editHistory.put(name, num);
    }

    /**
     * Change last user who edited meetings.Meeting
     *
     * @param name name of user who last edited meetings.Meeting
     */
    public void changeLastEdit(String name) {
        lastEdit = name;
    }

    /**
     * Get name of user who last edited meetings.Meeting
     *
     * @return Name of user who edited meetings.Meeting last
     */
    public String viewLastEdit() {
        return lastEdit;
    }

    /**
     * Edits the number edits of of a user
     *
     * @param user User who edited the meeting
     * @param i    New number of edits associated with user
     */
    public void changeHistory(User user, int i) {
        editHistory.replace(user.getName(), i);
    }

    /**
     * gets accounts.users edit history for meetings.Meeting
     *
     * @param user User who edited meeting
     * @return number of times user has edited the meeting
     */
    public int geteditHistory(String user) {
        return editHistory.get(user);
    }

    /**
     * gets date for meetings.Meeting
     *
     * @return this Meetings date
     */
    public Calendar getDate() {
        return date;
    }

    /**
     * gets place of meetings.Meeting
     *
     * @return this meetings.Meeting place
     */
    public String getPlace() {
        return place;
    }

    /**
     * gets confirmed value of meetings.Meeting
     *
     * @return this Meetings confirmed value
     */
    public Boolean getConfirmed() {
        return confirmed;
    }

    /**
     * gets number of times meeting was edited
     *
     * @return number of meeting edits
     */
    public int getEdits() {
        return edits;
    }

    /**
     * sets date for meetings.Meeting
     *
     * @param date date to be set for meetings.Meeting
     */
    public void setDate(Calendar date) {
        this.date = date;
    }

    /**
     * sets place for meetings.Meeting
     *
     * @param place place to be set for meetings.Meeting
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
     *
     * @param edits new number of edits
     */
    public void setEdits(int edits) {
        this.edits = edits;
    }

    /**
     * Returns a string representation of this meetings.Meeting.
     *
     * @return String of this Meetings date and place.
     */
    @Override
    public String toString() {

        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy hh:mma");
        return dateFormat.format(getDate().getTime()) + " at " + getPlace();
    }
}
