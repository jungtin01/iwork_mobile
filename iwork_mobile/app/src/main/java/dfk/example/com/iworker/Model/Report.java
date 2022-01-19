package dfk.example.com.iworker.Model;

import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Report {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data_report")
    @Expose
    private ArrayList<DataReport> dataReport = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ArrayList<DataReport> getDataReport() {
        return dataReport;
    }

    public void setDataReport(ArrayList<DataReport> dataReport) {
        this.dataReport = dataReport;
    }

}