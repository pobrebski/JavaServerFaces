package pl.com.mnemonic.editors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pl.com.mnemonic.entities.Car;
import pl.com.mnemonic.entities.Refueling;
import pl.com.mnemonic.entities.Rent;
import pl.com.mnemonic.entities.dao.CarDao;
import pl.com.mnemonic.entities.dao.RefuelingDao;

@ManagedBean
@ViewScoped
public class CarEdit implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_CAR_LIST = "/index?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_BIND = "bind_car?faces-redirect=true";
	private static final String PAGE_MAIN_LIST = "/index?faces-redirect=true";

	@EJB
	CarDao cardao;
	@EJB
	RefuelingDao refdao;

	private String idcar;
	private String carname;
	private String carrmake;
	private String carregistrationyear;
	private String carplates;
	private String cartype;
	private String carcolor;
	private Boolean available = true;
	private Set<Refueling> refs = new HashSet<Refueling>(0);
	private Set<Rent> rents = new HashSet<Rent>(0);

	private float average;

	public float getAverage() {
		if (refs != null) {
			float kil = 0f;
			float lit = 0f;
			for (Refueling r : refs) {
				kil += r.getKilometers();
				lit += r.getLitre();
			}
			average = lit / kil * 100;

		} else {
			average = 0f;
		}

		return average;
	}

	public void setAverage(float average) {
		this.average = average;
	}

	public String getCarplates() {
		return carplates;
	}

	public void setCarplates(String carplates) {
		this.carplates = carplates;
	}

	public String getCartype() {
		return cartype;
	}

	public void setCartype(String cartype) {
		this.cartype = cartype;
	}

	public String getCarcolor() {
		return carcolor;
	}

	public void setCarcolor(String carcolor) {
		this.carcolor = carcolor;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	@SuppressWarnings("unchecked")
	public List<Refueling> getRefs() {
		refs = (Set<Refueling>) refdao.getCarRefs(car);
		return (List<Refueling>) refs;
	}

	public void setRefs(Set<Refueling> set) {
		this.refs = set;
	}

	public String getCarname() {
		return carname;
	}

	public void setCarname(String carname) {
		this.carname = carname;
	}

	public String getCarrmake() {
		return carrmake;
	}

	public void setCarrmake(String carrmake) {
		this.carrmake = carrmake;
	}

	public String getCarregistrationyear() {
		return carregistrationyear + "";
	}

	public void setCarregistrationyear(String carregistrationyear) {
		// int idcarset = Integer.parseInt(carregistrationyear);
		this.carregistrationyear = carregistrationyear;
	}

	@SuppressWarnings("unchecked")
	public List<Rent> getRents() {
		return (List<Rent>) rents;
	}

	public void setRents(Set<Rent> set) {
		this.rents = (Set<Rent>) set;
	}

	private Car car = null;


	@PostConstruct
	public void postConstruct() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);

		car = (Car) session.getAttribute("car");
		if (car != null) {
			session.removeAttribute("car");
		}
		if (car == null) {
			HttpServletRequest req = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			idcar = req.getParameter("p");
			if (idcar != null) {
				Integer id = null;
				try {
					id = Integer.parseInt(idcar);
				} catch (NumberFormatException e) {
				}
				if (id != null) {
					car = cardao.find(id);
				}
			}
		}

		if (car != null && car.getIdcar() != null) {
			setCarname(car.getCarname());
			setCarrmake(car.getCarrmake());
			setCarregistrationyear(car.getCarregistrationyear().toString());
			setCarplates(car.getCarplates());
			setCarcolor(car.getCarcolor());
			setCartype(car.getCartype());
			setAvailable(car.getAvailable());
			setRents(car.getRents());
			setRefs(car.getRefuelings());

		}

	}

	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;

		if (carname == null || carname.trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage("nazwa wymagana"));
		}
		if (carrmake == null || carrmake.trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage("marka wymagana"));
		}
		if (carplates == null || carplates.trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage(
					"numer rejestraczjny wymagany"));
		}
		if (carcolor == null || carcolor.trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage("kolor wymagany"));
		}

		if (carregistrationyear == null
				|| carregistrationyear.trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage("kolor wymagany"));
		} else {
			int date = -1;
			try {
				date = Integer.valueOf(carregistrationyear);
			} catch (Exception e) {
				ctx.addMessage(null, new FacesMessage(
						"niepoprawny format datnych"));
			}
		}

		if (ctx.getMessageList().isEmpty()) {
			car.setCarname(carname.trim());
			car.setCarrmake(carrmake.trim());
			car.setCarregistrationyear(Integer.parseInt(carregistrationyear));
			car.setCarcolor(carcolor);
			car.setCartype(cartype);
			car.setCarplates(carplates);
			car.setAvailable(available);
			car.setRents((Set<Rent>) rents);
			car.setRefuelings((Set<Refueling>) refs);
			result = true;
		}

		return result;
	}

	public String saveData() {
		if (car == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("CAN'T ADD STOLEN CAR - NULL!"));
			return PAGE_STAY_AT_THE_SAME;
		}

		if (!validate()) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (car.getIdcar() ==null) {
				cardao.create(car);
			} else {
				cardao.merge(car);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("YOU BROKE IT. AGAIN."));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_CAR_LIST;

	}

	public String saveBindData() {

		if (car == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("B³¹d. Wiel B³¹d."));
			return PAGE_STAY_AT_THE_SAME;
		}

		if (!validate()) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (car.getIdcar() < 0) {

				// new record
				// bind = new Rent();
				// bind.setCar(car);
				cardao.create(car);

			} else {
				// existing record
				cardao.merge(car);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Buont podczas zapisu"));
			return PAGE_STAY_AT_THE_SAME;
		}

		Map<String, Object> searchParams = new HashMap<String, Object>();
		if (carrmake != null && carrmake.length() > 0) {
			searchParams.put("carrmake", carrmake);
		}

		List<Car> carzzzz = cardao.getList(searchParams);
		car = carzzzz.get(carzzzz.size()-1);

		// Rent.setCaruser(user);
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		Rent bind = new Rent();
		bind.setCar(car);
		session.setAttribute("bind", bind);

		return PAGE_BIND;
	}

}
