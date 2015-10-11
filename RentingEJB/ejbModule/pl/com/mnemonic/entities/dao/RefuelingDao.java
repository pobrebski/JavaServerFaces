package pl.com.mnemonic.entities.dao;

import java.util.List;






import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pl.com.mnemonic.entities.Bindrole;
import pl.com.mnemonic.entities.Car;
import pl.com.mnemonic.entities.Refueling;
import pl.com.mnemonic.entities.User;



@Stateless
public class RefuelingDao {
	private final static String UNIT_NAME = "rentingBase";
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Refueling ref) {
		em.persist(ref);
	}

	public Refueling merge(Refueling ref) {
		return em.merge(ref);
	}

	public void remove(Refueling ref) {
		em.remove(em.merge((ref)));
	}

	public Refueling find(Object a) {
		return em.find(Refueling.class, a);
	}

	public List<Refueling> getFullList() {
		List<Refueling> list = null;
		Query qu = em.createQuery("select r from Refueling r");

		try {
			list = qu.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	/*public List<Refueling> getList(Map<String, Object> searchParams){
		List<Refueling> reflist=null;
		String select = "select r ";
		String from = "from Refueling r ";
		String where = "";
		String orderby = "order by r.date asc, r.litre desc";

		String surname = (String) searchParams.get("date");
		if (date != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.surname like :surname ";
		}
		
		
		Query query = em.createQuery(select + from + where + orderby);

		if (surname != null) {
			query.setParameter("surname", surname+"%");
		}

		
		try {
			userlist = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userlist;
	}

	*/
	
	
	@SuppressWarnings("unchecked")
	public List<Refueling> getUserRefs(Bindrole user) {
		List<Refueling> list = null;

		String select = "select s ";
		String from = "from Refueling s ";
		String where = "";
		String orderby = "order by s.car asc, s.user";
		
	
		if (user !=null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "s.user like :user ";
		}
		
	
		Query query = em.createQuery(select + from + where + orderby);

	
		if (user!=null) {
			query.setParameter("user", user);
		}

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Refueling> getCarRefs(Car car) {
		List<Refueling> list = null;

		String select = "select s ";
		String from = "from Refueling s ";
		String where = "";
		String orderby = "order by s.car asc, s.user";
	
		if (car !=null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "s.car like :car ";
		}
		
	
		Query query = em.createQuery(select + from + where + orderby);

		if (car!=null) {
			query.setParameter("car", car);
		}

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}


}
