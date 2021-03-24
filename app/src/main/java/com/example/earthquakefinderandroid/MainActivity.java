package com.example.earthquakefinderandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String API_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(API_URL);


    }

    private class EarthquakeAsyncTask extends AsyncTask<String,Void,ArrayList<Earthquake>>{

        @Override
        protected ArrayList<Earthquake> doInBackground(String... url) {
            if(url.length<1 || url[0]==null){
                return null;
            }
            ArrayList<Earthquake> data = null;
            try {
                data = fetchEarthquakeData(url[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        public void updateUI(ArrayList<Earthquake> earthquakes){
            // Find a reference to the {@link ListView} in the layout
            ListView earthquakeListView = (ListView) findViewById(R.id.list);

            // Create a new {@link ArrayAdapter} of earthquakes
            EarthquakeAdapter adapter = new EarthquakeAdapter(
                    MainActivity.this,  earthquakes);

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            earthquakeListView.setAdapter(adapter);
        }


        @Override
        protected void onPostExecute(ArrayList<Earthquake> earthquakes) {
            if(earthquakes.size()<=0){
                return ;
            }
            updateUI(earthquakes);
        }

        private ArrayList<Earthquake> fetchEarthquakeData(String apiUrl) throws IOException {
               URL url = createUrl(apiUrl);
               String JsonData = null;
               JsonData = makeHTTPrequest(url);
            ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes(JsonData);

               return earthquakes;
        }

        public URL createUrl(String apiUrl) {

            URL url = null;

            try {
                url = new URL(apiUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
         return url;
        }

        public String makeHTTPrequest(URL url) throws IOException {
            String jsondata = "";

            if(url==null)
                  return jsondata;
            HttpURLConnection urlConnection = null;
            InputStream inputStream =null;

            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.connect();

                if(urlConnection.getResponseCode()==200){
                    inputStream = urlConnection.getInputStream();
                    jsondata = readfromStram(inputStream);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(urlConnection!=null){
                    urlConnection.disconnect();
                }
                if(inputStream!=null){
                    inputStream.close();
                }
            }

            return jsondata;
        }

        public String readfromStram(InputStream inputStream) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
           if(inputStream!=null){
               InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
               BufferedReader reader = new BufferedReader(inputStreamReader);
               String line = reader.readLine();
               while(line!=null){
                   stringBuilder.append(line);
                   line = reader.readLine();
               }
           }
           return stringBuilder.toString();
        }

    };


}