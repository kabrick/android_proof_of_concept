package com.website.health.helpers;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import java.util.Calendar;

public class HelperFunctions {

    private final Context context;
    private static ProgressDialog progressDialog;
    private final PreferenceManager preferenceManager;

    public HelperFunctions(Context context) {
        this.context = context;
        preferenceManager = new PreferenceManager(context);
    }

    public void genericDialog(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setMessage(message).setPositiveButton("Okay", (dialogInterface, i) -> {
            //
        }).show();
    }

    public void genericProgressBar(String message){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void stopProgressBar(){
        progressDialog.cancel();
    }

    public String capitalize_first_letter(String string){
        StringBuilder result = new StringBuilder(string.length());
        String[] words = string.split(" ");

        for (String word : words) {
            result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }

        return result.toString();
    }

    public int get_age_in_years(String date) {
        int age;

        Calendar now = Calendar.getInstance();
        Calendar dob = Calendar.getInstance();

        String[] date_of_birth_array = date.split("-");

        dob.set(Calendar.YEAR, Integer.parseInt(date_of_birth_array[0]));
        dob.set(Calendar.MONTH, Integer.parseInt(date_of_birth_array[1]));
        dob.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date_of_birth_array[2]));

        int year1 = now.get(Calendar.YEAR);
        int year2 = dob.get(Calendar.YEAR);
        age = year1 - year2;
        int month1 = now.get(Calendar.MONTH);
        int month2 = dob.get(Calendar.MONTH);
        if (month2 > month1) {
            age--;
        } else if (month1 == month2) {
            int day1 = now.get(Calendar.DAY_OF_MONTH);
            int day2 = dob.get(Calendar.DAY_OF_MONTH);
            if (day2 > day1) {
                age--;
            }
        }

        return age ;
    }
}
