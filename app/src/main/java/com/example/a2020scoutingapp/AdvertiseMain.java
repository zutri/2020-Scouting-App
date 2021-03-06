package com.example.a2020scoutingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.nearby.connection.Strategy;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class AdvertiseMain extends AppCompatActivity {
    ImageButton cameraButton;
    Button advertiseButton;
    Button dataViewButton;

    final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertise_main);
        cameraButton = findViewById(R.id.cameraButton);
        advertiseButton = findViewById(R.id.advertisebutton);
        dataViewButton= findViewById(R.id.DataViewButton);
        dataViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AdvertiseMain.this,DataView.class));
            }
        });

        advertiseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NearbyCreator.getPermissionToUseNearby(AdvertiseMain.this);
                NearbyCreator nearby;

                    nearby = new NearbyCreator(AdvertiseMain.this, "Labib Master", Strategy.P2P_STAR);



                Toast.makeText(AdvertiseMain.this, "advertising...", Toast.LENGTH_LONG).show();

            }
        });


        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Log.d("Me", "Thinking its working");
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        Log.d("Me", "Hi ");
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
            }
        });



    }

NearbyCreator.OptionsOfAdvertising advertising= new NearbyCreator.OptionsOfAdvertising() {
    @Override
    public void OnDiscoverySuccess() {
        Toast.makeText(AdvertiseMain.this,"Discovery was succesful",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnDiscoveryFailure() {
        Toast.makeText(AdvertiseMain.this,"Discovery was succesful",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnStringReceived(String s) {
        Toast.makeText(AdvertiseMain.this,"String was received",Toast.LENGTH_SHORT).show();
        //TODO Add to database
    }

    @Override
    public void OnStringUpdate() {
        Toast.makeText(AdvertiseMain.this,"Discovery was succesful",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnConnectionGood(String s) {

    }

    @Override
    public void OnConnectionError() {

    }

    @Override
    public void OnConnectionRejected() {

    }

    @Override
    public void OnConnectionDisconnected() {

    }
};


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("View", "I entered the wrong onActivityResult");


        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Log.d("Me", "On Activity result ");

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            BarcodeDetector detector =
                    new BarcodeDetector.Builder(getApplicationContext())
                            .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                            .build();
            if (!detector.isOperational()) {

                return;
            }
            Frame frame = new Frame.Builder().setBitmap(imageBitmap).build();
            SparseArray<Barcode> barcodes = detector.detect(frame);
            Barcode thisCode = barcodes.valueAt(0);
            String finalValue=thisCode.displayValue;
            //TODO Add to database final qr value

        }
    }
}
