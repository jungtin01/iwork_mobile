package dfk.example.com.iworker.Api;

import java.util.List;

import dfk.example.com.iworker.Model.Report;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WorkTimeClient {
    @FormUrlEncoded
    @POST("/api/log/devices")
    Call<ResponseBody> writeLog(@Field("mac_address") String mac_address, @Field("mac_device") String mac_devices);

    @FormUrlEncoded
    @POST("/api/user/reports")
    Call<Report> getReport(@Field("mac_address") String mac_address);

    @FormUrlEncoded
    @POST("/api/user/reports/date")
    Call<Report> getReportByDay(@Field("mac_address") String mac_address, @Field("date") String date);
}
