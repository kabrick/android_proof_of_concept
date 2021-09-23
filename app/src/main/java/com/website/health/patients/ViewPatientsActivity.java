package com.website.health.patients;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import com.website.health.R;
import com.website.health.adapters.PatientsAdapter;
import com.website.health.helpers.DataRouter;
import com.website.health.helpers.HelperFunctions;
import com.website.health.helpers.PreferenceManager;
import com.website.health.models.Patient;

public class ViewPatientsActivity extends AppCompatActivity {

    PreferenceManager preferenceManager;
    HelperFunctions helperFunctions;
    PatientsAdapter mAdapter;
    ArrayList<Patient> patients;
    DataRouter router;
    RecyclerView recyclerView;
    TextView no_active_patients;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patients);

        preferenceManager = new PreferenceManager(this);
        helperFunctions = new HelperFunctions(this);

        no_active_patients = findViewById(R.id.no_active_patients);

        patients = new ArrayList<>();

        mAdapter = new PatientsAdapter(ViewPatientsActivity.this, patients);

        searchView = findViewById(R.id.search_active_patients);

        searchView.setQueryHint("Search patients...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });

        recyclerView = findViewById(R.id.recycler_active_patients);

        router = new DataRouter(this);

        getPatients();
    }

    public void getPatients(){
        helperFunctions.genericProgressBar("Fetching patients...");

        /*String url = router.ip_address + "view_patients";

        JsonArrayRequest request = new JsonArrayRequest(url,
                response -> {

                    helperFunctions.stopProgressBar();

                    if(response.length() < 1){
                        searchView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        no_active_patients.setVisibility(View.VISIBLE);
                    } else {
                        searchView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        no_active_patients.setVisibility(View.GONE);
                    }

                    patients.clear();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonobject = response.getJSONObject(i);
                            patients.add(new Patient(jsonobject.getString("id"), jsonobject.getString("name"), jsonobject.getString("gender"), jsonobject.getString("date_of_birth")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    // insert patients into list
                    mAdapter.notifyDataSetChanged();

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ViewPatientsActivity.this);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                }, error -> {
                    helperFunctions.stopProgressBar();
                    helperFunctions.genericDialog("Something went wrong. Please try again.");
                });

        Volley.newRequestQueue(this).add(request);*/

        patients.clear();

        for (int i = 1; i < 21; i++) {
            String gender = "";
            if (new Random().nextInt(60) % 5 == 0) {
                gender = "Male";
            } else {
                gender = "Female";
            }

            patients.add(new Patient(String.valueOf(i), "Patient " + i, gender, String.valueOf(new Random().nextInt(60))));
        }

        // insert patients into list
        mAdapter.notifyDataSetChanged();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ViewPatientsActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        helperFunctions.stopProgressBar();
    }

    public void add_patient(View view) {
        startActivity(new Intent(this, AddPatientActivity.class));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

