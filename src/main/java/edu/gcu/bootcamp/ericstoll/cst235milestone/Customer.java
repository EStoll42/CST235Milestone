package edu.gcu.bootcamp.ericstoll.cst235milestone;

	import javax.persistence.Column;

	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
	import javax.persistence.SequenceGenerator;
	
	@Entity
	public class Customer {
		@Id
	    @Column(name = "CUST_ID", updatable = false, nullable = false)
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @SequenceGenerator(name = "SEQUENCE_NAME", sequenceName = "SEQUENCE_NAME", allocationSize = 1, initialValue = 1)
		private int cust_id; 
		private String first_name; 
		private String last_name; 
		private String user_name; 
		private String password;
		
//		public Customer() {
//			
//		}
//		public Customer(int cust_id) {
//			this.cust_id = cust_id;  
//		}
	//	
		public int getId() {
			return cust_id;
		}
		public void setId(int id) {
			this.cust_id = id;
		}
		
		public String getFirst_name() {
			return first_name;
		}
		public void setFirst_name(String first_name) {
			this.first_name = first_name;
		}
		public String getLast_name() {
			return last_name;
		}
		public void setLast_name(String last_name) {
			this.last_name = last_name;
		}
		public String getUser_name() {
			return user_name;
		}
		public void setUser_name(String user_name) {
			this.user_name = user_name;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		
		@Override
	    public String toString() {
	        return "Customer[Customer Id = "
	                + this.cust_id + ", First Name = "
	                + this.first_name + ", Last Name = "
	                + this.last_name+ "]";
	    }
}