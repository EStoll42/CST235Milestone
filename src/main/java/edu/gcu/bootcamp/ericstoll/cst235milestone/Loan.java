package edu.gcu.bootcamp.ericstoll.cst235milestone;

import java.math.BigDecimal;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Loan extends Account implements Withdraw{
	private static BigDecimal lateFee = new BigDecimal(25.00); 
	private static BigDecimal INTERESTRATELOAN = new BigDecimal(.09); 
	private static BigDecimal divisor = new BigDecimal(12);
	private static BigDecimal interestPerMonth = new BigDecimal(.0075);
	private static boolean paymentMade = false; 
	
	static BankTransaction trans = new BankTransaction();
	static Bank bank = new Bank();
	static SessionFactory factory = new Configuration().configure().buildSessionFactory();


	public boolean isPaymentMade() {
		return paymentMade;
	}

	public void setPaymentMade(boolean paymentMade) {
		this.paymentMade = paymentMade;
	}

	public BigDecimal getLateFee() {
		return lateFee;
	}

	public void setLateFee(BigDecimal lateFee) {
		this.lateFee = lateFee;
	}

	public BigDecimal getINTERESTRATELOAN() {
		return INTERESTRATELOAN;
	}

	public void setINTERESTRATELOAN(BigDecimal iNTERESTRATELOAN) {
		INTERESTRATELOAN = iNTERESTRATELOAN;
	}
	
	public void doWithdraw(BigDecimal amount, Account account) {
			setPaymentMade(true); 
			BigDecimal result = account.getAccount_balance().subtract(amount);
			account.setAccount_balance(result);
			trans = bank.createTransaction(account, amount, "Withdraw", result);
	
			Session session = factory.openSession();
			session.beginTransaction();
			session.save(trans);
			session.saveOrUpdate(account);
			session.getTransaction().commit();	
			
			Transaction tx = session.beginTransaction();
            CriteriaBuilder bob = session.getCriteriaBuilder();
            CriteriaUpdate<Account> criteria = bob.createCriteriaUpdate(Account.class);
            Root<Account> groot = criteria.from(Account.class);
            criteria.set(groot.get("account_balance"), account.getAccount_balance());
            criteria.where(bob.equal(groot.get("account_num"), account.getAccount_num()));
            session.createQuery(criteria).executeUpdate();
            tx.commit();
		}
	
	public static void loanEndOfMonth(Account account) {
			
			if(paymentMade = false) {
				BigDecimal interest = account.getAccount_balance().multiply(interestPerMonth);
				account.setAccount_balance(interest.add(lateFee).add(account.getAccount_balance()));
				trans = bank.createTransaction(account, lateFee, "Late Fee", account.getAccount_balance());
				
				Session session = factory.openSession();
				session.beginTransaction();
				session.save(trans);
				session.saveOrUpdate(account);
				session.getTransaction().commit();
			}else {

				BigDecimal interest = account.getAccount_balance().multiply(interestPerMonth);
				account.setAccount_balance(interest.add(account.getAccount_balance()));
				trans = bank.createTransaction(account, interest, "interest added", account.getAccount_balance());

				Session session = factory.openSession();
				session.beginTransaction();
				session.save(trans);
				session.saveOrUpdate(account);
				session.getTransaction().commit();
			}
		}
	}