package dfk.example.com.iworker.Model;

public class LeaveInfomation {
    int dateInLeave;
    String date, type, state;

    public int getDateInLeave() {
        return dateInLeave;
    }

    public void setDateInLeave(int dateInLeave) {
        this.dateInLeave = dateInLeave;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LeaveInfomation(int dateInLeave, String date, String type, String state) {
        this.dateInLeave = dateInLeave;
        this.date = date;
        this.type = type;
        this.state = state;
    }
}
