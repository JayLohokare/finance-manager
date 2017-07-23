package com.codeitsuisse.team92.expensetracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

public class Add_Income extends Activity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

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



    TextInputLayout lNameLayout3,lNameLayout1,lNameLayout2;


    String finaldate;



    public void setCurrentDateOnView() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview

        finaldate= new StringBuilder()
                // Month is 0 based, just add 1
                .append(year).append("-").append(month + 1).append("-").append(day).toString();
        // set current date into datepicker
        dpResult.init(year, month, day, null);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__income);

        amount = (EditText) findViewById(R.id.amount);

        dpResult = (DatePicker) findViewById(R.id.datePicker);






        done = (FloatingActionButton) findViewById(R.id.redo);


        lNameLayout2 = (TextInputLayout) findViewById(R.id
                .fNameLayout2);


        lNameLayout2.setErrorEnabled(false);

        setCurrentDateOnView();

        done = (FloatingActionButton) findViewById(R.id.redo);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lNameLayout2.setErrorEnabled(false);



                    if (amount.getText().toString().matches("")) {
                        lNameLayout2.setErrorEnabled(true);
                        lNameLayout2.setError("*Required field");
                    }



                else{



                    final String KEY_DATE = "DATE";
                    final String KEY_MONTH = "MONTH";
                    final String KEY_YEAR = "YEAR";

                    final String KEY_AMOUNT = "AMOUNT";

                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());

                    ContentValues values = new ContentValues();
                    values.put(KEY_DATE,dpResult.getDayOfMonth());
                    values.put(KEY_MONTH,dpResult.getMonth() + 1);
                    values.put(KEY_YEAR,dpResult.getYear());
                    values.put(KEY_AMOUNT,amount.getText().toString());

                    final String PREFS_NAME = "Expense";
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

                    db.insertIncome(settings.getString("name", ""), values);

                    finish();
                }
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

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
