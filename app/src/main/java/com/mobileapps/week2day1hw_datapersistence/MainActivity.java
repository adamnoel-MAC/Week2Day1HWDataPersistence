package com.mobileapps.week2day1hw_datapersistence;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static com.mobileapps.week2day1hw_datapersistence.Constants.SHARE_PREF_LOCATION;
import static com.mobileapps.week2day1hw_datapersistence.Constants.USER_NAME_KEY;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName() + "_TAG";

    SharedPreferences sharedPreferences;
    EditText etUserName;
    EditText etPassword;
    TextView tvDisplayUserName;
    TextView tvDisplayPW;
    String userNameToDisplay;

    MySQLLiteHelper mySqlLiteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        tvDisplayUserName = findViewById(R.id.tvDisplayUserName);
        tvDisplayPW = findViewById(R.id.tvDisplayPassword);

        sharedPreferences = getSharedPreferences(SHARE_PREF_LOCATION, Context.MODE_PRIVATE);

        mySqlLiteHelper = new MySQLLiteHelper(this,null );

    }

    public void onButtonClick(View view) {
        switch (view.getId()){
            case R.id.btnSaveUserName:  // Saves to Shared Preferences
                if (!etUserName.getText().toString().isEmpty()) {
                    String userName = etUserName.getText().toString();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(USER_NAME_KEY, userName);
                    editor.commit();
                    tvDisplayUserName.setText("PREFERENCES SAVED");
                } else {
                    tvDisplayUserName.setText("ENTER USER NAME");
                }
                break;
            case R.id.btnRetrieveUserName:
                userNameToDisplay = sharedPreferences.getString(USER_NAME_KEY, "NO NAME");
                tvDisplayUserName.setText(userNameToDisplay);
                break;
            case R.id.btnInsertUserName:
                if (!etUserName.getText().toString().isEmpty()) {
                    String userName = etUserName.getText().toString();
                    String password = etPassword.getText().toString();
                    if (mySqlLiteHelper.insertLogin(userName, password)){
                        tvDisplayUserName.setText("DATA SAVED");
                    } else {
                        tvDisplayUserName.setText("DATA SAVE FAILED - TRY AGAIN");
                    }

                } else {
                    tvDisplayUserName.setText("ENTER USER NAME");
                }
                break;
            case R.id.btnRetrieveUserName_db:
                Log.d("TAG", "onButtonClick: Opening Cursor");
                try{
                    Cursor cur = mySqlLiteHelper.getUsersByName(etUserName.getText().toString());
                    if (cur.getCount() > 0) {
                        cur.moveToFirst();
                        int uNameIndex = cur.getColumnIndex(Constants.USER_NAME);
                        int pWordIndex = cur.getColumnIndex(Constants.PASSWORD);
                        Log.d("TAG", "onButtonClick: Opening Cursor - uNameIndex: " + uNameIndex + "; pWordIndex: " + pWordIndex);
                        Log.d("TAG", "onButtonClick: cursor columns : " + cur.getColumnName(0) + "; " + cur.getColumnName(1));
                        String returnUserName = cur.getString(uNameIndex);
                        String returnPassword = cur.getString(pWordIndex);
                        tvDisplayUserName.setText(returnUserName);
                        tvDisplayPW.setText(returnPassword);
                    }else {
                        tvDisplayUserName.setText("USER DOES NOT EXIST");
                    }
                }catch (Exception e){
                    Log.d("TAG", "onButtonClick: SQL EXCEPTION: "); e.getMessage().toString();
                }
                break;
            case R.id.btnEnterNewUser:
                Intent intent = new Intent(this, UserProfileEntry.class);
                startActivity(intent);
                break;
        }
        //case R.id.btnInsertUserName
    }
}
