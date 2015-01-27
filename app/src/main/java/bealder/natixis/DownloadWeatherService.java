package bealder.natixis;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Date;

public class DownloadWeatherService extends Service {
    // WOEID list
    // To retrieve a WOEID run `select woeid from geo.places(1) where text="Bagnères de Bigorre"`
    // on https://developer.yahoo.com/weather/
    public static int[] WOEIDS = new int[]{
            578239, // Barèges
            20194027, // La Mongie
            577811, // Bagnères de Bigorre
    };

    public static String[] STATIONS = new String[]{
            "Barèges",
            "La Mongie",
            "Bagnères de Bigorre",
    };

    private DownloaderTask tutorialDownloader;

    public static String getCurrentDateFormatted() {
        return DateFormat.getDateInstance(DateFormat.LONG).format(new Date());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SharedPreferences weatherData = getApplicationContext().getSharedPreferences("weather", MODE_PRIVATE);

        int count = 0;
        for (int woeid : WOEIDS) {
            if (!weatherData.getString(woeid + "-update", "").equals(getCurrentDateFormatted())) {
                new DownloaderTask().execute(woeid);
                count++;
            } else {
                Log.i("SERVICE", "Data already up to date for " + woeid + ".");
            }
        }

        if (count == 0) {
            stopSelf();
        }
        return Service.START_FLAG_REDELIVERY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class DownloaderTask extends AsyncTask<Integer, String, String> {
        public int woeid;
        InputStream inputStream = null;

        @Override
        protected String doInBackground(Integer... params) {
            woeid = params[0];

            StringBuilder sBuilder = new StringBuilder();
            String url = "https://query.yahooapis.com/v1/public/yql?format=json&q=";
            try {
                // u ='c' : get results in Celsius
                url += URLEncoder.encode("select * from weather.forecast where woeid = " + woeid + " and u='c'", "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.i("SERVICE", "Starting download for " + url);

            try {
                HttpClient httpClient = new DefaultHttpClient();

                HttpGet httpGet = new HttpGet(url);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();

                // Read content & Log
                inputStream = httpEntity.getContent();

                BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);


                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sBuilder.append(line + "\n");
                }

                inputStream.close();
                Log.i("SERVICE", "Download ended for " + url);

            } catch (Exception e1) {
                Log.e("WTF", e1.toString());
                e1.printStackTrace();
            }

            return sBuilder.toString();
        }

        protected void onPostExecute(String result) {
            //parse JSON data
            try {
                JSONObject weatherData = new JSONObject(result);
                JSONArray forecast = weatherData.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONArray("forecast");

                // Write data on a shared preferences
                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("weather", MODE_PRIVATE).edit();
                editor.putString(woeid + "-update", getCurrentDateFormatted());
                editor.putString(woeid + "-today", forecast.getJSONObject(0).toString());
                editor.putString(woeid + "-tomorrow", forecast.getJSONObject(1).toString());
                editor.commit();

            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            }
        }
    }
}