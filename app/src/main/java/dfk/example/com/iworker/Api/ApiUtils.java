package dfk.example.com.iworker.Api;

import android.util.Log;

public class ApiUtils {
//    public static final String BASE_URL = "http://hrm.dfksoft.com:3300";
    public static final String BASE_URL = "http://128.199.255.218:8087";

    public static WorkTimeClient workTimeClient() {
        System.out.println(RetrofitClient.getClient(BASE_URL).create(WorkTimeClient.class));
        Log.d("Kietttttt", RetrofitClient.getClient(BASE_URL).create(WorkTimeClient.class).toString() + "");
        return RetrofitClient.getClient(BASE_URL).create(WorkTimeClient.class);
    }
    public static UserClient userClient(){
        Log.d("Kietttttt2211", RetrofitClient.getClient(BASE_URL).create(UserClient.class).toString() + "");
        return RetrofitClient.getClient(BASE_URL).create(UserClient.class);
    }

}
