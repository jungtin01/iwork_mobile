package dfk.example.com.iworker.SQLite.SQLiteModel;

public class ReportSQLite {
    String date_work, time_work, time_retirement, start_time;

    public ReportSQLite(String date_work, String time_work, String time_retirement, String start_time) {
        this.date_work = date_work;
        this.time_work = time_work;
        this.time_retirement = time_retirement;
        this.start_time = start_time;
    }

    public String getDate_work() {
        return date_work;
    }

    public void setDate_work(String date_work) {
        this.date_work = date_work;
    }

    public String getTime_work() {
        return time_work;
    }

    public void setTime_work(String time_work) {
        this.time_work = time_work;
    }

    public String getTime_retirement() {
        return time_retirement;
    }

    public void setTime_retirement(String time_retirement) {
        this.time_retirement = time_retirement;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
}
