package facci.com.conversorac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class CurrencyList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currencylist_layout);
        ArrayList<Currency> CurrencyArray = (ArrayList<Currency>) getIntent().getSerializableExtra("Currencies");

        CurrencyAdapter currencyadapter = new CurrencyAdapter(this, CurrencyArray);
        ListView listView_currencies = (ListView) findViewById(R.id.listview_currencies);
        listView_currencies.setAdapter(currencyadapter);

        System.out.println(CurrencyArray.toString());
    }
}
