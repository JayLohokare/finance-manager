package com.codeitsuisse.team92.expensetracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

public class Add_Transcation extends Activity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    EditText payingfor, amount, description;
    FloatingActionButton done;
    private TextView tvDisplayDate;
    private DatePicker dpResult;
    private Button btnChangeDate;

    private int year;
    private int month;
    private int day;

    Spinner spinner, from;

    String label,spinnerText,fromText;
    Boolean Selected,spinnerB ,fromB = false;

    Switch pay;

    int paychecked;




    TextInputLayout lNameLayout3,lNameLayout1,lNameLayout2;


String finaldate;



    public void setCurrentDateOnView() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        tvDisplayDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(year).append("-").append(month + 1).append("-").append(day));

        finaldate= new StringBuilder()
                // Month is 0 based, just add 1
                .append(year).append("-").append(month + 1).append("-").append(day).toString();
        // set current date into datepicker
        dpResult.init(year, month, day, null);

    }






    private void loadspinnerdata() {
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        List<String> labels = db.getCat();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    private void loadspinnerdata2() {
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        List<String> labels = db.getFrom();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        from.setAdapter(dataAdapter);
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transcation);

        payingfor = (EditText) findViewById(R.id.editText5);
        amount = (EditText) findViewById(R.id.editText6);
        description = (EditText) findViewById(R.id.editText7);

        tvDisplayDate = (TextView) findViewById(R.id.textView4);
        dpResult = (DatePicker) findViewById(R.id.datePicker);



        pay = (Switch) findViewById(R.id.switch1);
        pay.setChecked(true);
        paychecked =1;
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pay.isChecked()){
                    paychecked = 1;
                    pay.setText("Paid");
                }
                else{
                    paychecked = 0;
                    pay.setText("Pay later");
                }
            }
        });







        done = (FloatingActionButton) findViewById(R.id.redo);

        lNameLayout3 = (TextInputLayout) findViewById(R.id
                .fNameLayout3);
        lNameLayout1 = (TextInputLayout) findViewById(R.id
                .fNameLayout1);
        lNameLayout2 = (TextInputLayout) findViewById(R.id
                .fNameLayout2);

        lNameLayout3.setErrorEnabled(false);

        lNameLayout1.setErrorEnabled(false);

        lNameLayout2.setErrorEnabled(false);

        spinner = (Spinner) findViewById(R.id.spinner);


      //  spinner.setOnItemSelectedListener(this);

        loadspinnerdata();

        from = (Spinner) findViewById(R.id.from);


        //  spinner.setOnItemSelectedListener(this);

        loadspinnerdata2();

        setCurrentDateOnView();

        fromText = "Cash";

        spinnerText = "food";

        done = (FloatingActionButton) findViewById(R.id.redo);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lNameLayout3.setErrorEnabled(false);

                lNameLayout1.setErrorEnabled(false);

                lNameLayout2.setErrorEnabled(false);

                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                month ++;
                lNameLayout2.setErrorEnabled(false);

                if(paychecked == 1)
                {
                    if(Integer.parseInt(amount.getText().toString()) > db.getCurrentBalance(String.valueOf(month)))
                    {
                        lNameLayout2.setError("Balance is less. Please add income or set as pay later");
                    }

                    else{

                    if (payingfor.getText().toString().matches("") || amount.getText().toString().matches("")) {
                        if (payingfor.getText().toString().matches("")) {
                            lNameLayout1.setErrorEnabled(true);
                            lNameLayout1.setError("*Required field");
                        }

                        if (amount.getText().toString().matches("")) {
                            lNameLayout2.setErrorEnabled(true);
                            lNameLayout2.setError("*Required field");
                        }

                    }

                    else{


                        final String KEY_FROM = "froma";
                        final String KEY_TO = "toa";
                        final String KEY_DATE = "DATE";
                        final String KEY_MONTH = "MONTH";
                        final String KEY_YEAR = "YEAR";

                        final String KEY_AMOUNT = "AMOUNT";
                        final String KEY_DESCRIPTION = "DESCRIPTION";
                        final String KEY_CATEGORY = "CATEGORY";
                        final String KEY_LIABILITY = "LIABILITY";


                        ContentValues values = new ContentValues();
                        values.put(KEY_FROM,from.getSelectedItem().toString());
                        values.put(KEY_TO,payingfor.getText().toString());
                        values.put(KEY_DATE,dpResult.getDayOfMonth());
                        values.put(KEY_MONTH,dpResult.getMonth() + 1);
                        values.put(KEY_YEAR,dpResult.getYear());
                        values.put(KEY_LIABILITY,paychecked);

                        values.put(KEY_AMOUNT,amount.getText().toString());
                        values.put(KEY_DESCRIPTION,description.getText().toString());
                        values.put(KEY_CATEGORY,spinner.getSelectedItem().toString());

                        final String PREFS_NAME = "Expense";
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

                        db.insertData(settings.getString("name", ""), values);

                        finish();
                    }
                }


                }


        else{
                if (payingfor.getText().toString().matches("") || amount.getText().toString().matches("")) {
                    if (payingfor.getText().toString().matches("")) {
                        lNameLayout1.setErrorEnabled(true);
                        lNameLayout1.setError("*Required field");
                    }

                    if (amount.getText().toString().matches("")) {
                        lNameLayout2.setErrorEnabled(true);
                        lNameLayout2.setError("*Required field");
                    }

                }

                else{


                     final String KEY_FROM = "froma";
                     final String KEY_TO = "toa";
                     final String KEY_DATE = "DATE";
                     final String KEY_MONTH = "MONTH";
                     final String KEY_YEAR = "YEAR";

                     final String KEY_AMOUNT = "AMOUNT";
                     final String KEY_DESCRIPTION = "DESCRIPTION";
                     final String KEY_CATEGORY = "CATEGORY";
                    final String KEY_LIABILITY = "LIABILITY";





                    ContentValues values = new ContentValues();
                    values.put(KEY_FROM,from.getSelectedItem().toString());
                    values.put(KEY_TO,payingfor.getText().toString());
                    values.put(KEY_DATE,dpResult.getDayOfMonth());
                    values.put(KEY_MONTH,dpResult.getMonth() + 1);
                    values.put(KEY_YEAR,dpResult.getYear());
                    values.put(KEY_LIABILITY,paychecked);

                    values.put(KEY_AMOUNT,amount.getText().toString());
                    values.put(KEY_DESCRIPTION,description.getText().toString());
                    values.put(KEY_CATEGORY,spinner.getSelectedItem().toString());

                    final String PREFS_NAME = "Expense";
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

                     db.insertData(settings.getString("name", ""), values);

                    finish();
                }}
            }
        });






    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.from)
        {
            fromText = parent.getItemAtPosition(position).toString();
        }
        else if(spinner.getId() == R.id.spinner)
        {
            spinnerText = parent.getItemAtPosition(position).toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.from)
        {
            fromText = "Cash";
        }
        else if(spinner.getId() == R.id.spinner)
        {
            spinnerText = "food";
        }

    }
}
