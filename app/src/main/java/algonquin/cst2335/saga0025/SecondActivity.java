package algonquin.cst2335.saga0025;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import algonquin.cst2335.saga0025.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {
    private ActivitySecondBinding variableBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        variableBinding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());
        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        variableBinding.titleWelcome.setText("Welcome Back, " + emailAddress + "!");
        variableBinding.callButton.setOnClickListener(click -> {
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + variableBinding.editTextPhone.getText().toString()));
            startActivity(call);
        });
        File myImage = new File (getFilesDir(), "Picture.png");
        if (myImage.exists()) {
            Bitmap dp = BitmapFactory.decodeFile(getFilesDir() + "/" + "Picture.png");
            variableBinding.profileImage.setImageBitmap (dp);
        }
        variableBinding.cngPngBtn.setOnClickListener(click -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraResult.launch(cameraIntent);
        });
    }
    ActivityResultLauncher<Intent> cameraResult = registerForActivityResult( new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            Bitmap thumbnail = data.getParcelableExtra("data");
            variableBinding.profileImage.setImageBitmap( thumbnail );
            FileOutputStream fOut = null;
            try {
                fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
}