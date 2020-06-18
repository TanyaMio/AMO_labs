package com.example.myapplication.ui.Lab2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.FileHelper;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Lab2Fragment extends Fragment {

    private Lab2ViewModel lab2ViewModel;
    ArrayList<Double> array2 = new ArrayList<Double>(2);
    ArrayList<Double> array10 = new ArrayList<Double>(10);
    ArrayList<Double> array25 = new ArrayList<Double>(25);
    ArrayList<Double> array50 = new ArrayList<Double>(50);
    ArrayList<Double> array100 = new ArrayList<Double>(100);
    ArrayList<Double> array200 = new ArrayList<Double>(200);
    ArrayList<Double> array500 = new ArrayList<Double>(500);
    ArrayList<Double> array1000 = new ArrayList<Double>(1000);
    ArrayList<Double> array2000 = new ArrayList<Double>(2000);
    ArrayList<Double> array5000 = new ArrayList<Double>(5000);

    String arr2 = "";
    String arr10 = "";
    String arr25 = "";
    String arr50 = "";
    String arr100 = "";
    String arr200 = "";
    String arr500 = "";
    String arr1000 = "";
    String arr2000 = "";
    String arr5000 = "";

    String[] arr2_val;
    String[] arr10_val;
    String[] arr25_val;
    String[] arr50_val;
    String[] arr100_val;
    String[] arr200_val;
    String[] arr500_val;
    String[] arr1000_val;
    String[] arr2000_val;
    String[] arr5000_val;

    long time_start;
    long time_end;
    long time2;
    long time10;
    long time25;
    long time50;
    long time100;
    long time200;
    long time500;
    long time1000;
    long time2000;
    long time5000;

    String data_file2 = "lab2_data.txt";
    String file_content2 = "";
    String[] file_vars2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        lab2ViewModel =
                ViewModelProviders.of(this).get(Lab2ViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_lab2, container, false);
        final TextView textView = root.findViewById(R.id.arr2text);

        //--------------------------------------------------------------Arrays fields ----------------------------
        final EditText editArr2 = root.findViewById(R.id.edit_arr2);
        final EditText editArr10 = root.findViewById(R.id.edit_arr10);
        final EditText editArr25 = root.findViewById(R.id.edit_arr25);
        final EditText editArr50 = root.findViewById(R.id.edit_arr50);
        final EditText editArr100 = root.findViewById(R.id.edit_arr100);
        final EditText editArr200 = root.findViewById(R.id.edit_arr200);
        final EditText editArr500 = root.findViewById(R.id.edit_arr500);
        final EditText editArr1000 = root.findViewById(R.id.edit_arr1000);
        final EditText editArr2000 = root.findViewById(R.id.edit_arr2000);
        final EditText editArr5000 = root.findViewById(R.id.edit_arr5000);

        //--------------------------------------------------------------------Alert text box ------------------
        final TextView alert2Box = root.findViewById(R.id.lab2_alert);

        //---------------------------------------------------------------------Graph View --------------------
        final GraphView lab2_graph = root.findViewById(R.id.lab2_graph);
        final LineGraphSeries<DataPoint> theory_p = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0,0),
                new DataPoint(2, 2*Math.log(2)),
                new DataPoint(10, 10*Math.log(10)),
                new DataPoint(25, 25*Math.log(25)),
                new DataPoint(50, 50*Math.log(50)),
                new DataPoint(100, 100*Math.log(100)),
                new DataPoint(200, 200*Math.log(200)),
                new DataPoint(500, 500*Math.log(500)),
                new DataPoint(1000, 1000*Math.log(1000)),
                new DataPoint(2000, 2000*Math.log(2000)),
                new DataPoint(5000, 5000*Math.log(5000))
        });
        theory_p.setColor(Color.RED);
        theory_p.setTitle("Theory");

        for(int i = 0; i < 5000; i++) {
            if (i < 2) { array2.add(Math.random()*200 - 100); if(i != 1) { arr2 += String.format("%.2f, ", array2.get(i));} else {arr2 += String.format("%.2f", array2.get(i));} }
            if (i < 10) { array10.add(Math.random()*200 - 100); if(i != 9) { arr10 += String.format("%.2f, ", array10.get(i));} else {arr10 += String.format("%.2f", array10.get(i));} }
            if (i < 25) { array25.add(Math.random()*200 - 100); if(i != 24) { arr25 += String.format("%.2f, ", array25.get(i));} else {arr25 += String.format("%.2f", array25.get(i));} }
            if (i < 50) { array50.add(Math.random()*200 - 100); if(i != 49) { arr50 += String.format("%.2f, ", array50.get(i));} else {arr50 += String.format("%.2f", array50.get(i));} }
            if (i < 100) { array100.add(Math.random()*200 - 100); if(i != 99) { arr100 += String.format("%.2f, ", array100.get(i));} else {arr100 += String.format("%.2f", array100.get(i));} }
            if (i < 200) { array200.add(Math.random()*200 - 100); if(i != 199) {arr200 += String.format("%.2f, ", array200.get(i));} else {arr200 += String.format("%.2f", array200.get(i));} }
            if (i < 500) { array500.add(Math.random()*200 - 100); if(i != 499) { arr500 += String.format("%.2f, ", array500.get(i));} else {arr500 += String.format("%.2f", array500.get(i));} }
            if (i < 1000) { array1000.add(Math.random()*200 - 100); if(i != 999) { arr1000 += String.format("%.2f, ", array1000.get(i));} else {arr1000 += String.format("%.2f", array1000.get(i));} }
            if (i < 2000) { array2000.add(Math.random()*200 - 100); if(i != 1999) { arr2000 += String.format("%.2f, ", array2000.get(i));} else {arr2000 += String.format("%.2f", array2000.get(i));} }
            array5000.add(Math.random()*200 - 100); if(i != 4999) { arr5000 += String.format("%.2f, ", array5000.get(i));} else {arr5000 += String.format("%.2f", array5000.get(i));}
        }

        editArr2.setText(arr2);
        editArr10.setText(arr10);
        editArr25.setText(arr25);
        editArr50.setText(arr50);
        editArr100.setText(arr100);
        editArr200.setText(arr200);
        editArr500.setText(arr500);
        editArr1000.setText(arr1000);
        editArr2000.setText(arr2000);
        editArr5000.setText(arr5000);

        file_content2 = ("2-element array\n" + arr2 + "\n\n10-element array\n" + arr10 + "\n\n25-element array\n" + arr25 +
                "\n\n50-element array\n" + arr50 + "\n\n100-elementt array\n" + arr100 + "\n\n200-element array\n" + arr200 +
                "\n\n500-element array\n" + arr500 + "\n\n1000-element array\n" + arr1000 + "\n\n2000-element array\n" + arr2000 + "\n\n5000-element array\n" + arr5000);
        final FileHelper fh = new FileHelper();
        fh.saveToFile(file_content2, data_file2, alert2Box);

        //----------------------------------------------------------------Import from the file Button --------------------

        Button file2 = root.findViewById(R.id.lab2_file);
        View.OnClickListener file2_l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    file_content2 = fh.readFromFile(root.getContext(), data_file2, alert2Box);
                    file_vars2 = file_content2.split("\n\n");

                    arr2_val = file_vars2[0].split("\n")[1].split(", ");
                    arr10_val = file_vars2[1].split("\n")[1].split(", ");
                    arr25_val = file_vars2[2].split("\n")[1].split(", ");
                    arr50_val = file_vars2[3].split("\n")[1].split(", ");
                    arr100_val = file_vars2[4].split("\n")[1].split(", ");
                    arr200_val = file_vars2[5].split("\n")[1].split(", ");
                    arr500_val = file_vars2[6].split("\n")[1].split(", ");
                    arr1000_val = file_vars2[7].split("\n")[1].split(", ");
                    arr2000_val = file_vars2[8].split("\n")[1].split(", ");
                    arr5000_val = file_vars2[9].split("\n")[1].split(", ");

                    for(int i = 0; i<5000; i++) {
                        if (i < 2) { array2.set(i, Double.valueOf(arr2_val[i])); }
                        if (i < 10) { array10.set(i, Double.valueOf(arr10_val[i])); }
                        if (i < 25) { array25.set(i, Double.valueOf(arr25_val[i])); }
                        if (i < 50) { array50.set(i, Double.valueOf(arr50_val[i])); }
                        if (i < 100) { array100.set(i, Double.valueOf(arr100_val[i])); }
                        if (i < 200) { array200.set(i, Double.valueOf(arr200_val[i])); }
                        if (i < 500) { array500.set(i, Double.valueOf(arr500_val[i])); }
                        if (i < 1000) { array1000.set(i, Double.valueOf(arr1000_val[i])); }
                        if (i < 2000) { array2000.set(i, Double.valueOf(arr2000_val[i])); }
                        array5000.set(i, Double.valueOf(arr5000_val[i]));
                    }

                    arrsToStr();
                    editArr2.setText(arr2);
                    editArr10.setText(arr10);
                    editArr25.setText(arr25);
                    editArr50.setText(arr50);
                    editArr100.setText(arr100);
                    editArr200.setText(arr200);
                    editArr500.setText(arr500);
                    editArr1000.setText(arr1000);
                    editArr2000.setText(arr2000);
                    editArr5000.setText(arr5000);

                    alert2Box.setText("Values imported successfully. You can try to calculate again.");

                } catch (Exception e) {
                    alert2Box.setText("Unable to get values from file.\nMake sure you didn't change formatting.");
                }

            }
        };
        file2.setOnClickListener(file2_l);

        //----------------------------------------------------------------Apply Changes Button -----------------------------
        Button butt_appl = root.findViewById(R.id.lab2_apply);
        View.OnClickListener appl_l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    arr2_val = editArr2.getText().toString().split(", ");
                    arr10_val = editArr10.getText().toString().split(", ");
                    arr25_val = editArr25.getText().toString().split(", ");
                    arr50_val = editArr50.getText().toString().split(", ");
                    arr100_val = editArr100.getText().toString().split(", ");
                    arr200_val = editArr200.getText().toString().split(", ");
                    arr500_val = editArr500.getText().toString().split(", ");
                    arr1000_val = editArr1000.getText().toString().split(", ");
                    arr2000_val = editArr2000.getText().toString().split(", ");
                    arr5000_val = editArr5000.getText().toString().split(", ");

                    for(int i = 0; i<5000; i++) {
                        if (i < 2) { array2.set(i, Double.valueOf(arr2_val[i])); }
                        if (i < 10) { array10.set(i, Double.valueOf(arr10_val[i])); }
                        if (i < 25) { array25.set(i, Double.valueOf(arr25_val[i])); }
                        if (i < 50) { array50.set(i, Double.valueOf(arr50_val[i])); }
                        if (i < 100) { array100.set(i, Double.valueOf(arr100_val[i])); }
                        if (i < 200) { array200.set(i, Double.valueOf(arr200_val[i])); }
                        if (i < 500) { array500.set(i, Double.valueOf(arr500_val[i])); }
                        if (i < 1000) { array1000.set(i, Double.valueOf(arr1000_val[i])); }
                        if (i < 2000) { array2000.set(i, Double.valueOf(arr2000_val[i])); }
                        array5000.set(i, Double.valueOf(arr5000_val[i]));
                    }

                    alert2Box.setText("Values changed successfully. Press Sort and Plot button to generate new graphs.");

                } catch (Exception e) {
                    alert2Box.setText("Something went wrong.\nCheck whether all the values are decimal.\nCheck whether each line has correct number of values.\nThen try again.");
                }

            }
        };
        butt_appl.setOnClickListener(appl_l);

        //------------------------------------------------------------------Sort and Plot Button --------------------------------
        Button butt_sort = root.findViewById(R.id.lab2_sort);
        View.OnClickListener sort_l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    time_start = System.nanoTime();
                    array2 = new ArrayList<Double>(sort(array2));
                    time_end = System.nanoTime();
                    time2 = (time_end-time_start)/450;

                    time_start = System.nanoTime();
                    array10 = new ArrayList<Double>(sort(array10));
                    time_end = System.nanoTime();
                    time10 = (time_end-time_start)/450;

                    time_start = System.nanoTime();
                    array25 = new ArrayList<Double>(sort(array25));
                    time_end = System.nanoTime();
                    time25 = (time_end-time_start)/450;

                    time_start = System.nanoTime();
                    array50 = new ArrayList<Double>(sort(array50));
                    time_end = System.nanoTime();
                    time50 = (time_end-time_start)/450;

                    time_start = System.nanoTime();
                    array100 = new ArrayList<Double>(sort(array100));
                    time_end = System.nanoTime();
                    time100 = (time_end-time_start)/450;

                    time_start = System.nanoTime();
                    array200 = new ArrayList<Double>(sort(array200));
                    time_end = System.nanoTime();
                    time200 = (time_end-time_start)/450;

                    time_start = System.nanoTime();
                    array500 = new ArrayList<Double>(sort(array500));
                    time_end = System.nanoTime();
                    time500 = (time_end-time_start)/450;

                    time_start = System.nanoTime();
                    array1000 = new ArrayList<Double>(sort(array1000));
                    time_end = System.nanoTime();
                    time1000 = (time_end-time_start)/450;

                    time_start = System.nanoTime();
                    array2000 = new ArrayList<Double>(sort(array2000));
                    time_end = System.nanoTime();
                    time2000 = (time_end-time_start)/450;

                    time_start = System.nanoTime();
                    array5000 = new ArrayList<Double>(sort(array5000));
                    time_end = System.nanoTime();
                    time5000 = (time_end-time_start)/450;

                    arrsToStr();
                    editArr2.setText(arr2);
                    editArr10.setText(arr10);
                    editArr25.setText(arr25);
                    editArr50.setText(arr50);
                    editArr100.setText(arr100);
                    editArr200.setText(arr200);
                    editArr500.setText(arr500);
                    editArr1000.setText(arr1000);
                    editArr2000.setText(arr2000);
                    editArr5000.setText(arr5000);

                    LineGraphSeries<DataPoint> practice_p = new LineGraphSeries<DataPoint>(new DataPoint[] {
                            new DataPoint(0, 0),
                            new DataPoint(2, time2),
                            new DataPoint(10, time10),
                            new DataPoint(25, time25),
                            new DataPoint(50, time50),
                            new DataPoint(100, time100),
                            new DataPoint(200, time200),
                            new DataPoint(500, time500),
                            new DataPoint(1000, time1000),
                            new DataPoint(2000, time2000),
                            new DataPoint(5000, time5000)
                    });
                    practice_p.setColor(Color.BLUE);
                    practice_p.setTitle("Experiment");

                    lab2_graph.addSeries(theory_p);
                    lab2_graph.addSeries(practice_p);
                    lab2_graph.getGridLabelRenderer().setHorizontalAxisTitle("n");
                    lab2_graph.getViewport().setXAxisBoundsManual(true);
                    lab2_graph.getViewport().setMinX(0);
                    lab2_graph.getViewport().setMaxX(6000);
                    lab2_graph.getViewport().setYAxisBoundsManual(true);
                    lab2_graph.getViewport().setMinY(0);
                    lab2_graph.getViewport().setMaxY(52000);
                    lab2_graph.getGridLabelRenderer().setVerticalAxisTitle("O(n)");
                    lab2_graph.getLegendRenderer().setVisible(true);
                    lab2_graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

                    alert2Box.setText("Plotting successful.");

                } catch (Exception e) {
                    alert2Box.setText("Something went wrong.Try again.");
                }

            }
        };
        butt_sort.setOnClickListener(sort_l);

        return root;
    }

    //------------------------------------------------------------------Merge sort algorithm methods-------------------------
    public List<Double> sort (List<Double> arr){
        if (arr.size() < 2) { return arr; }
        else {
            List<Double> left = arr.subList(0, (int)(arr.size()/2));
            List<Double> right = arr.subList((int)(arr.size()/2), arr.size());
            return merge(sort(left), sort(right));
        }
    }

    public List<Double> merge (List<Double> arr_l, List<Double> arr_r) {
        ArrayList<Double> res = new ArrayList<Double>(arr_l.size()+arr_r.size());
        int il = 0; int ir = 0;
        for (int i = 0; i < arr_l.size()+arr_r.size(); i++) {
            if (il == arr_l.size()) { res.add(arr_r.get(ir++)); }
            else if (ir == arr_r.size()) { res.add(arr_l.get(il++)); }
            else {
                if (arr_l.get(il) < arr_r.get(ir)) { res.add(arr_l.get(il++)); }
                else { res.add(arr_r.get(ir++)); }
            }
        }
        return res;
    }

    public void arrsToStr() {
        arr2 = "";
        arr10 = "";
        arr25 = "";
        arr50 = "";
        arr100 = "";
        arr200 = "";
        arr500 = "";
        arr1000 = "";
        arr2000 = "";
        arr5000 = "";
        for(int i = 0; i < 5000; i++) {
            if (i < 2) { if(i != 1) { arr2 += String.format("%.2f, ", array2.get(i));} else {arr2 += String.format("%.2f", array2.get(i));} }
            if (i < 10) { if(i != 9) { arr10 += String.format("%.2f, ", array10.get(i));} else {arr10 += String.format("%.2f", array10.get(i));} }
            if (i < 25) { if(i != 24) { arr25 += String.format("%.2f, ", array25.get(i));} else {arr25 += String.format("%.2f", array25.get(i));} }
            if (i < 50) { if(i != 49) { arr50 += String.format("%.2f, ", array50.get(i));} else {arr50 += String.format("%.2f", array50.get(i));} }
            if (i < 100) { if(i != 99) { arr100 += String.format("%.2f, ", array100.get(i));} else {arr100 += String.format("%.2f", array100.get(i));} }
            if (i < 200) { if(i != 199) {arr200 += String.format("%.2f, ", array200.get(i));} else {arr200 += String.format("%.2f", array200.get(i));} }
            if (i < 500) { if(i != 499) { arr500 += String.format("%.2f, ", array500.get(i));} else {arr500 += String.format("%.2f", array500.get(i));} }
            if (i < 1000) { if(i != 999) { arr1000 += String.format("%.2f, ", array1000.get(i));} else {arr1000 += String.format("%.2f", array1000.get(i));} }
            if (i < 2000) { if(i != 1999) { arr2000 += String.format("%.2f, ", array2000.get(i));} else {arr2000 += String.format("%.2f", array2000.get(i));} }
            if(i != 4999) { arr5000 += String.format("%.2f, ", array5000.get(i));} else {arr5000 += String.format("%.2f", array5000.get(i));}
        }

    }
}