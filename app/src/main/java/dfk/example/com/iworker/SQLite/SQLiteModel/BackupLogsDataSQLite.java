package dfk.example.com.iworker.SQLite.SQLiteModel;

public class BackupLogsDataSQLite {
    private int id;
    private String time_event;
    private String log_name;

    public BackupLogsDataSQLite(int id, String time_event, String log_name) {
        this.id = id;
        this.time_event = time_event;
        this.log_name = log_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime_event() {
        return time_event;
    }

    public void setTime_event(String time_event) {
        this.time_event = time_event;
    }

    public String getLog_name() {
        return log_name;
    }

    public void setLog_name(String log_name) {
        this.log_name = log_name;
    }
}
