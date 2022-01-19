package dfk.example.com.iworker.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import dfk.example.com.iworker.Model.NotificationInfo;
import dfk.example.com.iworker.R;

public class NotificationListAdapter extends BaseAdapter {
    List<NotificationInfo> mNotiList;
    Context mContext;
    private LayoutInflater mInflater;
    public NotificationListAdapter(Context context, List<NotificationInfo> notificationInfoList){
        mNotiList = notificationInfoList;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mNotiList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.list_item_notification,null);
            viewHolder = new ViewHolder();
            viewHolder.object = convertView.findViewById(R.id.tv_noti_object);
            viewHolder.timeReceive = convertView.findViewById(R.id.tv_noti_time_receive);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String notiContent = "<b>"+mNotiList.get(position).getObject()+"</b>" +" "+ mNotiList.get(position).getDate()+" "+mNotiList.get(position).getState();
        viewHolder.object.setText(Html.fromHtml(notiContent));
        viewHolder.timeReceive.setText(" "+mNotiList.get(position).getTimeRecive());

        return convertView;
    }
    static class ViewHolder{
        TextView object;
        TextView timeReceive;
    }
}
