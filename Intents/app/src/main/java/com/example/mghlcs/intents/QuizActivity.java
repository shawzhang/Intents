package com.example.mghlcs.intents;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class QuizActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private HashMap<String, String> dictionary;
    private String word;
    private ArrayList<String> definitions;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent intent = getIntent();

        String favClass = intent.getStringExtra("favoriteClass");

        definitions = new ArrayList<>();
        adapter = new ArrayAdapter<String>(
                this,
                R.layout.mydictionarylistlayout,
                R.id.the_item_text,
                definitions
        );

        ListView definitionList = (ListView) findViewById(R.id.definition_list);

        if (definitionList != null) {
            definitionList.setAdapter(adapter);
            definitionList.setOnItemClickListener(this);

            readAllDifinitions();
            pick5randomWords();
        } else {
            Log.e("Debug E", "definitionList is NULL!!!");
        }

    }


    public void lookup(View view) {
        if (dictionary == null) {
            dictionary = new HashMap<>();
            readAllDifinitions();
        }
        //String defn = dictionary.get("abate");
    }

    private String readDefinition(String word) {
        Scanner scan = new Scanner(
                getResources().openRawResource(R.raw.words));
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] pieces = line.split("-");
            if (pieces[0].equalsIgnoreCase(word.trim())) {
                return pieces[1];
            }
        }
        return null; //word is not in dictionary
    }

    private void readAllDifinitions() {
        dictionary = new HashMap<>();

        //read pre exists file
        Scanner scan = new Scanner(
                getResources().openRawResource(R.raw.words));
        readWordsFromFile(scan);

        //read added new word
        try {
        Scanner scan2 = new Scanner(
                openFileInput(AddWordActivity.NEW_WORDS_FILE_NAME));
        readWordsFromFile(scan2);
        } catch (Exception e) {
            Log.wtf("No file exits",e);
        }
    }

    private void readWordsFromFile(Scanner scan) {
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] pieces = line.split("-");
            Log.d("heeelp", "dictionary=" + dictionary);
            if (pieces.length >= 2) {
                dictionary.put(pieces[0], pieces[1]);
            }
        }

    }

    public void pick5randomWords() {

        ArrayList<String> choosenWords = new ArrayList<>(dictionary.keySet());
        Collections.shuffle(choosenWords);
        word = choosenWords.get(0);
        TextView the_word = (TextView) findViewById(R.id.the_word);
        the_word.setText(word);

        definitions.clear();

        for (int i = 0; i < 5; i++) {
            String defn = dictionary.get(choosenWords.get(i));
            definitions.add(defn);
        }

        Collections.shuffle(definitions);
        //update the listview to store 5 dynamic items
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListView definitionList = (ListView) findViewById(R.id.definition_list);
        String text = definitionList.getItemAtPosition(position).toString();
        String correctDefination = dictionary.get(word);
        if (text.equalsIgnoreCase(correctDefination)) {
            Toast.makeText(this, "You got it!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
        }

        //Pick up 5 new words
        pick5randomWords();
    }
}