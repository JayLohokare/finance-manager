package com.codeitsuisse.team92.expensetracker;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Pre_launch_activity extends Activity {

    final Context context = this;
    public static int post=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String PREFS_NAME = "Expense";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {

            Intent i2 = new Intent(Pre_launch_activity.this, Sign_Up.class);
            startActivity(i2);
            finish();
        }
        else {

            Intent i2 = new Intent(Pre_launch_activity.this, Pass_Check.class);
            startActivity(i2);
            finish();


        }
    }


}
