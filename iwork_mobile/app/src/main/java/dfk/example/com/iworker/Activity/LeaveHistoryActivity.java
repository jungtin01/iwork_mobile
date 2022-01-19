package dfk.example.com.iworker.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dfk.example.com.iworker.R;

public class LeaveHistoryActivity extends AppCompatActivity {
    //ImageView imageFilter;
    RelativeLayout rlFilter;
    LinearLayout llLeaveHistory,llLeaveFilterAction,llLeaveHistorySearch;
    TextView tvYear, tvBu,tvNoSalary,tvSick,tvWithSalary,tvException;
    boolean yearSelected = false;
    boolean buSelected = false;
    boolean noSalarySelected = false;
    boolean sickSelected = false;
    boolean withSalarySelected = false;
    boolean exceptionSelected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_history);
        //action bar setting
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lịch sử nghỉ phép");
        getSupportActionBar().setElevation(0);
        //declare
        llLeaveFilterAction = findViewById(R.id.ll_leave_filter_action);
        rlFilter = findViewById(R.id.ll_leave_filter);
        llLeaveHistory = findViewById(R.id.ll_leave_history);
        llLeaveHistorySearch = findViewById(R.id.ll_leave_history_search);
        tvYear = findViewById(R.id.tv_leave_filter_year);
        tvBu = findViewById(R.id.tv_leave_filter_bu);
        tvNoSalary = findViewById(R.id.tv_leave_filter_no_salary);
        tvSick = findViewById(R.id.tv_leave_filter_sick);
        tvWithSalary = findViewById(R.id.tv_leave_filter_with_salary);
        tvException = findViewById(R.id.tv_leave_filter_exception);
        //
        llLeaveFilterAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlFilter.setVisibility(View.VISIBLE);
                llLeaveHistory.setBackgroundResource(R.color.dimColor);
                llLeaveHistorySearch.setBackgroundResource(R.color.dimColor);
            }
        });
        llLeaveHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlFilter.setVisibility(View.GONE);
                llLeaveHistory.setBackgroundResource(R.color.appBackgroundColor);
                llLeaveHistorySearch.setBackgroundResource(R.color.appColor);
            }
        });
        //set on select
        tvYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(yearSelected == false){
                    tvYear.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                    yearSelected = true;
                }
                else {
                    tvYear.setBackgroundResource(R.drawable.border_frame_gray_background_no_border);
                    yearSelected = false;
                }
            }
        });

        tvBu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buSelected == false){
                    tvBu.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                    buSelected = true;
                }
                else {
                    tvBu.setBackgroundResource(R.drawable.border_frame_gray_background_no_border);
                    buSelected = false;
                }
            }
        });

        tvNoSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noSalarySelected == false){
                    tvNoSalary.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                    noSalarySelected = true;
                }
                else {
                    tvNoSalary.setBackgroundResource(R.drawable.border_frame_gray_background_no_border);
                    noSalarySelected = false;
                }
            }
        });

        tvSick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sickSelected == false){
                    tvSick.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                    sickSelected = true;
                }
                else {
                    tvSick.setBackgroundResource(R.drawable.border_frame_gray_background_no_border);
                    sickSelected = false;
                }
            }
        });

        tvWithSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(withSalarySelected == false){
                    tvWithSalary.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                    withSalarySelected = true;
                }
                else {
                    tvWithSalary.setBackgroundResource(R.drawable.border_frame_gray_background_no_border);
                    withSalarySelected = false;
                }
            }
        });

        tvException.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exceptionSelected == false){
                    tvException.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                    exceptionSelected = true;
                }
                else {
                    tvException.setBackgroundResource(R.drawable.border_frame_gray_background_no_border);
                    exceptionSelected = false;
                }
            }
        });

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
}
