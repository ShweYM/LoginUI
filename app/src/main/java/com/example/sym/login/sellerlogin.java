package com.example.sym.login;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class sellerlogin extends AppCompatActivity {

    JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellerlogin);

        Button sellerlogout = (Button) findViewById(R.id.sellerlogout);
        Button btnPredict = (Button) findViewById(R.id.btnPredict);
        Button btnTreatment = (Button) findViewById(R.id.btnTreatment);
        sellerlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sellerlogin.this, MainActivity.class);
                startActivity(intent);
                finish();
            }


        });
        btnPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                predict predict = new predict();
                predict.execute();
            }


        });
        btnTreatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sellerlogin.this, GetTreatmentFromNoti.class);
                startActivity(intent);
                finish();
            }


        });

    }

    /*protected class predict extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://192.168.43.26/clinic/predictionfetchmonth.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }

        }


        protected void onPostExecute(String response) {

            String result = response;

            Log.e("month", result);

            if (result==null) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            } else {
                try {
                    Intent intent=new Intent(sellerlogin.this,Prediction.class);
                    intent.putExtra("month",result);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
    }*/

    private class predict extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {


            JSONObject json = jsonParser.makeHttpRequest("http://192.168.43.26/clinic/predictionfetchmonth.php", "POST", null);


            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            try {
                if (result != null) {
                    //Log.e("response",result.getString());
                    int code = result.getInt("code");
                    Intent intent = new Intent(sellerlogin.this, Prediction.class);
                    intent.putExtra("month", code);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }

}



