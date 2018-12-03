package edu.gcu.bootcamp.ericstoll.cst235milestone;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Employee {
    
    @Id
    private int Emp_Id;
    private String F_Name;
    private String L_Name;
    private String username;
    private String password;
    
    public int getEmp_Id() {
        return Emp_Id;
    }
    public void setEmp_Id(int emp_Id) {
        Emp_Id = emp_Id;
    }
    public String getF_Name() {
        return F_Name;
    }
    public void setF_Name(String f_Name) {
        F_Name = f_Name;
    }
    public String getL_Name() {
        return L_Name;
    }
    public void setL_Name(String l_Name) {
        L_Name = l_Name;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}