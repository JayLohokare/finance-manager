package com.codeitsuisse.team92.expensetracker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;

public class ExpenseManager extends Service {


    public static boolean mRunning = false;

    private int year;
    private int month;
    private int day;

    @Override
    public void onCreate() {
        mRunning = true;
        super.onCreate();


    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        startService(new Intent(this, ExpenseManager.class));
        mRunning = false;

    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final String PREFS_NAME = "Expense";
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        Display_Main.name =settings.getString("name","");

        /*************************************Notify***************************************/

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

                Intent resultIntent = new Intent(ExpenseManager.this,Pre_launch_activity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addParentStack(Pre_launch_activity.class);

                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(02, PendingIntent.FLAG_UPDATE_CURRENT);

                mBuilder.setContentIntent(resultPendingIntent);
                mBuilder.setSmallIcon(R.drawable.ic_launcher);
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        month ++;
        day = c.get(Calendar.DAY_OF_MONTH);
        Log.d(c.toString(), String.valueOf(month));


        mBuilder.setContentTitle("Expesnse Manager");
                mBuilder.setContentText("Total Expense : " + (String.valueOf(db.getTotalExpense(String.valueOf(month)))) + " Total income : " + (String.valueOf(db.getTotalIncome(String.valueOf(month)))));
                mBuilder.setOngoing(true);

                mBuilder.addAction(R.drawable.ic_action_settings, "Settings", resultPendingIntent);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(0, mBuilder.build());

                /*************************************Notify***************************************/



        if(  (Integer.parseInt(settings.getString("budget","0")) < (Integer.parseInt(String.valueOf(db.getTotalExpense(String.valueOf(month))))))) {

            if (settings.getString("alert", "0") != "0") {
                NotificationCompat.Builder mBuilder2 = new NotificationCompat.Builder(this);

                Intent resultIntent2 = new Intent(ExpenseManager.this, Pre_launch_activity.class);
                TaskStackBuilder stackBuilder2 = TaskStackBuilder.create(this);
                stackBuilder.addParentStack(Pre_launch_activity.class);

                stackBuilder.addNextIntent(resultIntent2);
                PendingIntent resultPendingIntent2 = stackBuilder.getPendingIntent(01, PendingIntent.FLAG_UPDATE_CURRENT);

                mBuilder2.setContentIntent(resultPendingIntent2);
                mBuilder2.setSmallIcon(R.drawable.ic_launcher);
                DatabaseHandler db2 = new DatabaseHandler(getApplicationContext());

                mBuilder2.setContentTitle("Expesnse Alert");
                mBuilder2.setContentText("Expenses crossing limit. ");
                mBuilder2.setOngoing(true);

                mBuilder2.addAction(R.drawable.ic_action_settings, "Settings", resultPendingIntent2);
                NotificationManager mNotificationManager2 = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager2.notify(1, mBuilder2.build());

            }

        }

        else{

            try {
                String ns = Context.NOTIFICATION_SERVICE;
                NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
                nMgr.cancel(1);
            }
            catch(Exception e)
            {

            }
        }
        mRunning = true;


        return START_STICKY;
    }


}