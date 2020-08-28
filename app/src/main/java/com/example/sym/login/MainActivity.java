package com.example.sym.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
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
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    /*//spinner
    TextView selection;
    String[] items={"doctor","drug seller"};*/


    EditText editEmail, editPassword, editName;
    Button btnSignIn, btnRegister;
    private ProgressDialog progressDialog;
    //TextView txtforget;
    String bb;

    String URL= "http://192.168.0.103/clinic/loginindex.php";

    JSONParser jsonParser=new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmail=(EditText)findViewById(R.id.editEmail);
        //editName=(EditText)findViewById(R.id.editName);
        editPassword=(EditText)findViewById(R.id.editPassword);

        btnSignIn=(Button)findViewById(R.id.btnSignIn);
        btnRegister=(Button)findViewById(R.id.btnRegister);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendTokenToServer();
                AttemptLogin attemptLogin= new AttemptLogin();
                attemptLogin.execute(editEmail.getText().toString(),editPassword.getText().toString(),"","");
            }


        });

        btnRegister.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                Intent intent=new Intent(MainActivity.this,RegisterUI.class);
                startActivity(intent);
                finish();
            }
        });


        Bundle bn=getIntent().getExtras();
        if(bn!=null) {
            String email = bn.getString("email");
            String psw = bn.getString("password");
            if(email!=null && psw!=null) {
                editName.setText(email);
                editPassword.setText(psw);
            }
        }

    }
    private void sendTokenToServer() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering Device...");
        progressDialog.show();

        final String token = SharedPrefManager.getInstance(this).getDeviceToken();


        final String email = editEmail.getText().toString();

        if (token == null) {
            progressDialog.dismiss();
            Toast.makeText(this, "Token not generated", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(MainActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Email", email);
                params.put("token", token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    private class AttemptLogin extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {


            //String role= args[3];
            //String email = args[2];
            String password = args[1];
            String email= args[0];


            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));
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
                    int code = result.getInt("success");
                    int regno=result.getInt("regno");
                    String rolename = result.getString("rolename");
                    String email=result.getString("email");
                    //String message=result.getString("message");
                    if (code == 1 && rolename.equals(String.valueOf("drug seller"))) {
                        Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, sellerlogin.class);
                        startActivity(intent);
                        finish();
                    } else if (code==1 && (rolename.equals(String.valueOf("Pulmonologist")) || rolename.equals(String.valueOf("Gastroenterologist")) ||
                    rolename.equals(String.valueOf("Cardiologist")) || rolename.equals(String.valueOf("Neurologist")) || rolename.equals(String.valueOf("Hepatologist")) || rolename.equals(String.valueOf("General Practitioner"))))
                    {
                        Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, doctorlogin.class);
                        intent.putExtra("rolename",rolename);
                        intent.putExtra("email",email);
                        intent.putExtra("regno",regno);
                        startActivity(intent);
                        finish();
                    }

                    /*else if(code==2 && message.equals(String.valueOf("Successfully registered the user"))){
                        Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();

                    }*/
                    else{
                        Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
                    }
                    //int code=result.getInt("success");
                    //String message=result.getString("message");


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
