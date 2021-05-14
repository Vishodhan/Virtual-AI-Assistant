package com.example.application;
//MainActivity.java in hitesh's app

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.Locale;

import static android.Manifest.permission.CAMERA;

public class surface_view extends AppCompatActivity {

    TextToSpeech  t2;
    private TextView textView;
    private SurfaceView surfaceView;
    private CameraSource cameraSource;
    private Button svback;
    private String stringResult = null;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surfaceview);
        svback =  findViewById(R.id.svback);


        svback.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(surface_view.this, new GestureDetector.SimpleOnGestureListener()
            {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    t2.speak("Going back to main menu", TextToSpeech.QUEUE_FLUSH, null);
                    startActivity(new Intent(getApplicationContext(), Main_appln.class));
                    return super.onDoubleTap(e);
                }

                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    t2.speak("Tap again to go back to main menu", TextToSpeech.QUEUE_FLUSH, null);
                    return super.onSingleTapConfirmed(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return false;
            }
        });

        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, PackageManager.PERMISSION_GRANTED);
        t2 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            }
        });

        t2 = new TextToSpeech(getApplicationContext(), status -> {
            t2.setLanguage(Locale.getDefault());
            t2.speak("Double tap anywhere on the screen to return to main menu", TextToSpeech.QUEUE_FLUSH, null);
        });

        textRecognizer();
    }

    @Override
    protected void onDestroy() {
        cameraSource.release();
        t2.stop();
        super.onDestroy();

    }

    public void textRecognizer() {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                .setRequestedPreviewSize(1080, 720)
                .build();

        surfaceView = findViewById(R.id.surfaceView);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(surface_view.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                try {
                    cameraSource.start(surfaceView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {

                SparseArray<TextBlock> sparseArray = detections.getDetectedItems();
                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 0; i < sparseArray.size(); ++i) {
                    TextBlock textBlock = sparseArray.valueAt(i);
                    if (textBlock != null && textBlock.getValue() != null) {

                        stringBuilder.append(textBlock.getValue() + " ");
                    }
                }

                stringResult = stringBuilder.toString();
                Log.d("stringResult", stringResult);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(() -> resultObtained(), 5000);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    private void resultObtained() {
        setContentView(R.layout.activity_start_reading);
        textView = findViewById(R.id.textView);
        textView.setText(stringResult);
    t2.speak(stringResult, TextToSpeech.QUEUE_FLUSH, null, null);

    }

}