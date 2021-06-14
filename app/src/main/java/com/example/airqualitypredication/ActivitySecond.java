package com.example.airqualitypredication;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivitySecond extends AppCompatActivity {
    List<String> modelNames;
    Spinner model_spinner;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please Select Model");
        bundle = new Bundle();
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // Toast.makeText(getApplicationContext(),"You clicked yes",Toast.LENGTH_LONG).show();

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        model_spinner = (Spinner) findViewById(R.id.spinner);
        modelNames = new ArrayList<>();
        modelNames.add("ARIMA");
        modelNames.add("LSTM");
        modelNames.add("fbProphet");
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, modelNames);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, modelNames);

        model_spinner.setAdapter(adapter);
        model_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = modelNames.get(position);
                String number = String.valueOf(position);
                bundle.putString("Model Name", s);
                bundle.putString("Position Number", number);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void OnClickButton(View view) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtras(bundle);
        startActivity(i);
    }
}