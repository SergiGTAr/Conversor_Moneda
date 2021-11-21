/*
package facci.com.conversorac;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class GetExchangeRates extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("rates");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String key = object.getString("code");
                String value = object.getString("rate");
                ArrayList.add(key + ": " + value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}*/
