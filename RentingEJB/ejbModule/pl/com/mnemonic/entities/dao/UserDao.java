package pl.com.mnemonic.entities.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pl.com.mnemonic.entities.Rent;
import pl.com.mnemonic.entities.User;

@Stateless
public class UserDao {
	private final static String UNIT_NAME = "rentingBase";
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;		
	public void create(User user) {
		em.persist(user);
		// em.flush();
	}

	public User merge(User user) {
		return em.merge(user);
	}

	public void remove(User user) {
		em.remove(em.merge(user));
	}
	public User find(Object id) {
		return em.find(User.class, id);
	}
	
	public List<User> getList(){
		List<User> userList = null;
		Query query = em.createQuery("select u from User u");
		try{
			userList = query.getResultList();
		}
		catch(Exception x){x.printStackTrace();}
		return userList;
				
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getNewList() {
		List<User> list = null;
		
		Query query = em.createQuery("SELECT s FROM User s");
		try {
			list = (List) query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return list;
	}
	
	
	
	
}
