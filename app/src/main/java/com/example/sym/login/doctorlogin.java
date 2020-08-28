package com.example.sym.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class doctorlogin extends AppCompatActivity {

    public int registernum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorlogin);

        Button doctorlogout = (Button) findViewById(R.id.doctorlogout);
        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        Button btnRegUpdate = (Button) findViewById(R.id.btnRegUpdate);

        Bundle b = getIntent().getExtras();
        final String rolename = b.getString("rolename");
        final String email=b.getString("email");
        final int regno=b.getInt("regno");

        /*final int regnumber=b.getInt("regnumber");
        registernum=regnumber+1;*/
        //Log.e("RoleName",rolename);

        doctorlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(doctorlogin.this, MainActivity.class);
                startActivity(intent);
                finish();
            }


        });
        if(rolename.equals(String.valueOf("General Practitioner"))) {
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(doctorlogin.this, GeneralPatientRegister.class);
                    intent.putExtra("email", email);
                    intent.putExtra("regno",regno);
                    intent.putExtra("rolename",rolename);
                    //intent.putExtra("registernum",registernum);
                    startActivity(intent);
                    finish();
                }


            });
        }
        else if(rolename.equals(String.valueOf("Neurologist"))){
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(doctorlogin.this, CNPatient.class);
                    startActivity(intent);
                    finish();
                }


            });
        }
        else if(rolename.equals(String.valueOf("Pulmonologist"))){
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(doctorlogin.this, PulmonaryPatient.class);
                    startActivity(intent);
                    finish();
                }


            });
        }
        else if(rolename.equals(String.valueOf("Gastroenterlogist"))){
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(doctorlogin.this, GastroPatient.class);
                    startActivity(intent);
                    finish();
                }


            });
        }
        else if(rolename.equals(String.valueOf("Hepatologist"))){
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(doctorlogin.this, HepatoPatient.class);
                    startActivity(intent);
                    finish();
                }


            });
        }
        else if(rolename.equals(String.valueOf("Cardiologist"))){
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(doctorlogin.this, CardioPatient.class);
                    startActivity(intent);
                    finish();
                }


            });
        }
        else{

        }
        btnRegUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(doctorlogin.this, UpdateExamination.class);
                startActivity(intent);
                finish();
            }


        });


    }
}
