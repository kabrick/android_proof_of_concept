package com.website.health.helpers;

import android.content.Context;
import android.content.Intent;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import com.website.health.patients.ViewPatientVitalsActivity;

public class DataRouter {

    private final Context context;
    private final RequestQueue requestQueue;
    public String ip_address = "http://192.168.43.4:8000/";
    private final HelperFunctions helperFunctions;
    private final PreferenceManager preferenceManager;

    public DataRouter(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        helperFunctions = new HelperFunctions(context);
        preferenceManager = new PreferenceManager(context);
    }

    public void addPatient(final String patient_names, final String date_of_birth, final String gender){

        helperFunctions.genericProgressBar("Adding new patient record...");

        String url = ip_address + "add_patients";

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, response -> {
            helperFunctions.stopProgressBar();

            if (response.equals("0")) {
                helperFunctions.genericDialog("Patient record not created. Please try again");
            } else {
                // display notification for new patient
                String[] results_string_array = response.split(",");
                preferenceManager.setPatientId(results_string_array[1]);

                Intent intent = new Intent(context, ViewPatientVitalsActivity.class);
                context.startActivity(intent);
            }
        }, error -> {
            // This code is executed if there is an error
            helperFunctions.stopProgressBar();
            helperFunctions.genericDialog("Patient record not created. Please try again");
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("patient_names", patient_names);
                data.put("date_of_birth", date_of_birth);
                data.put("gender", gender);
                return data;
            }
        };

        MyStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(MyStringRequest);
    }

    public void addVitals(final String patient_id, final int hr, final int rr, final double temp) {

        String url = ip_address + "add_vitals";

        helperFunctions.genericProgressBar("Adding new patient vitals...");

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, response -> {
            helperFunctions.stopProgressBar();
            Intent intent = new Intent(context, ViewPatientVitalsActivity.class);
            context.startActivity(intent);
        }, error -> {
            // This code is executed if there is an error.
            helperFunctions.stopProgressBar();
            helperFunctions.genericDialog("Patient vitals not added. Please try again");
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("patient_id", patient_id);
                data.put("hr", String.valueOf(hr));
                data.put("rr", String.valueOf(rr));
                data.put("temp", String.valueOf(temp));
                return data;
            }
        };

        MyStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(MyStringRequest);
    }
}
