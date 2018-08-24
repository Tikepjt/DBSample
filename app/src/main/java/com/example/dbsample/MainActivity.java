package com.example.dbsample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button addEmployeeButton;
    private Button editEmployeeButton;
    private Button deleteEmployeeButton;
    private Button viewAllEmployeeButton;
    private EmployeeOperations employeeOps;
    private static final String EXTRA_EMP_ID = "com.androidtutorialpoint.empId";
    private static final String EXTRA_ADD_UPDATE = "com.androidtutorialpoint.add_update";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addEmployeeButton = findViewById(R.id.button_add_employee);
        editEmployeeButton = findViewById(R.id.button_edit_employee);
        deleteEmployeeButton = findViewById(R.id.button_delete_employee);
        viewAllEmployeeButton = findViewById(R.id.button_view_employees);

        addEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AddUpdateEmployee.class);
                i.putExtra(EXTRA_ADD_UPDATE, "Add");
                startActivity(i);
            }
        });
        editEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEmpIdAndUpdateEmp();
            }
        });
        deleteEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEmpIdAndRemoveEmp();
            }
        });
        viewAllEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ViewAllEmployees.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.employee_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getEmpIdAndUpdateEmp(){

        LayoutInflater li = LayoutInflater.from(this);
        View getEmpIdView = li.inflate(R.layout.dialog_get_emp_id, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(getEmpIdView);

        final EditText userInput = getEmpIdView.findViewById(R.id.editTextDialogUserInput);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        Intent i = new Intent(MainActivity.this,AddUpdateEmployee.class);
                        i.putExtra(EXTRA_ADD_UPDATE, "Update");
                        i.putExtra(EXTRA_EMP_ID, Long.parseLong(userInput.getText().toString()));
                        startActivity(i);
                    }
                }).create()
                .show();
    }

    public void getEmpIdAndRemoveEmp(){

        LayoutInflater li = LayoutInflater.from(this);
        View getEmpIdView = li.inflate(R.layout.dialog_get_emp_id, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(getEmpIdView);

        final EditText userInput = (EditText) getEmpIdView.findViewById(R.id.editTextDialogUserInput);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        employeeOps = new EmployeeOperations(MainActivity.this);
                        employeeOps.removeEmployee(employeeOps.getEmployee(Long.parseLong(userInput.getText().toString())));
                        Toast t = Toast.makeText(MainActivity.this,"Employee removed successfully!", Toast.LENGTH_SHORT);
                        t.show();
                    }
                }).create()
                .show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        employeeOps.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        employeeOps.close();

    }

}
