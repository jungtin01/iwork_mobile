package dfk.example.com.iworker.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataReport implements Serializable {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("mac_address")
    @Expose
    private String macAddress;
    @SerializedName("day_work")
    @Expose
    private String dayWork;
    @SerializedName("time_work")
    @Expose
    private String timeWork;
    @SerializedName("time_retirement")
    @Expose
    private String timeRetirement;
    @SerializedName("start_time_work")
    @Expose
    private String startTimeWork;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getDayWork() {
        return dayWork;
    }

    public void setDayWork(String dayWork) {
        this.dayWork = dayWork;
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