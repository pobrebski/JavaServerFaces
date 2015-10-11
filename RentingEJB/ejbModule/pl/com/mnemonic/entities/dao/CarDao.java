package pl.com.mnemonic.entities.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import pl.com.mnemonic.entities.Car;
import pl.com.mnemonic.entities.Rent;



@Stateless
public class CarDao {
	
	private final static String UNIT_NAME = "rentingBase";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;
		
	public void create(Car car) {
		em.persist(car);
		// em.flush();
	}

	public Car merge(Car car) {
		return em.merge(car);
	}

	public void remove(Car car) {
		em.remove(em.merge(car));
	}

	public Car find(int a) {
		
		return em.find(Car.class, a);
	}
	
	public Car carById(int a){
		Car car = null;
		Query query = em.createQuery("select p from Car p where idcar="+a+";");
		try{
			car = (Car) query.getSingleResult();
		}catch(Exception e){e.printStackTrace();}
		return car;
	}
	
	
	public List<Car> getList() {
		List<Car> list = null;
		Query query = em.createQuery("select p from Car p");

		try {
			list = query.getResultList();}
		catch (Exception e) {
		e.printStackTrace();
			}
		
		return list;
		}
	
	
	public List<Car> getFullList() {
		List<Car> list = null;

		Query query = em.createQuery("select p from Car p");
		try {
			list = query.getResultList();
	
			Rent rent = null;
			Map<Car,Rent> searchParams = new HashMap<Car, Rent>();
			for(int i=0; i<list.size(); i++){
				searchParams.put((list.get(i)), rent);
				}
			
			for(int i=0; i<list.size(); i++){
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Car> getList(Map<String, Object> searchParams) {
		List<Car> list = null;

		String select = "select p ";
		String from = "from Car p ";
		String where = "";
		String orderby = "order by p.carrmake asc, p.carname";

		String carrmake = (String) searchParams.get("carrmake");
		if (carrmake != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "p.carrmake like :carrmake ";
		}
		

		Query query = em.createQuery(select + from + where + orderby);

		if (carrmake != null) {
			query.setParameter("carrmake", carrmake+"%");
		}

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
}