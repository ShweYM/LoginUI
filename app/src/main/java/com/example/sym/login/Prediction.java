package com.example.sym.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Prediction extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String URL = "http://192.168.43.26/clinic/seasonal.php";

    JSONParser jsonParser = new JSONParser();

    public String selectedquarter;

    TextView predictamount;


    public String[] quarter = {"Summer","Fall","Winter"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);



            /*try {
                selectmonth = Integer.parseInt(getmonth);
                System.out.println("Month"+selectmonth);
            }
            catch (NumberFormatException e){
                System.out.println("Could not parse"+e );
            }*/



        predictamount = (TextView) findViewById(R.id.viewpredict);

        Button btnPredictAmount = (Button) findViewById(R.id.btnMonthPredict);
        btnPredictAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PredictionCalculate calculate = new PredictionCalculate();
                calculate.execute(selectedquarter);
            }


        });

        final Spinner spin = (Spinner) findViewById(R.id.monthspinner);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, quarter);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        selectedquarter = quarter[position];
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class PredictionCalculate extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {


            String selectedquarter = args[0];


            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("quarter", selectedquarter));
            Log.e("quarter","selectedquarter");

            /*if(email.length()>0)
                params.add(new BasicNameValuePair("email",email));
            if(role.length()>0)
                params.add(new BasicNameValuePair("role", role));*/

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            try {
                if (result != null) {
                    //Log.e("response",result.getString());
                    double amount = result.getDouble("predictamount");
                    //String rolename=result.getString("rolename");
                    //String message=result.getString("message");
                    predictamount.setText("Your prediction sales for " + selectedquarter + " in next year is " +new DecimalFormat("##.##").format(amount));
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }

}






