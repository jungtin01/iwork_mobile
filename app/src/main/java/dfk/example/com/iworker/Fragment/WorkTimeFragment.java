package dfk.example.com.iworker.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dfk.example.com.iworker.Api.ApiUtils;
import dfk.example.com.iworker.Api.WorkTimeClient;
import dfk.example.com.iworker.DelayedProgressDialog;
import dfk.example.com.iworker.Model.DataReport;
import dfk.example.com.iworker.Model.Report;
import dfk.example.com.iworker.R;
import dfk.example.com.iworker.StaticData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkTimeFragment extends Fragment {
    private TextView tv_PreviousWeek, tv_ThisWeek, tv_TimeInWorkThisWeek, tv_DateWork, tv_StartTime, tv_RetireTime, tv_TimeWork, tv_TimeInWorkPreWeek;
    private FrameLayout frame_ThisWeek, frame_PreviousWeek;
    private ArrayList<DataReport> realTimeDataReportList;
    private ArrayList<DataReport> loadFirstReportList;
    private LinearLayout ll_ThisWeekBar2, ll_ThisWeekBar3, ll_ThisWeekBar4, ll_ThisWeekBar5, ll_ThisWeekBar6, ll_ThisWeekBar7, ll_ThisWeekBar8;
    private View v_ThisWeekTop2, v_ThisWeekTop3, v_ThisWeekTop4, v_ThisWeekTop5, v_ThisWeekTop6, v_ThisWeekTop7, v_ThisWeekTop8;
    private View v_ThisWeekBot2, v_ThisWeekBot3, v_ThisWeekBot4, v_ThisWeekBot5, v_ThisWeekBot6, v_ThisWeekBot7, v_ThisWeekBot8;
    private LinearLayout ll_PreWeekBar2, ll_PreWeekBar3, ll_PreWeekBar4, ll_PreWeekBar5, ll_PreWeekBar6, ll_PreWeekBar7, ll_PreWeekBar8;
    private View v_PreWeekTop2, v_PreWeekTop3, v_PreWeekTop4, v_PreWeekTop5, v_PreWeekTop6, v_PreWeekTop7, v_PreWeekTop8;
    private View v_PreWeekBot2, v_PreWeekBot3, v_PreWeekBot4, v_PreWeekBot5, v_PreWeekBot6, v_PreWeekBot7, v_PreWeekBot8;
    Date lastDate = new Date();
    DelayedProgressDialog progressDialog = new DelayedProgressDialog();
    WorkTimeClient workTimeClient = ApiUtils.workTimeClient();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_worktime, container, false);
        //declare
        tv_TimeInWorkThisWeek = view.findViewById(R.id.tv_worktime_time_in_work_week_content);
        tv_TimeInWorkPreWeek = view.findViewById(R.id.tv_worktime_time_in_work_previous_week_content);
        frame_ThisWeek = view.findViewById(R.id.frame_worktime_this_week);
        frame_PreviousWeek = view.findViewById(R.id.frame_worktime_previous_week);
        tv_PreviousWeek = view.findViewById(R.id.tv_view_previous_week);
        tv_ThisWeek = view.findViewById(R.id.tv_view_this_week);
        tv_DateWork = view.findViewById(R.id.tv_worktime_time_now);
        tv_StartTime = view.findViewById(R.id.tv_worktime_first_time_in_work);
        tv_RetireTime = view.findViewById(R.id.tv_worktime_break_time);
        tv_TimeWork = view.findViewById(R.id.tv_worktime_work_time);

        ll_ThisWeekBar2 = view.findViewById(R.id.ll_bar_chart_weightsum_2);
        ll_ThisWeekBar3 = view.findViewById(R.id.ll_bar_chart_weightsum_3);
        ll_ThisWeekBar4 = view.findViewById(R.id.ll_bar_chart_weightsum_4);
        ll_ThisWeekBar5 = view.findViewById(R.id.ll_bar_chart_weightsum_5);
        ll_ThisWeekBar6 = view.findViewById(R.id.ll_bar_chart_weightsum_6);
        ll_ThisWeekBar7 = view.findViewById(R.id.ll_bar_chart_weightsum_7);
        ll_ThisWeekBar8 = view.findViewById(R.id.ll_bar_chart_weightsum_8);

        ll_PreWeekBar2 = view.findViewById(R.id.ll_bar_chart_weightsum_2_previous_week);
        ll_PreWeekBar3 = view.findViewById(R.id.ll_bar_chart_weightsum_3_previous_week);
        ll_PreWeekBar4 = view.findViewById(R.id.ll_bar_chart_weightsum_4_previous_week);
        ll_PreWeekBar5 = view.findViewById(R.id.ll_bar_chart_weightsum_5_previous_week);
        ll_PreWeekBar6 = view.findViewById(R.id.ll_bar_chart_weightsum_6_previous_week);
        ll_PreWeekBar7 = view.findViewById(R.id.ll_bar_chart_weightsum_7_previous_week);
        ll_PreWeekBar8 = view.findViewById(R.id.ll_bar_chart_weightsum_8_previous_week);

        v_ThisWeekTop2 = view.findViewById(R.id.v_barchart_top_2);
        v_ThisWeekTop3 = view.findViewById(R.id.v_barchart_top_3);
        v_ThisWeekTop4 = view.findViewById(R.id.v_barchart_top_4);
        v_ThisWeekTop5 = view.findViewById(R.id.v_barchart_top_5);
        v_ThisWeekTop6 = view.findViewById(R.id.v_barchart_top_6);
        v_ThisWeekTop7 = view.findViewById(R.id.v_barchart_top_7);
        v_ThisWeekTop8 = view.findViewById(R.id.v_barchart_top_8);

        v_ThisWeekBot2 = view.findViewById(R.id.v_barchart_bottom_2);
        v_ThisWeekBot3 = view.findViewById(R.id.v_barchart_bottom_3);
        v_ThisWeekBot4 = view.findViewById(R.id.v_barchart_bottom_4);
        v_ThisWeekBot5 = view.findViewById(R.id.v_barchart_bottom_5);
        v_ThisWeekBot6 = view.findViewById(R.id.v_barchart_bottom_6);
        v_ThisWeekBot7 = view.findViewById(R.id.v_barchart_bottom_7);
        v_ThisWeekBot8 = view.findViewById(R.id.v_barchart_bottom_8);

        v_PreWeekTop2 = view.findViewById(R.id.v_barchart_top_2_previous_week);
        v_PreWeekTop3 = view.findViewById(R.id.v_barchart_top_3_previous_week);
        v_PreWeekTop4 = view.findViewById(R.id.v_barchart_top_4_previous_week);
        v_PreWeekTop5 = view.findViewById(R.id.v_barchart_top_5_previous_week);
        v_PreWeekTop6 = view.findViewById(R.id.v_barchart_top_6_previous_week);
        v_PreWeekTop7 = view.findViewById(R.id.v_barchart_top_7_previous_week);
        v_PreWeekTop8 = view.findViewById(R.id.v_barchart_top_8_previous_week);

        v_PreWeekBot2 = view.findViewById(R.id.v_barchart_bottom_2_previous_week);
        v_PreWeekBot3 = view.findViewById(R.id.v_barchart_bottom_3_previous_week);
        v_PreWeekBot4 = view.findViewById(R.id.v_barchart_bottom_4_previous_week);
        v_PreWeekBot5 = view.findViewById(R.id.v_barchart_bottom_5_previous_week);
        v_PreWeekBot6 = view.findViewById(R.id.v_barchart_bottom_6_previous_week);
        v_PreWeekBot7 = view.findViewById(R.id.v_barchart_bottom_7_previous_week);
        v_PreWeekBot8 = view.findViewById(R.id.v_barchart_bottom_8_previous_week);
        //
        realTimeDataReportList = new ArrayList<>();
        if(realTimeDataReportList.size()<1){
            //progressDialog.setCancelable(false);
            //progressDialog.show(getFragmentManager(),"work time");

        }

        //on Click
        tv_PreviousWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frame_PreviousWeek.setVisibility(View.VISIBLE);
                frame_ThisWeek.setVisibility(View.GONE);
            }
        });
        tv_ThisWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frame_PreviousWeek.setVisibility(View.GONE);
                frame_ThisWeek.setVisibility(View.VISIBLE);
            }
        });
        progressDialog.show(getFragmentManager(),"Work Time");
        Call<Report> callGetReport = workTimeClient.getReport(StaticData.PhoneMacAddress);
        callGetReport.enqueue(new Callback<Report>() {
            @Override
            public void onResponse(Call<Report> call, Response<Report> response) {

                if(response.isSuccessful()){
                    loadFirstReportList = response.body().getDataReport();
                    DataReport dataReportToDay;
                    if (loadFirstReportList.size() > 0) {
                        dataReportToDay = loadFirstReportList.get(loadFirstReportList.size() - 1);

                    } else {
                        dataReportToDay = loadFirstReportList.get(0);
                    }
                    String dateWorkBeforeFormat = dataReportToDay.getDayWork();

                    try {
                        lastDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateWorkBeforeFormat);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd/MM/yyyy");
                        String formatted = simpleDateFormat.format(lastDate);
                        dateWork = formatted;
                    } catch (ParseException e) {
                        dateWork = dateWorkBeforeFormat;
                        e.printStackTrace();
                    }

                    //SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                    //String parsed = format.format(dateWork);
                    startTime = "Vào làm lúc: " + dataReportToDay.getStartTimeWork().substring(0,1);

                    //calculate time




                    setData();
                    updateTimeWorkByDay();
                    progressDialog.cancel();
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<Report> call, Throwable t) {

            }
        });

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                mMessageReceiver, new IntentFilter("UpdateTimeWork"));
        setData();
        //TODO:
        return view;
    }




    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            //Bundle bundle = intent.getExtras();

            realTimeDataReportList = (ArrayList<DataReport>) intent.getSerializableExtra("ListTimeWork");
            DataReport realTimeDataReportToDay = new DataReport();
            if (realTimeDataReportList.size() > 0) {
                realTimeDataReportToDay = realTimeDataReportList.get(realTimeDataReportList.size() - 1);

            } else {
                realTimeDataReportToDay = realTimeDataReportList.get(0);
            }
            retireTime = realTimeDataReportToDay.getTimeRetirement().substring(0,1);
            //String timeWorkReceive = intent.getStringExtra("TimeWork");
            timeWork = realTimeDataReportToDay.getTimeWork().substring(0,1);
            tv_RetireTime.setText(retireTime);
            tv_TimeWork.setText(timeWork);
            try {
                Date checkDate = new SimpleDateFormat("yyyy-MM-dd").parse(realTimeDataReportToDay.getDayWork());
                Calendar c = Calendar.getInstance();
                c.setTime(checkDate);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                String checkTimeWork = timeWork;
                float hour = Integer.parseInt(checkTimeWork.substring(0, 1));
                float min = Integer.parseInt(checkTimeWork.substring(0, 1));
                float timePerDay = hour + (min / 60);
                switch (dayOfWeek) {
                    case 2:

                        if (timePerDay > 0) {
                            if (timePerDay > 4) {
                                v_ThisWeekBot2.setBackgroundResource(R.drawable.border_frame_green_background_green_border);
                                ll_ThisWeekBar2.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                            }
                            if (timePerDay < 4) {
                                v_ThisWeekBot2.setBackgroundResource(R.drawable.border_frame_red_background_red_border);
                                ll_ThisWeekBar2.setBackgroundResource(R.drawable.border_frame_while_background_red_border);
                            }
                            if(timePerDay<8){
                                v_ThisWeekBot2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, timePerDay));
                                v_ThisWeekTop2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8 - timePerDay));
                            }
                            else{
                                v_ThisWeekBot2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8));
                                v_ThisWeekTop2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0));
                            }

                        }

                        break;
                    case 3:
                        if (timePerDay > 0) {
                            if (timePerDay > 4) {
                                v_ThisWeekBot3.setBackgroundResource(R.drawable.border_frame_green_background_green_border);
                                ll_ThisWeekBar3.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                            }
                            if (timePerDay < 4) {
                                v_ThisWeekBot3.setBackgroundResource(R.drawable.border_frame_red_background_red_border);
                                ll_ThisWeekBar3.setBackgroundResource(R.drawable.border_frame_while_background_red_border);
                            }
                            if(timePerDay<8){
                                v_ThisWeekBot3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, timePerDay));
                                v_ThisWeekTop3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8 - timePerDay));
                            }
                            else{
                                v_ThisWeekBot3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8));
                                v_ThisWeekTop3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0));
                            }

                        }

                        break;
                    case 4:
                        if (timePerDay > 0) {
                            if (timePerDay > 4) {
                                v_ThisWeekBot4.setBackgroundResource(R.drawable.border_frame_green_background_green_border);
                                ll_ThisWeekBar4.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                            }
                            if (timePerDay < 4) {
                                v_ThisWeekBot4.setBackgroundResource(R.drawable.border_frame_red_background_red_border);
                                ll_ThisWeekBar4.setBackgroundResource(R.drawable.border_frame_while_background_red_border);
                            }
                            if(timePerDay<8){
                                v_ThisWeekBot4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, timePerDay));
                                v_ThisWeekTop4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8 - timePerDay));
                            }
                            else{
                                v_ThisWeekBot4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8));
                                v_ThisWeekTop4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0));
                            }

                        }
                        break;
                    case 5:
                        if (timePerDay > 0) {
                            if (timePerDay > 4) {
                                v_ThisWeekBot5.setBackgroundResource(R.drawable.border_frame_green_background_green_border);
                                ll_ThisWeekBar5.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                            }
                            if (timePerDay < 4) {
                                v_ThisWeekBot5.setBackgroundResource(R.drawable.border_frame_red_background_red_border);
                                ll_ThisWeekBar5.setBackgroundResource(R.drawable.border_frame_while_background_red_border);
                            }
                            if(timePerDay<8){
                                v_ThisWeekBot5.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, timePerDay));
                                v_ThisWeekTop5.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8 - timePerDay));
                            }
                            else{
                                v_ThisWeekBot5.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8));
                                v_ThisWeekTop5.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0));
                            }

                        }
                        break;
                    case 6:
                        if (timePerDay > 0) {
                            if (timePerDay > 4) {
                                v_ThisWeekBot6.setBackgroundResource(R.drawable.border_frame_green_background_green_border);
                                ll_ThisWeekBar6.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                            }
                            if (timePerDay < 4) {
                                v_ThisWeekBot6.setBackgroundResource(R.drawable.border_frame_red_background_red_border);
                                ll_ThisWeekBar6.setBackgroundResource(R.drawable.border_frame_while_background_red_border);
                            }
                            if(timePerDay<8){
                                v_ThisWeekBot6.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, timePerDay));
                                v_ThisWeekTop6.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8 - timePerDay));
                            }
                            else{
                                v_ThisWeekBot6.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8));
                                v_ThisWeekTop6.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0));
                            }

                        }
                        break;
                    case 7:
                        if (timePerDay > 0) {
                            if (timePerDay > 4) {
                                v_ThisWeekBot7.setBackgroundResource(R.drawable.border_frame_green_background_green_border);
                                ll_ThisWeekBar7.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                            }
                            if (timePerDay < 4) {
                                v_ThisWeekBot7.setBackgroundResource(R.drawable.border_frame_red_background_red_border);
                                ll_ThisWeekBar7.setBackgroundResource(R.drawable.border_frame_while_background_red_border);
                            }
                            if(timePerDay<8){
                                v_ThisWeekBot7.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, timePerDay));
                                v_ThisWeekTop7.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8 - timePerDay));
                            }
                            else{
                                v_ThisWeekBot7.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8));
                                v_ThisWeekTop7.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0));
                            }
                        }
                        break;
                    default:
                        break;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
            //calculate time
            hourWorkInThisWeek = 0;
            minWorkInThisWeek = 0;
            hourWorkInPreWeek = 0;
            minWorkInPreWeek = 0;
            thisWeek = getWeekOfYear(lastDate);
            for (int i = 0; i < realTimeDataReportList.size(); i++) {
                try {
                    Date checkDate = new SimpleDateFormat("yyyy-MM-dd").parse(realTimeDataReportList.get(i).getDayWork());
                    int week = getWeekOfYear(checkDate);
                    String checkTimeWork = realTimeDataReportList.get(i).getTimeWork();
                    if (week == thisWeek) {
                        int hour = Integer.parseInt(checkTimeWork.substring(0, 1));
                        int min = Integer.parseInt(checkTimeWork.substring(0, 1));
                        hourWorkInThisWeek = hourWorkInThisWeek + hour;
                        minWorkInThisWeek = minWorkInThisWeek + min;
                    }
                    if (week == thisWeek - 1) {
                        int hour = Integer.parseInt(checkTimeWork.substring(0, 1));
                        int min = Integer.parseInt(checkTimeWork.substring(0, 1));
                        hourWorkInPreWeek = hourWorkInPreWeek + hour;
                        minWorkInPreWeek = minWorkInPreWeek + min;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if(minWorkInThisWeek >= 60){
                int subHourThisWeek = minWorkInThisWeek/60;
                hourWorkInThisWeek = hourWorkInThisWeek + subHourThisWeek;
                minWorkInThisWeek = minWorkInThisWeek - (subHourThisWeek*60);
            }
            if(minWorkInPreWeek >= 60){
                int subHourThisWeek = minWorkInPreWeek/60;
                hourWorkInPreWeek = hourWorkInPreWeek + subHourThisWeek;
                minWorkInPreWeek = minWorkInPreWeek - (subHourThisWeek*60);
            }
            String hourThisWeek = hourWorkInThisWeek+"";
            String minThisWeek = minWorkInThisWeek+"";
            if(hourWorkInThisWeek<10){
                hourThisWeek = "0"+hourThisWeek;
            }
            if(minWorkInThisWeek<10){
                minThisWeek = "0"+minThisWeek;
            }
            String hourPreWeek = hourWorkInPreWeek+"";
            String minPreWeek = minWorkInPreWeek+"";
            if(hourWorkInPreWeek<10){
                hourPreWeek = "0"+hourPreWeek;
            }
            if(minWorkInPreWeek<10){
                minPreWeek = "0"+minPreWeek;
            }
            tv_TimeInWorkPreWeek.setText(hourPreWeek+":"+minPreWeek);
            tv_TimeInWorkThisWeek.setText(hourThisWeek+":"+minThisWeek);
            //dateWork = dataReportToDay.getDayWork();

            //TODO add it later
            //progressDialog.cancel();


        }
    };

    int thisWeek;
    private void updateTimeWorkByDay() {
        thisWeek = getWeekOfYear(lastDate);
        for (int i = 0; i < loadFirstReportList.size(); i++) {
            Date checkDate = new Date();
            try {
                checkDate = new SimpleDateFormat("yyyy-MM-dd").parse(loadFirstReportList.get(i).getDayWork());
                int week = getWeekOfYear(checkDate);
                if (week == thisWeek) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(checkDate);
                    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                    //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd/MM/yyyy");
                    //String formatted = simpleDateFormat.format(checkDate);
                    String checkTimeWork = loadFirstReportList.get(i).getTimeWork();
                    float hour = Integer.parseInt(checkTimeWork.substring(0, 1));
                    float min = Integer.parseInt(checkTimeWork.substring(0, 1));
                    float timePerDay = hour + (min / 60);
                    switch (dayOfWeek) {
                        case 2:

                            if (timePerDay > 0) {
                                if (timePerDay > 4) {
                                    v_ThisWeekBot2.setBackgroundResource(R.drawable.border_frame_green_background_green_border);
                                    ll_ThisWeekBar2.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                                }
                                if (timePerDay < 4) {
                                    v_ThisWeekBot2.setBackgroundResource(R.drawable.border_frame_red_background_red_border);
                                    ll_ThisWeekBar2.setBackgroundResource(R.drawable.border_frame_while_background_red_border);
                                }
                                if(timePerDay<8){
                                    v_ThisWeekBot2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, timePerDay));
                                    v_ThisWeekTop2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8 - timePerDay));
                                }
                                else{
                                    v_ThisWeekBot2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8));
                                    v_ThisWeekTop2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0));
                                }

                            }

                            break;
                        case 3:
                            if (timePerDay > 0) {
                                if (timePerDay > 4) {
                                    v_ThisWeekBot3.setBackgroundResource(R.drawable.border_frame_green_background_green_border);
                                    ll_ThisWeekBar3.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                                }
                                if (timePerDay < 4) {
                                    v_ThisWeekBot3.setBackgroundResource(R.drawable.border_frame_red_background_red_border);
                                    ll_ThisWeekBar3.setBackgroundResource(R.drawable.border_frame_while_background_red_border);
                                }
                                if(timePerDay<8){
                                    v_ThisWeekBot3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, timePerDay));
                                    v_ThisWeekTop3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8 - timePerDay));
                                }
                                else{
                                    v_ThisWeekBot3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8));
                                    v_ThisWeekTop3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0));
                                }

                            }

                            break;
                        case 4:
                            if (timePerDay > 0) {
                                if (timePerDay > 4) {
                                    v_ThisWeekBot4.setBackgroundResource(R.drawable.border_frame_green_background_green_border);
                                    ll_ThisWeekBar4.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                                }
                                if (timePerDay < 4) {
                                    v_ThisWeekBot4.setBackgroundResource(R.drawable.border_frame_red_background_red_border);
                                    ll_ThisWeekBar4.setBackgroundResource(R.drawable.border_frame_while_background_red_border);
                                }
                                if(timePerDay<8){
                                    v_ThisWeekBot4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, timePerDay));
                                    v_ThisWeekTop4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8 - timePerDay));
                                }
                                else{
                                    v_ThisWeekBot4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8));
                                    v_ThisWeekTop4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0));
                                }

                            }
                            break;
                        case 5:
                            if (timePerDay > 0) {
                                if (timePerDay > 4) {
                                    v_ThisWeekBot5.setBackgroundResource(R.drawable.border_frame_green_background_green_border);
                                    ll_ThisWeekBar5.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                                }
                                if (timePerDay < 4) {
                                    v_ThisWeekBot5.setBackgroundResource(R.drawable.border_frame_red_background_red_border);
                                    ll_ThisWeekBar5.setBackgroundResource(R.drawable.border_frame_while_background_red_border);
                                }
                                if(timePerDay<8){
                                    v_ThisWeekBot5.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, timePerDay));
                                    v_ThisWeekTop5.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8 - timePerDay));
                                }
                                else{
                                    v_ThisWeekBot5.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8));
                                    v_ThisWeekTop5.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0));
                                }

                            }
                            break;
                        case 6:
                            if (timePerDay > 0) {
                                if (timePerDay > 4) {
                                    v_ThisWeekBot6.setBackgroundResource(R.drawable.border_frame_green_background_green_border);
                                    ll_ThisWeekBar6.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                                }
                                if (timePerDay < 4) {
                                    v_ThisWeekBot6.setBackgroundResource(R.drawable.border_frame_red_background_red_border);
                                    ll_ThisWeekBar6.setBackgroundResource(R.drawable.border_frame_while_background_red_border);
                                }
                                if(timePerDay<8){
                                    v_ThisWeekBot6.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, timePerDay));
                                    v_ThisWeekTop6.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8 - timePerDay));
                                }
                                else{
                                    v_ThisWeekBot6.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8));
                                    v_ThisWeekTop6.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0));
                                }

                            }
                            break;
                        case 7:
                            if (timePerDay > 0) {
                                if (timePerDay > 4) {
                                    v_ThisWeekBot7.setBackgroundResource(R.drawable.border_frame_green_background_green_border);
                                    ll_ThisWeekBar7.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                                }
                                if (timePerDay < 4) {
                                    v_ThisWeekBot7.setBackgroundResource(R.drawable.border_frame_red_background_red_border);
                                    ll_ThisWeekBar7.setBackgroundResource(R.drawable.border_frame_while_background_red_border);
                                }
                                if(timePerDay<8){
                                    v_ThisWeekBot7.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, timePerDay));
                                    v_ThisWeekTop7.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8 - timePerDay));
                                }
                                else{
                                    v_ThisWeekBot7.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8));
                                    v_ThisWeekTop7.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0));
                                }
                            }
                            break;
                        default:
                            break;
                    }
                    //String byDayTimeWork = dataReportList.get(i).getTimeWork();
                }
                if (week < thisWeek) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(checkDate);
                    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                    //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd/MM/yyyy");
                    //String formatted = simpleDateFormat.format(checkDate);
                    String checkTimeWork = loadFirstReportList.get(i).getTimeWork();
                    float hour = Integer.parseInt(checkTimeWork.substring(0, 2));
                    float min = Integer.parseInt(checkTimeWork.substring(3, 5));
                    float timePerDay = hour + (min / 60);
                    switch (dayOfWeek) {
                        case 2:

                            if (timePerDay > 0) {
                                if (timePerDay > 4) {
                                    v_PreWeekBot2.setBackgroundResource(R.drawable.border_frame_green_background_green_border);
                                    ll_PreWeekBar2.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                                }
                                if (timePerDay < 4) {
                                    v_PreWeekBot2.setBackgroundResource(R.drawable.border_frame_red_background_red_border);
                                    ll_PreWeekBar2.setBackgroundResource(R.drawable.border_frame_while_background_red_border);
                                }
                                if(timePerDay<8){
                                    v_PreWeekBot2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, timePerDay));
                                    v_PreWeekTop2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8 - timePerDay));
                                }
                                else{
                                    v_PreWeekBot2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8));
                                    v_PreWeekTop2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0));
                                }

                            }

                            break;
                        case 3:
                            if (timePerDay > 0) {
                                if (timePerDay > 4) {
                                    v_PreWeekBot3.setBackgroundResource(R.drawable.border_frame_green_background_green_border);
                                    ll_PreWeekBar3.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                                }
                                if (timePerDay < 4) {
                                    v_PreWeekBot3.setBackgroundResource(R.drawable.border_frame_red_background_red_border);
                                    ll_PreWeekBar3.setBackgroundResource(R.drawable.border_frame_while_background_red_border);
                                }
                                if(timePerDay<8){
                                    v_PreWeekBot3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, timePerDay));
                                    v_PreWeekTop3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8 - timePerDay));
                                }
                                else{
                                    v_PreWeekBot3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8));
                                    v_PreWeekTop3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0));
                                }

                            }

                            break;
                        case 4:
                            if (timePerDay > 0) {
                                if (timePerDay > 4) {
                                    v_PreWeekBot4.setBackgroundResource(R.drawable.border_frame_green_background_green_border);
                                    ll_PreWeekBar4.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                                }
                                if (timePerDay < 4) {
                                    v_PreWeekBot4.setBackgroundResource(R.drawable.border_frame_red_background_red_border);
                                    ll_PreWeekBar4.setBackgroundResource(R.drawable.border_frame_while_background_red_border);
                                }
                                if(timePerDay<8){
                                    v_PreWeekBot4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, timePerDay));
                                    v_PreWeekTop4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8 - timePerDay));
                                }
                                else{
                                    v_PreWeekBot4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8));
                                    v_PreWeekTop4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0));
                                }

                            }
                            break;
                        case 5:
                            if (timePerDay > 0) {
                                if (timePerDay > 4) {
                                    v_PreWeekBot5.setBackgroundResource(R.drawable.border_frame_green_background_green_border);
                                    ll_PreWeekBar5.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                                }
                                if (timePerDay < 4) {
                                    v_PreWeekBot5.setBackgroundResource(R.drawable.border_frame_red_background_red_border);
                                    ll_PreWeekBar5.setBackgroundResource(R.drawable.border_frame_while_background_red_border);
                                }
                                if(timePerDay<8){
                                    v_PreWeekBot5.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, timePerDay));
                                    v_PreWeekTop5.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8 - timePerDay));
                                }
                                else{
                                    v_PreWeekBot5.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8));
                                    v_PreWeekTop5.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0));
                                }

                            }
                            break;
                        case 6:
                            if (timePerDay > 0) {
                                if (timePerDay > 4) {
                                    v_PreWeekBot6.setBackgroundResource(R.drawable.border_frame_green_background_green_border);
                                    ll_PreWeekBar6.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                                }
                                if (timePerDay < 4) {
                                    v_PreWeekBot6.setBackgroundResource(R.drawable.border_frame_red_background_red_border);
                                    ll_PreWeekBar6.setBackgroundResource(R.drawable.border_frame_while_background_red_border);
                                }
                                if(timePerDay<8){
                                    v_PreWeekBot6.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, timePerDay));
                                    v_PreWeekTop6.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8 - timePerDay));
                                }
                                else{
                                    v_PreWeekBot6.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8));
                                    v_PreWeekTop6.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0));
                                }

                            }
                            break;
                        case 7:
                            if (timePerDay > 0) {
                                if (timePerDay > 4) {
                                    v_PreWeekBot7.setBackgroundResource(R.drawable.border_frame_green_background_green_border);
                                    ll_PreWeekBar7.setBackgroundResource(R.drawable.border_frame_while_background_green_border);
                                }
                                if (timePerDay < 4) {
                                    v_PreWeekBot7.setBackgroundResource(R.drawable.border_frame_red_background_red_border);
                                    ll_PreWeekBar7.setBackgroundResource(R.drawable.border_frame_while_background_red_border);
                                }
                                if(timePerDay<8){
                                    v_PreWeekBot7.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, timePerDay));
                                    v_PreWeekTop7.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8 - timePerDay));
                                }
                                else{
                                    v_PreWeekBot7.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8));
                                    v_PreWeekTop7.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0));
                                }

                            }
                            break;
                        default:
                            break;
                    }

                }

            } catch (ParseException e) {

                e.printStackTrace();
            }
        }

    }

    private Date convertDateFromData(String dateFromData) {
        Date date = new Date();
        try {
            date = new SimpleDateFormat("").parse(dateFromData);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd/MM/yyyy");
            String formatted = simpleDateFormat.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    protected static int getWeekOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    private String timeWork = "00:00:00", dateWork = "", startTime = "", retireTime = "00:00:00";
    int hourWorkInThisWeek = 0, minWorkInThisWeek = 0, hourWorkInPreWeek = 0, minWorkInPreWeek = 0;

    private void setData() {
        String hourThisWeek = hourWorkInThisWeek+"";
        String minThisWeek = minWorkInThisWeek+"";
        if(hourWorkInThisWeek<10){
            hourThisWeek = "0"+hourThisWeek;
        }
        if(minWorkInThisWeek<10){
            minThisWeek = "0"+minThisWeek;
        }
        String hourPreWeek = hourWorkInPreWeek+"";
        String minPreWeek = minWorkInPreWeek+"";
        if(hourWorkInPreWeek<10){
            hourPreWeek = "0"+hourPreWeek;
        }
        if(minWorkInPreWeek<10){
            minPreWeek = "0"+minPreWeek;
        }
        tv_TimeInWorkPreWeek.setText(hourPreWeek+":"+minPreWeek);
        tv_TimeInWorkThisWeek.setText(hourThisWeek+":"+minThisWeek);
        tv_TimeWork.setText(timeWork);
        tv_DateWork.setText(dateWork);
        tv_StartTime.setText(startTime);
        tv_RetireTime.setText(retireTime);
    }

}
