package com.example.graza7;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView licznikText;
    TextView komunikatText;
    TextView trafieniaText;
    Button startButton;
    Button stopButton;

    int liczba = 0;
    int trafienia = 0;
    boolean licznikDziala = false;

    Handler handler = new Handler(Looper.getMainLooper());

    Runnable zmianaLiczby = new Runnable() {
        @Override
        public void run() {
            liczba++;

            if (liczba > 9) {
                liczba = 0;
            }

            licznikText.setText(String.valueOf(liczba));

            if (licznikDziala) {
                handler.postDelayed(this, 500);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        licznikText = findViewById(R.id.licznikText);
        komunikatText = findViewById(R.id.komunikatText);
        trafieniaText = findViewById(R.id.trafieniaText);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                liczba = 0;
                licznikText.setText("0");
                komunikatText.setText("");

                licznikDziala = true;
                startButton.setEnabled(false);
                stopButton.setEnabled(true);

                handler.postDelayed(zmianaLiczby, 500);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                licznikDziala = false;
                handler.removeCallbacks(zmianaLiczby);

                startButton.setEnabled(true);
                stopButton.setEnabled(false);

                if (liczba == 7) {
                    komunikatText.setText("Trafienie!");
                    trafienia++;
                    trafieniaText.setText("Trafienia: " + trafienia);
                } else {
                    komunikatText.setText("Pudło! Zatrzymano na " + liczba);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(zmianaLiczby);
    }
}