package facci.com.conversorac;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivityAC extends AppCompatActivity {

    final String[] datos = new String[]{"hola","adios"};

    private Spinner monedaActualSP;
    private Spinner monedaCambioSP;
    private EditText valorCambioET;
    private  EditText edittxtPerConvertir;
    private TextView resultadoTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_ac);
        ArrayList<Object> arrayList = new ArrayList<>();
        ArrayAdapter adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        ArrayAdapter adaptador2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        //new GetExchangeRates().execute();
        edittxtPerConvertir = findViewById(R.id.edittxtPerConvertir);
        monedaActualSP = (Spinner) findViewById(R.id.monedaActualSP);
        monedaCambioSP = (Spinner) findViewById(R.id.monedaCambioSP);
        monedaActualSP.setAdapter(adaptador);
        monedaCambioSP.setAdapter(adaptador2);
        monedaCambioSP.setSelection(monedaActualSP.getSelectedItemPosition() + 1);
        Button circular_button = (Button) findViewById(R.id.btnSwipe);
        circular_button.setOnClickListener(v -> {
            Animation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(1000);
            rotate.setRepeatCount(0);
            rotate.setRepeatMode(Animation.RESTART);
            circular_button.startAnimation(rotate);
        });
    }


    public void btnSwipeClick(View v) {
        monedaActualSP = (Spinner) findViewById(R.id.monedaActualSP);
        monedaCambioSP = (Spinner) findViewById(R.id.monedaCambioSP);
        int posicionMonedaActual = monedaActualSP.getSelectedItemPosition();
        int posicionMonedaCambio = monedaCambioSP.getSelectedItemPosition();
        monedaActualSP.setSelection(posicionMonedaCambio);
        monedaCambioSP.setSelection(posicionMonedaActual);
    }


    public void clickConvertir(View v) {

        monedaActualSP = (Spinner) findViewById(R.id.monedaActualSP);
        monedaCambioSP = (Spinner) findViewById(R.id.monedaCambioSP);
        /*valorCambioET =(EditText) findViewById(R.id.valorCambioET);
        resultadoTV =(TextView) findViewById(R.id.resultadoTV);*/

        String monedaActual = monedaActualSP.getSelectedItem().toString();
        String monedaCambio = monedaCambioSP.getSelectedItem().toString();

        double valorCambio = Double.parseDouble(valorCambioET.getText().toString());
        double resultado = procesarConversion(monedaActual, monedaCambio, valorCambio);

        if (resultado > 0) {
            resultadoTV.setText(String.format("Por %5.2f %s, usted recibirá %5.2f %s", valorCambio, monedaActual, resultado, monedaCambio));
            valorCambioET.setText("");

        } else {
            resultadoTV.setText(String.format("Usted recibirá"));
            Toast.makeText(MainActivityAC.this, "La opción a elegir no tiene un factor de conversión", Toast.LENGTH_SHORT).show();

        }

    }


    private double procesarConversion(String monedaActual, String monedaCambio, double valorCambio) {
        double resultadoConversion = 0;

        return resultadoConversion;

    }
}
