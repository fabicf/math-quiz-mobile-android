package com.example.mathquizprojectdraft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mathquizprojectdraft.model.Score;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Result extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Spinner spinnerResult;
    ListView listViewScore;
    EditText editTextName;
    TextView textViewPercentage;
    Button goBackBtn, sortScoreBtn;

    ArrayList<Score> listOfScores;
    ArrayAdapter<Score> scoreAdapter;

    String[] listAnswersSpinner = {"See all answers", "Only right answers", "Only wrong answers"};
    String strPercentage;
    ArrayAdapter<String> spinnerResultAdapter;
    ArrayList<Score> listOfRightAnswers;
    ArrayList<Score> listOfWrongAnswers;

    String sortOrder = "asc";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initialize();
        getMyIntent();
        initializeListView();
        initializeListView();
        scorePercentage();
    }

    private void initialize(){
        textViewPercentage = findViewById(R.id.textViewPercentage);


        spinnerResult = findViewById(R.id.spinnerResult);
        spinnerResult.setOnItemSelectedListener(this);

        spinnerResultAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listAnswersSpinner);
        spinnerResult.setAdapter(spinnerResultAdapter);

        goBackBtn = findViewById(R.id.buttonGoBack);
        goBackBtn.setOnClickListener(this);

        sortScoreBtn = findViewById(R.id.buttonSort);
        sortScoreBtn.setOnClickListener(this);

    }

    private void getMyIntent(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("intentExtra");
        Serializable bundleListOfScores = bundle.getSerializable("bundleExtra");

        listOfScores = (ArrayList<Score>) bundleListOfScores;
    }

    private void initializeListView() {
        listViewScore = findViewById(R.id.listViewScore);
        scoreAdapter = new ArrayAdapter<Score>(this, android.R.layout.simple_list_item_1, listOfScores);
        listViewScore.setAdapter(scoreAdapter);
    }

    private void scorePercentage(){
        double qtyOfRight = 0.0;
        double percentage;
        for (Score s : listOfScores){
            if (s.isAnswerCorrect()){
                qtyOfRight++;
            }
        }
        percentage = qtyOfRight/listOfScores.size();
        percentage = percentage * 100;


        strPercentage = String.valueOf(percentage);
        //String strPercentage = "qty of right: " + qtyOfRight + "array size:" + listOfScores.size();

        Toast.makeText(this, strPercentage, Toast.LENGTH_SHORT).show();
        textViewPercentage.setText(strPercentage + " %");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (id == 0){ // index 0 = show all
            scoreAdapter = new ArrayAdapter<Score>(this, android.R.layout.simple_list_item_1, listOfScores);
            listViewScore.setAdapter(scoreAdapter);
        } else {
            listOfRightAnswers = new ArrayList<>();
            listOfWrongAnswers = new ArrayList<>();
            for (Score s : listOfScores){
                if (s.isAnswerCorrect()){
                    listOfRightAnswers.add(s);
                } else {
                    listOfWrongAnswers.add(s);
                }
            }
            if (id == 1){
                scoreAdapter = new ArrayAdapter<Score>(this, android.R.layout.simple_list_item_1, listOfRightAnswers);
                listViewScore.setAdapter(scoreAdapter);
            } else {
                scoreAdapter = new ArrayAdapter<Score>(this, android.R.layout.simple_list_item_1, listOfWrongAnswers);
                listViewScore.setAdapter(scoreAdapter);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonGoBack:
                goBack();
                break;
            case R.id.buttonSort:
                sortScore();
                break;
        }
    }

    private void goBack(){
        editTextName = findViewById(R.id.editTextName);
        String name = editTextName.getText().toString(); // validate if it is empty
        String strReturnNameScore = name + " " + strPercentage;

        Intent intent = new Intent();
        intent.putExtra("return_result_tag", strReturnNameScore);

        setResult(RESULT_OK, intent);
        finish();
    }

    private void sortScore(){
        if (sortOrder == "asc"){
            Collections.sort(listOfScores);
            sortOrder = "desc";
        }else {
            Collections.sort(listOfScores, Collections.reverseOrder());
            sortOrder = "asc";
        }

        scoreAdapter = new ArrayAdapter<Score>(this, android.R.layout.simple_list_item_1, listOfScores);
        listViewScore.setAdapter(scoreAdapter);

    }
}