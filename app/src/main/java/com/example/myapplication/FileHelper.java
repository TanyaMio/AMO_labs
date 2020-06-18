package com.example.myapplication;

import android.content.Context;
import android.os.Environment;
import android.provider.ContactsContract;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileHelper {

    String fileName;
    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/amo/";
    String tag = FileHelper.class.getName();

    public FileHelper() {
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdir();
        }
    }

    public String readFromFile (Context c, String f, TextView a) {
        fileName = f;
        String line;
        String data = "";

        try {
            FileInputStream fis = new FileInputStream(new File(path+fileName));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line + System.getProperty("line.separator"));
            }
            fis.close();
            data = sb.toString();
            br.close();

        } catch (FileNotFoundException ex) {
            a.setText("File not found.");
        } catch (IOException e) {
            a.setText("I\\O error! Try again.");
        }

        return data;
    }

    public boolean saveToFile(String d, String f, TextView a) {
        try {
            fileName = f;
            File file = new File(path+fileName);
            if(!file.exists()){
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file, false);
                fos.write(d.getBytes());
                fos.close();
            }
            return true;
        } catch(FileNotFoundException ex) {
            a.setText("File not found.");
        } catch (IOException ex) {
            a.setText("I\\O error! Try again.");
        }
        return false;
    }
}
