package com.codeitsuisse.team92.expensetracker;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnBootListener extends BroadcastReceiver {

    final static String TAG = "BootCompletedReceiver";



    @Override
    public void onReceive(Context context, Intent arg1) {


        ActivityManager manager = (ActivityManager)context.   getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {

            if (ExpenseManager.class.getName().equals(service.service.getClassName())) {
                //running
            }
            else{
                context.startService(new Intent(context, ExpenseManager.class));
            }

        }
    }
}