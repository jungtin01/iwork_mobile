package dfk.example.com.iworker.Model;

import java.util.Date;
import java.util.List;

public class BackupLogs {
    private String type;
    private String mac_wifi;
    private String subType;
    private String mac_phone;


    private Date startTime, endTime;
    private List<BackupDataLogs> dataLogList;

    public BackupLogs(String type, String subType, String mac_wifi, String mac_phone, Date startTime, Date endTime, List<BackupDataLogs> dataLogList) {
        this.type = type;
        this.mac_wifi = mac_wifi;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dataLogList = dataLogList;
        this.subType = subType;
        this.mac_phone = mac_phone;
    }

    public String getMac_phone() {
        return mac_phone;
    }

    public void setMac_phone(String mac_phone) {
        this.mac_phone = mac_phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }
    public String getMac_wifi() {
        return mac_wifi;
    }

    public void setMac_wifi(String mac_wifi) {
        this.mac_wifi = mac_wifi;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<BackupDataLogs> getDataLogList() {
        return dataLogList;
    }

    public void setDataLogList(List<BackupDataLogs> dataLogList) {
        this.dataLogList = dataLogList;
    }
}
