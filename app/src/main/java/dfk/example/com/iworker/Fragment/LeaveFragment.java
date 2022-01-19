package dfk.example.com.iworker.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import dfk.example.com.iworker.Activity.LeaveDetailActivity;
import dfk.example.com.iworker.Activity.LeaveFormActivity;
import dfk.example.com.iworker.Activity.LeaveHistoryActivity;
import dfk.example.com.iworker.Activity.LeaveTypeActivity;
import dfk.example.com.iworker.Adapter.LeaveListAdapter;
import dfk.example.com.iworker.Model.LeaveInfomation;
import dfk.example.com.iworker.R;

public class LeaveFragment extends Fragment {
    private Button mCreateForm;
    private ListView listViewWaiting;
    List<LeaveInfomation> leaveInfomationList;
    LeaveListAdapter adapter;
    LinearLayout ll_LeaveDetail,ll_LeaveHistory;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leave,container,false);
        mCreateForm = view.findViewById(R.id.btn_create_form);
        mCreateForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LeaveFormActivity.class));
            }
        });
        //
        listViewWaiting = view.findViewById(R.id.lv_waiting);
        leaveInfomationList = new ArrayList<>();
        leaveInfomationList.add(new LeaveInfomation(1,"23/05/2019","Nghỉ ốm","Chưa duyệt"));
        leaveInfomationList.add(new LeaveInfomation(3,"28/05/2019 - 30/05/2019","Nghỉ không lương","Chưa duyệt"));
        adapter = new LeaveListAdapter(getActivity().getApplicationContext(),leaveInfomationList);
        listViewWaiting.setAdapter(adapter);
        listViewWaiting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getContext(), LeaveDetailActivity.class));
            }
        });

        ll_LeaveDetail =view.findViewById(R.id.ll_leave_detail);
        ll_LeaveDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LeaveTypeActivity.class));
            }
        });
        ll_LeaveHistory = view.findViewById(R.id.ll_leave_history);
        ll_LeaveHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LeaveHistoryActivity.class));
            }
        });
        return view;
    }
}
