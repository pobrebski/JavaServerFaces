package pl.com.mnemonic.entities.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pl.com.mnemonic.entities.Bindrole;
import pl.com.mnemonic.entities.Car;
import pl.com.mnemonic.entities.Rent;

@Stateless
public class BindroleDao {
	private final static String UNIT_NAME = "rentingBase";
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;
	RentDao bindao;
	Rent bind;

	public void create(Bindrole Bindrole) {
		em.persist(Bindrole);
	}

	public Bindrole merge(Bindrole Bindrole) {
		return em.merge(Bindrole);
	}

	public void remove(Bindrole Bindrole) {
		// em.remove(Bindrole);
		em.remove(em.contains(Bindrole) ? Bindrole : em.merge(Bindrole));
	}

	public Bindrole find(int a) {
		return em.find(Bindrole.class, a);
	}

	public List<Bindrole> getFullList() {
		List<Bindrole> list = null;
		Query qu = em.createQuery("select b from Bindrole b");

		try {
			list = qu.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Bindrole> getList(Map<String, Object> searchParams) {
		List<Bindrole> Bindrolelist = null;
		String select = "select p ";
		String from = "from Bindrole p ";
		String where = "";
		String orderby = "order by p.surname asc, p.idBindrole";

		String surname = (String) searchParams.get("surname");
		if (surname != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "p.surname like :surname ";
		}

		Query query = em.createQuery(select + from + where + orderby);

		if (surname != null) {
			query.setParameter("surname", surname + "%");
		}

		try {
			Bindrolelist = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Bindrolelist;
	}

	public List<Bindrole> getListBindrole() {
		List<Bindrole> list = null;

		List<Rent> binds = null;

		Query query = em.createQuery("select p from Bindrole p");

		try {
			list = query.getResultList();

			Map<Bindrole, Rent> searchParams = new HashMap<Bindrole, Rent>();

			for (int i = 0; i < list.size(); i++) {
				searchParams.put((list.get(i)), bind);

			}

			for (int i = 0; i < list.size(); i++) {

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Bindrole> getList() {
		List<Bindrole> list = null;

		Query query = em.createQuery("SELECT s FROM Rent s");
		try {
			list = query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return list;
	}

}
