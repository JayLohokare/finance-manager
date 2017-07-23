package com.codeitsuisse.team92.expensetracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

public class Edit_data extends Activity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    EditText payingfor, amount, description;
    FloatingActionButton done, del;
    private TextView tvDisplayDate;
    private DatePicker dpResult;
    private Button btnChangeDate;

    private int year;
    private int month;
    private int day;
    String id5;


    String to ,froms;

    int amounti;
    String descriptions, amounts;
    String category;

    Spinner spinner, from;

    String key;

    Switch pay;

    int paychecked;

    String label, spinnerText, fromText;
    Boolean Selected, spinnerB, fromB = false;


    TextInputLayout lNameLayout3, lNameLayout1, lNameLayout2;


    String finaldate;


    public void setCurrentDateOnView() {

        // set current date into textview
       /* tvDisplayDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(year).append("-").append(month).append("-").append(day));
/=*/
        finaldate = new StringBuilder()
                // Month is 0 based, just add 1
                .append(year).append("-").append(month).append("-").append(day).toString();
        // set current date into datepicker
        dpResult.init(year, month - 1, day, null);

    }


    private void loadspinnerdata() {
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        List<String> labels = db.getCat();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        int spinnerPosition = dataAdapter.getPosition(category);

//set the default according to value
        spinner.setSelection(spinnerPosition);
    }

    private void loadspinnerdata2() {
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        List<String> labels = db.getFrom();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        from.setAdapter(dataAdapter);

        int spinnerPosition = dataAdapter.getPosition(froms);

//set the default according to value
        from.setSelection(spinnerPosition);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);



        payingfor = (EditText) findViewById(R.id.editText5);
        amount = (EditText) findViewById(R.id.editText6);
        description = (EditText) findViewById(R.id.editText7);

        tvDisplayDate = (TextView) findViewById(R.id.textView4);
        dpResult = (DatePicker) findViewById(R.id.datePicker);

        pay = (Switch) findViewById(R.id.switch1);
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

      //  loadspinnerdata2();

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             id5 = extras.getString("id");
            Toast.makeText(getApplicationContext(), id5, Toast.LENGTH_LONG).show();

        }

        List<ExpenseDetails> fetch = db.getDetails(id5);
        for (ExpenseDetails cn1 : fetch)
        {
            froms = cn1.getFrom();
            to = cn1.getTo();
            day = cn1.getDate();
            month = cn1.getMonth();
            amounts = String.valueOf(cn1.getAmount());
           year = cn1.getYear();
            key = String.valueOf(cn1.getSr());

            int paaaay = cn1.getLiability();

            if(paaaay == 1) {
                pay.setChecked(true);
                paychecked = 1;
                pay.setText("Paid");
            }
            else{
                pay.setChecked(false);
                paychecked = 0;
                pay.setText("Pay later");
            }



            payingfor.setText(String.valueOf(cn1.getTo()));
            amount.setText(String.valueOf(cn1.getAmount()));
            description.setText(String.valueOf(cn1.getDescription()));

            category = cn1.getCategory();
        }
        loadspinnerdata2();

        setCurrentDateOnView();


        fromText = "Cash";

        spinnerText = "food";

        del = (FloatingActionButton) findViewById(R.id.del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.deleteData(id5);
                finish();

            }
        });

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
                    if(Integer.parseInt(amount.getText().toString()) > ( db.getCurrentBalance(String.valueOf(month)) + Integer.parseInt(amounts)))
                    {
                        lNameLayout2.setError("Balance is less. Please add income or set as pay later");
                    }

                    else {

                        if (payingfor.getText().toString().matches("") || amount.getText().toString().matches("")) {
                            if (payingfor.getText().toString().matches("")) {
                                lNameLayout1.setErrorEnabled(true);
                                lNameLayout1.setError("*Required field");
                            }

                            if (amount.getText().toString().matches("")) {
                                lNameLayout2.setErrorEnabled(true);
                                lNameLayout2.setError("*Required field");
                            }

                        } else {


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
                            values.put(KEY_FROM, from.getSelectedItem().toString());
                            values.put(KEY_TO, payingfor.getText().toString());
                            values.put(KEY_DATE, dpResult.getDayOfMonth());
                            values.put(KEY_MONTH, dpResult.getMonth() + 1);
                            values.put(KEY_YEAR, dpResult.getYear());

                            values.put(KEY_AMOUNT, amount.getText().toString());
                            values.put(KEY_DESCRIPTION, description.getText().toString());
                            values.put(KEY_CATEGORY, spinner.getSelectedItem().toString());
                            values.put(KEY_LIABILITY,paychecked);


                            final String PREFS_NAME = "Expense";
                            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

                            db.updateData(id5, values);



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

                } else {


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
                    values.put(KEY_FROM, from.getSelectedItem().toString());
                    values.put(KEY_TO, payingfor.getText().toString());
                    values.put(KEY_DATE, dpResult.getDayOfMonth());
                    values.put(KEY_MONTH, dpResult.getMonth() + 1);
                    values.put(KEY_YEAR, dpResult.getYear());

                    values.put(KEY_AMOUNT, amount.getText().toString());
                    values.put(KEY_DESCRIPTION, description.getText().toString());
                    values.put(KEY_CATEGORY, spinner.getSelectedItem().toString());
                    values.put(KEY_LIABILITY,paychecked);


                    final String PREFS_NAME = "Expense";
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

                    db.updateData(id5, values);



                    finish();

                }
            }}
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
       finish();


    }
}










