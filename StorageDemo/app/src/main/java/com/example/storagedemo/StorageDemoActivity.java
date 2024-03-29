package com.example.storagedemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StorageDemoActivity extends AppCompatActivity {

    private static EditText editText;

    private static final int CREATE_REQUEST_CODE = 40;
    private static final int OPEN_REQUEST_CODE = 41;
    private static final int SAVE_REQUEST_CODE = 42;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_demo);

        editText = (EditText)findViewById(R.id.fileText);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent resultData){
        Uri currentUri = null;

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == CREATE_REQUEST_CODE){
                if(resultData != null){
                    editText.setText("");
                }
            } else if(requestCode == SAVE_REQUEST_CODE){
                if(resultData != null){
                    currentUri = resultData.getData();
                    writeFileContent(currentUri);
                }
            } else if(requestCode == OPEN_REQUEST_CODE){
                if(resultData != null){
                    currentUri = resultData.getData();

                    try{
                        String content = readFileContent(currentUri);
                        editText.setText(content);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String readFileContent(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String currentline;
        while ((currentline = reader.readLine()) != null){
            stringBuilder.append(currentline + "\n");
        }
        inputStream.close();
        return stringBuilder.toString();
    }

    private void writeFileContent(Uri uri) {
        try{
            //
            ParcelFileDescriptor pfd = this.getContentResolver().openFileDescriptor(uri, "w");
            //
            FileOutputStream fileOutputStream = new FileOutputStream(pfd.getFileDescriptor());
            //
            String textContent = editText.getText().toString();
            //
            fileOutputStream.write(textContent.getBytes());
            // close file
            fileOutputStream.close();
            pfd.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void newFile(View view){
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);

        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "newfile.txt");

        startActivityForResult(intent, CREATE_REQUEST_CODE);
    }

    public void openFile(View view){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");

        startActivityForResult(intent, OPEN_REQUEST_CODE);
    }

    public void saveFile(View view){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");

        startActivityForResult(intent, SAVE_REQUEST_CODE);
    }


}























