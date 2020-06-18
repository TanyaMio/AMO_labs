package com.example.myapplication.ui.Lab4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.ArrayList;

import com.example.myapplication.FileHelper;

public class Lab4Fragment extends Fragment {

    private Lab4ViewModel lab4ViewModel;

    Double min_bound, max_bound;
    Integer n;
    Double precision;

    Double exact_sol;
    Double found_sol;

    String data_file = "lab4_data.txt";
    String file_content = "";
    String[] file_vars;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        lab4ViewModel =
                ViewModelProviders.of(this).get(Lab4ViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_lab4, container, false);
        final TextView textView = root.findViewById(R.id.textEq4);

        final SeekBar epsilum = root.findViewById(R.id.seekEps4);
        final TextView textN = root.findViewById(R.id.textN4);
        final EditText editA = root.findViewById(R.id.editA4);
        final EditText editB = root.findViewById(R.id.editB4);
        final TextView alertBox4 = root.findViewById(R.id.alert4);
        final TextView resultText = root.findViewById(R.id.res4);

        n = epsilum.getProgress() + 3;
        precision = Math.pow(10, -n);
        textN.setText(String.format("n = %d", n));

        min_bound = 0.25;
        max_bound = 0.8;
        editA.setText(min_bound.toString());
        editB.setText(max_bound.toString());

        file_content = String.format("n = %d\na = %.11f\nb = %.11f", n, min_bound, max_bound);
        final FileHelper fh = new FileHelper();
        fh.saveToFile(file_content, data_file, alertBox4);

        epsilum.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                n = epsilum.getProgress() + 3;
                precision = Math.pow(10, -n);
                textN.setText(String.format("n = %d", n));
            }

            public void onStartTrackingTouch(SeekBar seekBar) { }

            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        //---------------------------------------------------Import From File--------------------------------------
        Button file4 = root.findViewById(R.id.buttonFile4);
        View.OnClickListener file4_l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    file_vars = fh.readFromFile(root.getContext(), data_file, alertBox4).split("\n");

                    boolean change = false;
                    n = Integer.valueOf(file_vars[0].substring(4));
                    if (n < 3) {
                        n = 3;
                        change = true;
                    } else if (n > 9){
                        n = 9;
                        change = true;
                    }
                    precision = Math.pow(10, -n);
                    min_bound = Double.valueOf(file_vars[1].substring(4));
                    max_bound = Double.valueOf(file_vars[2].substring(4));

                    epsilum.setProgress(n - 3);
                    textN.setText(String.format("n = %d", n));
                    editA.setText(min_bound.toString());
                    editB.setText(max_bound.toString());

                    if (change) {
                        alertBox4.setText("Values imported successfully. Value of n was corrected to the closest supperted value. You can try to calculate again.");
                    } else {
                        alertBox4.setText("Values imported successfully. You can try to calculate again.");
                    }

                } catch (Exception e) {
                    alertBox4.setText("Unable to get values from file.\nMake sure you didn't change formatting.");
                }
            }
        };
        file4.setOnClickListener(file4_l);

        //-----------------------------------------------Apply Changes-------------------------------------------
        Button apply4 = root.findViewById(R.id.buttonApply4);
        View.OnClickListener apply4_l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    n = epsilum.getProgress() + 3;
                    precision = Math.pow(10, -n);
                    textN.setText(String.format("n = %d", n));

                    min_bound = Double.valueOf(editA.getText().toString());
                    max_bound = Double.valueOf(editB.getText().toString());

                    alertBox4.setText("Values changed. You can try to calculate again.");

                } catch (Exception e) {
                    alertBox4.setText("Something went wrong.\nCheck whether all the values are decimal.\nThen try again.");
                }
            }
        };
        apply4.setOnClickListener(apply4_l);

        //--------------------------------------------------Result Button--------------------------------------------
        Button calc4 = root.findViewById(R.id.calc4);
        View.OnClickListener calc4_l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(func(min_bound)*func(max_bound) > 0){
                        alertBox4.setText("The equation either has no solutions on this interval or it has more than 1.\nPlease, change the bounds of the interval and try again.");
                    } else {
                        if(min_bound > max_bound){
                            Double temp = min_bound;
                            min_bound = max_bound;
                            max_bound = temp;
                        }
                        if (func(max_bound) < 0 && func(min_bound) > 0) {
                            if (func1(min_bound) < 0 && func1(max_bound) < 0) {
                                if (func2(min_bound) < 0 && func2(max_bound) < 0) {
                                    found_sol = iterFindX(min_bound, max_bound, max_bound);
                                } else if (func2(min_bound) > 0 && func2(max_bound) > 0) {
                                    found_sol = iterFindX(min_bound, max_bound, min_bound);
                                } else {
                                    alertBox4.setText("The second derivative changes its sign on the interval.\nPlease, change the bounds of the interval and try again.");
                                }
                            } else {
                                alertBox4.setText("The first derivative changes its sign on the interval.\nPlease, change the bounds of the interval and try again.");
                            }
                        } else if (func(max_bound) > 0 && func(min_bound) < 0) {
                            if (func1(min_bound) > 0 && func1(max_bound) > 0) {
                                if (func2(min_bound) < 0 && func2(max_bound) < 0) {
                                    found_sol = iterFindX(min_bound, max_bound, min_bound);
                                } else if (func2(min_bound) > 0 && func2(max_bound) > 0) {
                                    found_sol = iterFindX(min_bound, max_bound, max_bound);
                                } else {
                                    alertBox4.setText("The second derivative changes its sign on the interval.\nPlease, change the bounds of the interval and try again.");
                                }
                            } else {
                                alertBox4.setText("The first derivative changes its sign on the interval.\nPlease, change the bounds of the interval and try again.");
                            }
                        }
                    }
                    if (!(found_sol == null)) {
                        exact_sol = findExactSol(found_sol);
                        alertBox4.setText("Calculation successful.");
                        resultText.setText(String.format("Found approximate solution:\nx = %.11f\n\nExact solution:\nx = %.11f\n\nDifference: %.11f", found_sol, exact_sol, Math.abs(found_sol-exact_sol)));
                    }
                } catch (Exception e) {
                    alertBox4.setText("Something went wrong.\nCheck whether all the values are decimal.\nThen try again.");
                }
            }
        };
        calc4.setOnClickListener(calc4_l);

        return root;
    }

    private double iterFindX(double a, double b, double start){
        double res = start - func(start)/func1(start);
        double next = res - func(res)/func1(res);
        while (Math.abs(next-res) >= precision) {
            res = next;
            next = res - func(res)/func1(res);
        }
        return res;
    }

    private double func(double x) {
        double res = Math.pow(x, 2) - Math.cos(Math.PI*x);
        return res;
    }

    private double func1(double x) {
        double res = 2*x + Math.PI*Math.sin(Math.PI*x);
        return res;
    }

    private double func2(double x) {
        double res = 2 + Math.PI*Math.PI*Math.cos(Math.PI*x);
        return res;
    }

    private double findExactSol(double apprX) {
        double apprRes = Math.pow(apprX, 2) - Math.cos(Math.PI*apprX);
        double step = Math.pow(10, -11);
        double x = apprX + step;
        double res = Math.pow(x, 2) - Math.cos(Math.PI*x);
        if (Math.abs(res) > Math.abs(apprRes)){
            step *= -1;
        }
        while(Math.abs(res) > Math.pow(10, -11)) {
            x += step;
            if (res*( Math.pow(x, 2) - Math.cos(Math.PI*x)) < 0){
                break;
            }
            res = Math.pow(x, 2) - Math.cos(Math.PI*x);
        }
        return x;
    }

}