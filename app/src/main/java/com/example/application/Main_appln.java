package com.example.application;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;

public class Main_appln extends AppCompatActivity {
    Button btn_obj, btn_code, btn_text, btn_facedetect, btn_chatbot;
    SpeechRecognizer recognizer;
    private TextToSpeech tts;
    int Count=0;
    Timer delay;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_obj = findViewById(R.id.btn_obj);
        btn_code = findViewById(R.id.btn_code);
        btn_text = findViewById(R.id.btn_text);
        btn_facedetect = findViewById(R.id.btn_face);
        btn_chatbot = findViewById(R.id.btn_chatbot);
        recognizer = SpeechRecognizer.createSpeechRecognizer(this);

        tts = new TextToSpeech(getApplicationContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.ENGLISH);
            }
        });
//
        Dexter.withContext(this)
                .withPermission(Manifest.permission.RECORD_AUDIO)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        System.exit(0);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
        if(Count==0){
        initializeTextToSpeech();
        Count+=1;}
//        initializeResult();


//        btn_chatbot.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                gestureDetector.onTouchEvent(motionEvent);
//                return false;
//            }
//
//            GestureDetector gestureDetector = new GestureDetector(Main_appln.this, new GestureDetector.SimpleOnGestureListener() {
//                @Override
//                public boolean onDoubleTap(MotionEvent e) {
//                    tts.speak("Launching Chatbot", TextToSpeech.QUEUE_FLUSH, null);
//                    startActivity(new Intent(getApplicationContext(), Chatbot_ui.class));
//                    return super.onDoubleTap(e);
//                }
//
//                @Override
//                public boolean onSingleTapConfirmed(MotionEvent e) {
//                    tts.speak("Chatbot.  Double tap to launch", TextToSpeech.QUEUE_FLUSH, null);
//                    return super.onSingleTapConfirmed(e);
//                }
//            });
//        });

//        btn_text.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                gestureDetector.onTouchEvent(motionEvent);
//                return false;
//            }
//
//            GestureDetector gestureDetector = new GestureDetector(Main_appln.this, new GestureDetector.SimpleOnGestureListener() {
//                @Override
//                public boolean onDoubleTap(MotionEvent e) {
//                    tts.speak("Launching Text recognition", TextToSpeech.QUEUE_FLUSH, null);
//                    startActivity(new Intent(getApplicationContext(), text_recognition.class));
//                    return super.onDoubleTap(e);
//                }
//
//                @Override
//                public boolean onSingleTapConfirmed(MotionEvent e) {
//                    tts.speak("Text recognition.  Double tap to launch", TextToSpeech.QUEUE_FLUSH, null);
//                    return super.onSingleTapConfirmed(e);
//                }
//            });
//        });
//
//        btn_facedetect.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent motionEvent) {
//                gestureDetector.onTouchEvent(motionEvent);
//                return false;
//            }
//
//            GestureDetector gestureDetector = new GestureDetector(Main_appln.this, new GestureDetector.SimpleOnGestureListener() {
//                @Override
//                public boolean onDoubleTap(MotionEvent e) {
//                    tts.speak("Launching Face Detection", TextToSpeech.QUEUE_FLUSH, null);
//                    startActivity(new Intent(getApplicationContext(), fDetectorActivity.class));
//                    return super.onDoubleTap(e);
//                }
//
//                @Override
//                public boolean onSingleTapConfirmed(MotionEvent e) {
//                    tts.speak("Face Detection.  Double tap to launch", TextToSpeech.QUEUE_FLUSH, null);
//                    return super.onSingleTapConfirmed(e);
//                }
//            });
//        });


//        btn_obj.setOnClickListener(v -> {
//            tts.speak("Hello worldd",TextToSpeech.QUEUE_FLUSH,null);
//            recognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
//            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); //added
//            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi Speak Something");
//            try {
//                recognizer.startListening(intent);
//            } catch (ActivityNotFoundException e) {
//                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

//        btn_code.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                gestureDetector.onTouchEvent(motionEvent);
//                return false;
//            }
//
//            GestureDetector gestureDetector = new GestureDetector(Main_appln.this, new GestureDetector.SimpleOnGestureListener() {
//                @Override
//                public boolean onDoubleTap(MotionEvent e) {
//                    tts.speak("Launching QR Code scanner", TextToSpeech.QUEUE_FLUSH, null);
//                    startActivity(new Intent(getApplicationContext(), QR_and_Barcode.class));
//                    return super.onDoubleTap(e);
//                }
//
//                @Override
//                public boolean onSingleTapConfirmed(MotionEvent e) {
//                    tts.speak("QR Code Scanner.  Double tap to launch", TextToSpeech.QUEUE_FLUSH, null);
//                    return super.onSingleTapConfirmed(e);
//                }
//            });
//        });


    }//oncreate ends here

    private void initializeTextToSpeech() {
        tts = new TextToSpeech(this, status -> {
            if (tts.getEngines().size() == 0) {
                Toast.makeText(Main_appln.this, "Engine is not available", Toast.LENGTH_LONG).show();
            } else {
                speak("Hi! Welcome to Virohit app.");
//                        We have 5 modules" +
//                        "Object detection" + " face detection " + " QR Code Scanner " + " Text Recognition " + " Chatbot ");
            }
        });
    }


    private void speak(String msg) {
        tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    //
    private void initializeResult() {
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            recognizer = SpeechRecognizer.createSpeechRecognizer(this);
            recognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onResults(Bundle results) {
                    ArrayList<String> result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    Toast.makeText(Main_appln.this, "" + result.get(0), Toast.LENGTH_LONG).show();
                    response(result.get(0));
                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }

            });
        } else {
            Toast.makeText(this, "Speech recognizer unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    //
    private void response(String message) {
        String msg = message.toLowerCase();

        switch (msg) {
            case "hi":
                speak("Hello sir! how are you?");
                break;

            case "open object detection":
                speak("Opening  object recognition");
                startActivity(new Intent(Main_appln.this, DetectorActivity.class));
                break;

            case "open face detection":
                speak("Opening face detection");
                startActivity(new Intent(Main_appln.this, fDetectorActivity.class));
                break;

            case "open qr code scanner":
                speak("Opening QR Code Scanner");
                startActivity(new Intent(Main_appln.this, QR_and_Barcode.class));
                break;

            case "open chatbot":
                speak("Opening Chatbot");
                startActivity(new Intent(Main_appln.this, Chatbot_ui.class));
                break;
            case "open text recognition":
                speak("Opening text recognition");
                startActivity(new Intent(Main_appln.this, surface_view.class));
                break;

            default:
                speak("Please speak a valid input");
        }
    }

    public void startRecording(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Which module do you want to open?");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        try {
            recognizer.startListening(intent);
            startActivityForResult(intent, 1);

        } catch (ActivityNotFoundException e) {
            Log.e("e", e.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            Toast.makeText(Main_appln.this, "" + result.get(0), Toast.LENGTH_LONG).show();
            response(result.get(0));
        }
    }
}