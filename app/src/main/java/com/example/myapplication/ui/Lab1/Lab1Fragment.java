package com.example.myapplication.ui.Lab1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class Lab1Fragment extends Fragment {

    private Lab1ViewModel lab1ViewModel;

    public Double lab1_b = Math.random()*20 - 10;
    public Double lab1_c = Math.random()*20 - 10;
    public Double lab1_d = Math.random()*20 - 10;
    public Double lab1_f = Math.random()*20 - 10;
    public Double lab1_g = Math.random()*20 - 10;
    public Double lab1_j = Math.random()*20 - 10;
    public Double lab1_k = Math.random()*20 - 10;
    public Double lab1_v = Math.random()*20 - 10;

    public ArrayList<Double> lab1_ai = new ArrayList<Double>(50);
    String ai = "";
    String[] ai_vals;
    public ArrayList<Double> lab1_ci = new ArrayList<Double>(50);
    String ci = "";
    String[] ci_vals;
    public ArrayList<Double> lab1_fi = new ArrayList<Double>(50);
    String fi = "";
    String[] fi_vals;
    public ArrayList<Double> lab1_gi = new ArrayList<Double>(50);
    String gi = "";
    String[] gi_vals;

    String data_file = "lab1_data.txt";
    String file_content = "";
    String[] file_vars;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        lab1ViewModel =
                ViewModelProviders.of(this).get(Lab1ViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_lab1, container, false);
        final TextView textView = root.findViewById(R.id.seq);

        for (int i = 0; i < 50; i++){
            lab1_ai.add(Math.random()*20 - 10);
            lab1_ci.add(Math.random()*20 - 10);
            lab1_fi.add(Math.random()*20 - 10);
            lab1_gi.add(Math.random()*20 - 10);
        }

        //-----------------------------------------------------Single parameters fields----------------------
        final EditText editB = root.findViewById(R.id.editB);
        final EditText editC = root.findViewById(R.id.editC);
        final EditText editD = root.findViewById(R.id.editD);
        final EditText editF = root.findViewById(R.id.editF);
        final EditText editG = root.findViewById(R.id.editG);
        final EditText editJ = root.findViewById(R.id.editJ);
        final EditText editK = root.findViewById(R.id.editK);
        final EditText editV = root.findViewById(R.id.editV);

        //------------------------------------------------------Multiple parameters fields---------------------
        final EditText editAi = root.findViewById(R.id.editAi);
        final EditText editCi = root.findViewById(R.id.editCi);
        final EditText editFi = root.findViewById(R.id.editFi);
        final EditText editGi = root.findViewById(R.id.editGi);

        //------------------------------------------------------Alert and result displays---------------------
        final TextView alertBox = root.findViewById(R.id.alert);
        final TextView seqBox = root.findViewById(R.id.textSeq);
        final TextView branchBox = root.findViewById(R.id.textBranch);
        final TextView cycBox = root.findViewById(R.id.textCyc);

        editB.setText(String.format("%.2f",lab1_b));
        editC.setText(String.format("%.2f",lab1_c));
        editD.setText(String.format("%.2f",lab1_d));
        editF.setText(String.format("%.2f",lab1_f));
        editG.setText(String.format("%.2f",lab1_g));
        editJ.setText(String.format("%.2f",lab1_j));
        editK.setText(String.format("%.2f",lab1_k));
        editV.setText(String.format("%.2f",lab1_v));

        for (int i = 0; i < 50; i++){
            ai += String.format("%.2f, ", lab1_ai.get(i));
            ci += String.format("%.2f, ", lab1_ci.get(i));
            fi += String.format("%.2f, ", lab1_fi.get(i));
            gi += String.format("%.2f, ", lab1_gi.get(i));
        }
        editAi.setText(ai);
        editCi.setText(ci);
        editFi.setText(fi);
        editGi.setText(gi);

        file_content = String.format("b = %.2f\n\nc = %.2f\n\nd = %.2f\n\nf = %.2f\n\ng = %.2f\n\nj = %.2f\n\nk = %.2f\n\nv = %.2f\n\nai = %s\n\nci = %s\n\nfi = %s\n\ngi = %s", lab1_b,
                lab1_c, lab1_d, lab1_f, lab1_g, lab1_j, lab1_k, lab1_v, ai, ci, fi, gi);
        final FileHelper fh = new FileHelper();
        fh.saveToFile(file_content, data_file, alertBox);

        //------------------------------------------------------------Import from the file Button-----------------------
        Button file1 = root.findViewById(R.id.buttonFile);
        View.OnClickListener file1_l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    file_vars = fh.readFromFile(root.getContext(), data_file, alertBox).split("\n\n");
                    lab1_b = Double.valueOf(file_vars[0].substring(4));
                    lab1_c = Double.valueOf(file_vars[1].substring(4));
                    lab1_d = Double.valueOf(file_vars[2].substring(4));
                    lab1_f = Double.valueOf(file_vars[3].substring(4));
                    lab1_g = Double.valueOf(file_vars[4].substring(4));
                    lab1_j = Double.valueOf(file_vars[5].substring(4));
                    lab1_k = Double.valueOf(file_vars[6].substring(4));
                    lab1_v = Double.valueOf(file_vars[7].substring(4));

                    ai_vals = file_vars[8].substring(5).split(", ");
                    ci_vals = file_vars[9].substring(5).split(", ");
                    fi_vals = file_vars[10].substring(5).split(", ");
                    gi_vals = file_vars[11].substring(5).split(", ");

                    for(int i = 0; i<50; i++){
                        lab1_ai.set(i, Double.valueOf(ai_vals[i]));
                        lab1_ci.set(i, Double.valueOf(ci_vals[i]));
                        lab1_fi.set(i, Double.valueOf(fi_vals[i]));
                        lab1_gi.set(i, Double.valueOf(gi_vals[i]));

                    }

                    editB.setText(String.format("%.2f",lab1_b));
                    editC.setText(String.format("%.2f",lab1_c));
                    editD.setText(String.format("%.2f",lab1_d));
                    editF.setText(String.format("%.2f",lab1_f));
                    editG.setText(String.format("%.2f",lab1_g));
                    editJ.setText(String.format("%.2f",lab1_j));
                    editK.setText(String.format("%.2f",lab1_k));
                    editV.setText(String.format("%.2f",lab1_v));

                    editAi.setText(file_vars[8].substring(5));
                    editCi.setText(file_vars[9].substring(5));
                    editFi.setText(file_vars[10].substring(5));
                    editGi.setText(file_vars[11].substring(5));

                    alertBox.setText("Values imported successfully. You can try to calculate again.");

                } catch (Exception e) {
                    alertBox.setText("Unable to get values from file.\nMake sure you didn't change formatting.");
                }
            }
        };
        file1.setOnClickListener(file1_l);

        //------------------------------------------------------------Apply Changes Button ---------------------
        Button apply1 = root.findViewById(R.id.buttonApply);
        View.OnClickListener apply1_l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    lab1_b = Double.valueOf(editB.getText().toString());
                    lab1_c = Double.valueOf(editC.getText().toString());
                    lab1_d = Double.valueOf(editD.getText().toString());
                    lab1_f = Double.valueOf(editF.getText().toString());
                    lab1_g = Double.valueOf(editG.getText().toString());
                    lab1_j = Double.valueOf(editJ.getText().toString());
                    lab1_k = Double.valueOf(editK.getText().toString());
                    lab1_v = Double.valueOf(editV.getText().toString());

                    ai_vals = editAi.getText().toString().split(", ");
                    ci_vals = editCi.getText().toString().split(", ");
                    fi_vals = editFi.getText().toString().split(", ");
                    gi_vals = editGi.getText().toString().split(", ");

                    for(int i = 0; i<50; i++){
                        lab1_ai.set(i, Double.valueOf(ai_vals[i]));
                        lab1_ci.set(i, Double.valueOf(ci_vals[i]));
                        lab1_fi.set(i, Double.valueOf(fi_vals[i]));
                        lab1_gi.set(i, Double.valueOf(gi_vals[i]));

                    }

                    alertBox.setText("Values changed. You can try to calculate again.");

                } catch (Exception e) {
                    alertBox.setText("Something went wrong.\nCheck whether all the values are decimal.\nCheck whether each line for cyclic algorithm contains 50 decimal values.\nThen try again.");
                }
            }
        };
        apply1.setOnClickListener(apply1_l);

        //-------------------------------------------------------------------Y1 = Button -------------------------
        Button seq_res = root.findViewById(R.id.buttonSeq);
        View.OnClickListener seq_res_l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double res = 2*(Math.cos(lab1_b*lab1_c/2)+Math.sin(lab1_b*lab1_c/2));
                    AlertDialog.Builder result =  new AlertDialog.Builder(getContext());
                    result.setCancelable(true);
                    result.setTitle("Sequential algorithm result");
                    seqBox.setText(String.format("Y1 = %.5f", res));

                } catch (Exception e) {
                    alertBox.setText("Error!!!\nOne of the parameters is not a decimal number.\nPlease, change values and try again");

                }
            }
        };

        lab1ViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        seq_res.setOnClickListener(seq_res_l);

        //-------------------------------------------------------------------Y = Button -------------------------------
        Button branch_res = root.findViewById(R.id.buttonBranch);
        View.OnClickListener branch_res_l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (lab1_g*lab1_f < 0){
                        alertBox.setText(String.format("Error!!!\ng * f = %.2f * %.2f < 0\nPlease, change values and try again.", lab1_g, lab1_f));

                    } else if(lab1_c*lab1_v<0){
                        alertBox.setText(String.format("Error!!!\nc * v = %.2f * %.2f < 0\nPlease, change values and try again.", lab1_c, lab1_v));
                    } else if(lab1_g*lab1_f == 0 && lab1_c*lab1_v==0) {
                        alertBox.setText(String.format("Error!!!\ng * f = %.2f * %.2f = 0 and c * v = %.2f * %.2f = 0\nPlease, change values and try again.", lab1_g, lab1_f, lab1_c, lab1_v));
                    } else if(lab1_d-lab1_b-lab1_k*lab1_j<0) {
                        alertBox.setText(String.format("Error!!!\nd - b - (k * j)%.2f - %.2f - (%.2f * %.2f) = 0\nPlease, change values and try again.", lab1_d, lab1_b, lab1_k, lab1_j));
                    }else{
                        double res = Math.sqrt((lab1_d-lab1_b-lab1_k*lab1_j)/(23*Math.sqrt(lab1_g*lab1_f)+6*Math.sqrt(lab1_c*lab1_v)));
                        branchBox.setText(String.format("y = %.5f", res));
                    }

                } catch (Exception e) {
                    alertBox.setText("One of the parameters is not a decimal number.\nPlease, change values and try again");

                }
            }
        };
        branch_res.setOnClickListener(branch_res_l);

        //----------------------------------------------------------------F = Button ----------------------------------------
        Button cyc_res = root.findViewById(R.id.buttonCyc);
        View.OnClickListener cyc_res_l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    boolean error = false;
                    double res = 0.0;
                    for (int i = 0; i < 50; i++){
                        if (lab1_fi.get(i)*lab1_gi.get(i)<0){
                            alertBox.setText(String.format("f[%d] = %.2f, g[%d] = %.2f\nf[%d] * g[%d] = %.2f * %.2f < 0\nPlease, try again.", i, lab1_fi.get(i), i, lab1_gi.get(i), i, i, lab1_fi.get(i), lab1_gi.get(i)));
                            error = true;
                            break;
                        } else {
                            res += lab1_ai.get(i)*lab1_ai.get(i)+56*lab1_ci.get(i)*Math.sqrt(lab1_fi.get(i)*lab1_gi.get(i));
                        }
                    }
                    if (!error) {
                        cycBox.setText(String.format("f = %.5f", res));
                    }

                } catch (Exception e) {
                    alertBox.setText("One of the parameters is not a decimal number or number of values for 1 or more cyclic algorithm parameters is not 50.\nPlease, change values and try again");

                }
            }
        };
        cyc_res.setOnClickListener(cyc_res_l);

        lab1ViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }


}