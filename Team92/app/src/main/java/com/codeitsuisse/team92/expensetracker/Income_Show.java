package com.codeitsuisse.team92.expensetracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

/**
 * Created by Jay Lohokare on 12-Sep-15.
 */
public class Income_Show extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener {


    private android.support.v7.widget.Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    FloatingActionButton redo;

    final Context context = this;


    Switch bck, upd, hst, doupdt;

    String user_name, sharing_on, password;

    private ProgressDialog pDialog;

    Activity contextActivityABC = null;

    EditText edt2;
    Button btn;
    TextView txt1, txt2;
    private String year;
    private String month;
    private String day;

    String new_password;
    String r;
    AlertDialog alertDialog;

    String to, froms;

    String amounti;
    String descriptions;
    String category;

    Spinner spinner, from;

    String key;

    Button b;

    RelativeLayout mRelativeLayout;

    private List<ExpenseDetails> m_parts = new ArrayList<ExpenseDetails>();
    private Income_adapter m_adapter;

    @Override
    protected void onPause() {
        super.onPause();
        // spinner.setSelection(0);
        //  m_parts.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //m_parts.clear();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }



    public void loadList() {

        final ListView listview = (ListView) findViewById(R.id.apps_fragment_list);
        listview.setAdapter(m_adapter);

        mRelativeLayout = (RelativeLayout) findViewById(R.id.relscroll);

        try {
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            final Calendar c = Calendar.getInstance();

            Integer month2 = c.get(Calendar.MONTH);
            month2++;


            List<ExpenseDetails> fetch = db.getIncome(String.valueOf(month2));

            for (ExpenseDetails cn1 : fetch) {
                int amount = cn1.getAmount();
                int date = cn1.getDate();
                int month = cn1.getMonth();
                int year = cn1.getYear();

                m_parts.add(cn1);

            }

        } catch (Exception e) {
            Log.d("Error dadadada", e.toString());
        }

        m_adapter = new Income_adapter(getApplicationContext(), R.layout.card_adapter, m_parts);

        listview.setAdapter(m_adapter);

       

    }

    View.OnClickListener snackaction;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_show);

        snackaction = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };

        Snackbar.make(findViewById(android.R.id.content), "Showing income", Snackbar.LENGTH_LONG)
                .setAction("Ok", snackaction)
                .setActionTextColor(Color.WHITE)
                .show();


        final ListView listview = (ListView) findViewById(R.id.apps_fragment_list);


        listview.setAdapter(m_adapter);


        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setElevation(5);
        getSupportActionBar().setTitle("FindX");
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        displayView(3);

        final String PREFS_NAME = "Expense";
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

/*
        try {
            m_parts.clear();

            loadList();

        } catch (Exception e) {

        }
*/

        loadList();

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

                finish();
                break;
            case 1:
                i = new Intent(this, Main_Screen.class);
                startActivity(i);
                finish();
                break;

            case 2:
                i = new Intent(this, Date_Select.class);
                startActivity(i);
                finish();
                break;

            case 3:


                break;
            case 4:

                i = new Intent(this, Budget_Saving.class);
                startActivity(i);
                finish();
                break;
            case 5:
                i = new Intent(this, Show_Liability.class);
                startActivity(i);
                finish();
                break;




            default:
                break;
        }
    }

}



