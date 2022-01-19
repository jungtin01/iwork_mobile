package dfk.example.com.iworker.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WorkTimeInfo implements Serializable {

    @SerializedName("mac_address")
    @Expose
    private String macAddress;
    @SerializedName("date_work")
    @Expose
    private String dateWork;
    @SerializedName("time_work")
    @Expose
    private String timeWork;
    @SerializedName("time_retirement")
    @Expose
    private String timeRetirement;
    @SerializedName("start_time_work")
    @Expose
    private String startTimeWork;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getDateWork() {
        return dateWork;
    }

    public void setDateWork(String dateWork) {
        this.dateWork = dateWork;
    }

    public String getTimeWork() {
        return timeWork;
    }

    public void setTimeWork(String timeWork) {
        this.timeWork = timeWork;
    }

    public String getTimeRetirement() {
        return timeRetirement;
    }

    public void setTimeRetirement(String timeRetirement) {
        this.timeRetirement = timeRetirement;
    }

    public String getStartTimeWork() {
        return startTimeWork;
    }

    public void setStartTimeWork(String startTimeWork) {
        this.startTimeWork = startTimeWork;
    }

}