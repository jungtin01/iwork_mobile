package dfk.example.com.iworker.Model;

public class NotificationInfo {
    private String object, date, state, timeRecive;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTimeRecive() {
        return timeRecive;
    }

    public void setTimeRecive(String timeRecive) {
        this.timeRecive = timeRecive;
    }

    public NotificationInfo(String object, String date, String state, String timeRecive) {
        this.object = object;
        this.date = date;
        this.state = state;
        this.timeRecive = timeRecive;
    }
}
