package com.example.dbsample;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;

public class ViewAllEmployees extends ListActivity {

    private EmployeeOperations employeeOps;
    List<Employee> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_employee);
        employeeOps = new EmployeeOperations(this);
        employeeOps.open();
        employees = employeeOps.getAllEmployees();
        employeeOps.close();
        ArrayAdapter<Employee> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, employees);
        setListAdapter(adapter);
    }
}