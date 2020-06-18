package com.example.myapplication.ui.Lab5;

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

public class Lab5Fragment extends Fragment {

    private Lab5ViewModel lab5ViewModel;

    Double[][] Matr_A = new Double[3][3];
    Double[] St_B = new Double[3];

    String data_file = "lab5_data.txt";
    String file_content = "";
    String[] file_vars;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        lab5ViewModel =
                ViewModelProviders.of(this).get(Lab5ViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_lab5, container, false);
        final TextView textView = root.findViewById(R.id.textSys5);

        final TextView alertBox5 = root.findViewById(R.id.alert5);
        final TextView resText5 = root.findViewById(R.id.res5);

        Matr_A[0][0] = 2.58;
        Matr_A[0][1] = 2.98;
        Matr_A[0][2] = 3.13;
        Matr_A[1][0] = 1.32;
        Matr_A[1][1] = 1.55;
        Matr_A[1][2] = 1.58;
        Matr_A[2][0] = 2.09;
        Matr_A[2][1] = 2.25;
        Matr_A[2][2] = 2.84;
        St_B[0] = -6.66;
        St_B[1] = -3.58;
        St_B[2] = -5.01;
        file_content = String.format("a11 = %f\n\na12 = %f\n\na13 = %f\n\na21 = %f\n\na22 = %f\n\na23 = %f\n\na31 = %f\n\na32 = %f\n\na33 = %f\n\nb1  = %f\n\nb2  = %f\n\nb3  = %f", Matr_A[0][0], Matr_A[0][1], Matr_A[0][2], Matr_A[1][0], Matr_A[1][1], Matr_A[1][2], Matr_A[2][0], Matr_A[2][1], Matr_A[2][2], St_B[0], St_B[1], St_B[2]);
        final FileHelper fh = new FileHelper();
        fh.saveToFile(file_content, data_file, alertBox5);

        final EditText editA11 = root.findViewById(R.id.editA11);
        final EditText editA12 = root.findViewById(R.id.editA12);
        final EditText editA13 = root.findViewById(R.id.editA13);
        final EditText editA21 = root.findViewById(R.id.editA21);
        final EditText editA22 = root.findViewById(R.id.editA22);
        final EditText editA23 = root.findViewById(R.id.editA23);
        final EditText editA31 = root.findViewById(R.id.editA31);
        final EditText editA32 = root.findViewById(R.id.editA32);
        final EditText editA33 = root.findViewById(R.id.editA33);

        final EditText editB1 = root.findViewById(R.id.editB1);
        final EditText editB2 = root.findViewById(R.id.editB2);
        final EditText editB3 = root.findViewById(R.id.editB3);

        editA11.setText(String.format("%f",Matr_A[0][0]));
        editA12.setText(String.format("%f",Matr_A[0][1]));
        editA13.setText(String.format("%f",Matr_A[0][2]));
        editA21.setText(String.format("%f",Matr_A[1][0]));
        editA22.setText(String.format("%f",Matr_A[1][1]));
        editA23.setText(String.format("%f",Matr_A[1][2]));
        editA31.setText(String.format("%f",Matr_A[2][0]));
        editA32.setText(String.format("%f",Matr_A[2][1]));
        editA33.setText(String.format("%f",Matr_A[2][2]));

        editB1.setText(String.format("%f", St_B[0]));
        editB2.setText(String.format("%f", St_B[1]));
        editB3.setText(String.format("%f", St_B[2]));


        //------------------------------------------------------------Import from the file Button-----------------------
        Button file5 = root.findViewById(R.id.buttonFile5);
        View.OnClickListener file5_l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    resText5.setText("");
                    file_vars = fh.readFromFile(root.getContext(), data_file, alertBox5).split("\n\n");
                    Matr_A[0][0] = Double.valueOf(file_vars[0].substring(5));
                    Matr_A[0][1] = Double.valueOf(file_vars[1].substring(5));
                    Matr_A[0][2] = Double.valueOf(file_vars[2].substring(5));
                    Matr_A[1][0] = Double.valueOf(file_vars[3].substring(5));
                    Matr_A[1][1] = Double.valueOf(file_vars[4].substring(5));
                    Matr_A[1][2] = Double.valueOf(file_vars[5].substring(5));
                    Matr_A[2][0] = Double.valueOf(file_vars[6].substring(5));
                    Matr_A[2][1] = Double.valueOf(file_vars[7].substring(5));
                    Matr_A[2][2] = Double.valueOf(file_vars[8].substring(5));

