package dfk.example.com.iworker.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dfk.example.com.iworker.Activity.GeneralActivity;
import dfk.example.com.iworker.Api.ApiUtils;
import dfk.example.com.iworker.Api.WorkTimeClient;
import dfk.example.com.iworker.DelayedProgressDialog;
import dfk.example.com.iworker.Model.DataReport;
import dfk.example.com.iworker.Model.Report;
import dfk.example.com.iworker.R;
import dfk.example.com.iworker.SQLite.DBManager;
import dfk.example.com.iworker.SQLite.SQLiteModel.ReportSQLite;
import dfk.example.com.iworker.StaticData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportFragment extends Fragment {
    private TextView tv_DayWork, tv_TimeWork, tv_TimeRetirement, tv_StartTimeWork;
    private CompactCalendarView compactCalendarView;
    private FrameLayout warningFrame, timeInWorkFrame;
    private LinearLayout leaveLinear, dropdownLinear;
    boolean isDropdowned = false;
    private ImageView mDropDownImage;
    private Button mGenerality;
    private TextView mMonthNow;
    WorkTimeClient workTimeClient = ApiUtils.workTimeClient();
    DataReport dataReport;
    String dayWork = "";
    List<DataReport> reportListFromServer;
    List<ReportSQLite> reportListFromSQLite;
    DelayedProgressDialog progressDialog = new DelayedProgressDialog();
    DBManager dbManager;
    ReportSQLite reportByDay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        //declare
        warningFrame = view.findViewById(R.id.frame_report_warning);
        timeInWorkFrame = view.findViewById(R.id.frame_report_time_in_work);
        leaveLinear = view.findViewById(R.id.ll_report_leave);
        dropdownLinear = view.findViewById(R.id.ll_report_dropdown);
        mDropDownImage = view.findViewById(R.id.iv_report_arrow);
        mGenerality = view.findViewById(R.id.btn_report_tong_quat);
        mMonthNow = view.findViewById(R.id.tv_report_month_now);
        tv_DayWork = view.findViewById(R.id.tv_report_date_work);
        tv_TimeWork = view.findViewById(R.id.tv_report_work_time);
        tv_StartTimeWork = view.findViewById(R.id.tv_report_start_time);
        tv_TimeRetirement = view.findViewById(R.id.tv_report_break_time);

        //
        leaveLinear.setVisibility(View.GONE);
        warningFrame.setVisibility(View.GONE);
        //calendar
        compactCalendarView = view.findViewById(R.id.compact_calendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        Event ev1 = new Event(Color.RED, 1556866038000L);
        Event ev2 = new Event(Color.RED, 1556952438000L);
        Event ev3 = new Event(Color.RED, 1557384438000L);
        compactCalendarView.addEvent(ev1);
        compactCalendarView.addEvent(ev2);
        compactCalendarView.addEvent(ev3);
        //declare progress dialog


        //get Report From Server
        reportListFromServer = new ArrayList<>();
        reportListFromSQLite = new ArrayList<>();
        dbManager = new DBManager(getContext());
        Call<Report> callGetReport = workTimeClient.getReport(StaticData.PhoneMacAddress);
        callGetReport.enqueue(new Callback<Report>() {
            @Override
            public void onResponse(Call<Report> call, Response<Report> response) {
                if (response.isSuccessful()) {
                    reportListFromServer = response.body().getDataReport();
                    //get Report From SQLite

                    reportListFromSQLite = dbManager.getAllReport();
                    int calculate = reportListFromServer.size() - reportListFromSQLite.size();
                    if (calculate > 0) {
                        for (int i = reportListFromSQLite.size(); i < reportListFromServer.size(); i++) {
                            dbManager.addReport(reportListFromServer.get(i).getDayWork(),reportListFromServer.get(i).getTimeWork(),reportListFromServer.get(i).getTimeRetirement(),reportListFromServer.get(i).getStartTimeWork() );
                        }
                    }
                    reportListFromSQLite = dbManager.getAllReport();
                } else {

                }
            }

            @Override
            public void onFailure(Call<Report> call, Throwable t) {

            }
        });





        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                //progressDialog.show(getFragmentManager(), "");
                //dataReport = new DataReport();
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("EEE yyyy/MM/dd");
                dayWork = simpleDateFormat1.format(dateClicked);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formatted = simpleDateFormat.format(dateClicked);
                reportByDay = dbManager.getReportByDay(formatted);
                if(reportByDay != null){
                    setDataToView();
                    timeInWorkFrame.setVisibility(View.VISIBLE);
                }
                else {
                    timeInWorkFrame.setVisibility(View.GONE);
                }

                //Call<Report> call = workTimeClient.getReportByDay(StaticData.PhoneMacAddress, formatted);
                //call.enqueue(new Callback<Report>() {
                //    @Override
                //    public void onResponse(Call<Report> call, Response<Report> response) {
                //        if (response.isSuccessful()) {
                //            if (response.body().getDataReport().size() > 0) {
                //                dataReport = response.body().getDataReport().get(0);
                //                timeInWorkFrame.setVisibility(View.VISIBLE);
                //                setDataToView();
                //            } else {
                //                timeInWorkFrame.setVisibility(View.GONE);
                //            }
                //            progressDialog.cancel();
                //        }
                //    }
                //    @Override
                //    public void onFailure(Call<Report> call, Throwable t) {
                //        progressDialog.cancel();
                //    }
                //});
                if (dateClicked.toString().compareTo("Fri May 03 00:00:00 GMT+07:00 2019") == 0) {
                    warningFrame.setVisibility(View.GONE);
                    //timeInWorkFrame.setVisibility(View.VISIBLE);
                    leaveLinear.setVisibility(View.VISIBLE);
                } else if (dateClicked.toString().compareTo("Sat May 04 00:00:00 GMT+07:00 2019") == 0) {
                    warningFrame.setVisibility(View.VISIBLE);
                    //timeInWorkFrame.setVisibility(View.VISIBLE);
                    leaveLinear.setVisibility(View.GONE);
                } else if (dateClicked.toString().compareTo("Thu May 09 00:00:00 GMT+07:00 2019") == 0) {
                    warningFrame.setVisibility(View.GONE);
                    //timeInWorkFrame.setVisibility(View.VISIBLE);
                    leaveLinear.setVisibility(View.GONE);
                }
            }


            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                String month = firstDayOfNewMonth.toString().substring(4, 7);
                //mMonthNow.setText(month);
                switch (month) {
                    case "Jan":
                        mMonthNow.setText("Tháng: 1");
                        break;
                    case "Feb":
                        mMonthNow.setText("Tháng: 2");
                        break;
                    case "Mar":
                        mMonthNow.setText("Tháng: 3");
                        break;
                    case "Apr":
                        mMonthNow.setText("Tháng: 4");
                        break;
                    case "May":
                        mMonthNow.setText("Tháng: 5");
                        break;
                    case "Jun":
                        mMonthNow.setText("Tháng: 6");
                        break;
                    case "Jul":
                        mMonthNow.setText("Tháng: 7");
                        break;
                    case "Aug":
                        mMonthNow.setText("Tháng: 8");
                        break;
                    case "Sep":
                        mMonthNow.setText("Tháng: 9");
                        break;
                    case "Oct":
                        mMonthNow.setText("Tháng: 10");
                        break;
                    case "Nov":
                        mMonthNow.setText("Tháng: 11");
                        break;
                    case "Dec":
                        mMonthNow.setText("Tháng: 12");
                        break;
                    default:
                        break;
                }
            }
        });
        //calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
        //    @Override
        //    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        //        if (dayOfMonth == 3) {
        //            warningFrame.setVisibility(View.GONE);
        //            timeInWorkFrame.setVisibility(View.VISIBLE);
        //            leaveLinear.setVisibility(View.VISIBLE);
        //        }
        //        else if(dayOfMonth == 4){
        //            warningFrame.setVisibility(View.VISIBLE);
        //            timeInWorkFrame.setVisibility(View.VISIBLE);
        //            leaveLinear.setVisibility(View.GONE);
        //        }
        //        else if(dayOfMonth == 8){
        //            warningFrame.setVisibility(View.GONE);
        //            timeInWorkFrame.setVisibility(View.VISIBLE);
        //            leaveLinear.setVisibility(View.GONE);
        //        }
        //    }
        //});
        //
        String[] monthName = {"Tháng: 1", "Tháng: 2",
                "Tháng: 3", "Tháng: 4", "Tháng: 5", "Tháng: 6", "Tháng: 7",
                "Tháng: 8", "Tháng: 9", "Tháng: 10", "Tháng: 11",
                "Tháng: 12"};

        Calendar cal = Calendar.getInstance();
        String month = monthName[cal.get(Calendar.MONTH)];
        mMonthNow.setText(month);
        //dropdown
        compactCalendarView.setVisibility(View.GONE);
        dropdownLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDropdowned == true) {
                    isDropdowned = false;
                    compactCalendarView.setVisibility(View.GONE);
                    mDropDownImage.setImageResource(R.drawable.ic_dropdow_open);
                } else {
                    isDropdowned = true;
                    compactCalendarView.setVisibility(View.VISIBLE);
                    mDropDownImage.setImageResource(R.drawable.ic_dropdown_close);
                }
            }
        });
        //generality
        mGenerality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), GeneralActivity.class));
            }
        });

        return view;
    }

    private void setDataToView() {
        tv_StartTimeWork.setText("Vào làm lúc: " + reportByDay.getStart_time().substring(0,5));
        tv_TimeRetirement.setText(reportByDay.getTime_retirement().substring(0,5));
        tv_TimeWork.setText(reportByDay.getTime_work().substring(0,5));
        tv_DayWork.setText(dayWork);
    }
}
