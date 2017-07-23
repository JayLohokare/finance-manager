package com.codeitsuisse.team92.expensetracker;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputLayout;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.github.clans.fab.FloatingActionButton;

public class Sign_Up extends Activity{

    FloatingActionButton btn;
    EditText edttxt1,edttxt2, edttxt3, edttxt4;

    final Context context = this;

    public static String name;




    TextInputLayout lNameLayout,lNameLayout1,lNameLayout2,lNameLayout4;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

            getWindow().setExitTransition(new Explode());
            // inside your activity (if you did not enable transitions in your theme)

        }

        setContentView(R.layout.activity_main);
        //this.getActionBar().hide();


        lNameLayout = (TextInputLayout) findViewById(R.id
                .fNameLayout);
        lNameLayout1 = (TextInputLayout) findViewById(R.id
                .fNameLayout2);
        lNameLayout2 = (TextInputLayout) findViewById(R.id
                .fNameLayout3);
        lNameLayout4 = (TextInputLayout) findViewById(R.id
                .fNameLayout4);


        btn = (FloatingActionButton) findViewById(R.id.button1);

        edttxt1 = (EditText) findViewById(R.id.editText3);
        edttxt1.setHint("User name");

        edttxt2 = (EditText) findViewById(R.id.editText1);
        edttxt2.setHint("Password");

        edttxt3 = (EditText) findViewById(R.id.editText2);
        edttxt3.setHint("Confirm Password");

        edttxt4 = (EditText) findViewById(R.id.editText4);
        edttxt4.setHint("Email ID");

        edttxt2.clearFocus();
        edttxt1.requestFocus();


        final String PREFS_NAME = "Expense";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String PREFS_NAME = "Expense";
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);


                 name = edttxt1.getText().toString();
                String password = edttxt2.getText().toString();
                String passwordc = edttxt3.getText().toString();
                String email = edttxt4.getText().toString();

                settings.edit().putString("name", name).commit();
                settings.edit().putString("password", password).commit();
                settings.edit().putString("email", email).commit();

                lNameLayout.setErrorEnabled(false);

                lNameLayout1.setErrorEnabled(false);

                lNameLayout2.setErrorEnabled(false);

                lNameLayout4.setErrorEnabled(false);


                if (edttxt1.getText().toString().matches("") || edttxt4.getText().toString().matches("") || !edttxt2.getText().toString().matches(edttxt3.getText().toString())  || (edttxt3.getText().toString().trim().length() < 1) || (edttxt2.getText().toString().trim().length() < 1)) {
                    if (edttxt1.getText().toString().matches("")) {
                        lNameLayout.setErrorEnabled(true);
                        lNameLayout.setError("*Required field");
                    }

                    if (edttxt4.getText().toString().matches("")) {
                        lNameLayout4.setErrorEnabled(true);
                        lNameLayout4.setError("*Required field");
                    }

                    if (!edttxt3.getText().toString().matches(edttxt2.getText().toString())) {
                        lNameLayout2.setErrorEnabled(true);
                        lNameLayout2.setError("*Password doesnt match");
                    }

                    if (edttxt2.getText().toString().trim().length() < 1) {
                        lNameLayout1.setErrorEnabled(true);
                        lNameLayout1.setError("*Minimum 6 characters");
                    }
                }

                else{
                    settings.edit().putString("name", name).commit();
                    settings.edit().putString("password", password).commit();
                    settings.edit().putString("email", email).commit();

                   name = settings.getString("name", name);


                    DatabaseHandler db = new DatabaseHandler(context);



                    db.createFrom();
                    db.insertFrom();

                    db.createCat();
                    db.insertcat();

                    db.createIncome();


                    Intent i = new Intent(Sign_Up.this, Display_Main.class);
                    startActivity(i);

                    settings.edit().putBoolean("my_first_time", false).commit();
                    finish();


                }


            }



        });

    }


}


