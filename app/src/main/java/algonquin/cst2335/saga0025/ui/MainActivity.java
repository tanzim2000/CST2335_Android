package algonquin.cst2335.saga0025.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import algonquin.cst2335.saga0025.data.MainViewModel;
import algonquin.cst2335.saga0025.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding variableBinding;
    private MainViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(MainViewModel.class);
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());
        variableBinding.textView.setText((CharSequence) model.editString);
        variableBinding.myButton.setOnClickListener(click -> {
            model.editString.postValue(variableBinding.textView.getText().toString());
        });
        model.editString.observe(this, s -> {
            variableBinding.textView.setText("Your edit text has "+ s);
        });
    }
}