package facci.com.conversorac;


import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivityAC extends AppCompatActivity {

    private Spinner monedaActualSP;
    private Spinner monedaCambioSP;
    private EditText valorCambioET;
    private  EditText edittxtPerConvertir;
    private TextView resultadoTV;
    ArrayList<Currency> CurrencyArray = new ArrayList<Currency>();
    ArrayList<String> CurrencyName = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document doc = db.parse(url.openStream());

                    NodeList namelist = (NodeList) doc.getElementsByTagName("Cube");

                    CurrencyArray.clear();
                    CurrencyName.clear();
                    for (int i = 0; i < namelist.getLength(); i++) {
                        Node p = namelist.item(i);
                        Element person = (Element) p;

                        String varCurrency = (String) person.getAttribute("currency");
                        String varRate = (String) person.getAttribute("rate");
                        if (!varCurrency.equals("")) {

                            CurrencyName.add(varCurrency);
                            Currency NewCurrency = new Currency(varCurrency,Double.parseDouble(varRate));
                            CurrencyArray.add(NewCurrency);
                        }
                    }

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-Type", "application/xml");
                    connection.setRequestProperty("Accept", "application/xml");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.connect();

                    InputStream stream = connection.getInputStream();
                    Scanner scanner = new Scanner(stream);
                    scanner.useDelimiter("\\A");
                    String xml = scanner.hasNext() ? scanner.next() : "";
                    scanner.close();

                    Log.d(TAG, xml);

                    String[] currencies = xml.split("<Cube currency=\\\"");
                    for (int i = 1; i < currencies.length; i++) {
                        String[] values = currencies[i].split("\\\"");
                        String currency = values[0];
                        String value = values[1].split("\\\"")[0];
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, CurrencyName);
        setContentView(R.layout.activity_main_activity_ac);
        //new GetExchangeRates().execute();
        edittxtPerConvertir = findViewById(R.id.edittxtPerConvertir);
        monedaActualSP = (Spinner) findViewById(R.id.monedaActualSP);
        monedaCambioSP = (Spinner) findViewById(R.id.monedaCambioSP);
        monedaActualSP.setAdapter(adapter);
        monedaCambioSP.setAdapter(adapter);
        monedaCambioSP.setSelection(monedaActualSP.getSelectedItemPosition() + 1);
        Button circular_button = (Button) findViewById(R.id.btnSwipe);
        circular_button.setOnClickListener(v -> {
            Animation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(500);
            circular_button.startAnimation(rotate);
            monedaActualSP = (Spinner) findViewById(R.id.monedaActualSP);
            monedaCambioSP = (Spinner) findViewById(R.id.monedaCambioSP);
            int posicioMActual = monedaActualSP.getSelectedItemPosition();
            int posicioMCanvi = monedaCambioSP.getSelectedItemPosition();
            monedaActualSP.setSelection(posicioMCanvi);
            monedaCambioSP.setSelection(posicioMActual);
            calcularCanvi();
        });
        edittxtPerConvertir.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcularCanvi();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
            });
        monedaActualSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (monedaActualSP.getSelectedItemPosition() == monedaCambioSP.getSelectedItemPosition()){
                    monedaCambioSP.setSelection(monedaCambioSP.getSelectedItemPosition() + 1);
                }
                calcularCanvi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // your code here
            }
        });
        monedaCambioSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (monedaActualSP.getSelectedItemPosition() == monedaCambioSP.getSelectedItemPosition()){
                    monedaCambioSP.setSelection(monedaCambioSP.getSelectedItemPosition() + 1);
                }
                calcularCanvi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // your code here
            }
        });

    }

    public void btnLlistaMonedes(View v){
        Intent intent = new Intent(MainActivityAC.this, CurrencyList.class);
        intent.putExtra("Currencies", CurrencyArray);
        startActivity(intent);
    }

    public void calcularCanvi(){
        monedaActualSP = (Spinner) findViewById(R.id.monedaActualSP);
        monedaCambioSP = (Spinner) findViewById(R.id.monedaCambioSP);
        valorCambioET = (EditText) findViewById(R.id.edittxtPerConvertir);
        resultadoTV = (TextView) findViewById(R.id.txtConvertit);

        if (valorCambioET.length() != 0) {

            String monedaActual = monedaActualSP.getSelectedItem().toString();
            String monedaCambio = monedaCambioSP.getSelectedItem().toString();

            double valorCambio = Double.parseDouble(valorCambioET.getText().toString());
            double resultado = procesarConversion(valorCambio);

            if (resultado > 0) {
                resultadoTV.setText(String.format("%.3f", resultado) + monedaCambio);
            }
        }
    }

    private double procesarConversion(double valorCambio){
        double resultadoConversion = 0;

        int posicion_mon_actual = monedaActualSP.getSelectedItemPosition();
        int posicion_mon_canvi = monedaCambioSP.getSelectedItemPosition();
        double valor_conv_actual = CurrencyArray.get(posicion_mon_actual).valor;
        double valor_conv_canvi = CurrencyArray.get(posicion_mon_canvi).valor;

        resultadoConversion = (valor_conv_canvi / valor_conv_actual) * valorCambio;

        return resultadoConversion;
    }
}
