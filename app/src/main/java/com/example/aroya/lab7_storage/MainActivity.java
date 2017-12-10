package com.example.aroya.lab7_storage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button buttonOk,buttonClear;
    EditText pw,pwConf;
    SharedPreferences settings;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadSharedPreferences();
    }

    private void loadSharedPreferences(){
        settings=getSharedPreferences(getPackageName(),0);
        password=settings.getString("password",null);
        if(password==null){
            newPw();
        }
        else inputPw();

    }
    private void newPw(){
        setContentView(R.layout.new_pw);
        buttonOk=(Button)findViewById(R.id.new_pw_ok);
        buttonClear=(Button)findViewById(R.id.new_pw_clear);
        pw=(EditText)findViewById(R.id.new_pw);
        pwConf=(EditText)findViewById(R.id.new_conf_pw);

        buttonOk.setOnClickListener(new onNewPwOk());
        buttonClear.setOnClickListener(new onNewPwClear());
    }

    private void inputPw(){
        setContentView(R.layout.input_pw);
        pw=(EditText)findViewById(R.id.input_pw);
        buttonOk=(Button)findViewById(R.id.input_pw_ok);
        buttonClear=(Button)findViewById(R.id.input_pw_clear);

        buttonOk.setOnClickListener(new onInputOk());
        buttonClear.setOnClickListener(new onInputClear());
    }

    private class onNewPwOk implements View.OnClickListener{
        @Override
        public void onClick(View view){
            if(pw.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),getString(R.string.pw_empty),Toast.LENGTH_SHORT).show();
            }
            else if(!pw.getText().toString().equals(pwConf.getText().toString())){
                Toast.makeText(getApplicationContext(),getString(R.string.pw_mismatch),Toast.LENGTH_SHORT).show();
            }
            else{
                SharedPreferences.Editor editor=settings.edit();
                editor.putString("password",pw.getText().toString());
                editor.commit();
                Intent intent=new Intent(MainActivity.this,FileEditor.class);
                startActivity(intent);
                finish();
            }
        }
    }
    private class onNewPwClear implements View.OnClickListener{
        @Override
        public void onClick(View view){
            pw.setText("");
            pwConf.setText("");
        }
    }
    private class onInputOk implements View.OnClickListener{
        @Override
        public void onClick(View view){
            if(pw.getText().toString().equals(password)){
                Intent intent=new Intent(MainActivity.this,FileEditor.class);
                startActivity(intent);
                finish();
            }
            else Toast.makeText(getApplicationContext(),getString(R.string.pw_mismatch),Toast.LENGTH_SHORT).show();
        }
    }
    private class onInputClear implements View.OnClickListener{
        @Override
        public void onClick(View view){
            pw.setText("");
        }
    }
}
