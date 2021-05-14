package com.example.application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Locale;

public class Start_reading extends Activity {
    private TextToSpeech tts;
    private surface_view recognition;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_reading);
        Button readText = findViewById(R.id.buttonStart);

        readText.setOnTouchListener(new View.OnTouchListener() {
            
            GestureDetector gestureDetector = new GestureDetector(Start_reading.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    tts.speak("Going back to main menu", TextToSpeech.QUEUE_FLUSH, null);
                    Toast.makeText(Start_reading.this, "LISTENING", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Main_appln.class));
                    return super.onDoubleTap(e);
                }

                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    tts.speak("Reading text", TextToSpeech.QUEUE_FLUSH, null);
                    recognition.textRecognizer();
                    return super.onSingleTapConfirmed(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return false;
            }
        });

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.getDefault());
                tts.speak("Double tap anywhere on the screen to return to main menu", TextToSpeech.QUEUE_FLUSH, null);
            }
        });



    }

}
