package algonquin.cst2335.saga0025;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import algonquin.cst2335.saga0025.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding variableBinding;
    private static final String TAG = "MainActivity";
    private String onStartMsg = "The application is now visible on screen.";
    private String onResumeMsg = "The application is now responding to user input";
    private String onPauseMsg = "The application no longer responds to user input";
    private String onStopMsg = "The application is no longer visible.";
    private String onDestroyMsg = "Any memory used by the application is freed.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String emailAddress = prefs.getString("loginName", "");
        try {
            variableBinding.editTextTextEmailAddress.setText(emailAddress);
        } catch (NullPointerException NullEx) {}

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());
        variableBinding.loginButton.setOnClickListener( click-> {
            SharedPreferences.Editor editor = prefs.edit();
            String userEmailInput = variableBinding.editTextTextEmailAddress.getText().toString();
            editor.putString("loginName", userEmailInput);
            editor.apply();
            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
            nextPage.putExtra("EmailAddress", userEmailInput);
            startActivity(nextPage);
        } );
        Log.w( TAG, "In onCreate() - Loading Widgets" );
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG, onStartMsg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, onResumeMsg);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, onPauseMsg);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG, onStopMsg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, onDestroyMsg);
    }
}