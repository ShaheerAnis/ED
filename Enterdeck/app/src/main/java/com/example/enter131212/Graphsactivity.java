package com.example.enter131212;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

    /*public class Graphsactivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    BarChart chart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphsactivity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        chart = findViewById(R.id.chart);

// Disable unnecessary chart features
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);
        chart.getLegend().setEnabled(false);

// Query the Firestore data for the top 5 projects with the highest trending values
                db.collection("Projects")
                .orderBy("Trending", Query.Direction.DESCENDING).whereEqualTo("Inverstor_id", "Null")
                .limit(5)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // Create a list to store the bar chart entries
                        ArrayList<BarEntry> entries = new ArrayList<>();

                        // Create a list to store the project names
                        ArrayList<String> names = new ArrayList<>();
                        List<Integer> colors = new ArrayList<>();
                        Random random = new Random();

                        // Loop through the Firestore data and add entries to the lists
                        int i = 0;
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            // Get the Trending and Title fields from the document
                            long trending = document.getLong("Trending");
                            String title = document.getString("Title");

                            // Add a new BarEntry to the entries list with the trending value and the index
                            entries.add(new BarEntry(i, trending));
                            colors.add(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));

                            // Add the project name to the names list
                            names.add(title);

                            // Increment the index
                            i++;
                        }

                        // Create a BarDataSet with the entries
                        BarDataSet dataSet = new BarDataSet(entries, "Top 5 Projects");

                        // Set BarDataSet properties
                        dataSet.setColors(colors);
                        dataSet.setValueTextColor(Color.BLACK);

                        // Create a BarData object with the BarDataSet
                        BarData barData = new BarData(dataSet);

                        // Set the BarData to the chart view
                        chart.setData(barData);

                        // Customize the X-axis labels
                        XAxis xAxis = chart.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(names));
                        xAxis.setDrawGridLines(false);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setGranularity(1f);
                        xAxis.setLabelRotationAngle(0f);
                        xAxis.setLabelCount(5);
                        xAxis.setTextColor(Color.BLACK);

                        // Refresh the chart
                        chart.invalidate();
                    }
                });


    }



}*/


public class Graphsactivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphsactivity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        LineChart chart = findViewById(R.id.chart);

// Disable unnecessary chart features
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);
        chart.getLegend().setEnabled(false);

// Query the Firestore data for the top 5 projects with the highest trending values
        db.collection("Projects")
                .orderBy("Trending", Query.Direction.DESCENDING)
                .whereEqualTo("Inverstor_id", "Null")
                .limit(5)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // Create a list to store the line chart entries
                        ArrayList<Entry> entries = new ArrayList<>();

                        // Create a list to store the project names
                        ArrayList<String> names = new ArrayList<>();
                        List<Integer> colors = new ArrayList<>();
                        Random random = new Random();

                        // Loop through the Firestore data and add entries to the lists
                        int i = 0;
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            // Get the Trending and Title fields from the document
                            long trending = document.getLong("Trending");
                            String title = document.getString("Title");

                            // Add a new Entry to the entries list with the trending value and the index
                            entries.add(new Entry(i, trending));
                            colors.add(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));

                            // Add the project name to the names list
                            names.add(title);

                            // Increment the index
                            i++;
                        }

                        // Create a LineDataSet with the entries
                        LineDataSet dataSet = new LineDataSet(entries, "Top 5 Projects");

                        // Set LineDataSet properties
                        dataSet.setColors(colors);
                        dataSet.setValueTextColor(Color.BLACK);
                        dataSet.setDrawFilled(true);

                        // Create a LineData object with the LineDataSet
                        LineData lineData = new LineData(dataSet);

                        // Set the LineData to the chart view
                        chart.setData(lineData);

                        // Customize the X-axis labels
                        XAxis xAxis = chart.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(names));
                        xAxis.setDrawGridLines(false);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setGranularity(1f);
                        xAxis.setLabelRotationAngle(0f);
                        xAxis.setLabelCount(5);
                        xAxis.setTextColor(Color.BLACK);

                        // Refresh the chart
                        chart.invalidate();
                    }
                });












       /* chart = findViewById(R.id.chart);


        // Disable unnecessary chart features
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);
        chart.getLegend().setEnabled(false);

        // Query the Firestore data for the top 5 projects with the highest trending values
        db.collection("Projects")
                .orderBy("Trending", Query.Direction.DESCENDING)
                .whereEqualTo("Inverstor_id", "Null")
                .limit(5)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // Create a list to store the bar chart entries
                        ArrayList<BarEntry> entries = new ArrayList<>();

                        // Create a list to store the project names
                        ArrayList<String> names = new ArrayList<>();
                        List<Integer> colors = new ArrayList<>();
                        Random random = new Random();

                        // Loop through the Firestore data and add entries to the lists
                        int i = 0;
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            // Get the Trending and Title fields from the document
                            long trending = document.getLong("Trending");
                            String title = document.getString("Title");

                            // Add a new BarEntry to the entries list with the trending value and the index
                            entries.add(new BarEntry(i, trending));
                            colors.add(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));

                            // Add the project name to the names list
                            names.add(title);

                            // Increment the index
                            i++;
                        }

                        // Create a BarDataSet with the entries
                        BarDataSet dataSet = new BarDataSet(entries, "Top 5 Projects");

                        // Set BarDataSet properties
                        dataSet.setColors(colors);
                        dataSet.setValueTextColor(Color.BLACK);

                        // Create a BarData object with the BarDataSet
                        BarData barData = new BarData(dataSet);

                        // Set the BarData to the chart view
                        chart.setData(barData);

                        // Customize the X-axis labels
                        XAxis xAxis = chart.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(names));
                        xAxis.setDrawGridLines(false);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setGranularity(1f);
                        xAxis.setLabelRotationAngle(0f);
                        xAxis.setLabelCount(5);
                        xAxis.setTextColor(Color.BLACK);

                        // Refresh the chart
                        chart.invalidate();
                    }
                });*/
    }
}
