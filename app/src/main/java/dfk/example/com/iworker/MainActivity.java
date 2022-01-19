package dfk.example.com.iworker;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dfk.example.com.iworker.Activity.NotificationActivity;
import dfk.example.com.iworker.Activity.ProfileActivity;
import dfk.example.com.iworker.Api.ApiUtils;
import dfk.example.com.iworker.Api.UserClient;
import dfk.example.com.iworker.Fragment.LeaveFragment;
import dfk.example.com.iworker.Fragment.ReportFragment;
import dfk.example.com.iworker.Fragment.WorkTimeFragment;
import dfk.example.com.iworker.Model.Logs;
import dfk.example.com.iworker.Model.UserInfomation;
import dfk.example.com.iworker.Model.UserInfomationDetail;
import dfk.example.com.iworker.Service.WifiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private ImageView mProfileImage;
    private ArrayList<Logs> listLog;
    private Logs logs;
    UserInfomationDetail userInfomationDetail;
    UserClient userClient = ApiUtils.userClient();
    TextView tv_Name;
    String mac_phone = "";
    WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        tv_Name = findViewById(R.id.tv_main_name);
        //
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Wifi")
                .setMessage("Ứng dụng cần sử dụng wifi để hoạt động. Vui lòng mở wifi!")
                .setCancelable(false)
                .setPositiveButton("Mở Wifi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                });
        AlertDialog alertDialog = builder.create();
        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            alertDialog.show();
        }
        //TODO: comment this line if don't want to go with service
        startService(new Intent(MainActivity.this, WifiService.class));
        //
        //

        listLog = new ArrayList<>();
        logs = new Logs();
        //FirebaseApp.initializeApp(this);
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("logs");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //
                //listLog = (ArrayList<Logs>) dataSnapshot.getValue();
                //Log.d("debug", listLog.get(0).getLogTime() + listLog.get(0).getLocation());

                for (DataSnapshot logSnapshot : dataSnapshot.getChildren()) {
                    logs = logSnapshot.getValue(Logs.class);
                    listLog.add(logs);
                }
                //Log.d("debug", listLog.get(0).getLogTime());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setElevation(0);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mProfileImage = findViewById(R.id.iv_profile);
        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        //get Phone Mac Address
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    mac_phone = "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                mac_phone = res1.toString();
                Log.d("Phone Address: ", mac_phone);
                StaticData.PhoneMacAddress = mac_phone;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //
        String mac_wifi = "";

        if (isLocationEnabled(getApplicationContext()) && wifiManager.isWifiEnabled()) {
            ConnectivityManager connManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (networkInfo.isConnected()) {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                //wifiInfo.getSSID();
                //String name = networkInfo.getExtraInfo();
                //String ssid = "\"" + wifiInfo.getSSID() + "\"";
                mac_wifi = wifiInfo.getBSSID();
                StaticData.WifiMacAddress = mac_wifi;
            }
            else {
                alertDialog.show();
            }
            //try {
            //    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            //    mac_wifi = wifiInfo.getBSSID();
            //    if(mac_wifi == null){
//
            //    }
            //    else {
            //        StaticData.WifiMacAddress = mac_wifi;
            //    }
//
//
            //} catch (Exception e) {
            //    e.printStackTrace();
            //}
        }
        //get UserInformation
        Call<UserInfomation> call = userClient.getUserInformation(mac_phone);
        call.enqueue(new Callback<UserInfomation>() {
            @Override
            public void onResponse(Call<UserInfomation> call, Response<UserInfomation> response) {
                if (response.isSuccessful()) {
                    userInfomationDetail = new UserInfomationDetail();
                    userInfomationDetail = response.body().getUserInfomationDetail();
                    tv_Name.setText(userInfomationDetail.getLastName() + " " + userInfomationDetail.getFirstName());
                    //setDataToView();

                }
                //progressDialog.cancel();
            }

            @Override
            public void onFailure(Call<UserInfomation> call, Throwable t) {
                //progressDialog.cancel();
            }
        });


        //tabLayout.setOnTabSelectedListener();

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                .setAction("Action", null).show();
        //    }
        //});

    }

    public boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Cho phép quyền:");
                    alertDialog.setMessage("Bạn cần phải cho phép quyền vị trí khi sử dụng ứng dụng này");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(MainActivity.this,
                                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                            1);
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_custom, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}
        if (id == R.id.menu_notification) {
            startActivity(new Intent(this, NotificationActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    WorkTimeFragment workTimeFragment = new WorkTimeFragment();
                    return workTimeFragment;
                case 1:
                    LeaveFragment leaveFragment = new LeaveFragment();
                    return leaveFragment;
                case 2:
                    ReportFragment reportFragment = new ReportFragment();
                    return reportFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
