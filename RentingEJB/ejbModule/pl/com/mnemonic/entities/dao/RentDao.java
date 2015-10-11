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
public class RentDao {
	private final static String UNIT_NAME = "rentingBase";
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager eb;
	CarDao cardao;
	Car car;
	
	public void create(Rent Rent) {
		eb.persist(Rent);
		// em.flush();
	}

	public Rent merge(Rent Rent) {
		return eb.merge(Rent);
	}

	public void remove(Rent Rent) {
		eb.remove(eb.merge(Rent));
	}

	public Rent find(Object id) {
		return eb.find(Rent.class, id);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Rent> getList() {
		List<Rent> list = null;
		
		
		Query query = eb.createQuery("SELECT s FROM Rent s");
		try {
			list = query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		/*

		try {
			list = query.getResultList();
			
			Map<Rent, Car> mapa = new HashMap<Rent, Car>();
			for(int i = 0; i<list.size(); i++){
				mapa.put((list.get(i)), car);
			}
			
			for(int i = 0; i<list.size();i++){
				list.get(i).setCar(cardao.getList().get(i));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			*/
		return list;
	}
	
	
	
	public List<Rent> getgroupList() {
		List<Rent> list = null;
		
		Query query = eb.createQuery("SELECT s FROM Rent s group by Bindrole");
		try {
			list = query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		} return list;
		}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<Rent> getListCar(Map<Car, Rent> search) {
		List<Rent> list = null;

		
		String select = "select s ";
		String from = "from Rent s ";
		String where = "";
		String orderby = "order by s.Bindrole asc, s.car";
		
	
		
		Rent car = (Rent) search.get("car");
		if (car != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "s.car like :car ";
		}
		
		Query query = eb.createQuery(select + from + where + orderby);

		if (car != null) {
			query.setParameter("car", car+"%");
		}

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Rent> getListBindrole(Map<Bindrole, Rent> search) {
		List<Rent> list = null;

		String select = "select s ";
		String from = "from Rent s ";
		String where = "";
		String orderby = "order by s.car asc, s.Bindrole";
		
	
		Rent user = (Rent) search.get("Bindrole");
		if (user != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "s.Bindrole like :user ";
		}
		
	
		Query query = eb.createQuery(select + from + where + orderby);

		if (user != null) {
			query.setParameter("Bindrole", user+"%");
		}

	
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	
	
	
	/*
	public List<Rent> getListUser(Map<String, Object> searchParams) {
		List<Rent> list = null;

		String select = "select b ";
		String from = "from Rent b ";
		String where = "";
		String orderby = "order by b.Car asc, b.Bindrole";

	Bindrole user = (Bindrole) searchParams.get("Bindrole");
		if (user != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "b.Bindrole like :Bindrole ";
		}
		
		
		Query query = eb.createQuery(select + from + where + orderby);

		if (user != null) {
			query.setParameter("Bindrole", user+"%");
		}

		
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public List<Rent> getListCars(Map<String, Object> searchParams) {
		List<Rent> list = null;

	
		String select = "select p ";
		String from = "from Rent p ";
		String where = "";
		String orderby = "order by p.idRent asc, p.Bindrole";

	/*	String carrmake = (String) searchParams.get("carrmake");
		if (carrmake != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "p.carrmake like :carrmake ";
		}*/
		

	/*	Query query = eb.createQuery(select + from + where + orderby);

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
	
	
	*/
	
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<Rent> getUserCars(Bindrole user) {
		List<Rent> list = null;

		
		String select = "select s ";
		String from = "from Rent s ";
		String where = "";
		String orderby = "order by s.car asc, s.Bindrole";
		
	
		
		if (user !=null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "s.Bindrole like :user ";
		}
		
		Query query = eb.createQuery(select + from + where + orderby);

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
	public List<Rent> getBindroles(Car car) {
		List<Rent> list = null;

		String select = "select s ";
		String from = "from Rent s ";
		String where = "";
		String orderby = "order by s.car asc, s.Bindrole";
		
	
		if (car !=null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "s.car like :car ";
		}
	
		Query query = eb.createQuery(select + from + where + orderby);

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
