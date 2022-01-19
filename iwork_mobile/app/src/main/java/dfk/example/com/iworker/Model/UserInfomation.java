package dfk.example.com.iworker.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfomation {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data_report")
    @Expose
    private UserInfomationDetail userInfomationDetail;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UserInfomationDetail getUserInfomationDetail() {
        return userInfomationDetail;
    }

    public void setDataReport(UserInfomationDetail userInfomationDetail) {
        this.userInfomationDetail = userInfomationDetail;
    }
}
