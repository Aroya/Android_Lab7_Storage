package com.example.aroya.lab7_storage;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Aroya on 12/10/2017.
 */

public class FileEditor extends Activity{
    Button save,load,clear,delete;
    EditText name,content;
    String nameStr,contentStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_editor);

        init();

    }
    private void init(){
        save=(Button)findViewById(R.id.file_save);
        load=(Button)findViewById(R.id.file_load);
        clear=(Button)findViewById(R.id.file_clear);
        delete=(Button)findViewById(R.id.file_delete);
        name=(EditText)findViewById(R.id.file_name);
        content=(EditText)findViewById(R.id.file_content);

        save.setOnClickListener(new fileSave());
        load.setOnClickListener(new fileLoad());
        clear.setOnClickListener(new fileClear());
        delete.setOnClickListener(new fileDelete());
    }
    private class fileSave implements View.OnClickListener{
        @Override
        public void onClick(View view){
            nameStr=name.getText().toString();
            contentStr=content.getText().toString();
            try(FileOutputStream fileOutputStream=openFileOutput(nameStr, Context.MODE_PRIVATE)){
                fileOutputStream.write(contentStr.getBytes());
                fileOutputStream.flush();
                fileOutputStream.close();
                Toast.makeText(getApplicationContext(),"Success to save file.",Toast.LENGTH_SHORT).show();
            }
            catch (IOException e){
                Toast.makeText(getApplicationContext(),"Fail to save file.",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class fileLoad implements View.OnClickListener{
        @Override
        public void onClick(View view){
            nameStr=name.getText().toString();
            try(FileInputStream fileInputStream=openFileInput(nameStr)){
                byte[] contents=new byte[fileInputStream.available()];
                fileInputStream.read(contents);
                contentStr = new String(contents);
                content.setText(contentStr);
                Toast.makeText(getApplicationContext(),"Success to read file.",Toast.LENGTH_SHORT).show();
                fileInputStream.close();
            }
            catch (IOException e){
                Toast.makeText(getApplicationContext(),"Fail to read file.",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class fileClear implements View.OnClickListener{
        @Override
        public void onClick(View view){
            name.setText("");
            content.setText("");
        }
    }
    private class fileDelete implements View.OnClickListener{
        @Override
        public void onClick(View view){
            nameStr=name.getText().toString();
            File file=new File(getApplicationContext().getFilesDir(),nameStr);
            if(file.exists()){
                file.delete();
                Toast.makeText(getApplicationContext(),"Success to delete file.",Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(getApplicationContext(),"Fail to delete file.",Toast.LENGTH_SHORT).show();
        }
    }
}
