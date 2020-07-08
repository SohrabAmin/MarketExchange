public class Meeting {

    private String date;
    private String time;
    private String place;

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
     *
     * @return this Meetings date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @return this Meetings time
     */
    public String getTime() {
        return time;
    }

    /**
     *
     * @return this Meeting place
     */
    public String getPlace() {
        return place;
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
}
