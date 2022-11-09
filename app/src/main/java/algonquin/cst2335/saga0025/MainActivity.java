package algonquin.cst2335.saga0025;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import algonquin.cst2335.saga0025.databinding.ActivityMainBinding;

/**
 * first page of the app. <br>
 * contains methods to check password.
 * @author Tanzim Ahmed Sagar
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    private TextView title;
    /** the password field to take input*/
    private EditText passwordField;
    /** the login button*/
    private Button loginBtn;
    public static ActivityMainBinding variableBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        passwordField = variableBinding.passwordField;
        loginBtn = variableBinding.loginBtn;
        title = variableBinding.titleText;

        loginBtn.setOnClickListener(clk -> {
            String password = passwordField.getText().toString();
            if(!checkPasswordComplexity(password)) {
                title.setText("You shall not pass!");
            } else {
                title.setText("Your password is complex enough");
            }
        });
    }

    /**
     * Method that checks if the argument {@code String} has
     * <br><u>an Upper Case letter.</u>
     * <br><u>a lower case letter</u>
     * <br><u>a number</u>
     * <br><u>a special symbol {@code (#$%^&*!@?)}</u>
     * @param password the argument string
     * @return boolean
     */
    private boolean checkPasswordComplexity(String password) {
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        Toast noUpperCaseToast = Toast.makeText(getApplicationContext(), "Your password does not have an upper case letter", Toast.LENGTH_SHORT);
        Toast noLowerCaseToast = Toast.makeText(getApplicationContext(), "Your password does not have an lower case letter", Toast.LENGTH_LONG);
        Toast noNumberToast = Toast.makeText(getApplicationContext(), "Your password does not have any numbers", Toast.LENGTH_LONG);
        Toast noSpecialCharToast = Toast.makeText(getApplicationContext(), "Your password does not have any special char (#$%^&*!@?)", Toast.LENGTH_LONG);

        for (int i = 0; i < password.length(); i++) {
            char[] passWord = password.toCharArray();
            String pass = String.valueOf(passWord[i]);
            if (Pattern.matches("[a-z]+", pass)) {
                foundLowerCase = true;
            } else if (Pattern.matches("[A-Z]+", pass)) {
                foundUpperCase = true;
            } else if (Pattern.matches("\\d+", pass)) {
                foundNumber = true;
            } else if (Pattern.matches("[(#$%^&*!@?)]+", pass)) {
                foundSpecial = true;
            }
        }
        if (!foundUpperCase) {
            noUpperCaseToast.show();
            return false;
        } else if (!foundLowerCase) {
            noLowerCaseToast.show();
            return false;
        } else if (!foundNumber) {
            noNumberToast.show();
            return false;
        } else if (!foundSpecial) {
            noSpecialCharToast.show();
            return false;
        } else {
            return true;
        }
    }
}