package facci.com.conversorac;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivityAC extends AppCompatActivity {

    final String[] datos= new String[] {"DÓLAR", "EURO", "PESO MEXICANO"};

    private Spinner monedaActualSP;
    private Spinner monedaCambioSP;
    private EditText valorCambioET;
    private TextView resultadoTV;


    final private double factorDolarEuro= 0.87;
    final private double factorPesoDolar= 0.54;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_ac);

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, datos);

        monedaActualSP =(Spinner) findViewById(R.id.monedaActualSP);
        monedaActualSP.setAdapter(adaptador);

    }

    public void clickConvertir(View v){

        monedaActualSP =(Spinner) findViewById(R.id.monedaActualSP);
        monedaCambioSP =(Spinner) findViewById(R.id.monedaCambioSP);
        /*valorCambioET =(EditText) findViewById(R.id.valorCambioET);
        resultadoTV =(TextView) findViewById(R.id.resultadoTV);*/

        String monedaActual = monedaActualSP.getSelectedItem().toString();
        String monedaCambio = monedaCambioSP.getSelectedItem().toString();

        double valorCambio = Double.parseDouble(valorCambioET.getText().toString());
        double resultado = procesarConversion(monedaActual,monedaCambio,valorCambio);

        if(resultado>0){
            resultadoTV.setText(String.format("Por %5.2f %s, usted recibirá %5.2f %s",valorCambio,monedaActual,resultado,monedaCambio));
            valorCambioET.setText("");

        }else{
            resultadoTV.setText(String.format("Usted recibirá"));
            Toast.makeText(MainActivityAC.this, "La opción a elegir no tiene un factor de conversión", Toast.LENGTH_SHORT).show();

        }

    }

    private double procesarConversion(String monedaActual, String monedaCambio, double valorCambio){
        double resultadoConversion =0;

        switch (monedaActual){
            case "DÓLAR":
                if(monedaCambio.equals("EURO"))
                    resultadoConversion = valorCambio * factorDolarEuro;

                if(monedaCambio.equals("PESO MEXICANO"))
                    resultadoConversion = valorCambio / factorPesoDolar;

                break;

            case "EURO":
                if(monedaCambio.equals("DÓLAR"))
                    resultadoConversion = valorCambio / factorDolarEuro;


                break;

            case "PESO MEXICANO":
                if(monedaCambio.equals("DÓLAR"))
                    resultadoConversion = valorCambio * factorPesoDolar;

                break;

        }

        return resultadoConversion;
    }

}
