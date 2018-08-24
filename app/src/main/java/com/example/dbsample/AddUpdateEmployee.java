package com.example.dbsample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class AddUpdateEmployee extends AppCompatActivity implements DatePickerFragment.DateDialogListener{

    private static final String EXTRA_EMP_ID = "com.androidtutorialpoint.empId";
    private static final String EXTRA_ADD_UPDATE = "com.androidtutorialpoint.add_update";
    private static final String DIALOG_DATE = "DialogDate";
    private ImageView calendarImage;
    private RadioGroup radioGroup;
    private RadioButton maleRadioButton,femaleRadioButton;
    private EditText FirstNameEditText;
    private EditText LastNameEditText;
    private EditText deptEditText;
    private EditText HireDateEditText;
    private Button addUpdateButton;
    private Employee newEmployee;
    private Employee oldEmployee;
    private String mode;
    private long empId;
    private EmployeeOperations employeeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_employee);
        newEmployee = new Employee();
        oldEmployee = new Employee();
        FirstNameEditText = (EditText)findViewById(R.id.edit_text_first_name);
        LastNameEditText = (EditText)findViewById(R.id.edit_text_last_name);
        HireDateEditText = (EditText) findViewById(R.id.edit_text_hire_date);
        radioGroup = (RadioGroup) findViewById(R.id.radio_gender);
        maleRadioButton = (RadioButton) findViewById(R.id.radio_male);
        femaleRadioButton = (RadioButton) findViewById(R.id.radio_female);
        calendarImage = (ImageView)findViewById(R.id.image_view_hire_date);
        deptEditText = (EditText)findViewById(R.id.edit_text_dept);
        addUpdateButton = (Button)findViewById(R.id.button_add_update_employee);
        employeeData = new EmployeeOperations(this);
        employeeData.open();


        mode = getIntent().getStringExtra(EXTRA_ADD_UPDATE);
        if(mode.equals("Update")){

            addUpdateButton.setText("Update Employee");
            empId = getIntent().getLongExtra(EXTRA_EMP_ID,0);

            initializeEmployee(empId);

        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.radio_male) {
                    newEmployee.setGender("M");
                    if(mode.equals("Update")){
                        oldEmployee.setGender("M");
                    }
                } else if (checkedId == R.id.radio_female) {
                    newEmployee.setGender("F");
                    if(mode.equals("Update")){
                        oldEmployee.setGender("F");
                    }

                }
            }

        });

        calendarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager manager = getSupportFragmentManager();
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.show(manager, DIALOG_DATE);
            }
        });


        addUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mode.equals("Add")) {
                    newEmployee.setFirstName(FirstNameEditText.getText().toString());
                    newEmployee.setLastName(LastNameEditText.getText().toString());
                    newEmployee.setHireDate(HireDateEditText.getText().toString());
                    newEmployee.setDept(deptEditText.getText().toString());
                    employeeData.addEmployee(newEmployee);
                    Toast t = Toast.makeText(AddUpdateEmployee.this, "Employee "+ newEmployee.getFirstName() + "has been added successfully !", Toast.LENGTH_SHORT);
                    t.show();
                    Intent i = new Intent(AddUpdateEmployee.this,MainActivity.class);
                    startActivity(i);
                }else {
                    oldEmployee.setFirstName(FirstNameEditText.getText().toString());
                    oldEmployee.setLastName(LastNameEditText.getText().toString());
                    oldEmployee.setHireDate(HireDateEditText.getText().toString());
                    oldEmployee.setDept(deptEditText.getText().toString());
                    employeeData.updateEmployee(oldEmployee);
                    Toast t = Toast.makeText(AddUpdateEmployee.this, "Employee "+ oldEmployee.getFirstName() + " has been updated successfully !", Toast.LENGTH_SHORT);
                    t.show();
                    Intent i = new Intent(AddUpdateEmployee.this,MainActivity.class);
                    startActivity(i);

                }


            }
        });


    }

    private void initializeEmployee(long empId) {
        oldEmployee = employeeData.getEmployee(empId);
        FirstNameEditText.setText(oldEmployee.getFirstName());
        LastNameEditText.setText(oldEmployee.getLastName());
        HireDateEditText.setText(oldEmployee.getHireDate());
        radioGroup.check(oldEmployee.getGender().equals("M") ? R.id.radio_male : R.id.radio_female);
        deptEditText.setText(oldEmployee.getDept());
    }


    @Override
    public void onFinishDialog(Date date) {
        HireDateEditText.setText(formatDate(date));

    }

    public String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String HireDate = sdf.format(date);
        return HireDate;
    }

}