package com.codeitsuisse.team92.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

public class Pass_Check extends AppCompatActivity {

    FloatingActionButton btn;
    EditText edttxt;

    final Context context = this;

    TextInputLayout lNameLayout;

    TextView email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass__check);

        lNameLayout = (TextInputLayout) findViewById(R.id
                .fNameLayout);

        edttxt = (EditText) findViewById(R.id.editText);
        edttxt.setHint("Password");

        btn = (FloatingActionButton) findViewById(R.id.button1);

        email = (TextView) findViewById(R.id.textView3);
        email.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {

                                         final String PREFS_NAME = "Expense";
                                         SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                                         String pass =settings.getString("password", "");
                                         String email =settings.getString("email","");

                                       WebView  webView = (WebView) findViewById(R.id.webView);
                                         webView.getSettings().setJavaScriptEnabled(true);
                                         webView.getSettings().setAllowFileAccess(true);
                                         webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
                                         webView.getSettings().setBuiltInZoomControls(true);
                                         webView.getSettings().setSupportZoom(true);
                                         webView.setWebViewClient(new WebViewClient());



                                         webView.loadUrl("http://www.skylinelabs.in/credit_s/mail.php?email="+email + "&password=" + pass );
                                     }
                                 }
        );


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String PREFS_NAME = "Expense";
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                String pass =settings.getString("password","");

                if(edttxt.getText().toString().matches(pass))
                {
                    Intent i = new Intent(Pass_Check.this, Display_Main.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    lNameLayout.setErrorEnabled(true);
                    lNameLayout.setError("*Passsword doesn't match");
                }

            }
        });

    }

}
