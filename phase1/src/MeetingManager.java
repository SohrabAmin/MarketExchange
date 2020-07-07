public class MeetingManager {

    /**
     *
     * @param date date of the Meeting to be created
     * @param time time of the Meeting to be created
     * @param place place of the Meeting to be created
     * @return Meeting object with date, time and place.
     */
    public Meeting createMeeting(String date, String time, String place) {
        return new Meeting(date, time, place);
    }

}
