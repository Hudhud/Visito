package dtu.app.visito;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Currency extends AppCompatActivity {

    private EditText EditText;
    private Spinner spinner1, spinner2;
    private double amount;
    String[] currencies = {"DKK","USD","EURO","MP", "Select a currency"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        EditText = findViewById(R.id.edit);
        spinner1 = findViewById(R.id.spinSearch);
        spinner2 = findViewById(R.id.spinSearch2);

        ArrayAdapter adapter = new ArrayAdapter(Currency.this, android.R.layout.simple_list_item_1){
            @Override
            public int getCount() {
                int count = super.getCount();
                if (count > 0)
                    return count - 1;
                else{
                    return count;
                }
            }
        };

        adapter.addAll(currencies);
        spinner1.setAdapter(adapter);
        spinner1.setSelection(adapter.getCount());


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if(spinner1.getSelectedItem() == "Select a currency") {}

                else{
                    Toast.makeText(Currency.this, spinner1.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
/*
    public void convert(View view){
        amount = Integer.parseInt(EditText.getText().toString());
        int euro = 2000;
        double result = dollars * euro ;
        Toast.makeText(Currency.this, Double.toString(result), Toast.LENGTH_LONG).show();
    }*/






}