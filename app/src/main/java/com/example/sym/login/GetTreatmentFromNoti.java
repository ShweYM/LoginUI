package com.example.sym.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class GetTreatmentFromNoti extends AppCompatActivity {

    public String getmsg, name, treatment,regno;

    String URL= "http://192.168.43.26/clinic/saleStore.php";

    JSONParser jsonParser=new JSONParser();

    EditText amount;
    TextView txtname,txtregno,txttreatment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_treatment_from_noti);

        Button btnstore=(Button)findViewById(R.id.btnstore);
        Button btnback=(Button)findViewById(R.id.btnback);
        txtname=(TextView) findViewById(R.id.editnotigetname);
        txtregno=(TextView) findViewById(R.id.editnotigetregno);
        txttreatment=(TextView) findViewById(R.id.editnotigettreatment);
        amount=(EditText)findViewById(R.id.editAmt);

        Bundle bn=getIntent().getExtras();
        if(bn!=null) {
            String title = bn.getString("Title");
            String message = bn.getString("Message");
            if(title!=null && message!=null) {
                //title1.setText(title);
                getmsg=message;
                StringTokenizer tokens = new StringTokenizer(getmsg, "#");
                name = tokens.nextToken();
                regno = tokens.nextToken();
                treatment=tokens.nextToken();

        txtname.setText(name);
        txtregno.setText("PT - "+regno);
        txttreatment.setText(treatment);

            }
        }

        btnstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetTreatmentFromNoti.AttemptStore attemptStore= new GetTreatmentFromNoti.AttemptStore();
                attemptStore.execute(name,regno,treatment,amount.getText().toString());


            }


        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetTreatmentFromNoti.this, sellerlogin.class);
                startActivity(intent);
                finish();
            }


        });
    }

    private class AttemptStore extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {

            String pname = args[0];
            String pregno= args[1];
            String ptreatment=args[2];
            String amount=args[3];


            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", pname));
            params.add(new BasicNameValuePair("regno", pregno));
            params.add(new BasicNameValuePair("treatment", ptreatment));
            params.add(new BasicNameValuePair("amount", amount));


            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            try {
                if (result != null) {
                    //Log.e("response",result.getString());

                    int code = result.getInt("success");
                    if (code == 1) {
                        Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                        txtname.setText(null);
                        txtregno.setText(null);
                        txttreatment.setText(null);
                        amount.setText(null);

                    } else {

                        Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();

                    }
                }



                else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }



}
