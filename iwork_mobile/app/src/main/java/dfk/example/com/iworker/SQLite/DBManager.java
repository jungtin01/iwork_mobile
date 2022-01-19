package dfk.example.com.iworker.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dfk.example.com.iworker.SQLite.SQLiteModel.BackupLogsDataSQLite;
import dfk.example.com.iworker.SQLite.SQLiteModel.BackupLogsSQLite;
import dfk.example.com.iworker.SQLite.SQLiteModel.ReportSQLite;

public class DBManager extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "logs";
    private static final String BACKUPS_LOGS_TABLE_NAME = "backup_logs";
    private static final String ID = "id";
    private static final String MAC_PHONE = "mac_phone";
    private static final String START_TIME = "start_time";
    private static final String END_TIME = "end_time";
    private static final String DATE = "date";

    private static final String BACKUPS_LOGS_DATA_TABLE_NAME = "backup_logs_data";
    private static final String TIME_EVENT = "time_event";
    private static final String LOG_NAME = "log_name";

    private static final String ACITIVITY_LOGS_TABLE_NAME = "activity_log";
    private static final String DATE_LOG = "date_log";

    private static final String ACITIVITY_LOGS_DATA_TABLE_NAME = "activity_log";
    private static final String TIME_LOG = "time_log";
    private static final String ACTIVITY = "activity";

    private static final String REPORT_TABLE = "report";
    private static final String REPORT_DATE_WORK = "day_work";
    private static final String REPORT_TIME_WORK = "time_work";
    private static final String REPORT_TIME_RETIREMENT = "time_retirement";
    private static final String REPORT_START_TIME = "start_time_work";


    private Context context;

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
        Log.d("DBManager", "DBManager: ");
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createBackupLogsTable = "CREATE TABLE If not exists " + BACKUPS_LOGS_TABLE_NAME + " (" +
                ID + " INTEGER Primary Key Autoincrement, " +
                DATE + " TEXT," +
                MAC_PHONE + " TEXT, " +
                START_TIME + " TEXT, " +
                END_TIME + " TEXT)";
        db.execSQL(createBackupLogsTable);
        String createBackupLogsDataTable = "CREATE TABLE If not exists " + BACKUPS_LOGS_DATA_TABLE_NAME + " (" +
                ID + " INTEGER, " +
                TIME_EVENT + " TEXT, " +
                LOG_NAME + " TEXT)";
        db.execSQL(createBackupLogsDataTable);

        String createReportTable = "CREATE TABLE If not exists " + REPORT_TABLE + " (" +
                REPORT_DATE_WORK + " TEXT, " +
                REPORT_TIME_WORK + " TEXT, " +
                REPORT_TIME_RETIREMENT + " TEXT, " +
                REPORT_START_TIME + " TEXT)";
        db.execSQL(createReportTable);

        String createActivityLogsTable = "CREATE TABLE If not exists " + ACITIVITY_LOGS_TABLE_NAME + " (" +
                ID + " INTEGER Primary Key Autoincrement, " +
                DATE_LOG + " TEXT)";
        db.execSQL(createActivityLogsTable);

        String createActivityLogsDataTable = "CREATE TABLE If not exists " + ACITIVITY_LOGS_DATA_TABLE_NAME + " (" +
                ID + " INTEGER, " +
                TIME_LOG + " TEXT, " +
                ACTIVITY + " TEXT)";
        db.execSQL(createActivityLogsDataTable);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addReport(String date_work, String time_work, String time_retirement, String start_time){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(REPORT_DATE_WORK, date_work);
        values.put(REPORT_TIME_WORK, time_work);
        values.put(REPORT_TIME_RETIREMENT, time_retirement);
        values.put(REPORT_START_TIME, start_time);
        long res = db.insert(REPORT_TABLE,null,values);
        db.close();
    }
    public List<ReportSQLite> getAllReport(){
        List<ReportSQLite> reportList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + REPORT_TABLE;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()){
            ReportSQLite data = new ReportSQLite(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            reportList.add(data);
        }
        cursor.close();
        return reportList;
    }
    public ReportSQLite getReportByDay(String date){
        String selectQuery = "SELECT * FROM " + REPORT_TABLE + " WHERE " + REPORT_DATE_WORK + " = '"+date+"'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ReportSQLite reportByDay = null;
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            reportByDay = new ReportSQLite(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }
        else {
            cursor.close();
            db.close();
            return reportByDay;
        }

        cursor.close();
        db.close();
        return reportByDay;
    }

    public void addBackupLogs(String date, String mac_phone, String start_time, String end_time) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DATE, date);
        values.put(MAC_PHONE, mac_phone);
        values.put(START_TIME, start_time);
        values.put(END_TIME, end_time);
        db.insert(BACKUPS_LOGS_TABLE_NAME, null, values);
        db.close();
    }

    public void addBackUpLogsData(int id, String time_event, String log_name) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(TIME_EVENT, time_event);
        values.put(LOG_NAME, log_name);
        db.insert(BACKUPS_LOGS_DATA_TABLE_NAME, null, values);
        db.close();
    }

    //public BackupLogsSQLite getBackupLogs(String date){
    //    SQLiteDatabase db = getReadableDatabase();
    //    Cursor cursor = db.query(BACKUPS_LOGS_TABLE_NAME, new String[]{DATE, MAC_PHONE,START_TIME,END_TIME},DATE+"=?",new String[]{date},null,null,null);
    //    if (cursor!=null) {
    //        cursor.moveToFirst();
    //    }
    //    BackupLogsSQLite backupLogsSQLite = new BackupLogsSQLite();
    //    cursor.close();
    //    db.close();
    //    return backupLogsSQLite;
    //}
    public List<BackupLogsSQLite> getAllBackupLogs(String date) {
        List<BackupLogsSQLite> listBackupLogs = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + BACKUPS_LOGS_TABLE_NAME + " WHERE " + DATE + " = '"+date+"'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()){
            BackupLogsSQLite data = new BackupLogsSQLite(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            listBackupLogs.add(data);
        }
        cursor.close();
        db.close();
        return listBackupLogs;
    }

    public List<BackupLogsDataSQLite> getAllBackupLogsData(String date) {
        List<BackupLogsDataSQLite> listBackupsData = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + BACKUPS_LOGS_DATA_TABLE_NAME + "WHERE " + DATE + " = " + date;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                BackupLogsDataSQLite data = new BackupLogsDataSQLite(cursor.getInt(1), cursor.getString(2), cursor.getString(3));
                listBackupsData.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listBackupsData;
    }
    public void updateEndTime(int id, String end_time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(END_TIME,end_time);

        db.update(BACKUPS_LOGS_TABLE_NAME, values, "ID = "+id, null);
        db.close();
    }

}
