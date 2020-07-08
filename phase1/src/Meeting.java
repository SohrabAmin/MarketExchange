public class Meeting {

    private String date;
    private String time;
    private String place;
    private Boolean confirmed;
    private int edits;

    /**
     *
     * @param date date of Meeting
     * @param time time of Meeting
     * @param place place of Meeting
     */
    public Meeting(String date, String time, String place) {
        this.date = date;
        this.time = time;
        this.place = place;
    }

    /**
     * gets date for Meeting
     * @return this Meetings date
     */
    public String getDate() {
        return date;
    }

    /**
     * gets time for Meeting
     * @return this Meetings time
     */
    public String getTime() {
        return time;
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
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * sets time for Meeting
     * @param time time to be set for Meeting
     */
    public void setTime(String time) {
        this.time = time;
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
}
