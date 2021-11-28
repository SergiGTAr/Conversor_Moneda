package facci.com.conversorac;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class CurrencyAdapter extends ArrayAdapter<Currency> {

    public CurrencyAdapter(Context context, ArrayList<Currency> currencies) {
        super(context, 0, currencies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Currency currency = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_llistamonedes, parent, false);
        }

        TextView Nom = (TextView) convertView.findViewById(R.id.txtMoneda);
        TextView Valor = (TextView) convertView.findViewById(R.id.txtValor);
        Nom.setText(currency.name);
        Valor.setText(Double.toString(currency.valor));

        return convertView;
    }
}
