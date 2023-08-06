package com.example.allergendetector;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.Uri;
import android.widget.Toast;

import com.example.allergendetector.ml.Model;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;


public class demoML3 extends AppCompatActivity {

    ImageView predictImage;
    Button selectButton, captureButton, predictButton;
    TextView resultTextView;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_ml3);




        predictImage = findViewById(R.id.predict_image);
        selectButton = findViewById(R.id.select_button);
        captureButton = findViewById(R.id.capture_button);
        predictButton = findViewById(R.id.predict_button);
        resultTextView = findViewById(R.id.result_textView);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
                startActivityForResult(intent, 12);

            }
        });

        predictButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {




                try {
                    Model model = Model.newInstance(demoML3.this);

                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.UINT8);

                    bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
                    inputFeature0.loadBuffer(TensorImage.fromBitmap(bitmap).getBuffer());

                    // Runs model inference and gets result.
                    Model.Outputs outputs = model.process(inputFeature0);

                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
                    resultTextView.setText(getMax(outputFeature0.getFloatArray())+"");
                    // Releases model resources if no longer used.
                    model.close();
                } catch (IOException e) {

                        String errorMessage = "Error: " + e.getMessage();





                }
            }
        });

    }


  int getMax(float[] arr){
     int max = 0;
     for(int i =0; i<arr.length; i++){
         if(arr[i]>arr[max])
             max=i;
     }
     return max;
  }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==10){
            if(data!=null){
                Uri  uri = data.getData();
                try{
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                    predictImage.setImageBitmap(bitmap);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        else if(requestCode==12){
            bitmap = (Bitmap) data.getExtras().get("data");
            predictImage.setImageBitmap(bitmap);
        }
        else if( requestCode==12 && resultCode==RESULT_OK){
            try {
                Model model = Model.newInstance(demoML3.this);

                // Creates inputs for reference.
                TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.UINT8);

                bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
                inputFeature0.loadBuffer(TensorImage.fromBitmap(bitmap).getBuffer());

                // Runs model inference and gets result.
                Model.Outputs outputs = model.process(inputFeature0);

                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
                resultTextView.setText(getMax(outputFeature0.getFloatArray())+"");
                // Releases model resources if no longer used.
                model.close();
            } catch (IOException e) {

                String errorMessage = "Error: " + e.getMessage();
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();



            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}