package dfk.example.com.iworker.Model;

import java.util.Date;

public class BackupDataLogs {
    private Date time;
    private boolean networkStatus;

    public BackupDataLogs(Date time, boolean networkStatus) {
        this.time = time;
        this.networkStatus = networkStatus;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isNetworkStatus() {
        return networkStatus;
    }

    public void setNetworkStatus(boolean networkStatus) {
        this.networkStatus = networkStatus;
    }
}
