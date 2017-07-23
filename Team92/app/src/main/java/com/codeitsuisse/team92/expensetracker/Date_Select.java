package com.codeitsuisse.team92.expensetracker;

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
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Date_Select extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener {


private android.support.v7.widget.Toolbar mToolbar;
private FragmentDrawer drawerFragment;

        final Context context = this;


        private ProgressDialog pDialog;


        EditText edt2;
        Button btn;
        TextView txt1, txt2;

        private String yearf;
        private String monthf;
        private String dayf;

        String r;
        AlertDialog alertDialog;

        String to ,froms;

        String amounti;
        String descriptions;
        String category;

        String key;

        CalendarView calendar;


        Button bday, bmonth, byear;

        String month2, day2,date2,year2;

        RelativeLayout mRelativeLayout;


        private List<ExpenseDetails> m_parts = new ArrayList<ExpenseDetails>();
        private StableArrayAdapter m_adapter;




        public void loadList(int day, int month, int year)
        {

                final ListView listview = (ListView) findViewById(R.id.apps_fragment_list);
                listview.setAdapter(m_adapter);

                mRelativeLayout = (RelativeLayout) findViewById(R.id.relscroll);

                try{
                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());



                        List<ExpenseDetails> cm = db.getDaily(day, month, year);

                        for (ExpenseDetails cn : cm) {
                                String log = "from: " + cn.getFrom() + " ,To: " + cn.getTo() + " ,AMOUNT: " + cn.getAmount() + ",DESCRIPTION: " +cn.getDescription() + ",category:"+cn.getCategory()   + " ,Date: " + cn.getDate() + ",month:" + cn.getMonth() +",year:"+cn.getYear() ;
                                Log.d("Row: ", log);
                                m_parts.add(cn);

                        }

                }
                catch(Exception e){
                        Log.d("Error dadadada", e.toString());
                }

                m_adapter = new StableArrayAdapter(getApplicationContext(), R.layout.card_adapter, m_parts);

                listview.setAdapter(m_adapter);

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                TextView tv = (TextView) view.findViewById(R.id.des);
                                r = tv.getText().toString();



                                DatabaseHandler db = new DatabaseHandler(getApplicationContext());


                                List<ExpenseDetails> fetch = db.getDetails(r);



                                for (ExpenseDetails cn1 : fetch) {
                                        froms = cn1.getFrom();
                                        to = cn1.getTo();
                                        day2 = String.valueOf(cn1.getDate());
                                        month2 = String.valueOf(cn1.getMonth());
                                        year2 = String.valueOf(cn1.getYear());
                                        descriptions = String.valueOf(cn1.getDescription());
                                        category = cn1.getCategory();
                                        amounti = String.valueOf(cn1.getAmount());
                                        key = String.valueOf(cn1.getSr());

                                }

                                String message = "Money used from : " + froms + "\nMoney used for : " + to + "\nDate : " + day2 + "-" + month2 + "-" + year2 + "\nCategory of usage : " + category + "\nAdditional description : " + descriptions + "\nAmount : " + amounti;
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                alertDialogBuilder.setTitle("Description");
                                alertDialogBuilder.setMessage(message);
                                alertDialogBuilder.setCancelable(false);


                                alertDialogBuilder.setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {

                                                        alertDialog.dismiss();

                                                }
                                        });
                                alertDialogBuilder.setNegativeButton("Edit",
                                        new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {


                                                        Intent i = new Intent(Date_Select.this, Edit_data.class);
                                                        Bundle b = new Bundle();


                                                        b.putString("id", r.toString());
                                                        b.putString("name","Main_Screen.class");
                                                        i.putExtras(b);
                                                        try {
                                                                startActivity(i);
                                                                // finish();

                                                        } catch (Exception e) {

                                                        }

                                                }
                                        });

                                alertDialog = alertDialogBuilder.create();
                                alertDialog.show();


                        }
                });



        }

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



                calendar = (CalendarView) findViewById(R.id.calendarView);
                calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

                        @Override

                        public void onSelectedDayChange(CalendarView view, int year, int month, int day) {

                                m_parts.clear();
                                loadList(day, month, year);
                        }

                });



        }



        View.OnClickListener snackaction;


        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_date__select);



                snackaction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

                }
                };

        Snackbar.make(findViewById(android.R.id.content), "Select date", Snackbar.LENGTH_LONG)
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
                displayView(2);

        final String PREFS_NAME = "Expense";
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);




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

                        break;

                case 3:

                        i = new Intent(this, Income_Show.class);
                        startActivity(i);
                        finish();
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