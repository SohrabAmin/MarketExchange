public class Meeting {

    private String date;
    private String time;
    private String place;

    public Meeting(String date, String time, String place) {
        this.date = date;
        this.time = time;
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getPlace() {
        return place;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
