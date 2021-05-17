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
    TextToSpeech tts;
    Button readText;
    TextView textView;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_reading);
        readText = findViewById(R.id.buttonStart);
        textView = findViewById(R.id.textView);

        tts = new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(tts.getEngines().size()==0)
                            Toast.makeText(Start_reading.this, "Engine Not Available", Toast.LENGTH_SHORT).show();
                        if (status != TextToSpeech.ERROR) {
                            tts.setLanguage(Locale.getDefault());
                            }
                    }
                });




        readText.setOnTouchListener(new View.OnTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(Start_reading.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    tts.speak("Going back to main menu", TextToSpeech.QUEUE_FLUSH, null);
                     startActivity(new Intent(getApplicationContext(), Main_appln.class));
                    return super.onDoubleTap(e);
                }

                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    tts.speak("Reading text", TextToSpeech.QUEUE_FLUSH, null);
                    startActivity(new Intent(getApplicationContext(),surface_view.class));
                    return super.onSingleTapConfirmed(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return false;
            }
        });



        Intent intent = getIntent();
        String stringResult = intent.getStringExtra("TextResult");
        assert stringResult != null;
        Log.d("stringResult", stringResult);
        textView = findViewById(R.id.textView);
        textView.setText(stringResult);
        tts.speak(stringResult, TextToSpeech.QUEUE_FLUSH, null, null);


    }

    public void onPause(){
        if(tts !=null){
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }




}
