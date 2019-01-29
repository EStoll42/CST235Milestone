package edu.gcu.bootcamp.ericstoll.cst235milestone;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Bank {
	
	Customer cust = new Customer();
	static SessionFactory factory = new Configuration().configure().buildSessionFactory();
	static private BigDecimal bd;
	Scanner scanner = new Scanner(System.in);
	static Bank bank = new Bank();

	public static void main(String[] args) {
		
		bank.startMenu();
	}
	
	public void startMenu() {
		
		String option;
		int o;
		  do { 
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			System.out.println();
			System.out.println("          WELCOME TO GCU BANK");
			System.out.println();
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			System.out.println("Pick an option: ");
			System.out.println("------------------------");
			System.out.println(" 1: : Customer sign in");
			System.out.println(" 2: : Admin sign in");
			System.out.println(" 3: : Exit");
			System.out.println("------------------------");
			option = scanner.nextLine();
			o = Integer.parseInt(option);
			
		switch(o) {
			case 1: bank.checkCustomer();
				break;
			case 2: bank.checkAdmin();
				break;
			case 3: bank.displayExitScreen();	
			}
		  }while (o != 3);
	}
	
	private void adminMenu() {
		String option;
		do {
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			System.out.println("              ADMIN MENU");
			System.out.println("               "+ "GCU BANK");
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			System.out.println("Pick an option: ");
			System.out.println("-----------------------");
			System.out.println(" 1: : Customer List");
			System.out.println(" 2: : Create new Customer");		
			System.out.println(" 3: : Close month");
			System.out.println("------------------------");
			System.out.println(" 9: : Logout");
			option = scanner.nextLine();
			bank.adminActionMenu(Integer.parseInt(option));
		} while (Integer.parseInt(option) != 9);
	}
	
	public void displayExitScreen() {
		
		System.out.println("Thank you for banking with GCU Bank");
	}
	
public void adminActionMenu(int option) {
        
        switch(option) {
        case 1: createCustomerList();
            break;
        case 2: createCustomer();
            break;
        case 3: System.out.println("Enter id for customer statement: ");
                String customerId = scanner.nextLine();
                System.out.println(customerId);
                Session session = factory.openSession();
                CriteriaBuilder cb = session.getCriteriaBuilder();
                CriteriaQuery<Account> criteria = cb.createQuery(Account.class);
                Root<Account> groot = criteria.from(Account.class);
                criteria.select(groot);
                criteria.where(cb.equal(groot.get("cust_id"),Integer.parseInt(customerId)));
               
                try {
                List<Account> acct = session.createQuery(criteria).getResultList();
                bank.doEndOfMonth(acct);
                }
                catch (NoResultException e) {
                    System.out.println("Customer does not exist");
                    bank.adminMenu();
                }
            break;
        case 9: System.out.println("user has logged out");
            break;
        default: System.out.println("Wrong Entry");
        }
    }
	
	private void createCustomerList() {
	
		List<Customer> allUsers = null;
        
        try {
            Session session = factory.openSession();
            
            CriteriaQuery<Customer> criteriaQuery =
            session.getCriteriaBuilder().createQuery(Customer.class);
            criteriaQuery.from(Customer.class);
            allUsers = session.createQuery(criteriaQuery).getResultList();
            
        }
        catch(Exception e) {
        
        }
        for(Customer h: allUsers) {
            System.out.println(h);
        }
}
	
	public void customerMenu(Customer customer) {
		
		String option;
		do {
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			System.out.println("            MAIN MENU");
			System.out.println("            "+ "GCU BANK");
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			System.out.println("Pick an option: ");
			System.out.println("-----------------------");
			System.out.println(" 1: : Deposit to Checking");
			System.out.println(" 2: : Deposit to Savings");
			System.out.println(" 3: : Withdraw from Checking");
			System.out.println(" 4: : Withdraw from Savings");			
			System.out.println(" 5: : Get balance");
			System.out.println(" 6: : Make Loan Payment");
			System.out.println(" 7: : Get monthly statement");
			System.out.println("------------------------");
			System.out.println(" 9: : Logout");
			option = scanner.nextLine();
			bank.actionMenu(Integer.parseInt(option), customer);
		} while (Integer.parseInt(option) != 9);
	}
	
	private void actionMenu(int option, Customer customer) {
        List<Account> accountList = null;
        Checking checking = new Checking();
        Saving saving = new Saving();
        Loan loan = new Loan();
        Account acct;
        int id = customer.getId();
        Session session = factory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Account> criteria = cb.createQuery(Account.class);
        Root<Account> groot = criteria.from(Account.class);
        criteria.select(groot);
        criteria.where(cb.equal(groot.get("cust_id"),id));
        accountList = session.createQuery(criteria).getResultList();
        
                switch(option) {
                case 1: criteria.where(cb.and(cb.equal(groot.get("cust_id"),id),cb.equal(groot.get("account_type"),
                							"checking")));
                    try {
                        acct = session.createQuery(criteria).getSingleResult();
                        System.out.println("How much would you like to deposit: ");
                        String amount = scanner.nextLine();
                        bd = new BigDecimal(amount);
                        checking.doDeposit(bd, acct);
                    }
                    catch (NoResultException e) {
                        System.out.println("You do not have a checking account");
                        bank.customerMenu(customer);
                    }
                    break;
                case 2:  criteria.where(cb.and(cb.equal(groot.get("cust_id"),id),cb.equal(groot.get("account_type"),
                								"saving")));
                    try {
                        acct = session.createQuery(criteria).getSingleResult();
                        System.out.println("How much would you like to deposit: ");
                        String amount = scanner.nextLine();
                        bd = new BigDecimal(amount);
                        saving.doDeposit(bd, acct);
                    }
                    catch (NoResultException e) {
                        System.out.println("You do not have a savings account");
                        bank.customerMenu(customer);
                    }
                    break;
                case 3:  criteria.where(cb.and(cb.equal(groot.get("cust_id"),id),cb.equal(groot.get("account_type"),
                								"checking")));
                    try {
                        acct = session.createQuery(criteria).getSingleResult();
                        System.out.println("How much would you like to withdraw: ");
                        String amount = scanner.nextLine();
                        bd = new BigDecimal(amount);
                        checking.doWithdraw(bd, acct);
                    }
                    catch (NoResultException e) {
                        System.out.println("You do not have a checking account");
                        bank.customerMenu(customer);
                    }
                    break;
                case 4:  criteria.where(cb.and(cb.equal(groot.get("cust_id"),id),cb.equal(groot.get("account_type"),
                													"saving")));
                    try {
                        acct = session.createQuery(criteria).getSingleResult();
                        System.out.println("How much would you like to withdraw: ");
                        String amount = scanner.nextLine();
                        bd = new BigDecimal(amount);
                        saving.doDeposit(bd, acct);
                    }
                    catch (NoResultException e) {
                        System.out.println("You do not have a savings account");
                        bank.customerMenu(customer);
                    }
                    break;
                case 5: for (Account a : accountList) {
                    System.out.println("Your account balance for " + a.getAccount_num() + " is " + 
                    					a.getAccount_balance());
                }
                case 6:  criteria.where(cb.and(cb.equal(groot.get("cust_id"),id),cb.equal(groot.get("account_type"),
                								"loan")));
                    try {
                        acct = session.createQuery(criteria).getSingleResult();
                        System.out.println("How much would you like to make a payment: ");
                        String amount = scanner.nextLine();
                        bd = new BigDecimal(amount);
                        loan.doWithdraw(bd, acct);
                    }
                    catch (NoResultException e) {
                        System.out.println("You do not have a loan");
                        bank.customerMenu(customer);
                    }
                    break;
                case 7: bank.doEndOfMonth(accountList);
                    break;
                case 9: System.out.println("User has logged out");
                    break;
                default: System.out.println("Wrong Entry");
                }        
    }	
		
		private void doEndOfMonth(List<Account> acct) {
			
	        Account checking = acct.stream().filter(x -> 
	        "checking".equals(x.getAccount_type())).findAny().orElse(null);
	        Account saving = acct.stream().filter(x -> "saving".equals(x.getAccount_type())).findAny().orElse(null);
	        Account loan = acct.stream().filter(x -> "loan".equals(x.getAccount_type())).findAny().orElse(null);
	       
	        if (checking != null) {
	            Session session = factory.openSession();
	            CriteriaBuilder cb = session.getCriteriaBuilder();
	            CriteriaQuery<BankTransaction> criteria = cb.createQuery(BankTransaction.class);
	            Root<BankTransaction> groot = criteria.from(BankTransaction.class);
	            criteria.select(groot);
	            criteria.where(cb.equal(groot.get("account_num"), checking.getAccount_num()));
	            List<BankTransaction> checkingTransactions = session.createQuery(criteria).getResultList();
	            for(BankTransaction trans : checkingTransactions) {
	                System.out.println(trans);
	            }
	        }
	        
	        if (saving != null) {
	            Saving.savingEndOfMonth(saving);
	            Session session = factory.openSession();
	            CriteriaBuilder cb = session.getCriteriaBuilder();
	            CriteriaQuery<BankTransaction> criteria = cb.createQuery(BankTransaction.class);
	            Root<BankTransaction> groot = criteria.from(BankTransaction.class);
	            criteria.select(groot);
	            criteria.where(cb.equal(groot.get("account_num"), saving.getAccount_num()));
	            List<BankTransaction> savingTransactions = session.createQuery(criteria).getResultList();
	            for(BankTransaction trans : savingTransactions) {
	                System.out.println(trans);
	            }
	        }
	        
	        if (loan != null) {
	        	Loan.loanEndOfMonth(loan);
	            Session session = factory.openSession();
	            CriteriaBuilder cb = session.getCriteriaBuilder();
	            CriteriaQuery<BankTransaction> criteria = cb.createQuery(BankTransaction.class);
	            Root<BankTransaction> groot = criteria.from(BankTransaction.class);
	            criteria.select(groot);
	            criteria.where(cb.equal(groot.get("account_num"), loan.getAccount_num()));
	            List<BankTransaction> loanTransactions = session.createQuery(criteria).getResultList();
	            for(BankTransaction trans : loanTransactions) {
	                System.out.println(trans);
	            }
	        }
	}
		
		public void createCustomer() {
			Customer user1 = new Customer();

			System.out.print("Enter first name: ");
			user1.setFirst_name(scanner.nextLine());
			System.out.print("Enter last name: ");
			user1.setLast_name(scanner.nextLine());
			System.out.print("Pick login name: ");
			user1.setUser_name(scanner.nextLine());
			System.out.print("Pick password: ");
			user1.setPassword(EncryptPassword.encrypt(scanner.nextLine()));
	
			Session session = factory.openSession();
			session.beginTransaction();
			session.save(user1);
			session.getTransaction().commit();
			
			bank.createAccount(user1.getId());			
		}

		public void createAccount(int cust_id) {
	        Account account = new Account();
	        BankTransaction trans = new BankTransaction();
	        account.setCust_id(cust_id);
	        String userInput = "Y";
	        Random rnd = new Random();
	        String accountNum;
	        String phrase = "";
	        int n;
	        
	        do {
	            System.out.print("What type of account would you like to create? 1 for Saving, "
	                    + "2 for Checking, 3 for Loan: ");
	            String option = scanner.nextLine();
	            int o = Integer.parseInt(option);
	            switch(o) {
	            case 1: account.setAccount_type("saving");
	            	n = 100000 + rnd.nextInt(900000);
	            	accountNum = cust_id + "-SAV" + n;
	            	account.setAccount_num(accountNum);
	            	phrase = "Enter the amount you would like to deposit: ";
	            break;
	            case 2: account.setAccount_type("checking");
	            	n = 100000 + rnd.nextInt(900000);
	            	accountNum = cust_id + "-CHK" + n;
	            	account.setAccount_num(accountNum);
	            	phrase = "Enter the amount you would like to deposit: ";
	            break;
	            case 3: account.setAccount_type("loan");
	            	n = 100000 + rnd.nextInt(900000);
	            	accountNum = cust_id + "-LOA" + n;
	            	account.setAccount_num(accountNum);
	            	phrase = "Enter the amount for the loan: ";
	            break;
	            default: System.out.println("You have entered an incorrect number... ");
	            
	            }
	        
	        System.out.println(phrase);
	        bd = new BigDecimal(scanner.nextLine());
	        account.setAccount_balance(bd);
	        trans = bank.createTransaction(account, bd, "initial deposit", bd);
	        
	        Session session = factory.openSession();
	        session.beginTransaction();
	        session.save(account);
	        session.save(trans);
	        session.getTransaction().commit();
	        
	        System.out.println("Would you like to add an account? Y or N");
	        userInput = scanner.nextLine().toUpperCase();
	        
	        }while(userInput.equals("Y"));
	    }
		
		public BankTransaction createTransaction(Account account, BigDecimal amount, String option, 
													BigDecimal balance) {
			BankTransaction trans = new BankTransaction();
			
			trans.setAccount_num(account.getAccount_num());
			trans.setAmount_trans(amount);
			trans.setTrans_type(option);
			trans.setBalance(balance);
			trans.setDate(LocalDate.now());
			
			return trans;
		}

		public void addEmployee() {
			
	        Employee emp = new Employee();
	        emp.setEmp_Id(1);
	        emp.setF_Name("Bill");
	        emp.setL_Name("Palowski");
	        emp.setUsername("palowski01");
	        emp.setPassword(EncryptPassword.encrypt("Tripp$ki2211"));
	        
	        Employee emp1 = new Employee();
	        emp1.setEmp_Id(2);
	        emp1.setF_Name("Eric");
	        emp1.setL_Name("Stoll");
	        emp1.setUsername("EStoll");
	        emp1.setPassword(EncryptPassword.encrypt("Java42"));
	        
	        Employee emp2 = new Employee();
	        emp2.setEmp_Id(3);
	        emp2.setF_Name("Christina");
	        emp2.setL_Name("Herman");
	        emp2.setUsername("KikiHerm");
	        emp2.setPassword(EncryptPassword.encrypt("!@#KikiHerm"));
	        
	        Session session = factory.openSession();
	        session.beginTransaction();
	        session.save(emp);
	        session.save(emp1);
	        session.save(emp2);
	        session.getTransaction().commit();          
	    }
		
		public void checkAdmin() {
			
			System.out.println("Enter Username: ");
			String userName = scanner.nextLine();
			System.out.println("Enter Password: ");
			String password = scanner.nextLine();
			
	        Employee emp = null;
	        Session session = factory.openSession();
	        CriteriaBuilder cb = session.getCriteriaBuilder();
	        CriteriaQuery<Employee> criteria = cb.createQuery(Employee.class);
	        Root<Employee> groot = criteria.from(Employee.class);
	        criteria.select(groot);
	        criteria.where(cb.and(cb.equal(groot.get("username"),userName),cb.equal(groot.get("password"),
	        		EncryptPassword.encrypt(password))));
	        try {
	        emp = session.createQuery(criteria).getSingleResult();
	        bank.adminMenu();
	        }
	        catch (NoResultException e) {
	        	System.out.println("Wrong username and password");
	        	bank.checkAdmin();
	        }      	        
	    }
		
		public void checkCustomer() {
	        
	        System.out.println("Enter user name: ");
	        String userName = scanner.nextLine();
	        System.out.println("Enter password: ");
	        String password = scanner.nextLine();
	        Customer customer = null;
	        Session session = factory.openSession();
	        CriteriaBuilder cb = session.getCriteriaBuilder();
	        CriteriaQuery<Customer> criteria = cb.createQuery(Customer.class);
	        Root<Customer> groot = criteria.from(Customer.class);
	        criteria.select(groot);
	        criteria.where(cb.and(cb.equal(groot.get("user_name"),userName),cb.equal(groot.get("password"),
	        		EncryptPassword.encrypt(password))));
	        try {
	        customer = session.createQuery(criteria).getSingleResult();
	        bank.customerMenu(customer);
	        }
	        catch (NoResultException e) {
	            System.out.println("Wrong username or password");
	            bank.checkCustomer();
	        }          
	    }
}
