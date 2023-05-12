package com.example.aplikacjagraficzna;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private DrawingView drawingView;
    private Button buttonRed, buttonBlue, buttonGreen, buttonClear;
    private int currentColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawingView = findViewById(R.id.drawing_view);
        buttonRed = findViewById(R.id.button_red);
        buttonBlue = findViewById(R.id.button_blue);
        buttonGreen = findViewById(R.id.button_green);
        buttonClear = findViewById(R.id.button_clear);

        // Ustawienie domyślnego koloru rysowania
        setCurrentColor(Color.RED);

        buttonRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentColor(Color.RED);
            }
        });

        buttonBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentColor(Color.BLUE);
            }
        });

        buttonGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentColor(Color.GREEN);
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.clearCanvas();
            }
        });
    }

    private void setCurrentColor(int color) {
        currentColor = color;
        drawingView.setPaintColor(color);
        drawingView.invalidate(); // Odświeżenie widoku DrawingView, aby zobaczyć zmianę koloru
    }

}
