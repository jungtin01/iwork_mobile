package dfk.example.com.iworker.Api;

public class ApiUtils {
    public static final String BASE_URL = "http://hrm.dfksoft.com:3300";

    public static WorkTimeClient workTimeClient() {
        return RetrofitClient.getClient(BASE_URL).create(WorkTimeClient.class);
    }
    public static UserClient userClient(){
        return RetrofitClient.getClient(BASE_URL).create(UserClient.class);
    }

}
