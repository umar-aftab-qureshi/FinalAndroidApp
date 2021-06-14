package com.example.airqualitypredication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GraphViewActivity extends AppCompatActivity {
    ProgressDialog pDialog;
    BarChart barChart;
    TextView day;
    String dayNumber;
    ArrayList<Double> day1_quality, day2_quality, day3_quality, day4_quality, day5_quality, day6_quality, day7_quality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);
        day = findViewById(R.id.day);
        Bundle bundle = getIntent().getExtras();
        String modelNumber = bundle.getString("Model Number");
         dayNumber = bundle.getString("Day");
        pDialog = new ProgressDialog(GraphViewActivity.this);
        pDialog.setMessage("Loading...");
        initizingAlltheViews();
        day.setText("Day -: " + dayNumber);
        //Initlizing BarChart
        barChart = (BarChart) findViewById(R.id.barChart);
        pDialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Models");

        if (modelNumber.equals("0")) {
            DatabaseReference pred = myRef.child("ARIMA");
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
                    pDialog.hide();
                    //Toast.makeText(getApplicationContext(),"Arima",Toast.LENGTH_LONG).show();
                    if (dayNumber.equals("1")) {
                        displayingGraph(day1_quality);
                    } else if (dayNumber.equals("2")) {
                        displayingGraph(day2_quality);
                    } else if (dayNumber.equals("3")) {
                        displayingGraph(day3_quality);
                    } else if (dayNumber.equals("4")) {
                        displayingGraph(day4_quality);
                    } else if (dayNumber.equals("5")) {
                        displayingGraph(day5_quality);
                    } else if (dayNumber.equals("6")) {
                        displayingGraph(day6_quality);
                    } else if (dayNumber.equals("7")) {
                        displayingGraph(day7_quality);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        } else if (modelNumber.equals("1")) {
            DatabaseReference pred = myRef.child("LSTM");
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
                    pDialog.hide();

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
                    pDialog.hide();
                    //Toast.makeText(getApplicationContext(),"LSTM",Toast.LENGTH_LONG).show();
                    if (dayNumber.equals("1")) {
                        displayingGraph(day1_quality);
                    } else if (dayNumber.equals("2")) {
                        displayingGraph(day2_quality);
                    } else if (dayNumber.equals("3")) {
                        displayingGraph(day3_quality);
                    } else if (dayNumber.equals("4")) {
                        displayingGraph(day4_quality);
                    } else if (dayNumber.equals("5")) {
                        displayingGraph(day5_quality);
                    } else if (dayNumber.equals("6")) {
                        displayingGraph(day6_quality);
                    } else if (dayNumber.equals("7")) {
                        displayingGraph(day7_quality);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else if (modelNumber.equals("2")) {
            DatabaseReference pred = myRef.child("fbProphet");
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


                    pDialog.hide();
                    if (dayNumber.equals("1")) {
                        displayingGraph(day1_quality);
                    } else if (dayNumber.equals("2")) {
                        displayingGraph(day2_quality);
                    } else if (dayNumber.equals("3")) {
                        displayingGraph(day3_quality);
                    } else if (dayNumber.equals("4")) {
                        displayingGraph(day4_quality);
                    } else if (dayNumber.equals("5")) {
                        displayingGraph(day5_quality);
                    } else if (dayNumber.equals("6")) {
                        displayingGraph(day6_quality);
                    } else if (dayNumber.equals("7")) {
                        displayingGraph(day7_quality);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
            //displayingGraph(day1_quality);


        }
        //Toast.makeText(getApplicationContext(),Integer.toString(day1_quality.size()),Toast.LENGTH_LONG).show();


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


    }

    private void displayingGraph(ArrayList<Double> array) {
        final ArrayList<String> xAxisLabel = new ArrayList<>();
        xAxisLabel.add("CO");
        xAxisLabel.add("Dust");
        xAxisLabel.add("Smoke");
        xAxisLabel.add("Sui_Gas");
        xAxisLabel.add("Temperature");
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));

        ArrayList<BarEntry> entries = new ArrayList<>();

        entries.add(new BarEntry(0, array.get(0).floatValue()));
        entries.add(new BarEntry(1, array.get(1).floatValue()));

        entries.add(new BarEntry(2, array.get(2).floatValue()));
        entries.add(new BarEntry(3, array.get(3).floatValue()));
        entries.add(new BarEntry(4, array.get(4).floatValue()));
//        ArrayList<Integer> list_colors = new ArrayList<>();
//        list_colors.add(Color.parseColor("#D0F172"));
//        list_colors.add(Color.parseColor("#E9CE6f"));
//        list_colors.add(Color.parseColor("#B56C36"));
//        list_colors.add(Color.parseColor("#DF3E3E"));
//        list_colors.add(Color.parseColor("#0BEFD0"));

        BarDataSet barDataSet = new BarDataSet(entries, "Air Quality Prediction");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.setScaleEnabled(false);

        barDataSet.setValueTextSize(20f);


        barChart.setDrawBarShadow(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);

        barChart.setDrawGridBackground(false);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
     barData.setValueFormatter(new ValueFormatter() {
         @Override
         public String getFormattedValue(float value) {
             DecimalFormat decimalFormat = new DecimalFormat("##.###");
             return decimalFormat.format(value);
         }
     });
        barChart.setData(barData);
        //barChart.animateY(2000);
        barChart.animateXY(1000, 3000);
        YAxis rightYAxis = barChart.getAxisRight();
        rightYAxis.setEnabled(false);
        barChart.getDescription().setText("Bar Graph of Day "+dayNumber);
    }

}

