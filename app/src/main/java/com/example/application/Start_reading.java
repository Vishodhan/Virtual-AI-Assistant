package com.example.application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Locale;

public class Start_reading extends Activity {
    TextToSpeech tts2;
    Button readText;
    TextView textView;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_reading);
        readText = findViewById(R.id.buttonStart);
        textView = findViewById(R.id.textView);


        Intent intent = getIntent();
        String stringResult = intent.getStringExtra("TextResult");
        assert stringResult != null;
        Log.d("stringResult", stringResult);
        textView = findViewById(R.id.textView);
        textView.setText(stringResult);

        tts2 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts2.setLanguage(Locale.getDefault());
                    tts2.speak("Hello World", TextToSpeech.QUEUE_FLUSH, null, null);
                }
                readText.setOnTouchListener(new View.OnTouchListener() {
                    GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onDoubleTap(MotionEvent e) {
                            tts2.speak("Going back to main menu", TextToSpeech.QUEUE_FLUSH, null, null);
                            startActivity(new Intent(getApplicationContext(), Main_appln.class));
                            return super.onDoubleTap(e);
                        }

                        @Override
                        public boolean onSingleTapConfirmed(MotionEvent e) {
                            tts2.speak("Reading text", TextToSpeech.QUEUE_FLUSH, null, null);
                            startActivity(new Intent(getApplicationContext(), surface_view.class));
                            return super.onSingleTapConfirmed(e);
                        }
                    });

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        gestureDetector.onTouchEvent(event);
                        return false;
                    }
                });
                tts2.speak(stringResult, TextToSpeech.QUEUE_FLUSH, null, null);
            }

        });


//        tts2 = new TextToSpeech(this,   new TextToSpeech.OnInitListener() {
//                    @Override
//                    public void onInit(int status) {
//                        if (tts2.getEngines().size() == 0) {
//                            Toast.makeText(Start_reading.this, "Engine Not Available", Toast.LENGTH_SHORT).show();
//                        }
//                        if (status != TextToSpeech.ERROR) {
//                            Log.d("tts2Engine",tts2.getDefaultEngine());
//                            tts2.setLanguage(Locale.getDefault());
//                        }
//                    }
//                });

    }//oncreate

    @Override
    protected void onDestroy() {
        tts2.stop();
        super.onDestroy();
    }
}