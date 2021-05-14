package com.example.application;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.Result;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;


public class QR_and_Barcode extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    public static final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";
    //private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    public static final String URL_NUM = "-?\\d+(\\.\\d+)?";
    private static final int REQUEST_CAMERA = 1;
    public Matcher m1, m2 = null;
    Intent call_scanqr;
    private TextToSpeech tts;
    private ZXingScannerView scannerView;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(QR_and_Barcode.this, "Permission is granted", Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }
        }
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(QR_and_Barcode.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }


    @Override
    public void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if (scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
        tts.stop();
    }

    public void displayAlertMessage(String msg, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(QR_and_Barcode.this)
                .setMessage(msg)
                .setPositiveButton("Ok", listener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void handleResult(Result rawResult) {
        final String scanResult = rawResult.getText();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setNegativeButton("Ok", (dialogInterface, i) -> {
//                scannerView.resumeCameraPreview(QR_and_Barcode.this);
            Intent callqr = new Intent(QR_and_Barcode.this, Main_appln.class);
            startActivity(callqr);
        });
        builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Pattern p = Pattern.compile(URL_REGEX);
                Pattern p3 = Pattern.compile(URL_NUM);
                Matcher m = p.matcher(scanResult);//replace with string to compare
                Matcher m3 = p3.matcher(scanResult);
                if (m.find()) {
                    tts.speak("Opening the Link", TextToSpeech.QUEUE_FLUSH, null, null);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(scanResult));
                    startActivity(intent);

                } else if (m3.find()) {
                    tts.speak("Message only contains Number", TextToSpeech.QUEUE_FLUSH, null, null);
                    call_scanqr = new Intent(QR_and_Barcode.this, Main_appln.class);
                    startActivity(call_scanqr);
                } else {
                    tts.speak("Message contain Only Text message", TextToSpeech.QUEUE_FLUSH, null, null);
                    call_scanqr = new Intent(QR_and_Barcode.this, Main_appln.class);
                    startActivity(call_scanqr);
                }
            }
        });

        builder.setPositiveButton("Scan again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                scannerView.resumeCameraPreview(QR_and_Barcode.this);
            }
        });
        builder.setMessage(scanResult);
        AlertDialog alert = builder.create();
        alert.show();
        Pattern p1 = Pattern.compile(URL_REGEX);
        Pattern p2 = Pattern.compile(URL_NUM);
        m1 = p1.matcher(scanResult);
        m2 = p2.matcher(scanResult);
        if (m1.find()) {
            tts.speak("String contains URL  " + scanResult, TextToSpeech.QUEUE_FLUSH, null, null);
        } else if (m2.find()) {
            tts.speak("String Contains Number" + scanResult, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak(scanResult, TextToSpeech.QUEUE_FLUSH, null, null);
        }


    }
}