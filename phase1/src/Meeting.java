import java.util.Calendar;

public class Meeting {

    private Calendar date;
    private String place;
    private Boolean confirmed;
    private int edits;

    /**
     *
     * @param date date of Meeting
     * @param place place of Meeting
     */
    public Meeting(Calendar date, String place) {
        this.date = date;
        this.place = place;
    }

    /**
     * gets date for Meeting
     * @return this Meetings date
     */
    public String getDate() {
        return date.getTime().toString();
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

    @Override
    public String toString() {
        return "Date: " + getDate() + ", Place: " + place;
    }
}