                    St_B[0] = Double.valueOf(file_vars[9].substring(5));
                    St_B[1] = Double.valueOf(file_vars[10].substring(5));
                    St_B[2] = Double.valueOf(file_vars[11].substring(5));

                    editA11.setText(String.format("%f",Matr_A[0][0]));
                    editA12.setText(String.format("%f",Matr_A[0][1]));
                    editA13.setText(String.format("%f",Matr_A[0][2]));
                    editA21.setText(String.format("%f",Matr_A[1][0]));
                    editA22.setText(String.format("%f",Matr_A[1][1]));
                    editA23.setText(String.format("%f",Matr_A[1][2]));
                    editA31.setText(String.format("%f",Matr_A[2][0]));
                    editA32.setText(String.format("%f",Matr_A[2][1]));
                    editA33.setText(String.format("%f",Matr_A[2][2]));

                    editB1.setText(String.format("%f", St_B[0]));
                    editB2.setText(String.format("%f", St_B[1]));
                    editB3.setText(String.format("%f", St_B[2]));

                    alertBox5.setText("Values imported successfully. You can try to calculate again.");

                } catch (Exception e) {
                    alertBox5.setText("Unable to get values from file.\nMake sure you didn't change formatting.");
                }
            }
        };
        file5.setOnClickListener(file5_l);

        //------------------------------------------------------------Apply Changes Button ---------------------
        Button apply5 = root.findViewById(R.id.buttonApply5);
        View.OnClickListener apply5_l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    resText5.setText("");
                    Matr_A[0][0] = Double.valueOf(editA11.getText().toString());
                    Matr_A[0][1] = Double.valueOf(editA12.getText().toString());
                    Matr_A[0][2] = Double.valueOf(editA13.getText().toString());
                    Matr_A[1][0] = Double.valueOf(editA21.getText().toString());
                    Matr_A[1][1] = Double.valueOf(editA22.getText().toString());
                    Matr_A[1][2] = Double.valueOf(editA23.getText().toString());
                    Matr_A[2][0] = Double.valueOf(editA31.getText().toString());
                    Matr_A[2][1] = Double.valueOf(editA32.getText().toString());
                    Matr_A[2][2] = Double.valueOf(editA33.getText().toString());

                    St_B[0] = Double.valueOf(editB1.getText().toString());
                    St_B[1] = Double.valueOf(editB2.getText().toString());
                    St_B[2] = Double.valueOf(editB3.getText().toString());

                    alertBox5.setText("Values changed. You can try to calculate again.");

                } catch (Exception e) {
                    alertBox5.setText("Something went wrong.\nCheck whether all the values are decimal.\nThen try again.");
                }
            }
        };
        apply5.setOnClickListener(apply5_l);


        //-------------------------------------------------------------------Solve Button -------------------------
        final Button res5 = root.findViewById(R.id.slv5);
        View.OnClickListener res5_l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkpass = false;
                try {
                    resText5.setText("");
                    int max_i = 0;
                    if(Math.abs(Matr_A[1][0])>Math.abs(Matr_A[0][0])){
                        if(Math.abs(Matr_A[2][0])>Math.abs(Matr_A[1][0])){
                            max_i = 2;
                        } else {
                            max_i = 1;
                        }
                    } else if(Math.abs(Matr_A[2][0])>Math.abs(Matr_A[0][0])) {
                        max_i = 2;
                    }

                    if(max_i != 0){
                        Double[] tmp = Matr_A[0];
                        Matr_A[0] = Matr_A[max_i];
                        Matr_A[max_i] = tmp;

                        Double b_tmp = St_B[0];
                        St_B[0] = St_B[max_i];
                        St_B[max_i] = b_tmp;
                    }

                    double m = Matr_A[1][0]/Matr_A[0][0];
                    for (int i = 0; i <3; i++){
                        Matr_A[1][i] -= Matr_A[0][i]*m;
                    }
                    St_B[1] -= St_B[0]*m;

                    m = Matr_A[2][0]/Matr_A[0][0];
                    for (int i = 0; i <3; i++){
                        Matr_A[2][i] -= Matr_A[0][i]*m;
                    }
                    St_B[2]-= St_B[0]*m;

                    if(Math.abs(Matr_A[1][1])<Math.abs(Matr_A[2][1])){
                        Double[] tmp = Matr_A[1];
                        Matr_A[1] = Matr_A[2];
                        Matr_A[2] = tmp;

                        Double b_tmp = St_B[1];
                        St_B[1] = St_B[2];
                        St_B[2] = b_tmp;
                    }

                    m = Matr_A[2][1]/Matr_A[1][1];
                    for (int i = 0; i <3; i++){
                        Matr_A[2][i] -= Matr_A[1][i]*m;
                    }
                    St_B[2]-= St_B[1]*m;
                    if(Matr_A[0][0].isNaN()||Matr_A[0][1].isNaN()||Matr_A[0][2].isNaN()||Matr_A[1][0].isNaN()||Matr_A[1][1].isNaN()||Matr_A[1][2].isNaN()||Matr_A[2][0].isNaN()||Matr_A[2][1].isNaN()||Matr_A[2][2].isNaN()||St_B[0].isNaN()||St_B[1].isNaN()||St_B[2].isNaN()) {
                        throw new Exception();
                    }
                    checkpass= true;

                    Double x3 = St_B[2]/Matr_A[2][2];
                    Double x2 = (St_B[1]-Matr_A[1][2]*x3)/Matr_A[1][1];
                    Double x1 = (St_B[0]-Matr_A[0][1]*x2-Matr_A[0][2]*x3)/Matr_A[0][0];
                    if(x1.isNaN()||x2.isNaN()||x3.isNaN()){
                        throw new Exception();
                    }

                    Matr_A[0][0] = Double.valueOf(editA11.getText().toString());
                    Matr_A[0][1] = Double.valueOf(editA12.getText().toString());
                    Matr_A[0][2] = Double.valueOf(editA13.getText().toString());
                    Matr_A[1][0] = Double.valueOf(editA21.getText().toString());
                    Matr_A[1][1] = Double.valueOf(editA22.getText().toString());
                    Matr_A[1][2] = Double.valueOf(editA23.getText().toString());
                    Matr_A[2][0] = Double.valueOf(editA31.getText().toString());
                    Matr_A[2][1] = Double.valueOf(editA32.getText().toString());
                    Matr_A[2][2] = Double.valueOf(editA33.getText().toString());

                    St_B[0] = Double.valueOf(editB1.getText().toString());
                    St_B[1] = Double.valueOf(editB2.getText().toString());
                    St_B[2] = Double.valueOf(editB3.getText().toString());

                    String result5 = String.format("x1 = %f\nx2 = %f\nx3 = %f\n\nCheck:\n%f %+f %+f = %f\n%f %+f %+f = %f\n%f %+f %+f = %f", x1, x2, x3, Matr_A[0][0]*x1, Matr_A[0][1]*x2, Matr_A[0][2]* x3, Matr_A[0][0]*x1+Matr_A[0][1]*x2+Matr_A[0][2]*x3, Matr_A[1][0]*x1, Matr_A[1][1]*x2, Matr_A[1][2]*x3, Matr_A[1][0]*x1+Matr_A[1][1]*x2+Matr_A[1][2]*x3, Matr_A[2][0]*x1, Matr_A[2][1]*x2, Matr_A[2][2]*x3, Matr_A[2][0]*x1+Matr_A[2][1]*x2+Matr_A[2][2]*x3);

                    alertBox5.setText("Calculation Successful!");
                    resText5.setText(result5);

                } catch (Exception e) {
                    if (checkpass){
                        String check = "";
                        for(int i=0; i<3; i++){
                            for (int j=0; j<3; j++){
                                check += String.format("a%d%d = %.2f\t\t",i+1, j+1, Matr_A[i][j]);
                            }
                            check += String.format("b%d = %.2f\n",i+1, St_B[i]);
                        }
                        alertBox5.setText("Encountered problems calculating values of x.\nHere are the coefficients before their calculation:");
                        resText5.setText(check);
                    } else {
                        alertBox5.setText("Something went wrong.\nCheck whether all the values are decimal.\nCheck if each of x1, x2 and x3 has at least 1 non-zero coefficient.\nThen try again.");
                    }
                }
            }
        };
        res5.setOnClickListener(res5_l);

        return root;
    }

}