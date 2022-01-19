package dfk.example.com.iworker.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import dfk.example.com.iworker.Adapter.NotificationListAdapter;
import dfk.example.com.iworker.Fragment.LeaveFragment;
import dfk.example.com.iworker.MainActivity;
import dfk.example.com.iworker.Model.NotificationInfo;
import dfk.example.com.iworker.R;

public class NotificationActivity extends AppCompatActivity {
    String check;
    private ListView listViewNoti;
    List<NotificationInfo> notiList;
    NotificationListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        //action bar setting
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thông báo");
        //
        listViewNoti = findViewById(R.id.lv_notification);
        notiList = new ArrayList<>();
        notiList.add(new NotificationInfo("Đơn xin nghỉ phép","29/04/2019","đã bị từ chối","10 phút trước"));
        notiList.add(new NotificationInfo("Đơn xin nghỉ phép","10/04/2019","đã được duyệt","11/04/2019 lúc 15:00"));
        notiList.add(new NotificationInfo("Cảnh báo","04/04/2019","Vào trễ, Không đủ giờ làm, Vượt thời gian nghỉ","04/04/2019 lúc 18:00"));
        adapter = new NotificationListAdapter(this,notiList);
        listViewNoti.setAdapter(adapter);


        listViewNoti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                check = check+"|"+position;
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
