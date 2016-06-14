package com.valdez.project1_tempconverter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class TempConverterActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    // define instance variables
    private EditText conversionEditText;
    private TextView farenheitTextView;
    private TextView celciusTextView;
    private TextView tempConversionTextView;

    private String tempString;
    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_converter);

        // get references to the widgets
        conversionEditText = (EditText) findViewById(R.id.farenheitTemp);
        farenheitTextView = (TextView) findViewById(R.id.farenheitID);
        celciusTextView = (TextView) findViewById(R.id.celciusID);
        tempConversionTextView = (TextView) findViewById(R.id.celciusTemp);

        // set the listener
        conversionEditText.setOnEditorActionListener(this);

        savedValues = getSharedPreferences("savedValues", MODE_PRIVATE);

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if(actionId == EditorInfo.IME_ACTION_DONE ||
            actionId == EditorInfo.IME_ACTION_UNSPECIFIED)
        {
            calculateAndDisplay();
        }
        return false;
    }

    private void calculateAndDisplay()
    {
        tempString = conversionEditText.getText().toString();
        float temperature;
        float c;

        if(tempString.equals(""))
        {
            temperature = 0;
        }
        else
        {
            temperature = Float.parseFloat(tempString);
        }

        // convert temp
        c = (temperature -32) * 5/9;

        // pass values
        NumberFormat number = NumberFormat.getNumberInstance();
        tempConversionTextView.setText(number.format(c));
    }


    @Override
    protected void onPause() {

        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("tempString", tempString);
        editor.commit();

        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();

        tempString = savedValues.getString("tempString", "");
        conversionEditText.setText(tempString);
        calculateAndDisplay();
    }
}