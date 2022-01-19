package dfk.example.com.iworker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import dfk.example.com.iworker.Model.LeaveInfomation;
import dfk.example.com.iworker.R;

public class LeaveListAdapter extends BaseAdapter {
    List<LeaveInfomation> mLeaveList;
    Context mContext;
    private LayoutInflater mInflater;
    public LeaveListAdapter(Context context, List<LeaveInfomation> leaveList){
        mLeaveList = leaveList;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mLeaveList.size();
    }

    @Override
    public Object getItem(int position) {
        return mLeaveList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.leave_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.dateInLeave = convertView.findViewById(R.id.tv_leave_item_date_in_leave);
            viewHolder.date = convertView.findViewById(R.id.tv_leave_item_date);
            viewHolder.type = convertView.findViewById(R.id.tv_leave_item_type);
            viewHolder.state = convertView.findViewById(R.id.tv_leave_item_state);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.dateInLeave.setText(mLeaveList.get(position).getDateInLeave()+"");
        viewHolder.date.setText(mLeaveList.get(position).getDate());
        viewHolder.type.setText(mLeaveList.get(position).getType());
        viewHolder.state.setText(mLeaveList.get(position).getState());

        return convertView;
    }
    static class ViewHolder{
        TextView dateInLeave;
        TextView date;
        TextView type;
        TextView state;
    }
}
