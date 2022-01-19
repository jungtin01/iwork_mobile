package dfk.example.com.iworker.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dfk.example.com.iworker.Api.ApiUtils;
import dfk.example.com.iworker.Api.WorkTimeClient;
import dfk.example.com.iworker.MainActivity;
import dfk.example.com.iworker.Model.BackupDataLogs;
import dfk.example.com.iworker.Model.BackupLogs;
import dfk.example.com.iworker.SQLite.SQLiteModel.BackupLogsSQLite;
import dfk.example.com.iworker.Model.DataReport;
import dfk.example.com.iworker.Model.Report;
import dfk.example.com.iworker.R;
import dfk.example.com.iworker.SQLite.DBManager;
import dfk.example.com.iworker.StaticData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WifiService extends Service {
    public static String mac_phone = "";
    public static String mac_wifi = "";
    WifiManager wifiManager;
    Timer timer;
    WorkTimeClient workTimeClient = ApiUtils.workTimeClient();
    ArrayList<DataReport> dataReportList;
    LocalBroadcastManager localBroadcastManager;
    LocationManager locationManager;
    boolean locationEnable = false;
    boolean wifiEnable = false;
    boolean isWifiConnected = false;
    String[] arr;
    BackupLogs backupLogs;
    ArrayList<BackupDataLogs> dataLogList;
    private Report report;
    //ArrayList<WorkTimeInfoParacle> workTimeInfoParacleList;

    public WifiService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        timer = new Timer();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        arr = new String[]{"a0-ab-1b-6c-14-38", "50-64-2b-12-22-b9", "0e-8d-cb-6e-9b-bc", "fe-ec-da-ea-51-13", "9c-50-ee-2c-0c-dc", "d4-6e-0e-3f-82-bc"};
        //
        dataLogList = new ArrayList<>();

    }

    private void sendDataToFragment(ArrayList<DataReport> listWorkTime) {
        Intent intent = new Intent("UpdateTimeWork");
        intent.putExtra("ListTimeWork", listWorkTime);
        localBroadcastManager.sendBroadcast(intent);
    }
    private String notRegistedMacWifi = "";
    private String formattedTime = "";
    private Date startTime = null;
    private Date endTime = null;
    String type = "Backup Logs";
    String noWifiLogs = "No Wifi Logs";
    String noLocationLogs = "No Location Logs";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //
                Date date = new Date();
                int hour = date.getHours();
                if (isLocationEnabled(getApplicationContext()) && wifiManager.isWifiEnabled()) {
                    locationEnable = true;
                    wifiEnable = true;
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
                        //Log.d("Wifi Adress Connected: ", mac_wifi);
                        isWifiConnected = true;
                    } else {
                        isWifiConnected = false;
                    }
                    //WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    //mac_wifi = wifiInfo.getBSSID();


                    wifiManager.startScan();
                    List<ScanResult> results = wifiManager.getScanResults();
                    for (ScanResult result : results) {
                        Log.d("Access Point MacAddr:", result.BSSID);
                    }

                    try {
                        //String mac_phone = "";
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
                            //Log.d("Phone Address: ", mac_phone);

                            //if(wifiManager.isWifiEnabled()) {
                            //    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                            //    mac_wifi = wifiInfo.getBSSID();
                            //    Log.d("Wifi Adress Connected: ", wifiInfo.getBSSID());
//
                            //    wifiManager.startScan();
                            //    List<ScanResult> results = wifiManager.getScanResults();
                            //    for (ScanResult result : results)
                            //    {
                            //        Log.d("Access Point MacAddr:", result.BSSID);
                            //    }
//
                            //}

                        }
                        //Toast.makeText(WifiService.this, , Toast.LENGTH_SHORT).show();
                        //Toast.makeText(WifiService.this, "ABC", Toast.LENGTH_SHORT).show();
                        if (!mac_phone.equals("") && !mac_wifi.equals("")) {

                            mac_wifi = mac_wifi.toLowerCase();
                            if (mac_wifi.contains(":")) {
                                mac_wifi = mac_wifi.replaceAll(":", "-");
                            }

                            if (hour > 8 && hour < 18) {
                                Call<ResponseBody> call = workTimeClient.writeLog(mac_phone, mac_wifi);
                                call.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.isSuccessful()) {
                                            //Toast.makeText(WifiService.this, "Write Log Success | " +"Phone: "+ mac_phone+" | Wifi Debug4: " + mac_wifi, Toast.LENGTH_LONG).show();
                                            //testWriteLog = true;
                                            //Toast.makeText(WifiService.this, "Write Log Success", Toast.LENGTH_SHORT).show();
                                            //Toast.makeText(WifiService.this, "Wifi is ON: " +wifiEnable+ "\nWifi is connected: "+ isWifiConnected+
                                            //        "\n Location is ON: "+ locationEnable+ "MAC Phone: "+ mac_phone+"MAC Wifi: "+mac_wifi+"Write Log "+ testWriteLog, Toast.LENGTH_SHORT).show();
                                        } else {
                                            //Toast.makeText(WifiService.this, "Write Log Fail", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        //Toast.makeText(WifiService.this, "Cannot connect server", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            //TODO: use this code when have list of mac wifi
                            //if (mac_wifi.equals(notRegistedMacWifi)) {
                            //    return;
                            //}
                            //for (int i = 0; i <= arr.length - 1; i++) {
                            //    if (arr[i].equals(mac_wifi.trim())) {
                            //        notRegistedMacWifi = "";
                            //        break;
                            //    }
                            //    else {
                            //        notRegistedMacWifi = mac_wifi;
                            //    }
//
                            //}
                            StaticData.WifiMacAddress = mac_wifi;
                            dataLogList = new ArrayList<>();
                            report = new Report();
                            Call<Report> callGetReport = workTimeClient.getReport(mac_phone);
                            callGetReport.enqueue(new Callback<Report>() {
                                @Override
                                public void onResponse(Call<Report> call, Response<Report> response) {
                                    if (response.isSuccessful()) {
                                        dataReportList = response.body().getDataReport();
                                        sendDataToFragment(dataReportList);
                                    } else {

                                    }
                                }

                                @Override
                                public void onFailure(Call<Report> call, Throwable t) {

                                }
                            });
                            //workTimeInfoList = new ArrayList<>();
                            //workTimeInfoParacleList = new ArrayList<>();
                            //TODO

                            ///Call<List<WorkTimeInfo>> callGetReport = workTimeClient.getReport(mac_phone);
                            ///callGetReport.enqueue(new Callback<List<WorkTimeInfo>>() {
                            ///    @Override
                            ///    public void onResponse(Call<List<WorkTimeInfo>> call, Response<List<WorkTimeInfo>> response) {
                            ///        if (response.isSuccessful()) {
                            ///            //Toast.makeText(WifiService.this, "Get Report Success!", Toast.LENGTH_SHORT).show();
                            ///            if (response.body().size() > 0) {
///
                            ///                for (int i = 0; i < response.body().size(); i++) {
                            ///                    workTimeInfoList.add(response.body().get(i));
                            ///                }
                            ///                sendDataToFragment(workTimeInfoList);
                            ///                //sendDataToFragment(response.body().get(response.body().size()-1).getTimeWork(), response.body().get(response.body().size()-1).getStartTimeWork(), response.body().get(response.body().size()-1).getDateWork(),response.body().get(response.body().size()-1).getTimeRetirement());
                            ///            }
///
                            ///        } else {
                            ///            //Toast.makeText(WifiService.this, "Get Report Fail!", Toast.LENGTH_SHORT).show();
                            ///        }
                            ///    }
///
                            ///    @Override
                            ///    public void onFailure(Call<List<WorkTimeInfo>> call, Throwable t) {
                            ///        //Toast.makeText(WifiService.this, "Can't connect!", Toast.LENGTH_SHORT).show();
                            ///    }
                            ///});
                            //TODO
                            //Call<WorkTimeInfo> callGetReport = workTimeClient.getReport(mac_phone);
                            //callGetReport.enqueue(new Callback<WorkTimeInfo>() {
                            //    @Override
                            //    public void onResponse(Call<WorkTimeInfo> call, Response<WorkTimeInfo> response) {
                            //        if (response.isSuccessful()) {
                            //            WorkTimeInfo workTimeInfo = new WorkTimeInfo();
                            //
                            //            workTimeInfo.setTimeWork(response.body().getTimeWork());
                            //            Toast.makeText(WifiService.this, "Get Report Success!", Toast.LENGTH_SHORT).show();
//
                            //        }
                            //        else {
//
                            //            Toast.makeText(WifiService.this, "Get Report Fail!", Toast.LENGTH_SHORT).show();
                            //        }
                            //    }
//
                            //    @Override
                            //    public void onFailure(Call<WorkTimeInfo> call, Throwable t) {
//
                            //        Toast.makeText(WifiService.this, "Cannot connect!", Toast.LENGTH_SHORT).show();
                            //    }
                            //});
                        }


                    } catch (Exception ex) {
                    }


                } else {

                    locationEnable = isLocationEnabled(getApplicationContext());
                    wifiEnable = wifiManager.isWifiEnabled();


                }
                if (hour > 8 && hour < 18) {

                    createBackupLogs(wifiEnable, locationEnable, isWifiConnected);
                    createNotification(wifiEnable, locationEnable, isWifiConnected);
                }

                //if (wifiEnable == false || locationEnable == false) {
                //    if (startTime == null) {
                //        startTime = new Date();
                //    }
                //    BackupDataLogs backupDataLogs = new BackupDataLogs(new Date(), wifiEnable);
                //    dataLogList.add(backupDataLogs);
