package edu.gcu.bootcamp.ericstoll.cst235milestone;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {
	
@Id	
protected String account_num; 
private String account_type;
protected BigDecimal account_balance;
private int cust_id; 

public String getAccount_num() {
	return account_num;
}
public void setAccount_num(String account_num) {
	this.account_num = account_num;
}
public String getAccount_type() {
	return account_type;
}
public void setAccount_type(String account_type) {
	this.account_type = account_type;
}
public BigDecimal getAccount_balance() {
	return account_balance;
}
public void setAccount_balance(BigDecimal account_balance) {
	this.account_balance = account_balance;
} 

public int getCust_id() {
	return cust_id;
}
public void setCust_id(int cust_id) {
	this.cust_id = cust_id;
}

@Override
public String toString() {
    return "Account["
            + this.cust_id + this.account_num + this.account_type + this.account_balance;
	}
}
