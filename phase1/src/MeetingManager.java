public class MeetingManager {

    /**
     * Creates a meeting object
     * @param date date of the Meeting to be created
     * @param time time of the Meeting to be created
     * @param place place of the Meeting to be created
     * @return Meeting object with date, time and place.
     */
    public Meeting createMeeting(String date, String time, String place) {
        return new Meeting(date, time, place);
    }

    /**
     * Edits the meetings date, time and place
     * @param meeting Meeting to be edited
     * @param date New date for meeting
     * @param time New time for meeting
     * @param place New place for meeting
     */
    public void editMeeting(Meeting meeting, String date, String time, String place) {
        meeting.setDate(date);
        meeting.setTime(time);
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
