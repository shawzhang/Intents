package com.example.mghlcs.intents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.io.PrintStream;

public class AddWordActivity extends AppCompatActivity {

    public static final String NEW_WORDS_FILE_NAME = "newwords.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
    }

    public void addWordOKClick(View view) {
        EditText theNewWord = (EditText) findViewById(R.id.the_new_word);
        EditText theNewDefinition = (EditText) findViewById(R.id.the_new_definition);
        String word = theNewWord.getText().toString();
        String defn = theNewWord.getText().toString();

        try {
            PrintStream out = new PrintStream(
                    openFileOutput(NEW_WORDS_FILE_NAME, MODE_APPEND)
                    );
            out.println(word + "\t" + defn);
            out.close();

            //shut down this activity and go back the main
            Intent intent = new Intent();
            intent.putExtra("word", word);
            intent.putExtra("definition", defn);
            setResult(RESULT_OK, intent);
            finish();

        } catch (IOException ioe) {
            Log.wtf("add word failed", ioe);
        }

    }
}
