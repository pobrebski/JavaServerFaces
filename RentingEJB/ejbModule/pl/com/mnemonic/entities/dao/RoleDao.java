package pl.com.mnemonic.entities.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pl.com.mnemonic.entities.Role;

@Stateless
public class RoleDao {
	private final static String UNIT_NAME = "rentingBase";
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;		
	public void create(Role role) {
		em.persist(role);
		// em.flush();
	}

	public Role merge(Role role) {
		return em.merge(role);
	}

	public void remove(Role role) {
		em.remove(em.merge(role));
	}
	public Role find(Object id) {
		return em.find(Role.class, id);
	}
	

	public List<Role> getList() {
		List<Role> list = null;
		Query query = em.createQuery("select r from Role r");

		try {
			list = query.getResultList();}
		catch (Exception e) {
		e.printStackTrace();
			}
		
		return list;
		}
}
