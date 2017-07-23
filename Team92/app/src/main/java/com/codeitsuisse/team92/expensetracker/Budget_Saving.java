package com.codeitsuisse.team92.expensetracker;

import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class Budget_Saving extends AppCompatActivity {

    TextView budgettxt,  savingstxt;
    EditText budgetedit, savingsedit;
    Button budgetbtn,  savingbtn;

    private Toolbar mToolbar;

    String svBudget, svSavings;
    TextInputLayout lNameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget__saving);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setElevation(5);
        getSupportActionBar().setTitle("FindX");



        budgetedit = (EditText) findViewById(R.id.editText5);
        savingsedit = (EditText) findViewById(R.id.editText10);

        final String PREFS_NAME = "Expense";
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        svSavings = settings.getString("savings","");
        svBudget = settings.getString("budget","");


        budgetedit.setText(svBudget);
        savingsedit.setText(svSavings);

        lNameLayout = (TextInputLayout) findViewById(R.id
                .fNameLayout);
        lNameLayout.setErrorEnabled(false);

        budgetbtn = (Button) findViewById(R.id.button2);
        savingbtn = (Button) findViewById(R.id.button3);


        // budgetbtn.setVisibility(View.GONE);
        budgetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                Calendar c = Calendar.getInstance();
               int year = c.get(Calendar.YEAR);
               int month = c.get(Calendar.MONTH);
                month ++;
                lNameLayout.setErrorEnabled(false);

                Log.d(c.toString(), String.valueOf(month));

                settings.edit().putString("budget", budgetedit.getText().toString()).commit();
                if((Integer.valueOf(budgetedit.getText().toString())) > (Integer.valueOf(db.getTotalIncome(String.valueOf(month)))))
                {
                    lNameLayout.setError("*Budget cant be greater than Income. Please add income");
                    budgetedit.setText("");

                }
                else
                {
                    settings.edit().putString("alert", budgetedit.getText().toString()).commit();
                }
            }
        });

      //  alertbtn.setVisibility(View.GONE);


    //    savingbtn.setVisibility(View.GONE);
        savingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                settings.edit().putString("savings", savingsedit.getText().toString()).commit();
           //     savingsedit.setVisibility(View.GONE);
             //   savingbtn.setVisibility(View.GONE);
            //    savingstxt.setText(settings.getString("savings", ""));
            //    savingstxt.setVisibility(View.VISIBLE);
            }
        });











    }


}
