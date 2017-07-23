package com.codeitsuisse.team92.expensetracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Display_Main extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener {


    private android.support.v7.widget.Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    FloatingActionButton redo, add;

    final Context context = this;


    Switch bck, upd, hst, doupdt;

    String user_name, sharing_on, password;

    private ProgressDialog pDialog;

    Activity contextActivityABC = null;

    EditText edt2;
    Button btn;
    TextView txt1, txt2;
    private int year;
    private int month;
    private int day;

    String new_password;
    String r;
    AlertDialog alertDialog;

    String to ,froms;

    String amounti;
    String descriptions;
    String category;

    Spinner spinner, from;

    String key;

    public static String name;


    Button b;

    TextView budget, savings, curBal, income,exp;

    RelativeLayout mRelativeLayout;

    private List<ExpenseDetails> m_parts = new ArrayList<ExpenseDetails>();
    private StableArrayAdapter m_adapter;

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }


    @Override
    protected void onResume() {
        super.onResume();

        loaddata();

        context.startService(new Intent(context, ExpenseManager.class));

        final String PREFS_NAME = "Expense";
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        this.name = settings.getString("name","");

    }


    public void loaddata()
    {
        final String PREFS_NAME = "Expense";
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        this.name = settings.getString("name","");
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        month ++;
        day = c.get(Calendar.DAY_OF_MONTH);
        Log.d(c.toString(),String.valueOf(month));


        budget.setText("Month's budget : " + (settings.getString("budget","")));
        savings.setText("Total saving : " + (String.valueOf(db.getCurrentSaving(String.valueOf(month), Integer.parseInt(settings.getString("budget", "0"))))));


        if((settings.getString("budget","") == "" || (settings.getString("budget","")==null)))
        {
            budget.setText("");
            savings.setText("");
        }



        curBal.setText("Curren balance : " + (String.valueOf(db.getCurrentBalance(String.valueOf(month)))));
        exp.setText("Total Expense : " + (String.valueOf(db.getTotalExpense(String.valueOf(month)))));

        income.setText("Total income : " + (String.valueOf(db.getTotalIncome(String.valueOf(month)))));


    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__main);





    savings = (TextView) findViewById(R.id.sav);
        budget = (TextView) findViewById(R.id.budg);
        curBal =  (TextView) findViewById(R.id.bal);
        income = (TextView) findViewById(R.id.inc);
        exp = (TextView) findViewById(R.id.exp);


        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setElevation(5);
        getSupportActionBar().setTitle("FindX");
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        displayView(0);

        redo = (FloatingActionButton) findViewById(R.id.redo);
        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Display_Main.this, Add_Transcation.class);
                startActivity(i);

            }
        });

        add = (FloatingActionButton) findViewById(R.id.income);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Display_Main.this, Add_Income.class);
                startActivity(i);

            }
        });


    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        //Fragment fragment = null;
        String title = getString(R.string.app_name);
        Intent i;
        switch (position) {
            case 0:

                break;
            case 1:
                i = new Intent(Display_Main.this, Main_Screen.class);
                startActivity(i);
                title ="Date_Select";
                break;

            case 2:
                i = new Intent(Display_Main.this, Date_Select.class);
                startActivity(i);
                title ="Date_Select";
                break;

            case 3:

                i = new Intent(Display_Main.this, Income_Show.class);
                startActivity(i);
                title ="Date_Select";
                break;
            case 4:

                i = new Intent(Display_Main.this, Budget_Saving.class);
                startActivity(i);
                title ="Date_Select";
                break;
            case 5:
                i = new Intent(Display_Main.this, Show_Liability.class);
                startActivity(i);
                title ="Date_Select";
                break;


            default:
                break;
        }
    }






}