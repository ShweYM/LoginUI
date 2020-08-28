package com.example.sym.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneralPatientRegister extends AppCompatActivity{

    private Button buttonSendPush;
    private Spinner spinner;
    private ProgressDialog progressDialog;
    private List<String> devices;



    EditText gname,gage,gsex,gaddress,gcomplaint,ghopi,gmedicalhistory,gsurgicalhistory,gdrughistory,gfhistory,gshistory,gcondition,gtemperature,ganaemia,gjaundice,gheart,glung,gabdomen,gspo2,gpulserate,gbloodpressure,gurineoutput,gdiagnosis,ginvestigation,gtreatment;
    Button gpatientRegister,btnback;
    String URL= "http://192.168.43.26/clinic/generalpatientregister.php";
    public  String rolename,uemail;
    public int regno1,regno2;
    public String name,age,sex,address,complaint,hopi,medicalhistory,surgicalhistory,drughistory,fhistory,shistory,condition,temperature,anaemia,jaundice,heart,lung,abdomen,spo2,pulserate,bloodpressure,urineoutput,diagnosis,investigation,treatment,email,regno;
    JSONParser jsonParser=new JSONParser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generalpatient_register);


        spinner = (Spinner) findViewById(R.id.spinnerDevices);
        devices = new ArrayList<>();

        loadRegisteredDevices();

        gname=(EditText)findViewById(R.id.editName);
        gsex=(EditText)findViewById(R.id.editsex);
        gage=(EditText)findViewById(R.id.editAge);
        //gdate=(EditText)findViewById(R.id.editDate);
        gaddress=(EditText)findViewById(R.id.editAddress);
        gcomplaint=(EditText)findViewById(R.id.editComplaint);
        ghopi=(EditText)findViewById(R.id.editHOPI);
        gmedicalhistory=(EditText)findViewById(R.id.editmedical);
        gsurgicalhistory=(EditText)findViewById(R.id.editSurgery);
        gshistory=(EditText)findViewById(R.id.editsocialhistory);
        gdrughistory=(EditText)findViewById(R.id.editallegry);
        gfhistory=(EditText)findViewById(R.id.editfamilyhistory);
        gcondition=(EditText)findViewById(R.id.editgeneralcondition);
        gtemperature=(EditText)findViewById(R.id.edittemperature);
        ganaemia=(EditText)findViewById(R.id.editanaemia);
        gjaundice=(EditText)findViewById(R.id.editjaundice);
        gheart=(EditText)findViewById(R.id.editheart);
        glung=(EditText)findViewById(R.id.editlung);
        gabdomen=(EditText)findViewById(R.id.editabdomen);
        gspo2=(EditText)findViewById(R.id.editspo2);
        gpulserate=(EditText)findViewById(R.id.editpulserate);
        gbloodpressure=(EditText)findViewById(R.id.editbloodpressure);
        gurineoutput=(EditText)findViewById(R.id.editurineoutput);
        gdiagnosis=(EditText)findViewById(R.id.editPD);
        ginvestigation=(EditText)findViewById(R.id.editinvestigation);
        gtreatment=(EditText)findViewById(R.id.edittreatment);

        gpatientRegister=(Button)findViewById(R.id.btnRegister);
        btnback=(Button)findViewById(R.id.btnBack);

        Bundle b = getIntent().getExtras();

        uemail = b.getString("email");
        regno1=b.getInt("regno");
        rolename=b.getString("rolename");
        /*regno2=b.getInt("registernum");
        regno1=regno2;*/


        TextView txtregno=(TextView) findViewById(R.id.registerno);
        txtregno.setText(("Register Number -  PT - "+regno1));

        gpatientRegister.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                AttemptRegister attemptRegister=new AttemptRegister();
                attemptRegister.execute(gname.getText().toString(),gage.getText().toString(),gsex.getText().toString(),gaddress.getText().toString(),
                        gcomplaint.getText().toString(),ghopi.getText().toString(),gmedicalhistory.getText().toString(),
                        gsurgicalhistory.getText().toString(),gdrughistory.getText().toString(),gfhistory.getText().toString(),gshistory.getText().toString(),
                        gcondition.getText().toString(),gtemperature.getText().toString(),ganaemia.getText().toString(),gjaundice.getText().toString(),
                        gheart.getText().toString(),glung.getText().toString(),gabdomen.getText().toString(),gspo2.getText().toString(),gpulserate.getText().toString(),
                        gbloodpressure.getText().toString(),gurineoutput.getText().toString(),gdiagnosis.getText().toString(),ginvestigation.getText().toString(),gtreatment.getText().toString(),uemail,String.valueOf(regno1));

            }
        });

        btnback.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent=new Intent(GeneralPatientRegister.this,doctorlogin.class);
                intent.putExtra("regno",regno1);
                intent.putExtra("rolename",rolename);
                startActivity(intent);
            }
        });


    }
    //method to load all the devices from database
    private void loadRegisteredDevices() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Devices...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, EndPoints.URL_FETCH_DEVICES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                JSONArray jsonDevices = obj.getJSONArray("devices");

                                for (int i = 0; i < jsonDevices.length(); i++) {
                                    JSONObject d = jsonDevices.getJSONObject(i);
                                    devices.add(d.getString("email"));
                                }

                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                        GeneralPatientRegister.this,
                                        android.R.layout.simple_spinner_dropdown_item,
                                        devices);

                                spinner.setAdapter(arrayAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

        };
        MyVolley.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void sendSinglePush() {
        final String title = "Patient Information";
        final String message = gname.getText().toString()+"#"+regno1+"#"+gtreatment.getText().toString();
        final String image = null;
        final String spinemail = spinner.getSelectedItem().toString();

        progressDialog.setMessage("Sending Push");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.URL_SEND_SINGLE_PUSH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        Toast.makeText(GeneralPatientRegister.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("message", message);

                if(!TextUtils.isEmpty(image)){
                    params.put("image",image);
                }

                params.put("email", spinemail);
                return params;
            }
        };

        MyVolley.getInstance(this).addToRequestQueue(stringRequest);
    }

    private class AttemptRegister extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {


            name=args[0];
            age=args[1];
            sex=args[2];
            address=args[3];
            //date=args[4];
            complaint=args[4];
            hopi=args[5];
            medicalhistory=args[6];
            surgicalhistory=args[7];
            drughistory=args[8];
            fhistory=args[9];
            shistory=args[10];
            condition=args[11];
            temperature=args[12];
            anaemia=args[13];
            jaundice=args[14];
            heart=args[15];
            lung=args[16];
            abdomen=args[17];
            spo2=args[18];
            pulserate=args[19];
            bloodpressure=args[20];
            urineoutput=args[21];
            diagnosis=args[22];
            investigation=args[23];
            treatment=args[24];
            email=args[25];
            regno=args[26];

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("age", age));
            params.add(new BasicNameValuePair("sex", sex));
            params.add(new BasicNameValuePair("address", address));
            //params.add(new BasicNameValuePair("date", date));
            params.add(new BasicNameValuePair("complaint", complaint));
            params.add(new BasicNameValuePair("hopi", hopi));
            params.add(new BasicNameValuePair("medicalhistory", medicalhistory));
            params.add(new BasicNameValuePair("surgicalhistory", surgicalhistory));
            params.add(new BasicNameValuePair("drughistory", drughistory));
            params.add(new BasicNameValuePair("familyhistory", fhistory));
            params.add(new BasicNameValuePair("socialhistory", shistory));
            params.add(new BasicNameValuePair("generalcondition", condition));
            params.add(new BasicNameValuePair("temperature", temperature));
            params.add(new BasicNameValuePair("anaemia", anaemia));
            params.add(new BasicNameValuePair("jaundice", jaundice));
            params.add(new BasicNameValuePair("heart", heart));
            params.add(new BasicNameValuePair("lung", lung));
            params.add(new BasicNameValuePair("abdomen", abdomen));
            params.add(new BasicNameValuePair("spo2", spo2));
            params.add(new BasicNameValuePair("pulserate", pulserate));
            params.add(new BasicNameValuePair("bloodpressure", bloodpressure));
            params.add(new BasicNameValuePair("urineoutput", urineoutput));
            params.add(new BasicNameValuePair("diagnosis", diagnosis));
            params.add(new BasicNameValuePair("investigation", investigation));
            params.add(new BasicNameValuePair("treatment", treatment));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("regno",regno));

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);

            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            try {
                if (result != null) {
                    //Log.e("response",result.getString());
                    int code=result.getInt("success");
                    //regno=result.getInt("regno");

                    //String message=result.getString("message");
                    if(code==1) {
                        sendSinglePush();
                        Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(GeneralPatientRegister.this,GeneralPatientRegister.class);
                        intent.putExtra("regno",regno1+1);
                        intent.putExtra("rolename",rolename);
                        intent.putExtra("email",uemail);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
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
