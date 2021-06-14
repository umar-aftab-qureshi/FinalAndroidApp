package com.example.airqualitypredication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Double> carbon_arima, dust_arima, smoke_arima, sui_gas_arima, temp_arima;
    ArrayList<Double> carbon_fb, dust_fb, smoke_fb, sui_gas_fb, temp_fb;
    ArrayList<Double> day1_quality, day2_quality, day3_quality, day4_quality, day5_quality, day6_quality, day7_quality;
    ArrayList<Double> carbon_lstm, dust_lstm, smoke_lstm, sui_gas_lstm, temp_lstm;
    ProgressDialog pDialog;
    CardView day1, day2, day3, day4, day5, day6, day7;
    Bundle bundle;
    String modelName;
    Intent i;
    private static String Carbon_monoxide_unit = "ppm";
    private static String Smoke_unit = "SF";
    private static String Temerature_unit =(char) 0x00B0+"C";
    private static String Dust_unit = "µg/m3";
    private static String Sui_Gas_unit = "PA";

    private static double CarbonThreshold = 550.65;
    private static double DustThreshold = 0.12;
    private static double SmokeThreshold = 212.20;
    private static double Sui_Gas_Threshold = 60.18;
    private static double Temoerature_Threshold = 25.0;
    TextView day_Quality_1, day_Quality_2, day_Quality_3, day_Quality_4, day_Quality_5, day_Quality_6, day_Quality_7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_file);
        bundle = new Bundle();
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Loading...");
        i = new Intent(getApplicationContext(), GraphViewActivity.class);
        Bundle bundle = getIntent().getExtras();
        //Calling the function
        initizingAlltheViews();
        //Now Initilazing Textviews


        //Extract the data…
        modelName = bundle.getString("Model Name");
        String selected_position = bundle.getString("Position Number");
        bundle.putString("Model Name", modelName);

        //Toast.makeText(getApplicationContext(),"Model Name is "+modelName,Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(),"Selected Position is "+selected_position,Toast.LENGTH_LONG).show();

        Spinner model_spinner = findViewById(R.id.spinner);
        ArrayList<String> modelNames = new ArrayList<>();
        modelNames.add("ARIMA");
        modelNames.add("LSTM");
        modelNames.add("fbProphet");
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, modelNames);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, modelNames);

        model_spinner.setAdapter(adapter);
        model_spinner.setSelection(Integer.parseInt(selected_position));
        bundle.putString("Model", selected_position);
        //Working with spinner....
        model_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getting_data(modelNames.get(position));
                bundle.putString("Model Number", selected_position);
                model_spinner.setEnabled(false);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        day1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("Day","1");
                i.putExtras(bundle);

                startActivity(i);
            }
        });
        day2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("Day","2");

                i.putExtras(bundle);

                startActivity(i);
            }
        });
        day3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("Day","3");

                i.putExtras(bundle);

                startActivity(i);
            }
        });
        day4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("Day","4");

                i.putExtras(bundle);


                startActivity(i);
            }
        });
        day5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("Day","5");
                i.putExtras(bundle);

                startActivity(i);
            }
        });

        day6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("Day","6");
                i.putExtras(bundle);

                startActivity(i);
            }
        });
        day7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("Day","7");
                i.putExtras(bundle);

                startActivity(i);
            }
        });

    }

    private void getting_data(String path) {

        pDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Models");
        if (path.equals("ARIMA")) {

            //Toast.makeText(getApplicationContext(),"Inside if COndition Arima",Toast.LENGTH_SHORT).show();
            DatabaseReference pred = myRef.child(path);
            pred.child("Carbonmonoxide").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Model data = snapshot.getValue(Model.class);
                    carbon_arima.add(data.getDay_1());
                    day1_quality.add(data.getDay_1());
                    TextView carbon1 = findViewById(R.id.carbonmonoxide_1);
                    carbon1.setText(upto2decimalPlaces(carbon_arima.get(0))+" "+Carbon_monoxide_unit);
                    carbon_arima.add(data.getDay_2());
                    day2_quality.add(data.getDay_2());
                    TextView carbon2 = findViewById(R.id.carbonmonoxide_2);
                    carbon2.setText(upto2decimalPlaces(carbon_arima.get(1))+" "+Carbon_monoxide_unit);
                    carbon_arima.add(data.getDay_3());
                    day3_quality.add(data.getDay_3());
                    TextView carbon3 = findViewById(R.id.carbonmonoxide_3);
                    carbon3.setText(upto2decimalPlaces(carbon_arima.get(2))+" "+Carbon_monoxide_unit);
                    carbon_arima.add(data.getDay_4());
                    day4_quality.add(data.getDay_4());
                    TextView carbon4 = findViewById(R.id.carbonmonoxide_4);
                    carbon4.setText(upto2decimalPlaces(carbon_arima.get(3))+" "+Carbon_monoxide_unit);
                    carbon_arima.add(data.getDay_5());
                    day5_quality.add(data.getDay_5());
                    TextView carbon5 = findViewById(R.id.carbonmonoxide_5);
                    carbon5.setText(upto2decimalPlaces(carbon_arima.get(4))+" "+Carbon_monoxide_unit);
                    carbon_arima.add(data.getDay_6());
                    day6_quality.add(data.getDay_6());
                    TextView carbon6 = findViewById(R.id.carbonmonoxide_6);
                    carbon6.setText(upto2decimalPlaces(carbon_arima.get(5))+" "+Carbon_monoxide_unit);
                    carbon_arima.add(data.getDay_7());
                    day7_quality.add(data.getDay_7());
                    TextView carbon7 = findViewById(R.id.carbonmonoxide_7);
                    carbon7.setText(upto2decimalPlaces(carbon_arima.get(6))+" "+Carbon_monoxide_unit);
                    //mean Square Error
                    carbon_arima.add(data.getMSE());
                    //bundle.putString("Carbon_Arima", Double.toString(carbon_arima.get(7)));
                    //Toast.makeText(getApplicationContext(),Double.toString(carbon_arima.get(7)),Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            pred.child("Dust").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Model data = snapshot.getValue(Model.class);
                    dust_arima.add(data.getDay_1());
                    day1_quality.add(data.getDay_1());
                    TextView dust_1 = findViewById(R.id.dust_1);
                    dust_1.setText(upto2decimalPlaces(dust_arima.get(0))+" "+Dust_unit);
                    dust_arima.add(data.getDay_2());
                    day2_quality.add(data.getDay_2());
                    TextView dust_2 = findViewById(R.id.dust_2);
                    dust_2.setText(upto2decimalPlaces(dust_arima.get(1))+" "+Dust_unit);
                    dust_arima.add(data.getDay_3());
                    day3_quality.add(data.getDay_3());
                    TextView dust_3 = findViewById(R.id.dust_3);
                    dust_3.setText(upto2decimalPlaces(dust_arima.get(2))+" "+Dust_unit);
                    dust_arima.add(data.getDay_4());
                    day4_quality.add(data.getDay_4());
                    TextView dust_4 = findViewById(R.id.dust_4);
                    dust_4.setText(upto2decimalPlaces(dust_arima.get(3))+" "+Dust_unit);
                    dust_arima.add(data.getDay_5());
                    day5_quality.add(data.getDay_5());
                    TextView dust_5 = findViewById(R.id.dust_5);
                    dust_5.setText(upto2decimalPlaces(dust_arima.get(4))+" "+Dust_unit);
                    dust_arima.add(data.getDay_6());
                    day6_quality.add(data.getDay_6());
                    TextView dust_6 = findViewById(R.id.dust_6);
                    dust_6.setText(upto2decimalPlaces(dust_arima.get(5))+" "+Dust_unit);
                    dust_arima.add(data.getDay_7());
                    day7_quality.add(data.getDay_7());
                    TextView dust_7 = findViewById(R.id.dust_7);
                    dust_7.setText(upto2decimalPlaces(dust_arima.get(6))+" "+Dust_unit);
                    //mean Square Error
                    dust_arima.add(data.getMSE());
                    //bundle.putString("Dust Arima", Double.toString(dust_arima.get(7)));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            pred.child("Smoke").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Model data = snapshot.getValue(Model.class);
                    day1_quality.add(data.getDay_1());
                    day2_quality.add(data.getDay_2());
                    day3_quality.add(data.getDay_3());
                    day4_quality.add(data.getDay_4());
                    day5_quality.add(data.getDay_5());
                    day6_quality.add(data.getDay_6());
                    day7_quality.add(data.getDay_7());
                    smoke_arima.add(data.getDay_1());

                    TextView smoke_1 = findViewById(R.id.smoke_1);
                    smoke_1.setText(upto2decimalPlaces(smoke_arima.get(0))+" "+Smoke_unit);
                    smoke_arima.add(data.getDay_2());
                    TextView smoke_2 = findViewById(R.id.smoke_2);
                    smoke_2.setText(upto2decimalPlaces(smoke_arima.get(1))+" "+Smoke_unit);
                    smoke_arima.add(data.getDay_3());
                    TextView smoke_3 = findViewById(R.id.smoke_3);
                    smoke_3.setText(upto2decimalPlaces(smoke_arima.get(2))+" "+Smoke_unit);
                    smoke_arima.add(data.getDay_4());
                    TextView smoke_4 = findViewById(R.id.smoke_4);
                    smoke_4.setText(upto2decimalPlaces(smoke_arima.get(3))+" "+Smoke_unit);
                    smoke_arima.add(data.getDay_5());
                    TextView smoke_5 = findViewById(R.id.smoke_5);
                    smoke_5.setText(upto2decimalPlaces(smoke_arima.get(4))+" "+Smoke_unit);
                    smoke_arima.add(data.getDay_6());
                    TextView smoke_6 = findViewById(R.id.smoke_6);
                    smoke_6.setText(upto2decimalPlaces(smoke_arima.get(5))+" "+Smoke_unit);
                    smoke_arima.add(data.getDay_7());
                    TextView smoke_7 = findViewById(R.id.smoke_7);
                    smoke_7.setText(upto2decimalPlaces(smoke_arima.get(6))+" "+Smoke_unit);
                    //mean Square Error
                    smoke_arima.add(data.getMSE());
                    //bundle.putString("Smoke Arima", Double.toString(smoke_arima.get(7)));


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            pred.child("SuiGas").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Model data = snapshot.getValue(Model.class);
                    day1_quality.add(data.getDay_1());
                    day2_quality.add(data.getDay_2());
                    day3_quality.add(data.getDay_3());
                    day4_quality.add(data.getDay_4());
                    day5_quality.add(data.getDay_5());
                    day6_quality.add(data.getDay_6());
                    day7_quality.add(data.getDay_7());

                    sui_gas_arima.add(data.getDay_1());
                    TextView sui_gas1 = findViewById(R.id.sui_gas_1);
                    sui_gas1.setText(upto2decimalPlaces(sui_gas_arima.get(0))+" "+Sui_Gas_unit);
                    sui_gas_arima.add(data.getDay_2());
                    TextView sui_gas2 = findViewById(R.id.sui_gas_2);
                    sui_gas2.setText(upto2decimalPlaces(sui_gas_arima.get(1))+" "+Sui_Gas_unit);
                    sui_gas_arima.add(data.getDay_3());
                    TextView sui_gas3 = findViewById(R.id.sui_gas_3);
                    sui_gas3.setText(upto2decimalPlaces(sui_gas_arima.get(2))+" "+Sui_Gas_unit);
                    sui_gas_arima.add(data.getDay_4());
                    TextView sui_gas4 = findViewById(R.id.sui_gas_4);
                    sui_gas4.setText(upto2decimalPlaces(sui_gas_arima.get(3))+" "+Sui_Gas_unit);
                    sui_gas_arima.add(data.getDay_5());
                    TextView sui_gas5 = findViewById(R.id.sui_gas_5);
                    sui_gas5.setText(upto2decimalPlaces(sui_gas_arima.get(4))+" "+Sui_Gas_unit);
                    sui_gas_arima.add(data.getDay_6());
                    TextView sui_gas6 = findViewById(R.id.sui_gas_6);
                    sui_gas6.setText(upto2decimalPlaces(sui_gas_arima.get(5))+" "+Sui_Gas_unit);
                    sui_gas_arima.add(data.getDay_7());
                    TextView sui_gas7 = findViewById(R.id.sui_gas_7);
                    sui_gas7.setText(upto2decimalPlaces(sui_gas_arima.get(6))+" "+Sui_Gas_unit);
                    //mean Square Error
                    sui_gas_arima.add(data.getMSE());
                    //bundle.putString("SuiGas Arima", Double.toString(sui_gas_arima.get(7)));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            pred.child("Temperature").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Model data = snapshot.getValue(Model.class);
                    day1_quality.add(data.getDay_1());
                    day2_quality.add(data.getDay_2());
                    day3_quality.add(data.getDay_3());
                    day4_quality.add(data.getDay_4());
                    day5_quality.add(data.getDay_5());
                    day6_quality.add(data.getDay_6());
                    day7_quality.add(data.getDay_7());
                    temp_arima.add(data.getDay_1());
                    TextView temp_1 = findViewById(R.id.temperature_1);
                    temp_1.setText(upto2decimalPlaces(temp_arima.get(0))+" "+Temerature_unit);
                    temp_arima.add(data.getDay_2());
                    TextView temp_2 = findViewById(R.id.temperature_2);
                    temp_2.setText(upto2decimalPlaces(temp_arima.get(1))+" "+Temerature_unit);
                    temp_arima.add(data.getDay_3());
                    TextView temp_3 = findViewById(R.id.temperature_3);
                    temp_3.setText(upto2decimalPlaces(temp_arima.get(2))+" "+Temerature_unit);
                    temp_arima.add(data.getDay_4());
                    TextView temp_4 = findViewById(R.id.temperature_4);
                    temp_4.setText(upto2decimalPlaces(temp_arima.get(3))+" "+Temerature_unit);
                    temp_arima.add(data.getDay_5());
                    TextView temp_5 = findViewById(R.id.temperature_5);
                    temp_5.setText(upto2decimalPlaces(temp_arima.get(4))+" "+Temerature_unit);
                    temp_arima.add(data.getDay_6());
                    TextView temp_6 = findViewById(R.id.temperature_6);
                    temp_6.setText(upto2decimalPlaces(temp_arima.get(5))+" "+Temerature_unit);
                    temp_arima.add(data.getDay_7());
                    TextView temp_7 = findViewById(R.id.temperature_7);
                    temp_7.setText(upto2decimalPlaces(temp_arima.get(6))+" "+Temerature_unit);
                    pDialog.hide();
                    //mean Square Error
                    temp_arima.add(data.getMSE());
                    //bundle.putString("Temp Arima", Double.toString(temp_arima.get(7)));
                    //Toast.makeText(getApplicationContext(),Integer.toString(day2_quality.size()),Toast.LENGTH_LONG).show();
                    finding_Air_Quality(day1_quality, "Day 1");
                    finding_Air_Quality(day2_quality, "Day 2");
                    finding_Air_Quality(day3_quality, "Day 3");
                    finding_Air_Quality(day4_quality, "Day 4");
                    finding_Air_Quality(day5_quality, "Day 5");
                    finding_Air_Quality(day6_quality, "Day 6");
                    finding_Air_Quality(day7_quality, "Day 7");

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        } else if (path == "fbProphet") {
            //Toast.makeText(getApplicationContext(),"Inside if COndition fbrophet",Toast.LENGTH_SHORT).show();

            DatabaseReference pred = myRef.child(path);
            pred.child("Carbonmonoxide").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Model data = snapshot.getValue(Model.class);
                    day1_quality.add(data.getDay_1());
                    day2_quality.add(data.getDay_2());
                    day3_quality.add(data.getDay_3());
                    day4_quality.add(data.getDay_4());
                    day5_quality.add(data.getDay_5());
                    day6_quality.add(data.getDay_6());
                    day7_quality.add(data.getDay_7());
                    carbon_fb.add(data.getDay_1());
                    TextView carbon_1 = findViewById(R.id.carbonmonoxide_1);
                    carbon_1.setText(upto2decimalPlaces(carbon_fb.get(0))+" "+Carbon_monoxide_unit);
                    carbon_fb.add(data.getDay_2());
                    TextView carbon_2 = findViewById(R.id.carbonmonoxide_2);
                    carbon_2.setText(upto2decimalPlaces(carbon_fb.get(1))+" "+Carbon_monoxide_unit);
                    carbon_fb.add(data.getDay_3());
                    TextView carbon_3 = findViewById(R.id.carbonmonoxide_3);
                    carbon_3.setText(upto2decimalPlaces(carbon_fb.get(2))+" "+Carbon_monoxide_unit);
                    carbon_fb.add(data.getDay_4());
                    TextView carbon_4 = findViewById(R.id.carbonmonoxide_4);
                    carbon_4.setText(upto2decimalPlaces(carbon_fb.get(3))+" "+Carbon_monoxide_unit);
                    carbon_fb.add(data.getDay_5());
                    TextView carbon_5 = findViewById(R.id.carbonmonoxide_5);
                    carbon_5.setText(upto2decimalPlaces(carbon_fb.get(4))+" "+Carbon_monoxide_unit);
                    carbon_fb.add(data.getDay_6());
                    TextView carbon_6 = findViewById(R.id.carbonmonoxide_6);
                    carbon_6.setText(upto2decimalPlaces(carbon_fb.get(5))+" "+Carbon_monoxide_unit);
                    carbon_fb.add(data.getDay_7());
                    TextView carbon_7 = findViewById(R.id.carbonmonoxide_7);
                    carbon_7.setText(upto2decimalPlaces(carbon_fb.get(6))+" "+Carbon_monoxide_unit);
                    //mean Square Error
                    carbon_fb.add(data.getMSE());
                    //bundle.putString("Carbon Fb", Double.toString(carbon_fb.get(7)));
                    //Toast.makeText(getApplicationContext(),Double.toString(carbon.get(7)),Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            pred.child("Dust").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Model data = snapshot.getValue(Model.class);
                    day1_quality.add(data.getDay_1());
                    day2_quality.add(data.getDay_2());
                    day3_quality.add(data.getDay_3());
                    day4_quality.add(data.getDay_4());
                    day5_quality.add(data.getDay_5());
                    day6_quality.add(data.getDay_6());
                    day7_quality.add(data.getDay_7());
                    dust_fb.add(data.getDay_1());
                    TextView dust_1 = findViewById(R.id.dust_1);
                    dust_1.setText(upto2decimalPlaces(dust_fb.get(0))+" "+Dust_unit);
                    dust_fb.add(data.getDay_2());
                    TextView dust_2 = findViewById(R.id.dust_2);
                    dust_2.setText(upto2decimalPlaces(dust_fb.get(1))+" "+Dust_unit);
                    dust_fb.add(data.getDay_3());
                    TextView dust_3 = findViewById(R.id.dust_3);
                    dust_3.setText(upto2decimalPlaces(dust_fb.get(2))+" "+Dust_unit);

                    dust_fb.add(data.getDay_4());
                    TextView dust_4 = findViewById(R.id.dust_4);
                    dust_4.setText(upto2decimalPlaces(dust_fb.get(3))+" "+Dust_unit);
                    dust_fb.add(data.getDay_5());
                    TextView dust_5 = findViewById(R.id.dust_5);
                    dust_5.setText(upto2decimalPlaces(dust_fb.get(4))+" "+Dust_unit);
                    dust_fb.add(data.getDay_6());
                    TextView dust_6 = findViewById(R.id.dust_6);
                    dust_6.setText(upto2decimalPlaces(dust_fb.get(5))+" "+Dust_unit);
                    dust_fb.add(data.getDay_7());
                    TextView dust_7 = findViewById(R.id.dust_7);
                    dust_7.setText(upto2decimalPlaces(dust_fb.get(6))+" "+Dust_unit);
                    //mean Square Error
                    dust_fb.add(data.getMSE());
                    //bundle.putString("Dust Fb", Double.toString(dust_fb.get(7)));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            pred.child("Smoke").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Model data = snapshot.getValue(Model.class);
                    day1_quality.add(data.getDay_1());
                    day2_quality.add(data.getDay_2());
                    day3_quality.add(data.getDay_3());
                    day4_quality.add(data.getDay_4());
                    day5_quality.add(data.getDay_5());
                    day6_quality.add(data.getDay_6());
                    day7_quality.add(data.getDay_7());
                    smoke_fb.add(data.getDay_1());
                    TextView smoke_1 = findViewById(R.id.smoke_1);
                    smoke_1.setText(upto2decimalPlaces(smoke_fb.get(0))+" "+Smoke_unit);
                    smoke_fb.add(data.getDay_2());
                    TextView smoke_2 = findViewById(R.id.smoke_2);
                    smoke_2.setText(upto2decimalPlaces(smoke_fb.get(1))+" "+Smoke_unit);
                    smoke_fb.add(data.getDay_3());
                    TextView smoke_3 = findViewById(R.id.smoke_3);
                    smoke_3.setText(upto2decimalPlaces(smoke_fb.get(2))+" "+Smoke_unit);
                    smoke_fb.add(data.getDay_4());
                    TextView smoke_4 = findViewById(R.id.smoke_4);
                    smoke_4.setText(upto2decimalPlaces(smoke_fb.get(3))+" "+Smoke_unit);
                    smoke_fb.add(data.getDay_5());
                    TextView smoke_5 = findViewById(R.id.smoke_5);
                    smoke_5.setText(upto2decimalPlaces(smoke_fb.get(4))+" "+Smoke_unit);
                    smoke_fb.add(data.getDay_6());
                    TextView smoke_6 = findViewById(R.id.smoke_6);
                    smoke_6.setText(upto2decimalPlaces(smoke_fb.get(5))+" "+Smoke_unit);
                    smoke_fb.add(data.getDay_7());
                    TextView smoke_7 = findViewById(R.id.smoke_7);
                    smoke_7.setText(upto2decimalPlaces(smoke_fb.get(6))+" "+Smoke_unit);
                    //mean Square Error
                    smoke_fb.add(data.getMSE());
                    //bundle.putString("Smoke fb", Double.toString(smoke_fb.get(7)));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            pred.child("SuiGas").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Model data = snapshot.getValue(Model.class);
                    day1_quality.add(data.getDay_1());
                    day2_quality.add(data.getDay_2());
                    day3_quality.add(data.getDay_3());
                    day4_quality.add(data.getDay_4());
                    day5_quality.add(data.getDay_5());
                    day6_quality.add(data.getDay_6());
                    day7_quality.add(data.getDay_7());
                    sui_gas_fb.add(data.getDay_1());
                    TextView sui_gas1 = findViewById(R.id.sui_gas_1);
                    sui_gas1.setText(upto2decimalPlaces(sui_gas_fb.get(0))+" "+Sui_Gas_unit);
                    sui_gas_fb.add(data.getDay_2());
                    TextView sui_gas2 = findViewById(R.id.sui_gas_2);
                    sui_gas2.setText(upto2decimalPlaces(sui_gas_fb.get(1))+" "+Sui_Gas_unit);
                    sui_gas_fb.add(data.getDay_3());
                    TextView sui_gas3 = findViewById(R.id.sui_gas_3);
                    sui_gas3.setText(upto2decimalPlaces(sui_gas_fb.get(2))+" "+Sui_Gas_unit);
                    sui_gas_fb.add(data.getDay_4());
                    TextView sui_gas4 = findViewById(R.id.sui_gas_4);
                    sui_gas4.setText(upto2decimalPlaces(sui_gas_fb.get(3))+" "+Sui_Gas_unit);
                    sui_gas_fb.add(data.getDay_5());
                    TextView sui_gas5 = findViewById(R.id.sui_gas_5);
                    sui_gas5.setText(upto2decimalPlaces(sui_gas_fb.get(4))+" "+Sui_Gas_unit);
                    sui_gas_fb.add(data.getDay_6());
                    TextView sui_gas6 = findViewById(R.id.sui_gas_6);
                    sui_gas6.setText(upto2decimalPlaces(sui_gas_fb.get(5))+" "+Sui_Gas_unit);
                    sui_gas_fb.add(data.getDay_7());
                    TextView sui_gas7 = findViewById(R.id.sui_gas_7);
                    sui_gas7.setText(upto2decimalPlaces(sui_gas_fb.get(6))+" "+Sui_Gas_unit);
                    //mean Square Error
                    sui_gas_fb.add(data.getMSE());
                    //bundle.putString("Sui_gas_fb", Double.toString(sui_gas_fb.get(7)));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            pred.child("Temperature").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Model data = snapshot.getValue(Model.class);
                    day1_quality.add(data.getDay_1());
                    day2_quality.add(data.getDay_2());
                    day3_quality.add(data.getDay_3());
                    day4_quality.add(data.getDay_4());
                    day5_quality.add(data.getDay_5());
                    day6_quality.add(data.getDay_6());
                    day7_quality.add(data.getDay_7());
                    temp_fb.add(data.getDay_1());
                    TextView temp_1 = findViewById(R.id.temperature_1);
                    temp_1.setText(upto2decimalPlaces(temp_fb.get(0))+" "+Temerature_unit);

                    temp_fb.add(data.getDay_2());
                    TextView temp_2 = findViewById(R.id.temperature_2);
                    temp_2.setText(upto2decimalPlaces(temp_fb.get(1))+" "+Temerature_unit);
                    temp_fb.add(data.getDay_3());
                    TextView temp_3 = findViewById(R.id.temperature_3);
                    temp_3.setText(upto2decimalPlaces(temp_fb.get(2))+" "+Temerature_unit);
                    temp_fb.add(data.getDay_4());
                    TextView temp_4 = findViewById(R.id.temperature_4);
                    temp_4.setText(upto2decimalPlaces(temp_fb.get(3))+" "+Temerature_unit);
                    temp_fb.add(data.getDay_5());
                    TextView temp_5 = findViewById(R.id.temperature_5);
                    temp_5.setText(upto2decimalPlaces(temp_fb.get(4))+" "+Temerature_unit);
                    temp_fb.add(data.getDay_6());
                    TextView temp_6 = findViewById(R.id.temperature_6);
                    temp_6.setText(upto2decimalPlaces(temp_fb.get(5))+" "+Temerature_unit);
                    temp_fb.add(data.getDay_7());
                    TextView temp_7 = findViewById(R.id.temperature_7);
                    temp_7.setText(upto2decimalPlaces(temp_fb.get(6))+" "+Temerature_unit);
                    pDialog.hide();
                    //Toast.makeText(getApplicationContext(),Double.toString(temp_fb.get(1)),Toast.LENGTH_LONG).show();
                    //mean Square Error
                    temp_fb.add(data.getMSE());
                    //bundle.putString("Temp_fb", Double.toString(temp_fb.get(7)));
                    finding_Air_Quality(day1_quality, "Day 1");
                    finding_Air_Quality(day2_quality, "Day 2");
                    finding_Air_Quality(day3_quality, "Day 3");
                    finding_Air_Quality(day4_quality, "Day 4");
                    finding_Air_Quality(day5_quality, "Day 5");
                    finding_Air_Quality(day6_quality, "Day 6");
                    finding_Air_Quality(day7_quality, "Day 7");

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        } else if (path == "LSTM") {
            DatabaseReference pred = myRef.child(path);
            pred.child("Carbonmonoxide").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Model data = snapshot.getValue(Model.class);
                    day1_quality.add(data.getDay_1());
                    day2_quality.add(data.getDay_2());
                    day3_quality.add(data.getDay_3());
                    day4_quality.add(data.getDay_4());
                    day5_quality.add(data.getDay_5());
                    day6_quality.add(data.getDay_6());
                    day7_quality.add(data.getDay_7());
                    carbon_lstm.add(data.getDay_1());
                    TextView carbon1 = findViewById(R.id.carbonmonoxide_1);
                    carbon1.setText(upto2decimalPlaces(carbon_lstm.get(0))+" "+Carbon_monoxide_unit);
                    carbon_lstm.add(data.getDay_2());
                    TextView carbon2 = findViewById(R.id.carbonmonoxide_2);
                    carbon2.setText(upto2decimalPlaces(carbon_lstm.get(1))+" "+Carbon_monoxide_unit);
                    carbon_lstm.add(data.getDay_3());
                    TextView carbon3 = findViewById(R.id.carbonmonoxide_3);
                    carbon3.setText(upto2decimalPlaces(carbon_lstm.get(2))+" "+Carbon_monoxide_unit);
                    carbon_lstm.add(data.getDay_4());
                    TextView carbon4 = findViewById(R.id.carbonmonoxide_4);
                    carbon4.setText(upto2decimalPlaces(carbon_lstm.get(3))+" "+Carbon_monoxide_unit);
                    carbon_lstm.add(data.getDay_5());
                    TextView carbon5 = findViewById(R.id.carbonmonoxide_5);
                    carbon5.setText(upto2decimalPlaces(carbon_lstm.get(4))+" "+Carbon_monoxide_unit);
                    carbon_lstm.add(data.getDay_6());
                    TextView carbon6 = findViewById(R.id.carbonmonoxide_6);
                    carbon6.setText(upto2decimalPlaces(carbon_lstm.get(5))+" "+Carbon_monoxide_unit);
                    carbon_lstm.add(data.getDay_7());
                    TextView carbon7 = findViewById(R.id.carbonmonoxide_7);
                    carbon7.setText(upto2decimalPlaces(carbon_lstm.get(6))+" "+Carbon_monoxide_unit);
                    //mean Square Error
                    carbon_lstm.add(data.getMSE());
                    //bundle.putString("Carbon Lstm", Double.toString(carbon_lstm.get(7)));
                    //Toast.makeText(getApplicationContext(),Double.toString(carbon_arima.get(7)),Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            pred.child("Dust").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Model data = snapshot.getValue(Model.class);
                    day1_quality.add(data.getDay_1());
                    day2_quality.add(data.getDay_2());
                    day3_quality.add(data.getDay_3());
                    day4_quality.add(data.getDay_4());
                    day5_quality.add(data.getDay_5());
                    day6_quality.add(data.getDay_6());
                    day7_quality.add(data.getDay_7());
                    dust_lstm.add(data.getDay_1());
                    TextView dust_1 = findViewById(R.id.dust_1);
                    dust_1.setText(upto2decimalPlaces(dust_lstm.get(0))+" "+Dust_unit);
                    dust_lstm.add(data.getDay_2());
                    TextView dust_2 = findViewById(R.id.dust_2);
                    dust_2.setText(upto2decimalPlaces(dust_lstm.get(1))+" "+Dust_unit);
                    dust_lstm.add(data.getDay_3());
                    TextView dust_3 = findViewById(R.id.dust_3);
                    dust_3.setText(upto2decimalPlaces(dust_lstm.get(2))+" "+Dust_unit);
                    dust_lstm.add(data.getDay_4());
                    TextView dust_4 = findViewById(R.id.dust_4);
                    dust_4.setText(upto2decimalPlaces(dust_lstm.get(3))+" "+Dust_unit);
                    dust_lstm.add(data.getDay_5());
                    TextView dust_5 = findViewById(R.id.dust_5);
                    dust_5.setText(upto2decimalPlaces(dust_lstm.get(4))+" "+Dust_unit);
                    dust_lstm.add(data.getDay_6());
                    TextView dust_6 = findViewById(R.id.dust_6);
                    dust_6.setText(upto2decimalPlaces(dust_lstm.get(5))+" "+Dust_unit);
                    dust_lstm.add(data.getDay_7());
                    TextView dust_7 = findViewById(R.id.dust_7);
                    dust_7.setText(upto2decimalPlaces(dust_lstm.get(6))+" "+Dust_unit);
                    //mean Square Error
                    dust_lstm.add(data.getMSE());
                    //bundle.putString("dust_lstm", Double.toString(dust_lstm.get(7)));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            pred.child("Smoke").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Model data = snapshot.getValue(Model.class);
                    day1_quality.add(data.getDay_1());
                    day2_quality.add(data.getDay_2());
                    day3_quality.add(data.getDay_3());
                    day4_quality.add(data.getDay_4());
                    day5_quality.add(data.getDay_5());
                    day6_quality.add(data.getDay_6());
                    day7_quality.add(data.getDay_7());
                    smoke_lstm.add(data.getDay_1());
                    TextView smoke_1 = findViewById(R.id.smoke_1);
                    smoke_1.setText(upto2decimalPlaces(smoke_lstm.get(0))+" "+Smoke_unit);
                    smoke_lstm.add(data.getDay_2());
                    TextView smoke_2 = findViewById(R.id.smoke_2);
                    smoke_2.setText(upto2decimalPlaces(smoke_lstm.get(1))+" "+Smoke_unit);
                    smoke_lstm.add(data.getDay_3());
                    TextView smoke_3 = findViewById(R.id.smoke_3);
                    smoke_3.setText(upto2decimalPlaces(smoke_lstm.get(2))+" "+Smoke_unit);
                    smoke_lstm.add(data.getDay_4());
                    TextView smoke_4 = findViewById(R.id.smoke_4);
                    smoke_4.setText(upto2decimalPlaces(smoke_lstm.get(3))+" "+Smoke_unit);
                    smoke_lstm.add(data.getDay_5());
                    TextView smoke_5 = findViewById(R.id.smoke_5);
                    smoke_5.setText(upto2decimalPlaces(smoke_lstm.get(4))+" "+Smoke_unit);
                    smoke_lstm.add(data.getDay_6());
                    TextView smoke_6 = findViewById(R.id.smoke_6);
                    smoke_6.setText(upto2decimalPlaces(smoke_lstm.get(5))+" "+Smoke_unit);
                    smoke_lstm.add(data.getDay_7());
                    TextView smoke_7 = findViewById(R.id.smoke_7);
                    smoke_7.setText(upto2decimalPlaces(smoke_lstm.get(6))+" "+Smoke_unit);
                    //mean Square Error
                    smoke_lstm.add(data.getMSE());
                    //bundle.putString("Smoke Lstm", Double.toString(smoke_lstm.get(7)));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            pred.child("SuiGas").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Model data = snapshot.getValue(Model.class);
                    day1_quality.add(data.getDay_1());
                    day2_quality.add(data.getDay_2());
                    day3_quality.add(data.getDay_3());
                    day4_quality.add(data.getDay_4());
                    day5_quality.add(data.getDay_5());
                    day6_quality.add(data.getDay_6());
                    day7_quality.add(data.getDay_7());
                    sui_gas_lstm.add(data.getDay_1());
                    TextView sui_gas1 = findViewById(R.id.sui_gas_1);
                    sui_gas1.setText(upto2decimalPlaces(sui_gas_lstm.get(0))+" "+Sui_Gas_unit);
                    sui_gas_lstm.add(data.getDay_2());
                    TextView sui_gas2 = findViewById(R.id.sui_gas_2);
                    sui_gas2.setText(upto2decimalPlaces(sui_gas_lstm.get(1))+" "+Sui_Gas_unit);
                    sui_gas_lstm.add(data.getDay_3());
                    TextView sui_gas3 = findViewById(R.id.sui_gas_3);
                    sui_gas3.setText(upto2decimalPlaces(sui_gas_lstm.get(2))+" "+Sui_Gas_unit);
                    sui_gas_lstm.add(data.getDay_4());
                    TextView sui_gas4 = findViewById(R.id.sui_gas_4);
                    sui_gas4.setText(upto2decimalPlaces(sui_gas_lstm.get(3))+" "+Sui_Gas_unit);
                    sui_gas_lstm.add(data.getDay_5());
                    TextView sui_gas5 = findViewById(R.id.sui_gas_5);
                    sui_gas5.setText(upto2decimalPlaces(sui_gas_lstm.get(4))+" "+Sui_Gas_unit);
                    sui_gas_lstm.add(data.getDay_6());
                    TextView sui_gas6 = findViewById(R.id.sui_gas_6);
                    sui_gas6.setText(upto2decimalPlaces(sui_gas_lstm.get(5))+" "+Sui_Gas_unit);
                    sui_gas_lstm.add(data.getDay_7());
                    TextView sui_gas7 = findViewById(R.id.sui_gas_7);
                    sui_gas7.setText(upto2decimalPlaces(sui_gas_lstm.get(6))+" "+Sui_Gas_unit);
                    //mean Square Error
                    sui_gas_lstm.add(data.getMSE());
                    //bundle.putString("Sui_gas_lstm", Double.toString(sui_gas_lstm.get(7)));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            pred.child("Temperature").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Model data = snapshot.getValue(Model.class);
                    day1_quality.add(data.getDay_1());
                    day2_quality.add(data.getDay_2());
                    day3_quality.add(data.getDay_3());
                    day4_quality.add(data.getDay_4());
                    day5_quality.add(data.getDay_5());
                    day6_quality.add(data.getDay_6());
                    day7_quality.add(data.getDay_7());
                    temp_lstm.add(data.getDay_1());
                    TextView temp_1 = findViewById(R.id.temperature_1);
                    temp_1.setText(upto2decimalPlaces(temp_lstm.get(0))+" "+Temerature_unit);
                    temp_lstm.add(data.getDay_2());
                    TextView temp_2 = findViewById(R.id.temperature_2);
                    temp_2.setText(upto2decimalPlaces(temp_lstm.get(1))+" "+Temerature_unit);
                    temp_lstm.add(data.getDay_3());
                    TextView temp_3 = findViewById(R.id.temperature_3);
                    temp_3.setText(upto2decimalPlaces(temp_lstm.get(2))+" "+Temerature_unit);
                    temp_lstm.add(data.getDay_4());
                    TextView temp_4 = findViewById(R.id.temperature_4);
                    temp_4.setText(upto2decimalPlaces(temp_lstm.get(3))+" "+Temerature_unit);
                    temp_lstm.add(data.getDay_5());
                    TextView temp_5 = findViewById(R.id.temperature_5);
                    temp_5.setText(upto2decimalPlaces(temp_lstm.get(4))+" "+Temerature_unit);
                    temp_lstm.add(data.getDay_6());
                    TextView temp_6 = findViewById(R.id.temperature_6);
                    temp_6.setText(upto2decimalPlaces(temp_lstm.get(5))+" "+Temerature_unit);
                    temp_lstm.add(data.getDay_7());
                    TextView temp_7 = findViewById(R.id.temperature_7);
                    temp_7.setText(upto2decimalPlaces(temp_lstm.get(6))+" "+Temerature_unit);
                    pDialog.hide();
                    //mean Square Error
                    temp_lstm.add(data.getMSE());
                    //bundle.putString("Temp lstm", Double.toString(temp_lstm.get(7)));
                    finding_Air_Quality(day1_quality, "Day 1");
                    finding_Air_Quality(day2_quality, "Day 2");
                    finding_Air_Quality(day3_quality, "Day 3");
                    finding_Air_Quality(day4_quality, "Day 4");
                    finding_Air_Quality(day5_quality, "Day 5");
                    finding_Air_Quality(day6_quality, "Day 6");
                    finding_Air_Quality(day7_quality, "Day 7");

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


    }

    private void initizingAlltheViews() {
        //Initilazing ArrayList
        day1_quality = new ArrayList<>();
        day2_quality = new ArrayList<>();
        day3_quality = new ArrayList<>();
        day4_quality = new ArrayList<>();
        day5_quality = new ArrayList<>();
        day6_quality = new ArrayList<>();
        day7_quality = new ArrayList<>();

        carbon_arima = new ArrayList<>();
        dust_arima = new ArrayList<>();
        smoke_arima = new ArrayList<>();
        sui_gas_arima = new ArrayList<>();
        temp_arima = new ArrayList<>();
        carbon_fb = new ArrayList<>();
        dust_fb = new ArrayList<>();
        smoke_fb = new ArrayList<>();
        sui_gas_fb = new ArrayList<>();
        temp_fb = new ArrayList<>();
        carbon_lstm = new ArrayList<>();
        dust_lstm = new ArrayList<>();
        smoke_lstm = new ArrayList<>();
        sui_gas_lstm = new ArrayList<>();
        temp_lstm = new ArrayList<>();
        day_Quality_1 = findViewById(R.id.QualityValue_1);
        day_Quality_2 = findViewById(R.id.QualityValue_2);
        day_Quality_3 = findViewById(R.id.QualityValue_3);
        day_Quality_4 = findViewById(R.id.QualityValue_4);
        day_Quality_5 = findViewById(R.id.QualityValue_5);
        day_Quality_6 = findViewById(R.id.QualityValue_6);
        day_Quality_7 = findViewById(R.id.QualityValue_7);
        //Initializing Card Views
        day1 = findViewById(R.id.card_1);
        day2 = findViewById(R.id.card_2);
        day3 = findViewById(R.id.card_3);
        day4 = findViewById(R.id.card_4);
        day5 = findViewById(R.id.card_5);
        day6 = findViewById(R.id.card_6);
        day7 = findViewById(R.id.card_7);


    }

    private String upto2decimalPlaces(double d) {
        DecimalFormat df = new DecimalFormat("0.00");
        String updatedValue = df.format(d);
        //Toast.makeText(getApplicationContext(),updatedValue,Toast.LENGTH_LONG).show();
        return updatedValue;
    }

    private void finding_Air_Quality(ArrayList<Double> array, String s) {
        int result = 0;
        String quality = "";


        for (int i = 0; i < array.size(); i++) {


            if (i == 0) {
                if (array.get(i) <= CarbonThreshold && array.get(i) > 0) {
                    //Toast.makeText(getApplicationContext(), Double.toString(array.get(i)), Toast.LENGTH_SHORT).show();
                    result++;
                }

            } else if (i == 1) {
                if (array.get(i) <= DustThreshold && array.get(i) > 0) {
                    //Toast.makeText(getApplicationContext(), Double.toString(array.get(i)), Toast.LENGTH_SHORT).show();
                    result++;
                }

            } else if (i == 2) {
                if (array.get(i) <= SmokeThreshold && array.get(i) > 0) {
                    //Toast.makeText(getApplicationContext(), Double.toString(array.get(i)), Toast.LENGTH_SHORT).show();
                    result++;
                }

            } else if (i == 3) {
                if (array.get(i) <= Sui_Gas_Threshold && array.get(i) > 0) {
                    //Toast.makeText(getApplicationContext(), Double.toString(array.get(i)), Toast.LENGTH_SHORT).show();
                    result++;
                }

            } else if (i == 4) {
                //Toast.makeText(getApplicationContext(), Double.toString(array.get(i)), Toast.LENGTH_SHORT).show();
                if (array.get(i) <= Temoerature_Threshold && array.get(i) > 0) {
                    result++;
                }

            }


        }
        //Toast.makeText(getApplicationContext(),Integer.toString(result), Toast.LENGTH_SHORT).show();
        if (result == 5) {
            quality = "Best";
        } else if (result >= 3 && result <= 4) {
            quality = "Better";
        } else if (result < 3) {
            quality = "Worst";
        }

        if (s.equals("Day 1")) {
            day_Quality_1.setText(quality);
            if (quality.equals("Best")) {
                Drawable drawable = getResources().getDrawable(R.drawable.rounded_corner_best);
                day_Quality_1.setBackground(drawable);
                //day_Quality_1.setBackgroundColor(Color.parseColor("#03913E"));
            } else if (quality.equals("Better")) {
                //day_Quality_1.setBackgroundColor(Color.parseColor("#98F4BE"));
                Drawable drawable = getResources().getDrawable(R.drawable.rounded_corner_better);
                day_Quality_1.setBackground(drawable);
            } else if (quality.equals("Worst")) {
                //day_Quality_1.setBackgroundColor(Color.parseColor("#D50000"));
                Drawable drawable = getResources().getDrawable(R.drawable.rounded_corner_worst);
                day_Quality_1.setBackground(drawable);
            }
        }
        if (s.equals("Day 2")) {
            day_Quality_2.setText(quality);
            if (quality.equals("Best")) {
                //day_Quality_2.setBackgroundColor(Color.parseColor("#03913E"));
                Drawable drawable = getResources().getDrawable(R.drawable.rounded_corner_best);
                day_Quality_2.setBackground(drawable);
            } else if (quality.equals("Better")) {
                //day_Quality_2.setBackgroundColor(Color.parseColor("#98F4BE"));
                Drawable drawable = getResources().getDrawable(R.drawable.rounded_corner_better);
                day_Quality_2.setBackground(drawable);
            } else if (quality.equals("Worst")) {
                //day_Quality_2.setBackgroundColor(Color.parseColor("#D50000"));
                Drawable drawable = getResources().getDrawable(R.drawable.rounded_corner_worst);
                day_Quality_2.setBackground(drawable);
            }
        }
        if (s.equals("Day 3")) {
            day_Quality_3.setText(quality);
            if (quality.equals("Best")) {
                //day_Quality_3.setBackgroundColor(Color.parseColor("#03913E"));
                Drawable drawable = getResources().getDrawable(R.drawable.rounded_corner_best);
                day_Quality_3.setBackground(drawable);
            } else if (quality.equals("Better")) {
                //day_Quality_3.setBackgroundColor(Color.parseColor("#98F4BE"));
                Drawable drawable = getResources().getDrawable(R.drawable.rounded_corner_better);
                day_Quality_3.setBackground(drawable);
            } else if (quality.equals("Worst")) {
                //day_Quality_3.setBackgroundColor(Color.parseColor("#D50000"));
                Drawable drawable = getResources().getDrawable(R.drawable.rounded_corner_worst);
                day_Quality_3.setBackground(drawable);
            }
        }
        if (s.equals("Day 4")) {
            day_Quality_4.setText(quality);
            if (quality.equals("Best")) {
                //day_Quality_4.setBackgroundColor(Color.parseColor("#03913E"));
                Drawable drawable = getResources().getDrawable(R.drawable.rounded_corner_best);
                day_Quality_4.setBackground(drawable);
            } else if (quality.equals("Better")) {
                //day_Quality_4.setBackgroundColor(Color.parseColor("#98F4BE"));
                Drawable drawable = getResources().getDrawable(R.drawable.rounded_corner_better);
                day_Quality_4.setBackground(drawable);
            } else if (quality.equals("Worst")) {
                //day_Quality_4.setBackgroundColor(Color.parseColor("#D50000"));
                Drawable drawable = getResources().getDrawable(R.drawable.rounded_corner_worst);
                day_Quality_4.setBackground(drawable);
            }
        }
        if (s.equals("Day 5")) {
            day_Quality_5.setText(quality);
            if (quality.equals("Best")) {
                //day_Quality_5.setBackgroundColor(Color.parseColor("#03913E"));
                Drawable drawable = getResources().getDrawable(R.drawable.rounded_corner_best);
                day_Quality_5.setBackground(drawable);
            } else if (quality.equals("Better")) {
                //day_Quality_5.setBackgroundColor(Color.parseColor("#98F4BE"));
                Drawable drawable = getResources().getDrawable(R.drawable.rounded_corner_better);
                day_Quality_5.setBackground(drawable);
            } else if (quality.equals("Worst")) {
                //day_Quality_5.setBackgroundColor(Color.parseColor("#D50000"));
                Drawable drawable = getResources().getDrawable(R.drawable.rounded_corner_worst);
                day_Quality_5.setBackground(drawable);
            }
        }
        if (s.equals("Day 6")) {
            day_Quality_6.setText(quality);
            if (quality.equals("Best")) {
                //day_Quality_6.setBackgroundColor(Color.parseColor("#03913E"));
                Drawable drawable = getResources().getDrawable(R.drawable.rounded_corner_best);
                day_Quality_6.setBackground(drawable);
            } else if (quality.equals("Better")) {
                //day_Quality_6.setBackgroundColor(Color.parseColor("#98F4BE"));
                Drawable drawable = getResources().getDrawable(R.drawable.rounded_corner_better);
                day_Quality_6.setBackground(drawable);
            } else if (quality.equals("Worst")) {
                //day_Quality_6.setBackgroundColor(Color.parseColor("#D50000"));
                Drawable drawable = getResources().getDrawable(R.drawable.rounded_corner_worst);
                day_Quality_6.setBackground(drawable);
            }
        }
        if (s.equals("Day 7")) {
            day_Quality_7.setText(quality);
            if (quality.equals("Best")) {
                //day_Quality_7.setBackgroundColor(Color.parseColor("#03913E"));
                Drawable drawable = getResources().getDrawable(R.drawable.rounded_corner_best);
                day_Quality_7.setBackground(drawable);
            } else if (quality.equals("Better")) {
                //day_Quality_7.setBackgroundColor(Color.parseColor("#98F4BE"));
                Drawable drawable = getResources().getDrawable(R.drawable.rounded_corner_better);
                day_Quality_7.setBackground(drawable);
            } else if (quality.equals("Worst")) {
                //day_Quality_7.setBackgroundColor(Color.parseColor("#D50000"));
                Drawable drawable = getResources().getDrawable(R.drawable.rounded_corner_worst);
                day_Quality_7.setBackground(drawable);
            }
        }


    }

}