//
//
                //}
                //if (wifiEnable) {
                //    if (startTime != null) {
                //        endTime = new Date();
                //        if (locationEnable == true) {
                //            backupLogs = new BackupLogs(type, noWifiLogs, mac_wifi, mac_phone, startTime, endTime, dataLogList);
                //            startTime = null;
                //            endTime = null;
                //        }
                //        if (locationEnable == false) {
                //            backupLogs = new BackupLogs(type, noLocationLogs, mac_wifi, mac_phone, startTime, endTime, dataLogList);
                //            startTime = null;
                //            endTime = null;
                //        }
                //    }
//
                //}


            }

        }, 0, 15000);
        return super.onStartCommand(intent, flags, startId);
    }

    List<BackupLogsSQLite> listBackupLogsSQLite;

    private void createBackupLogs(boolean wifiEnable, boolean locationEnable, boolean isWifiConnected) {
        if (wifiEnable == true && locationEnable == true && isWifiConnected == true) {
            Date nowDate = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String date = simpleDateFormat.format(nowDate);

            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String event_time = simpleDateFormat1.format(nowDate);

            DBManager dbManager = new DBManager(getApplicationContext());
            listBackupLogsSQLite = new ArrayList<>();
            listBackupLogsSQLite = dbManager.getAllBackupLogs(date);
            if (listBackupLogsSQLite.size() > 0) {
                //TODO: check and update at position i
                for (int i = 0; i < listBackupLogsSQLite.size(); i++) {
                    if (listBackupLogsSQLite.get(i).getEndTime() == null) {
                        //TODO: add data with this id
                        int id = listBackupLogsSQLite.get(i).getId();
                        dbManager.updateEndTime(id, event_time);
                    }
                }
            }
            return;
        }

        String log_name = "";
        if (!wifiEnable || !isWifiConnected) {
            log_name = log_name + " Do not connect wifi";
        }
        if (!locationEnable) {
            log_name = log_name + " Turn off location";
        }
        int id = 0;
        DBManager dbManager = new DBManager(getApplicationContext());
        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String date = simpleDateFormat.format(nowDate);

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String event_time = simpleDateFormat1.format(nowDate);

        //BackupLogsSQLite backupLogsSQLite = dbManager.getBackupLogs(date);

        listBackupLogsSQLite = new ArrayList<>();
        listBackupLogsSQLite = dbManager.getAllBackupLogs(date);
        if (listBackupLogsSQLite.size() > 0) {
            for (int i = 0; i < listBackupLogsSQLite.size(); i++) {
                if (listBackupLogsSQLite.get(i).getEndTime() == null) {
                    //TODO: add data with this id
                    id = listBackupLogsSQLite.get(i).getId();
                    dbManager.addBackUpLogsData(id, event_time, log_name);
                }
            }
            if (id == 0) {
                dbManager.addBackupLogs(date, StaticData.PhoneMacAddress, event_time, null);
            }

        } else {
            //TODO create new backup log
            dbManager.addBackupLogs(date, StaticData.PhoneMacAddress, event_time, null);
        }


    }

    private void createNotification(boolean wifiEnable, boolean locationEnable, boolean wifiConnected) {

        if (wifiEnable == true && locationEnable == true && wifiConnected == true) {
            return;
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("WifiLocation", "WifiLocation", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Use to notify if your device's wifi or location is off");
            notificationManager.createNotificationChannel(channel);

        }
        String wifiConnect = "";
        String content2 = " đang tắt. Vui lòng mở lên để sử dụng dịch vụ!";
        String content1 = "";
        String showContent = "";


        //if (wifiEnable == false && locationEnable == false) {
        //    content1 = "Wifi và vị trí";
        //}
        //if (wifiEnable == true && locationEnable == false) {
        //    content1 = "Vị trí";
        //}
        //if (wifiEnable == false && locationEnable == true) {
        //    content1 = "Wifi";
        //}
        if (wifiEnable == false) {
            if (content1.equals("")) {
                content1 = "Wifi";
            } else {
                content1 = content1 + ", wifi";
            }
        }
        if (locationEnable == false) {
            if (content1.equals("")) {
                content1 = "Location";
            } else {
                content1 = content1 + ", location";
            }
        }
        // you have been hacked!!!!!!! i'm hacker
        else {
            if (wifiConnected == false) {
                if (content1.equals("")) {
                    wifiConnect = "Vui lòng kết nối với wifi.";
                } else {
                    wifiConnect = "Vui lòng kết nối với wifi.";
                }
            }
        }

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (wifiConnect.equals("")) {
            showContent = content1 + content2;
        } else {
            showContent = wifiConnect;
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "WifiLocation")
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle("iHR/iWorker") // title for notification
                .setContentText(showContent)// message for notification
                .setAutoCancel(true)// clear notification after click
                .setSound(defaultSound);


        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        notificationManager.notify(0, mBuilder.build());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
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
}
