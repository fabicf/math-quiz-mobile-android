package com.example.mathquizprojectdraft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mathquizprojectdraft.model.Score;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    final static int REQUEST_CODE1 = 1;

    TextView textViewQuestion;
    TextView textViewAnswer;
    TextView textViewAppTitle;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnDot, btnZero, btnMinus, btnGenerate, btnValidate, btnClear, btnScore, btnFinish;

    double rightResult;

    String userEnteredValue;
    String operationArray[] = {"+", "-", "*", "/"};


    ArrayList<Score> listOfScores;
    String operationQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewAppTitle = findViewById(R.id.textViewAppTitle);
        textViewAnswer = findViewById(R.id.texViewAnswer);
        textViewAnswer.addTextChangedListener(this);

        listOfScores = new ArrayList<>();

        btn1 = findViewById(R.id.button1);
        btn1.setOnClickListener(this);

        btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(this);

        btn3 = findViewById(R.id.button3);
        btn3.setOnClickListener(this);

        btn4 = findViewById(R.id.button4);
        btn4.setOnClickListener(this);

        btn5 = findViewById(R.id.button5);
        btn5.setOnClickListener(this);

        btn6 = findViewById(R.id.button6);
        btn6.setOnClickListener(this);

        btn7 = findViewById(R.id.button7);
        btn7.setOnClickListener(this);

        btn8 = findViewById(R.id.button8);
        btn8.setOnClickListener(this);

        btn9 = findViewById(R.id.button9);
        btn9.setOnClickListener(this);

        btnZero = findViewById(R.id.button0);
        btnZero.setOnClickListener(this);

        btnDot = findViewById(R.id.buttonDot);
        btnDot.setOnClickListener(this);

        btnMinus = findViewById(R.id.buttonMinus);
        btnMinus.setOnClickListener(this);

        btnGenerate = findViewById(R.id.buttonGenerate);
        btnGenerate.setOnClickListener(this);

        btnValidate = findViewById(R.id.buttonValidate);
        btnValidate.setOnClickListener(this);

        btnClear = findViewById(R.id.buttonClear);
        btnClear.setOnClickListener(this);

        btnScore = findViewById(R.id.buttonScore);
        btnScore.setOnClickListener(this);

        btnFinish = findViewById(R.id.buttonFinish);
        btnFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        userEnteredValue = textViewAnswer.getText().toString();

        switch (v.getId()) {
            case R.id.button0:
                textViewAnswer.setText(userEnteredValue + "0");
                break;
            case R.id.button1:
                textViewAnswer.setText(userEnteredValue + "1");
                break;
            case R.id.button2:
                textViewAnswer.setText(userEnteredValue + "2");
                break;
            case R.id.button3:
                textViewAnswer.setText(userEnteredValue + "3");
                break;
            case R.id.button4:
                textViewAnswer.setText(userEnteredValue + "4");
                break;
            case R.id.button5:
                textViewAnswer.setText(userEnteredValue + "5");
                break;
            case R.id.button6:
                textViewAnswer.setText(userEnteredValue + "6");
                break;
            case R.id.button7:
                textViewAnswer.setText(userEnteredValue + "7");
                break;
            case R.id.button8:
                textViewAnswer.setText(userEnteredValue + "8");
                break;
            case R.id.button9:
                textViewAnswer.setText(userEnteredValue + "9");
                break;
            case R.id.buttonDot:
                if (userEnteredValue.length() == 0){
                    textViewAnswer.setText("0.");
                } else{
                    textViewAnswer.setText(userEnteredValue + ".");
                }
                break;
            case R.id.buttonMinus:
                textViewAnswer.setText(userEnteredValue + "-");
                break;
            case R.id.buttonGenerate:
                generateQuestion();
                break;
            case R.id.buttonValidate:
                validadeAnswer();
                break;
            case R.id.buttonClear:
                clearFields();
                break;
            case R.id.buttonScore:
                goToScore();
                break;
            case R.id.buttonFinish:
                finish();
                break;
        }
    }

    private void generateQuestion() {
        clearFields();

        Random random = new Random();
        int operand1 = random.nextInt(10);
        int operand2 = random.nextInt(10);
        int ramdomOperation = random.nextInt(4);
        double result = 0;

        switch (operationArray[ramdomOperation]){
            case "+":
                result = (float) operand1 + (float) operand2;
                break;
            case "-":
                result = (float) operand1 - (float) operand2;
                break;
            case "*":
                result = (float) operand1 * (float) operand2;
                break;
            case "/":
                while (operand2 == 0){
                    operand2 = random.nextInt(10);
                }
                result = (float) operand1 / (float) operand2;
                break;
        }

        rightResult = Math.round(result * 100.0) / 100.0;

        operationQuestion = operand1 + operationArray[ramdomOperation]+ operand2 + "= ?";
        textViewQuestion.setText(operationQuestion);
    }

    private void validadeAnswer(){
        String resultPhrase;
        // 1. check if is empty
        if (userEnteredValue.isEmpty()){
            resultPhrase = "Please enter a valid value";
            textViewQuestion.setText(resultPhrase);
            return;
        }

        // check if there is more than one - (minus)
        // check if there is a minus and it is in the first position
        if (userEnteredValue.contains("-")){
            // check if is in the position one, otherwise will be wrong
            if (userEnteredValue.charAt(0) != '-'){
                resultPhrase = "Please enter a valid value - minus in wrong place";
                textViewQuestion.setText(resultPhrase);
                return;
            }
            int minusCount = 0;
            for (int i = 0; i<userEnteredValue.length(); i++){
                if (userEnteredValue.charAt(i) == '-'){
                    minusCount++;
                }
            }
            if (minusCount > 1){
                resultPhrase = "Please enter a valid value - repeated minus";
                textViewQuestion.setText(resultPhrase);
                return;
            }
        }

        // check if there is more than one . (dot)
        if (userEnteredValue.contains(".")){

            int dotCount = 0;
            for (int i = 0; i<userEnteredValue.length(); i++){
                if (userEnteredValue.charAt(i) == '.'){
                    dotCount++;
                }
            }
            if (dotCount > 1){
                resultPhrase = "Please enter a valid value - repeated dot";
                textViewQuestion.setText(resultPhrase);
                return;
            }
        }
        // 2. parse to double
        double userAnswer = Math.round((Double.parseDouble(userEnteredValue)) * 100.0) / 100.0;
        String userAnswerString = String.valueOf(userAnswer);

        boolean isAnswerCorret;

        // 3. compare to rightResult
        if (userAnswer == rightResult){
            resultPhrase = "Right Answer. Click on Generate to try a new question";
            isAnswerCorret = true;
        } else {
            resultPhrase = "Wrong Answer. Click on Generate to try again";
            isAnswerCorret = false;
        }
        textViewQuestion.setText(resultPhrase);
        //Toast.makeText(this, resultPhrase, Toast.LENGTH_SHORT).show();

        Score score = new Score(operationQuestion, userAnswerString, isAnswerCorret);
        listOfScores.add(score);
    }

    private void clearFields(){
        textViewAnswer.setText("");
        textViewQuestion.setText("Click on Generate button to Question");
    }

    private void goToScore(){
        clearFields();

        Bundle bundle = new Bundle();
        bundle.putSerializable("bundleExtra", listOfScores);

        Intent intent = new Intent(this, Result.class);
        intent.putExtra("intentExtra", bundle);
        startActivityForResult(intent, REQUEST_CODE1);

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        try {
            int userAnswer = Integer.parseInt(textViewAnswer.getText().toString());
            if (userAnswer > 108) {
                Toast toastRange = Toast.makeText(this, "The total should be <= 108", Toast.LENGTH_SHORT);
                toastRange.setGravity(Gravity.TOP|Gravity.CENTER, 0, 500);
                toastRange.show();

                btnValidate.setEnabled(false);
            } else
                btnValidate.setEnabled(true);
        } catch (Exception e) {
            // don't need to validate for text because there is no way to enter a text
            Toast toastException = Toast.makeText(this, "Enter a number data type", Toast.LENGTH_SHORT);
            toastException.setGravity(Gravity.TOP|Gravity.CENTER, 0, 500);
            toastException.show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE1) {
            String receivedData = (String) data.getStringExtra("return_result_tag");
            if (resultCode == RESULT_OK) {
                textViewAppTitle.setText(receivedData);
            } else {
                textViewAppTitle.setText("Canceled");
            }
        }
    }
}

