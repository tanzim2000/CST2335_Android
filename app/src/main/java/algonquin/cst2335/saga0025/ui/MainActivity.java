package algonquin.cst2335.saga0025.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.widget.Toast;

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
        variableBinding.textView.setText(model.editString.toString());
        variableBinding.myButton.setOnClickListener(click -> model.editString.postValue(variableBinding.textView.getText().toString()));
        model.editString.observe(this, s -> variableBinding.textView.setText("Your edit text has "+ s));
        model.isSelected.observe(this, selected -> {
            variableBinding.checkBox.setChecked(selected);
            variableBinding.radioButton.setChecked(selected);
            variableBinding.switch1.setChecked(selected);
            Toast.makeText(this,"Now the value is: " + selected, Toast.LENGTH_SHORT).show();
        });
        variableBinding.checkBox.setOnCheckedChangeListener( (btn, isSelected) -> model.isSelected.postValue(isSelected));
        variableBinding.switch1.setOnCheckedChangeListener( (btn, isSelected) -> model.isSelected.postValue(isSelected));
        variableBinding.radioButton.setOnCheckedChangeListener( (btn, isSelected) -> model.isSelected.postValue(isSelected));
        variableBinding.myImageButton.setOnClickListener(click -> Toast.makeText(this,"The width = " + click.getWidth() + " and height = " + click.getHeight(), Toast.LENGTH_SHORT).show());
    }
}