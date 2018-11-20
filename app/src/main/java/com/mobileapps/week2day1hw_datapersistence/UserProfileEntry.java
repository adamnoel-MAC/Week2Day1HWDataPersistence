package com.mobileapps.week2day1hw_datapersistence;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static com.mobileapps.week2day1hw_datapersistence.Constants.DATABASE_VERSION;
import static com.mobileapps.week2day1hw_datapersistence.Constants.DATABSE_NAME;
import static com.mobileapps.week2day1hw_datapersistence.Constants.PASSWORD;
import static com.mobileapps.week2day1hw_datapersistence.Constants.SHARE_PREF_LOCATION;
import static com.mobileapps.week2day1hw_datapersistence.Constants.TABLE_NAME;
import static com.mobileapps.week2day1hw_datapersistence.Constants.USER_NAME;
import static com.mobileapps.week2day1hw_datapersistence.Constants.USER_NAME_KEY;


public class UserProfileEntry extends AppCompatActivity {

    EditText etFirstName;
    EditText etPassword;

    TextView tvDisplayStatus;

    SharedPreferences sharedPreferences;
    MySQLLiteHelper mySqlLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_entry);

        tvDisplayStatus = findViewById(R.id.tvDisplayStatus);
        etFirstName = findViewById(R.id.etFirstName);
        etPassword = findViewById(R.id.etLastName);

        sharedPreferences = getSharedPreferences(SHARE_PREF_LOCATION, Context.MODE_PRIVATE);

        mySqlLiteHelper = new MySQLLiteHelper(this,null );

        String message = "Hello " + sharedPreferences.getString(USER_NAME_KEY, "NO NAME");
        tvDisplayStatus.setText(message);
    }

    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btnOK:
                if (!etFirstName.getText().toString().isEmpty()) {
                    String userName = etFirstName.getText().toString();
                    String password = etPassword.getText().toString();
                    Log.d("TAG", "onButtonClick: UserName, Password: " + userName + ", " + password);
                    if (mySqlLiteHelper.insertContact(userName, password)){
                        tvDisplayStatus.setText("DATA SAVED");
                    } else {
                        tvDisplayStatus.setText("DATA SAVE FAILED - TRY AGAIN");
                    }

                } else {
                    tvDisplayStatus.setText("ENTER USER NAME");
                }
                break;
            case R.id.btnDone:
                Intent sendIntent = new Intent(this, MainActivity.class);
                startActivity(sendIntent);
                finish();
        }
    }
}
