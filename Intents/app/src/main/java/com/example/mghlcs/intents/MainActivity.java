package com.example.mghlcs.intents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_ADD_WORD = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addAWordClick(View view) {
        Intent intent = new Intent(this, AddWordActivity.class);
        startActivityForResult(intent, REQ_CODE_ADD_WORD);
    }

    public void playTheQuizClick(View view) {
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_ADD_WORD && resultCode == RESULT_OK) {
            String word = data.getStringExtra("word");
            String defn = data.getStringExtra("definition");
            Toast.makeText(this, "Word is: '" + word + "' defn is: " + defn, Toast.LENGTH_SHORT).show();
        }
    }
}
