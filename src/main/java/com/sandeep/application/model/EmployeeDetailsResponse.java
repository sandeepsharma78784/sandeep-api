package com.sandeep.application.model;
import java.util.List;

/* note db se ya dusri appi se jo reponse aayega vo esa aana chahiye 
{
  "data": [ ... ]
}
tbhi isme cast hoga.
*/
public class EmployeeDetailsResponse {
    private List<EmployeeDetails> data;

    public List<EmployeeDetails> getData() {
        return data;
    }

    public void setData(List<EmployeeDetails> data) {
        this.data = data;
    }
}