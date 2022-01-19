package dfk.example.com.iworker.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import dfk.example.com.iworker.R;

public class GeneralActivity extends AppCompatActivity {
    private boolean isWeekExpanded, isMonthExpanded, isYearExpanded;
    private ExpandableRelativeLayout mExpandWeek, mExpandMonth, mExpandYear;
    private ImageView mExpandWeekImage, mExpandMonthImage, mExpandYearImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tổng quát");
        //boolean expand
        isWeekExpanded = true;
        isMonthExpanded = true;
        isYearExpanded = true;
        //declare expand
        mExpandWeek = findViewById(R.id.expand_week);
        mExpandMonth = findViewById(R.id.expand_month);
        mExpandYear = findViewById(R.id.expand_year);
        //declare arrow image
        mExpandWeekImage = findViewById(R.id.iv_general_week_dropdown);
        mExpandMonthImage = findViewById(R.id.iv_general_month_dropdown);
        mExpandYearImage = findViewById(R.id.iv_general_year_dropdown);
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

    public void clickToExpandWeekInfo(View view) {

    }

    public void clickToExpandMonthInfo(View view) {
    }

    public void clickToExpandYearInfo(View view) {
    }
}
