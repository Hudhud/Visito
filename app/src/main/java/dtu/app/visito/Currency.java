package dtu.app.visito;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class Currency extends AppCompatActivity {

    private EditText EditText;
    private Spinner spinner1, spinner2;
    private double amount;

    public void convert(View view){
          }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        EditText = findViewById(R.id.edit);
        spinner1 = findViewById(R.id.spinSearch);
        spinner2 = findViewById(R.id.spinSearch2);

        //ArrayAdapter <charSequence> adapter = ArrayAdapter.createFromResource(this,R.array.currency,R.layout.support_simple_spinner_dropdown_item);
        //adapter.setDropDownViewResource(R.layout.activity_currency);
        //spinner1.setAdapter(adapter);


        amount = Integer.parseInt(EditText.getText().toString());
        double result = 77;
        Toast.makeText(Currency.this, Double.toString(result), Toast.LENGTH_LONG).show();


    }








}
