package com.example.sym.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class Notitest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notitest);

        EditText title1=(EditText)findViewById(R.id.title);
        EditText message1=(EditText)findViewById(R.id.message);

        Bundle bn=getIntent().getExtras();
        if(bn!=null) {
            String title = bn.getString("Title");
            String message = bn.getString("Message");
            if(title!=null && message!=null) {
                title1.setText(title);
                message1.setText(message);
            }
        }
    }
}
