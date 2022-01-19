package dfk.example.com.iworker.SQLite.SQLiteModel;

public class BackupLogsSQLite {
    private Integer id;
    private String date;
    private String mac_phone;
    private String startTime;
    private String endTime;

    public BackupLogsSQLite(Integer id, String date, String mac_phone, String startTime, String endTime) {
        this.id = id;
        this.date = date;
        this.mac_phone = mac_phone;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMac_phone() {
        return mac_phone;
    }

    public void setMac_phone(String mac_phone) {
        this.mac_phone = mac_phone;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
