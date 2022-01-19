package dfk.example.com.iworker.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import dfk.example.com.iworker.R;

public class LeaveFormActivity extends AppCompatActivity {
    private Spinner mLeaveTypeSpinner, mSubLeaveTypeSpinner;
    String[] spinnerArr, subSpinnerArr;
    private Calendar calendar = Calendar.getInstance();
    private TextView mSubLeaveType;
    private EditText mLeaveDate;
    int from_year, from_date, from_month, to_year, to_date, to_month;
    DatePickerDialog.OnDateSetListener from_dateListener, to_dateListener;
    private static final int FROM_DATE_PICKER = 101;
    private static final int TO_DATE_PICKER = 102;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_form);
        //action bar setting
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        //spinner
        mLeaveTypeSpinner = findViewById(R.id.spinner_leave_type);
        spinnerArr = new String[]{"Nghỉ phép năm","Nghỉ bệnh","Nghỉ bù", "Nghỉ có lương", "Nghỉ không lương"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,spinnerArr);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLeaveTypeSpinner.setAdapter(arrayAdapter);
        mLeaveTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("Nghỉ có lương")){
                    mSubLeaveType.setVisibility(View.VISIBLE);
                    mSubLeaveTypeSpinner.setVisibility(View.VISIBLE);

                }
                else {
                    mSubLeaveType.setVisibility(View.GONE);
                    mSubLeaveTypeSpinner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSubLeaveType.setVisibility(View.GONE);
                mSubLeaveTypeSpinner.setVisibility(View.GONE);
            }
        });


        //sub spinner
        mSubLeaveType = findViewById(R.id.tv_leave_sub_type);
        mSubLeaveTypeSpinner = findViewById(R.id.spinner_sub_leave_type);
        subSpinnerArr = new String[]{"Nghỉ kết hôn","Nghỉ con kết hôn","Nghỉ thai sản", "Nghỉ tang"};
        ArrayAdapter<String> subArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,subSpinnerArr);
        subArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSubLeaveTypeSpinner.setAdapter(subArrayAdapter);

        //
        mLeaveDate = findViewById(R.id.et_leave_date);
        //

        from_dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                from_date = dayOfMonth;
                from_month = month+1;
                from_year = year;
                updateFromDate();
            }
        };

        to_dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                to_date = dayOfMonth;
                to_month = month+1;
                to_year = year;
                updateToDate();
            }
        };


    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id){
            case FROM_DATE_PICKER:
            return new DatePickerDialog(this, from_dateListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
            case TO_DATE_PICKER:
                return new DatePickerDialog(this, to_dateListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        }
        return null;
    }

    //action bar on back
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

    public void clickToChooseDate(View view) {
        showDialog(FROM_DATE_PICKER);



    }
    boolean checkFromDateDone = false;
    String fromDate, toDate;
    private void updateFromDate(){
        fromDate = from_date +"/"+from_month+"/"+from_year;
        //String myFormat = "dd/MM/yyyy";
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        ////from = "";
        //fromDate = simpleDateFormat.format(fromDate);
        showDialog(TO_DATE_PICKER);
    }
    private void updateToDate(){
        toDate = to_date +"/"+to_month+"/"+to_year;
        //String myFormat = "dd/MM/yyyy";
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        ////to = "";
        //toDate = simpleDateFormat.format(toDate);
        //mLeaveDate.setText("");
        mLeaveDate.setText(fromDate + " - "+toDate);

    }
}
