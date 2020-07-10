import java.util.Calendar;

public class MeetingManager {

    /**
     * Creates a meeting object
     * @param date date of the Meeting to be created
     * @param time time of the Meeting to be created
     * @param place place of the Meeting to be created
     * @return Meeting object with date, time and place.
     */
    public Meeting createMeeting(String date, String time, String place) {
        return new Meeting(createDate(date, time), place);
    }

    /**
     * Creates a Calendar object from date string
     * @param date String version of date
     * @return Calendar version of date
     */
    private Calendar createDate(String date, String time) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt((date.split("-")[2])),
                Integer.parseInt((date.split("-")[1]))-1,
                Integer.parseInt((date.split("-")[0])),
                Integer.parseInt(time.split(":")[0]),
                Integer.parseInt(time.split(":")[1]));
        return cal;
    }

    /**
     * Edits the meetings date, time and place
     * @param meeting Meeting to be edited
     * @param date New date for meeting
     * @param time New time for meeting
     * @param place New place for meeting
     */
    public void editMeeting(Meeting meeting, String date, String time, String place) {
        meeting.setDate(createDate(date, time));
        meeting.setPlace(place);
        meeting.setEdits(meeting.getEdits()+1);
    }

    /**
     * confirms meeting
     * @param meeting meeting to be confirmed
     */
    public void confirmMeeting(Meeting meeting) {
        meeting.setConfirmedTrue();
    }
}
