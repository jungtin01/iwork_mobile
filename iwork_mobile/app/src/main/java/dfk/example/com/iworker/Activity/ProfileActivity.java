package dfk.example.com.iworker.Activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import dfk.example.com.iworker.Api.ApiUtils;
import dfk.example.com.iworker.Api.UserClient;
import dfk.example.com.iworker.DelayedProgressDialog;
import dfk.example.com.iworker.Model.UserInfomation;
import dfk.example.com.iworker.Model.UserInfomationDetail;
import dfk.example.com.iworker.R;
import dfk.example.com.iworker.StaticData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private TextView tv_Name,tv_Position, tv_Email, tv_Rank, tv_Title, tv_Manager, tv_Team,tv_HRISRole, tv_MacPhone, tv_Language;
    private ExpandableRelativeLayout mExpandInfo, mExpandContract, mExpandPersonal;
    ImageView mExpandInfoImage, mExpandContractImage, mExpandPersonalImage;
    boolean checkInfoDrop, checkContractDrop, checkPersonalDrop;
    boolean isVietnamese = true;
    private long mLastClickTime = 0;
    UserClient userClient = ApiUtils.userClient();
    DelayedProgressDialog progressDialog = new DelayedProgressDialog();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //declare
        tv_Name = findViewById(R.id.tv_profile_name);
        tv_Position = findViewById(R.id.tv_profile_position);
        tv_Email = findViewById(R.id.tv_email_content);
        tv_Rank = findViewById(R.id.tv_rank_content);
        tv_Title = findViewById(R.id.tv_title_content);
        tv_Manager = findViewById(R.id.tv_manager_content);
        tv_Team = findViewById(R.id.tv_team_content);
        tv_HRISRole =findViewById(R.id.tv_hris_role_content);
        tv_MacPhone = findViewById(R.id.tv_profile_mac_phone);
        tv_Language = findViewById(R.id.tv_language_content);
        //action bar setting
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);
        //
        mExpandInfo = findViewById(R.id.expand_info);
        mExpandContract = findViewById(R.id.expand_contract_detail);
        mExpandPersonal = findViewById(R.id.expand_personal_info);
        //toggle to close the expand first
        mExpandInfo.toggle();
        mExpandContract.toggle();
        mExpandPersonal.toggle();
        //
        checkInfoDrop = false;
        checkContractDrop = false;
        checkPersonalDrop = false;
        //
        mExpandInfoImage = findViewById(R.id.iv_infomation);
        mExpandContractImage = findViewById(R.id.iv_contract_info);
        mExpandPersonalImage = findViewById(R.id.iv_personal_info);
        if(StaticData.PhoneMacAddress != ""){

            getUserInformation(StaticData.PhoneMacAddress);

        }
    }
    UserInfomationDetail userInfomationDetail;
    private void getUserInformation(String mac_phone) {
        progressDialog.show(getSupportFragmentManager(),"tag");
        Call<UserInfomation> call = userClient.getUserInformation(mac_phone);
        call.enqueue(new Callback<UserInfomation>() {
            @Override
            public void onResponse(Call<UserInfomation> call, Response<UserInfomation> response) {
                if (response.isSuccessful()) {
                    userInfomationDetail = new UserInfomationDetail();
                    userInfomationDetail = response.body().getUserInfomationDetail();
                    setDataToView();

                }
                progressDialog.cancel();
            }

            @Override
            public void onFailure(Call<UserInfomation> call, Throwable t) {
                progressDialog.cancel();
            }
        });
    }
    //TODO
    private void setDataToView() {
        tv_Name.setText(userInfomationDetail.getLastName()+" "+userInfomationDetail.getFirstName());
        tv_Title.setText(userInfomationDetail.getTitleName());
        tv_Position.setText(userInfomationDetail.getTitleName()+" | #"+userInfomationDetail.getPhone());
        tv_Rank.setText(userInfomationDetail.getRoleName());
        tv_MacPhone.setText(StaticData.PhoneMacAddress);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
                default:
                    break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickToExpandInfo(View view) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 500){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        mExpandInfo.toggle();
        if(checkInfoDrop == true){
            mExpandInfoImage.setImageResource(R.drawable.ic_dropdow_open);
            checkInfoDrop = false;
        }
        else {
            mExpandInfoImage.setImageResource(R.drawable.ic_dropdown_close);
            checkInfoDrop = true;
        }
    }

    public void clickToExpandContract(View view) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 500){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        mExpandContract.toggle();
        if(checkContractDrop == true){
            mExpandContractImage.setImageResource(R.drawable.ic_dropdow_open);
            checkContractDrop = false;
        }
        else {
            mExpandContractImage.setImageResource(R.drawable.ic_dropdown_close);
            checkContractDrop = true;
        }
    }

    public void clickToExpandPersonal(View view) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 500){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        mExpandPersonal.toggle();
        if(checkPersonalDrop == true){
            mExpandPersonalImage.setImageResource(R.drawable.ic_dropdow_open);
            checkPersonalDrop = false;
        }
        else {
            mExpandPersonalImage.setImageResource(R.drawable.ic_dropdown_close);
            checkPersonalDrop = true;
        }

    }

    public void clickToChangeLanguage(View view) {
        if(isVietnamese){
            tv_Language.setText("English");
            Toast.makeText(getApplicationContext(), "Language have been changed to English.", Toast.LENGTH_SHORT).show();
            isVietnamese = false;
        }
        else {
            tv_Language.setText("Tiếng Việt");
            Toast.makeText(getApplicationContext(), "Ngôn ngữ đã được chuyển sang tiếng Việt.", Toast.LENGTH_SHORT).show();
            isVietnamese = true;
        }
    }

    public void clickToCopyMacAddress(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("PhoneMacAddress",tv_MacPhone.getText());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Đã sao chép địa chỉ MAC: "+tv_MacPhone.getText(), Toast.LENGTH_SHORT).show();
    }
}
