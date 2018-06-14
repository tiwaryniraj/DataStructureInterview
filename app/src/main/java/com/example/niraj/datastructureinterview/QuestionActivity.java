package com.example.niraj.datastructureinterview;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        String qestion = getIntent().getStringExtra("question");
        String answer = getIntent().getStringExtra("answer");
        TextView textView = findViewById(R.id.questionAct);
        TextView textView2 = findViewById(R.id.answerAct);
        textView.setText(qestion);
        textView2.setText(answer);

        ImageView imageView = findViewById(R.id.webLink);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://courses.learncodeonline.in/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}
