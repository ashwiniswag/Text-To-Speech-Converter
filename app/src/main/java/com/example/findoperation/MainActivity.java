package com.example.findoperation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextToSpeech textToSpeech;
    EditText find,editor;
    Button findbbtn,readbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        find=findViewById(R.id.find);
        editor=findViewById(R.id.Editor);
        findbbtn=findViewById(R.id.findbtn);
        readbtn=findViewById(R.id.read);

        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech.setLanguage(Locale.US);

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
//                        Log.e("TTS", "The Language is not supported!");
                    } else {
//                        Log.i("TTS", "Language Supported.");
                    }
//                    Log.i("TTS", "Initialization success.");
                } else {
                    Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        readbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data=editor.getText().toString();
                int speechstatus=textToSpeech.speak(data,TextToSpeech.QUEUE_FLUSH,null);
                if (speechstatus == TextToSpeech.ERROR) {
//                    Log.e("TTS", "Error in converting Text to Speech!");
                    Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                }
            }
        });

        findbbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tohighlight=find.getText().toString();
                String replacewith="<span style='background-color:green'>"+ tohighlight+ "</span>";
                String originaltext=editor.getText().toString();
                String modifiedtext=originaltext.replaceAll(tohighlight,replacewith);
                editor.setText(Html.fromHtml(modifiedtext));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
