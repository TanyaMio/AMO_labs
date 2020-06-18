package com.example.myapplication.ui.Lab3;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class Lab3Fragment extends Fragment {

    private Lab3ViewModel lab3ViewModel;
    private ArrayList<Double> Xi_val = new ArrayList<>(11);
    private ArrayList<Double> sinXi_val = new ArrayList<>(11);
    private ArrayList<Double> funcXi_val = new ArrayList<>(11);
    private ArrayList<ArrayList<Double>> matrix_A;          // for interpolation method
    private ArrayList<Double> Yi_val;
    private ArrayList<ArrayList<Double>> interpolXi_val;    //each row - values of interpolation polynome of power j in x[i]
    private ArrayList<ArrayList<Double>> deltaXi_val = new ArrayList<ArrayList<Double>>();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        lab3ViewModel =
                ViewModelProviders.of(this).get(Lab3ViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_lab3, container, false);
        final TextView textView = root.findViewById(R.id.choice_l3);

        //--------------------------------------------------------------------Alert text box ------------------
        final TextView alert3Box = root.findViewById(R.id.lab3_alert);

        //-------------------------------------------------------------------Graphs-----------------------
        final GraphView lab3_graph = root.findViewById(R.id.lab3_graph);
        final GraphView err3_graph = root.findViewById(R.id.lab3_err_graph);

        //--------------------------------------------------------------------Calculating Initial Values --------------
        for(int i =0; i < 11; i ++){
            Xi_val.add(0.1*i);
            sinXi_val.add(Math.sin(Xi_val.get(i)));
            funcXi_val.add(Math.cos(2*Xi_val.get(i)+Math.pow(Xi_val.get(i), 2)));

        }

        //---------------------------------------------------------------------Graph View --------------------

        final LineGraphSeries<DataPoint> theory_sin = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(Xi_val.get(0),sinXi_val.get(0)),
                new DataPoint(Xi_val.get(1),sinXi_val.get(1)),
                new DataPoint(Xi_val.get(2),sinXi_val.get(2)),
                new DataPoint(Xi_val.get(3),sinXi_val.get(3)),
                new DataPoint(Xi_val.get(4),sinXi_val.get(4)),
                new DataPoint(Xi_val.get(5),sinXi_val.get(5)),
                new DataPoint(Xi_val.get(6),sinXi_val.get(6)),
                new DataPoint(Xi_val.get(7),sinXi_val.get(7)),
                new DataPoint(Xi_val.get(8),sinXi_val.get(8)),
                new DataPoint(Xi_val.get(9),sinXi_val.get(9)),
                new DataPoint(Xi_val.get(10),sinXi_val.get(10))
        });
        theory_sin.setColor(Color.BLACK);
        theory_sin.setThickness(15);
        theory_sin.setTitle("sin(x)");

        final LineGraphSeries<DataPoint> theory_func = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(Xi_val.get(0),funcXi_val.get(0)),
                new DataPoint(Xi_val.get(1),funcXi_val.get(1)),
                new DataPoint(Xi_val.get(2),funcXi_val.get(2)),
                new DataPoint(Xi_val.get(3),funcXi_val.get(3)),
                new DataPoint(Xi_val.get(4),funcXi_val.get(4)),
                new DataPoint(Xi_val.get(5),funcXi_val.get(5)),
                new DataPoint(Xi_val.get(6),funcXi_val.get(6)),
                new DataPoint(Xi_val.get(7),funcXi_val.get(7)),
                new DataPoint(Xi_val.get(8),funcXi_val.get(8)),
                new DataPoint(Xi_val.get(9),funcXi_val.get(9)),
                new DataPoint(Xi_val.get(10),funcXi_val.get(10))
        });
        theory_func.setColor(Color.BLACK);
        theory_func.setThickness(15);
        theory_func.setTitle("cos(2x+x^2)");

        //------------------------------------------------------------------Interpolate and Plot Button --------------------------------
        Button butt_interpol = root.findViewById(R.id.lab3_interpol);
        View.OnClickListener sort_l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    lab3_graph.removeAllSeries();
                    err3_graph.removeAllSeries();
                    RadioButton sin_lab3 = root.findViewById(R.id.sin_l3);
                    if(sin_lab3.isChecked()){
                        Yi_val = sinXi_val;
                        lab3_graph.addSeries(theory_sin);
                    } else {
                        Yi_val = funcXi_val;
                        lab3_graph.addSeries(theory_func);
                    }
                    interpolXi_val = new ArrayList<>(11);
                    deltaXi_val = new ArrayList<ArrayList<Double>>();
                    for(int i = 0; i < 11; i++){
                        interpolXi_val.add(new ArrayList<Double>(11));
                        deltaXi_val.add(new ArrayList<Double>());
                        for(int n = 1; n < 11; n++){
                            matrix_A = new ArrayList<>(11);
                            for(int j = 0; j < 10; j++){
                                matrix_A.add(new ArrayList<Double>(11));
                            }
                            interpolXi_val.get(i).add(interpolate(n, Xi_val.get((i))));
                            if(n > 1) {
                                deltaXi_val.get(i).add(interpolXi_val.get(i).get(n-2)- interpolXi_val.get(i).get(n-1));
                            }
                        }
                    }
                    final LineGraphSeries<DataPoint> interpol = new LineGraphSeries<DataPoint>(new DataPoint[] {
                            new DataPoint(Xi_val.get(0),interpolXi_val.get(0).get(8)),
                            new DataPoint(Xi_val.get(1),interpolXi_val.get(1).get(8)),
                            new DataPoint(Xi_val.get(2),interpolXi_val.get(2).get(8)),
                            new DataPoint(Xi_val.get(3),interpolXi_val.get(3).get(8)),
                            new DataPoint(Xi_val.get(4),interpolXi_val.get(4).get(8)),
                            new DataPoint(Xi_val.get(5),interpolXi_val.get(5).get(8)),
                            new DataPoint(Xi_val.get(6),interpolXi_val.get(6).get(8)),
                            new DataPoint(Xi_val.get(7),interpolXi_val.get(7).get(8)),
                            new DataPoint(Xi_val.get(8),interpolXi_val.get(8).get(8)),
                            new DataPoint(Xi_val.get(9),interpolXi_val.get(9).get(8)),
                            new DataPoint(Xi_val.get(10),interpolXi_val.get(10).get(8))
                    });
                    interpol.setColor(Color.RED);
                    interpol.setThickness(4);
                    interpol.setTitle("Interpolation");

                    lab3_graph.addSeries(interpol);
                    lab3_graph.getGridLabelRenderer().setHorizontalAxisTitle("x");
                    lab3_graph.getViewport().setXAxisBoundsManual(true);
                    lab3_graph.getViewport().setMinX(0);
                    lab3_graph.getViewport().setMaxX(1.1);
                    lab3_graph.getViewport().setYAxisBoundsManual(true);
                    lab3_graph.getViewport().setMinY(-1.5);
                    lab3_graph.getViewport().setMaxY(1.5);
                    lab3_graph.getGridLabelRenderer().setVerticalAxisTitle("y");
                    lab3_graph.setTitle("Interpolation results");
                    lab3_graph.getLegendRenderer().setVisible(true);
                    lab3_graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

                    final LineGraphSeries<DataPoint> err1 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                            new DataPoint(Xi_val.get(0), deltaXi_val.get(0).get(0)),
                            new DataPoint(Xi_val.get(1),deltaXi_val.get(1).get(0)),
                            new DataPoint(Xi_val.get(2),deltaXi_val.get(2).get(0)),
                            new DataPoint(Xi_val.get(3),deltaXi_val.get(3).get(0)),
                            new DataPoint(Xi_val.get(4),deltaXi_val.get(4).get(0)),
                            new DataPoint(Xi_val.get(5),deltaXi_val.get(5).get(0)),
                            new DataPoint(Xi_val.get(6),deltaXi_val.get(6).get(0)),
                            new DataPoint(Xi_val.get(7),deltaXi_val.get(7).get(0)),
                            new DataPoint(Xi_val.get(8),deltaXi_val.get(8).get(0)),
                            new DataPoint(Xi_val.get(9),deltaXi_val.get(9).get(0)),
                            new DataPoint(Xi_val.get(10),deltaXi_val.get(10).get(0))
                    });
                    err1.setTitle("n=1");
                    err1.setColor(Color.BLACK);

                    final LineGraphSeries<DataPoint> err2 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                            new DataPoint(Xi_val.get(0), deltaXi_val.get(0).get(1)),
                            new DataPoint(Xi_val.get(1),deltaXi_val.get(1).get(1)),
                            new DataPoint(Xi_val.get(2),deltaXi_val.get(2).get(1)),
                            new DataPoint(Xi_val.get(3),deltaXi_val.get(3).get(1)),
                            new DataPoint(Xi_val.get(4),deltaXi_val.get(4).get(1)),
                            new DataPoint(Xi_val.get(5),deltaXi_val.get(5).get(1)),
                            new DataPoint(Xi_val.get(6),deltaXi_val.get(6).get(1)),
                            new DataPoint(Xi_val.get(7),deltaXi_val.get(7).get(1)),
                            new DataPoint(Xi_val.get(8),deltaXi_val.get(8).get(1)),
                            new DataPoint(Xi_val.get(9),deltaXi_val.get(9).get(1)),
                            new DataPoint(Xi_val.get(10),deltaXi_val.get(10).get(1)) });
                    err2.setTitle("n=2");
                    err2.setColor(Color.GRAY);

                    final LineGraphSeries<DataPoint> err3 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                            new DataPoint(Xi_val.get(0), deltaXi_val.get(0).get(2)),
                            new DataPoint(Xi_val.get(1),deltaXi_val.get(1).get(2)),
                            new DataPoint(Xi_val.get(2),deltaXi_val.get(2).get(2)),
                            new DataPoint(Xi_val.get(3),deltaXi_val.get(3).get(2)),
                            new DataPoint(Xi_val.get(4),deltaXi_val.get(4).get(2)),
                            new DataPoint(Xi_val.get(5),deltaXi_val.get(5).get(2)),
                            new DataPoint(Xi_val.get(6),deltaXi_val.get(6).get(2)),
                            new DataPoint(Xi_val.get(7),deltaXi_val.get(7).get(2)),
                            new DataPoint(Xi_val.get(8),deltaXi_val.get(8).get(2)),
                            new DataPoint(Xi_val.get(9),deltaXi_val.get(9).get(2)),
                            new DataPoint(Xi_val.get(10),deltaXi_val.get(10).get(2))
                    });
                    err3.setTitle("n=3");
                    err3.setColor(Color.LTGRAY);

                    final LineGraphSeries<DataPoint> err4 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                            new DataPoint(Xi_val.get(0), deltaXi_val.get(0).get(3)),
                            new DataPoint(Xi_val.get(1),deltaXi_val.get(1).get(3)),
                            new DataPoint(Xi_val.get(2),deltaXi_val.get(2).get(3)),
                            new DataPoint(Xi_val.get(3),deltaXi_val.get(3).get(3)),
                            new DataPoint(Xi_val.get(4),deltaXi_val.get(4).get(3)),
                            new DataPoint(Xi_val.get(5),deltaXi_val.get(5).get(3)),
                            new DataPoint(Xi_val.get(6),deltaXi_val.get(6).get(3)),
                            new DataPoint(Xi_val.get(7),deltaXi_val.get(7).get(3)),
                            new DataPoint(Xi_val.get(8),deltaXi_val.get(8).get(3)),
                            new DataPoint(Xi_val.get(9),deltaXi_val.get(9).get(3)),
                            new DataPoint(Xi_val.get(10),deltaXi_val.get(10).get(3))
                    });
                    err4.setTitle("n=4");
                    err4.setColor(Color.BLUE);

                    final LineGraphSeries<DataPoint> err5 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                            new DataPoint(Xi_val.get(0), deltaXi_val.get(0).get(4)),
                            new DataPoint(Xi_val.get(1),deltaXi_val.get(1).get(4)),
                            new DataPoint(Xi_val.get(2),deltaXi_val.get(2).get(4)),
                            new DataPoint(Xi_val.get(3),deltaXi_val.get(3).get(4)),
                            new DataPoint(Xi_val.get(4),deltaXi_val.get(4).get(4)),
                            new DataPoint(Xi_val.get(5),deltaXi_val.get(5).get(4)),
                            new DataPoint(Xi_val.get(6),deltaXi_val.get(6).get(4)),
                            new DataPoint(Xi_val.get(7),deltaXi_val.get(7).get(4)),
                            new DataPoint(Xi_val.get(8),deltaXi_val.get(8).get(4)),
                            new DataPoint(Xi_val.get(9),deltaXi_val.get(9).get(4)),
                            new DataPoint(Xi_val.get(10),deltaXi_val.get(10).get(4))
                    });
                    err5.setTitle("n=5");
                    err5.setColor(Color.GREEN);

                    final LineGraphSeries<DataPoint> err6 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                            new DataPoint(Xi_val.get(0), deltaXi_val.get(0).get(5)),
                            new DataPoint(Xi_val.get(1),deltaXi_val.get(1).get(5)),
                            new DataPoint(Xi_val.get(2),deltaXi_val.get(2).get(5)),
                            new DataPoint(Xi_val.get(3),deltaXi_val.get(3).get(5)),
                            new DataPoint(Xi_val.get(4),deltaXi_val.get(4).get(5)),
                            new DataPoint(Xi_val.get(5),deltaXi_val.get(5).get(5)),
                            new DataPoint(Xi_val.get(6),deltaXi_val.get(6).get(5)),
                            new DataPoint(Xi_val.get(7),deltaXi_val.get(7).get(5)),
                            new DataPoint(Xi_val.get(8),deltaXi_val.get(8).get(5)),
                            new DataPoint(Xi_val.get(9),deltaXi_val.get(9).get(5)),
                            new DataPoint(Xi_val.get(10),deltaXi_val.get(10).get(5))
                    });
                    err6.setTitle("n=6");
                    err6.setColor(Color.YELLOW);

                    final LineGraphSeries<DataPoint> err7 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                            new DataPoint(Xi_val.get(0), deltaXi_val.get(0).get(6)),
                            new DataPoint(Xi_val.get(1),deltaXi_val.get(1).get(6)),
                            new DataPoint(Xi_val.get(2),deltaXi_val.get(2).get(6)),
                            new DataPoint(Xi_val.get(3),deltaXi_val.get(3).get(6)),
                            new DataPoint(Xi_val.get(4),deltaXi_val.get(4).get(6)),
                            new DataPoint(Xi_val.get(5),deltaXi_val.get(5).get(6)),
                            new DataPoint(Xi_val.get(6),deltaXi_val.get(6).get(6)),
                            new DataPoint(Xi_val.get(7),deltaXi_val.get(7).get(6)),
                            new DataPoint(Xi_val.get(8),deltaXi_val.get(8).get(6)),
                            new DataPoint(Xi_val.get(9),deltaXi_val.get(9).get(6)),
                            new DataPoint(Xi_val.get(10),deltaXi_val.get(10).get(6))
                    });
                    err7.setTitle("n=7");
                    err7.setColor(Color.RED);

                    final LineGraphSeries<DataPoint> err8 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                            new DataPoint(Xi_val.get(0), deltaXi_val.get(0).get(7)),
                            new DataPoint(Xi_val.get(1),deltaXi_val.get(1).get(7)),
                            new DataPoint(Xi_val.get(2),deltaXi_val.get(2).get(7)),
                            new DataPoint(Xi_val.get(3),deltaXi_val.get(3).get(7)),
                            new DataPoint(Xi_val.get(4),deltaXi_val.get(4).get(7)),
                            new DataPoint(Xi_val.get(5),deltaXi_val.get(5).get(7)),
                            new DataPoint(Xi_val.get(6),deltaXi_val.get(6).get(7)),
                            new DataPoint(Xi_val.get(7),deltaXi_val.get(7).get(7)),
                            new DataPoint(Xi_val.get(8),deltaXi_val.get(8).get(7)),
                            new DataPoint(Xi_val.get(9),deltaXi_val.get(9).get(7)),
                            new DataPoint(Xi_val.get(10),deltaXi_val.get(10).get(7))
                    });
                    err8.setTitle("n=8");
                    err8.setColor(Color.MAGENTA);

                    final LineGraphSeries<DataPoint> err9 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                            new DataPoint(Xi_val.get(0), deltaXi_val.get(0).get(8)),
                            new DataPoint(Xi_val.get(1),deltaXi_val.get(1).get(8)),
                            new DataPoint(Xi_val.get(2),deltaXi_val.get(2).get(8)),
                            new DataPoint(Xi_val.get(3),deltaXi_val.get(3).get(8)),
                            new DataPoint(Xi_val.get(4),deltaXi_val.get(4).get(8)),
                            new DataPoint(Xi_val.get(5),deltaXi_val.get(5).get(8)),
                            new DataPoint(Xi_val.get(6),deltaXi_val.get(6).get(8)),
                            new DataPoint(Xi_val.get(7),deltaXi_val.get(7).get(8)),
                            new DataPoint(Xi_val.get(8),deltaXi_val.get(8).get(8)),
                            new DataPoint(Xi_val.get(9),deltaXi_val.get(9).get(8)),
                            new DataPoint(Xi_val.get(10),deltaXi_val.get(10).get(8))
                    });
                    err9.setTitle("n=9");
                    err9.setColor(Color.CYAN);

                    err3_graph.addSeries(err1);
                    err3_graph.addSeries(err2);
                    err3_graph.addSeries(err3);
                    err3_graph.addSeries(err4);
                    err3_graph.addSeries(err5);
                    err3_graph.addSeries(err6);
                    err3_graph.addSeries(err7);
                    err3_graph.addSeries(err8);
                    err3_graph.addSeries(err9);
                    err3_graph.getGridLabelRenderer().setHorizontalAxisTitle("x");
                    err3_graph.getViewport().setXAxisBoundsManual(true);
                    err3_graph.getViewport().setMinX(0);
                    err3_graph.getViewport().setMaxX(1.1);
                    err3_graph.getViewport().setYAxisBoundsManual(true);
                    err3_graph.getViewport().setMinY(-Math.pow(10, -15));
                    err3_graph.getViewport().setMaxY(Math.pow(10, -15));
                    err3_graph.getGridLabelRenderer().setVerticalAxisTitle("-10^(-15)               delta n               10^(-15)");
                    err3_graph.setTitle("Error graph");
                    err3_graph.getLegendRenderer().setVisible(true);
                    err3_graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

                    TextView blurring = root.findViewById(R.id.lab3_blur);
                    double blur = 0.0;
                    double maxBlur = 0.0;
                    for(int i = 0; i < 11; i++) {
                        for(int j = 1; j < 9; j++){
                            if(deltaXi_val.get(i).get(j-1)==0) {
                                continue;
                            } else {
                                blur = Math.abs(deltaXi_val.get(i).get(j)/deltaXi_val.get(i).get(j-1));
                                if (blur > maxBlur){
                                    maxBlur = blur;
                                }
                            }
                        }
                    }
                    blurring.setText(String.format("Blurring of error is not greater than %.2f", maxBlur));
                    alert3Box.setText("Plotting successful.");

                    ArrayList<TextView> tableErr = new ArrayList<>();
                    TextView tableErr_1 = root.findViewById(R.id.lab3_Err1);
                    tableErr.add(tableErr_1);
                    TextView tableErr_2 = root.findViewById(R.id.lab3_Err2);
                    tableErr.add(tableErr_2);
                    TextView tableErr_3 = root.findViewById(R.id.lab3_Err3);
                    tableErr.add(tableErr_3);
                    TextView tableErr_4 = root.findViewById(R.id.lab3_Err4);
                    tableErr.add(tableErr_4);
                    TextView tableErr_5 = root.findViewById(R.id.lab3_Err5);
                    tableErr.add(tableErr_5);
                    TextView tableErr_6 = root.findViewById(R.id.lab3_Err6);
                    tableErr.add(tableErr_6);
                    TextView tableErr_7 = root.findViewById(R.id.lab3_Err7);
                    tableErr.add(tableErr_7);
                    TextView tableErr_8 = root.findViewById(R.id.lab3_Err8);
                    tableErr.add(tableErr_8);
                    TextView tableErr_9 = root.findViewById(R.id.lab3_Err9);
                    tableErr.add(tableErr_9);

                    ArrayList<TextView> tableExErr = new ArrayList<>();
                    TextView tableExErr_1 = root.findViewById(R.id.lab3_ExErr1);
                    tableExErr.add(tableExErr_1);
                    TextView tableExErr_2 = root.findViewById(R.id.lab3_ExErr2);
                    tableExErr.add(tableExErr_2);
                    TextView tableExErr_3 = root.findViewById(R.id.lab3_ExErr3);
                    tableExErr.add(tableExErr_3);
                    TextView tableExErr_4 = root.findViewById(R.id.lab3_ExErr4);
                    tableExErr.add(tableExErr_4);
                    TextView tableExErr_5 = root.findViewById(R.id.lab3_ExErr5);
                    tableExErr.add(tableExErr_5);
                    TextView tableExErr_6 = root.findViewById(R.id.lab3_ExErr6);
                    tableExErr.add(tableExErr_6);
                    TextView tableExErr_7 = root.findViewById(R.id.lab3_ExErr7);
                    tableExErr.add(tableExErr_7);
                    TextView tableExErr_8 = root.findViewById(R.id.lab3_ExErr8);
                    tableExErr.add(tableExErr_8);
                    TextView tableExErr_9 = root.findViewById(R.id.lab3_ExErr9);
                    tableExErr.add(tableExErr_9);

                    ArrayList<TextView> tableK = new ArrayList<>();
                    TextView tableK_1 = root.findViewById(R.id.lab3_K1);
                    tableK.add(tableK_1);
                    TextView tableK_2 = root.findViewById(R.id.lab3_K2);
                    tableK.add(tableK_2);
                    TextView tableK_3 = root.findViewById(R.id.lab3_K3);
                    tableK.add(tableK_3);
                    TextView tableK_4 = root.findViewById(R.id.lab3_K4);
                    tableK.add(tableK_4);
                    TextView tableK_5 = root.findViewById(R.id.lab3_K5);
                    tableK.add(tableK_5);
                    TextView tableK_6 = root.findViewById(R.id.lab3_K6);
                    tableK.add(tableK_6);
                    TextView tableK_7 = root.findViewById(R.id.lab3_K7);
                    tableK.add(tableK_7);
                    TextView tableK_8 = root.findViewById(R.id.lab3_K8);
                    tableK.add(tableK_8);
                    TextView tableK_9 = root.findViewById(R.id.lab3_K9);
                    tableK.add(tableK_9);

                    for(int i = 0; i < 9; i++){
                        tableErr.get(i).setText(String.format("%.2f*10^(-15)", deltaXi_val.get(3).get(i)*Math.pow(10, 15)));
                        tableExErr.get(i).setText(String.format("%.2f*10^(-15)", (interpolXi_val.get(3).get(i)-Yi_val.get(3))*Math.pow(10, 15)));
                        if(deltaXi_val.get(3).get(i)!=0){
                            tableK.get(i).setText(String.format("%.2f", 1 - (interpolXi_val.get(3).get(i)-Yi_val.get(3))/deltaXi_val.get(3).get(i)));
                        } else{
                            tableK.get(i).setText("0.0");
                        }

                    }

                } catch (Exception e) {
                    alert3Box.setText(e.getMessage());
                }

            }
        };
        butt_interpol.setOnClickListener(sort_l);

        return root;
    }

    //------------------------------------------------------------------Interpolation method-------------------------
    private double interpolate(int n, double x0){
        int k1 =0 ;
        if(x0<= Xi_val.get(0)){
            k1 = 0;
        } else if (x0 >= Xi_val.get(10)){
            k1 = 10;
        }
        for(int i = 1; i <= 10; i++){
            if(Xi_val.get(i-1) <= x0 && x0 < Xi_val.get(i)){
                k1 = i-1;
                break;
            }
        }
        if(10-k1<n){ k1 = 10-n;}
        int k = 10;
        for(int j = 0; j < n; j++){
            for(int i = k1; i < k; i++){
                if(j == 0){
                    matrix_A.get(i).add((((Xi_val.get(i+1)-x0)*Yi_val.get(i)) - ((Xi_val.get(i) - x0)*Yi_val.get(i+1)))/(Xi_val.get(i+1)-Xi_val.get(i)));

                } else {
                    matrix_A.get(i).add((((Xi_val.get(i+j+1)-x0)*matrix_A.get(i).get(j-1)) - ((Xi_val.get(i) - x0)*matrix_A.get(i+1).get(j-1)))/(Xi_val.get(i+j+1)-Xi_val.get(i)));
                }
            }
            k -= 1;
        }
        return matrix_A.get(k1).get(n-1);
    }

}