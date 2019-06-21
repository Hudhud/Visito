package dtu.app.visito;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class Currency extends AppCompatActivity {

    private EditText amount;
    private Spinner spinner1;
    private TextView ResultEUR1, ResultUSD1, ResultDKK1;

    String[] currencies = {"DKK","USD","EURO", "Select a currency"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        amount = findViewById(R.id.edit);

        spinner1 = findViewById(R.id.spinSearch);

        ResultEUR1 = findViewById(R.id.resultEUR);
        ResultUSD1 =findViewById(R.id.resultUSD);
        ResultDKK1 =findViewById(R.id.resultDKK);


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
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                amount.addTextChangedListener(new TextWatcher() {

                    public void afterTextChanged(Editable s) {
                    }

                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {
                    }

                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        double finalAmount;
                        if (amount.getText().length() >= 1) {
                            finalAmount = Double.valueOf(amount.getText().toString());

                            if (spinner1.getSelectedItem() == "Select a currency") {
                            }
                            if (spinner1.getSelectedItem() == "DKK") {
                                double ResultEUR = finalAmount * 0.13392;
                                double ResultUSD = finalAmount * 0.15113;
                                ResultEUR1.setVisibility(View.VISIBLE);
                                ResultUSD1.setVisibility(View.VISIBLE);
                                ResultDKK1.setVisibility(View.GONE);
                                ResultEUR1.setText("EUR: \n" +Double.toString(ResultEUR));
                                ResultUSD1.setText("USD: \n" +Double.toString(ResultUSD));

                            }
                            if (spinner1.getSelectedItem() == "EURO") {
                                double ResultDKK = finalAmount * 7.46563;
                                double ResultUSD = finalAmount * 1.12847;

                                ResultDKK1.setVisibility(View.VISIBLE);
                                ResultUSD1.setVisibility(View.VISIBLE);

                                ResultDKK1.setText("DKK: \n" +Double.toString(ResultDKK));
                                ResultUSD1.setText("USD: \n" +Double.toString(ResultUSD));
                                ResultEUR1.setVisibility(View.GONE);

                            }
                            if (spinner1.getSelectedItem() == "USD") {
                                double ResultDKK = finalAmount * 6.61517;
                                double ResultEUR = finalAmount * 0.88605;
                                ResultDKK1.setVisibility(View.VISIBLE);
                                ResultEUR1.setVisibility(View.VISIBLE);

                                ResultDKK1.setText("DKK: \n" + Double.toString(ResultDKK));
                                ResultEUR1.setText("EUR: \n" +Double.toString(ResultEUR));
                                ResultUSD1.setVisibility(View.GONE);

                            } else {
                                Toast.makeText(Currency.this, spinner1.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                });

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}