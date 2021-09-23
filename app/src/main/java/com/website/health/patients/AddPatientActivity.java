package com.website.health.patients;

import com.website.health.R;
import com.website.health.helpers.DataRouter;
import com.website.health.helpers.HelperFunctions;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddPatientActivity extends AppCompatActivity {

    EditText last_name, first_name, date_of_birth;
    final Calendar myCalendar = Calendar.getInstance();
    HelperFunctions helperFunctions;
    String gender_string;
    RadioGroup gender;
    DataRouter dataRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        dataRouter = new DataRouter(this);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        helperFunctions = new HelperFunctions(this);

        last_name = findViewById(R.id.last_name);
        first_name = findViewById(R.id.first_name);

        gender = findViewById(R.id.gender);
        gender.setOnCheckedChangeListener((radioGroup, checked_id) -> {
            if(checked_id == R.id.gender_female){
                gender_string = "Female";
            } else if(checked_id == R.id.gender_male){
                gender_string = "Male";
            }
        });

        date_of_birth = findViewById(R.id.date_of_birth);

        // disable editing of the date of birth
        date_of_birth.setFocusable(false);

        // set up onclick dialog
        @SuppressLint("SetTextI18n") final DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // get time now
            long currentTime = System.currentTimeMillis();
            Calendar now = Calendar.getInstance();
            now.setTimeInMillis(currentTime);

            int years = now.get(Calendar.YEAR) - myCalendar.get(Calendar.YEAR);
            int currMonth = now.get(Calendar.MONTH) + 1;
            int birthMonth = myCalendar.get(Calendar.MONTH) + 1;

            //Get difference between months
            int months = currMonth - birthMonth;

            if (months < 0) {
                years--;
                months = 12 - birthMonth + currMonth;
                if (now.get(Calendar.DATE) < myCalendar.get(Calendar.DATE)) {
                    months--;
                }
            } else if (months == 0 && now.get(Calendar.DATE) < myCalendar.get(Calendar.DATE)) {
                years--;
                months = 11;
            }

            TextView patient_years = findViewById(R.id.patient_years);
            TextView patient_months = findViewById(R.id.patient_months);
            LinearLayout patient_age = findViewById(R.id.patient_age);
            patient_age.setVisibility(View.VISIBLE);
            patient_years.setText(years + " years");
            patient_months.setText(months + " months");

            updateDateLabel();

            myCalendar.set(Calendar.YEAR, now.get(Calendar.YEAR));
            myCalendar.set(Calendar.MONTH, now.get(Calendar.MONTH));
            myCalendar.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
        };

        date_of_birth.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(AddPatientActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });
    }

    // update the edit text view with appropriate date format
    public void updateDateLabel(){
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        date_of_birth.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void registerPatient(View view){
        String last_name_string = last_name.getText().toString();
        String first_name_string = first_name.getText().toString();
        String date_of_birth_string = date_of_birth.getText().toString();

        if(last_name_string.isEmpty()){
            helperFunctions.genericDialog("Please fill in the patient last name");
            return;
        }

        if(first_name_string.isEmpty()){
            helperFunctions.genericDialog("Please fill in the patient first name");
            return;
        }

        String patient_names_string = last_name_string + " " + first_name_string;

        if(date_of_birth_string.isEmpty()){
            helperFunctions.genericDialog("Please fill in the date of birth");
            return;
        }

        dataRouter.addPatient(patient_names_string, date_of_birth_string, gender_string);
    }
}
