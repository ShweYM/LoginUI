package com.example.sym.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RegisterUI extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //spinner

    String[] items={"Pulmonologist","Gastroenterlogist","Neurologist","Cardiologist","Hepatologist","General Practitioner","drug seller"};
    //public String role;

    EditText editEmail, editPassword, editName;
    Button btnRegister;
    public String role,name,email,password;
    //TextView txtforget;


    String URL= "http://192.168.43.26/clinic/registerindex.php";

    JSONParser jsonParser=new JSONParser();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_ui);

        editEmail=(EditText)findViewById(R.id.editEmail);
        editName=(EditText)findViewById(R.id.editName);
        editPassword=(EditText)findViewById(R.id.editPassword);
        btnRegister=(Button)findViewById(R.id.btnRegister);
        //spinner

        final Spinner spin=(Spinner)findViewById(R.id.spinner);

        spin.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,items);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);


        //editUserRole=(EditText)findViewById(R.id.editUserRole);
        //txtforget=(TextView)findViewById(R.id.forget);

        //btnSignIn=(Button)findViewById(R.id.btnSignIn);


        /*btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttemptLogin attemptLogin= new AttemptLogin();
                attemptLogin.execute(editName.getText().toString(),editPassword.getText().toString(),"","");
            }


        });*/

        btnRegister.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                AttemptRegister attemptRegister=new AttemptRegister();
                attemptRegister.execute(editName.getText().toString(),editEmail.getText().toString(),editPassword.getText().toString(),role);
            }
        });


    }

    public void onItemSelected(AdapterView<?> parent,View v,int position,long id){
        role=items[position];
    }
    public void onNothingSelected(AdapterView<?> parent){

    }
  private class AttemptRegister extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {


             role= args[3];
             email = args[1];
             password = args[2];
             name= args[0];


            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", name));
            params.add(new BasicNameValuePair("email",email));
            params.add(new BasicNameValuePair("password", password));

            params.add(new BasicNameValuePair("role", role));

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

                    //String message=result.getString("message");
                    if(code==1) {
                        Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(RegisterUI.this,MainActivity.class);
                        intent.putExtra("name",name);
                        intent.putExtra("password",password);
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
