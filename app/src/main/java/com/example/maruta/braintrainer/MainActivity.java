package com.example.maruta.braintrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton switchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchButton = findViewById(R.id.switchButton);

        switchButton.setOnClickListener((View event) -> {
            Intent it = new Intent(this, BrainMain.class);

            startActivity(it);
        });
    }
}
