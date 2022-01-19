package dfk.example.com.iworker.Api;

import dfk.example.com.iworker.Model.UserInfomation;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserClient {
    @FormUrlEncoded
    @POST("/api/user/detail")
    Call<UserInfomation> getUserInformation(@Field("mac_address") String mac_phone);


}
