package pl.com.mnemonic.editors;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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



import org.primefaces.event.SelectEvent;

import pl.com.mnemonic.entities.Bindrole;
import pl.com.mnemonic.entities.Car;
import pl.com.mnemonic.entities.Refueling;
import pl.com.mnemonic.entities.Rent;
import pl.com.mnemonic.entities.dao.BindroleDao;
import pl.com.mnemonic.entities.dao.CarDao;
import pl.com.mnemonic.entities.dao.RentDao;

@ManagedBean
@ViewScoped
public class RentEdit implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String PAGE_BIND_LIST = "/index?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	@EJB
	RentDao rentdao;
	@EJB
	CarDao cardao;
	@EJB
	BindroleDao bindroledao;
	
	private String idrent;
	private Bindrole bindrole;
	private Car car;
	private String attachment = "niach";
	private String dateFrom;
	private String dateTo;
	
	private List<Bindrole> users;
	private List<Car> cars;
	
	private List<Bindrole> comparedr;
	private List<Car> comparedc;
	
	private Map<Integer, Boolean> checkedr = new HashMap<Integer, Boolean>();
	private Map<Integer, Boolean> checkedc = new HashMap<Integer, Boolean>();
	
	private Date test1;
	private Date test2;
	
	/*SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	
	public void onDateSelect(SelectEvent event) {
        Date fromsel = null;
		try {
			fromsel = format.parse(event.getObject().toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }*/
	
	public Date getTest1() {		
		return test1;
	}

	public void setTest1(Date test1) {
		this.test1 = test1;
	}

	public Date getTest2() {
		return test2;
	}

	public void setTest2(Date test2) {
		this.test2 = test2;
	}

	public List<Bindrole> getUsers() {
		users = bindroledao.getFullList();
		return users;
	}

	public void setUsers(List<Bindrole> users) {
		this.users = users;
	}

	public List<Car> getCars() {
		cars = cardao.getList();
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	public List<Bindrole> getComparedr() {
		return comparedr;
	}

	public void setComparedr(List<Bindrole> comparedr) {
		this.comparedr = comparedr;
	}

	public List<Car> getComparedc() {
		return comparedc;
	}

	public void setComparedc(List<Car> comparedc) {
		this.comparedc = comparedc;
	}

	public Map<Integer, Boolean> getCheckedr() {
		return checkedr;
	}

	public void setCheckedr(Map<Integer, Boolean> checkedr) {
		this.checkedr = checkedr;
	}

	public Map<Integer, Boolean> getCheckedc() {
		return checkedc;
	}

	public void setCheckedc(Map<Integer, Boolean> checkedc) {
		this.checkedc = checkedc;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getIdrent() {
		return idrent;
	}

	public void setIdrent(String idrent) {
		this.idrent = idrent;
	}

	public Bindrole getBindrole() {
		return bindrole;
	}

	public void setBindrole(Bindrole bindrole) {
		this.bindrole = bindrole;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	private Rent rent = null;
	
	
	@PostConstruct
	public void postConstruct() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);

		rent = (Rent) session.getAttribute("rent");
		if (rent != null) {
			session.removeAttribute("rent");
		}
		if (rent == null) {
			HttpServletRequest req = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			idrent = req.getParameter("p");
			if (idrent != null) {
				Integer id = null;
				try {
					id = Integer.parseInt(idrent);
				} catch (NumberFormatException e) {
				}
				if (id != null) {
					rent = rentdao.find(id);
				}
			}
		}

		if (car != null && car.getIdcar() != null) {
			setIdrent(rent.getIdrent().toString());
			setBindrole(rent.getBindrole());
			//setCar(cardao.find(rent.getCar().getIdcar()));
			setCar(rent.getCar());
			//setDateFrom(rent.getDateFrom().toString());
			//setDateTorent.getDateTo().toString());
			setAttachment(rent.getAttachment());
			String dateFromString = new SimpleDateFormat("dd-MM-yyyy").format(rent.getDateFrom());
			setDateFrom(dateFromString);
			String dateToString = new SimpleDateFormat("dd-MM-yyyy").format(rent.getDateTo());
			setDateTo(dateToString);
		}

	}

	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;

		if (car == null) {
			if (comparedc != null && comparedc.size() > 0) {
				setCar(comparedc.get(0));
			} else {
				ctx.addMessage(null, new FacesMessage("Wybierz samochód"));
			}
		}
		if (bindrole ==null) {
			if (comparedr != null && comparedr.size() > 0) {
				setBindrole(comparedr.get(0));
			} else {
				ctx.addMessage(null, new FacesMessage("Wybierz uzytkownika"));
			}
		}
		
		/*
		Date dateF=null;
		try {
			dateF = new SimpleDateFormat("dd-MM-yyyy").parse(dateFrom);
		} catch (ParseException e) {
			ctx.addMessage(null, new FacesMessage(
					"OD DATY niepoprawny format daty (DD-MM-RRRR)"));
		}
		Date dateT = null;
		try {
			dateT = new SimpleDateFormat("dd-MM-yyyy").parse(dateTo);
		} catch (ParseException e) {
			ctx.addMessage(null, new FacesMessage(
					"DO DATY niepoprawny format daty (DD-MM-RRRR)"));
		}*/
		
		
		if (ctx.getMessageList().isEmpty()) {
			rent.setCar(car);
			rent.setBindrole(bindrole);
			rent.setDateFrom(test1);
			rent.setDateTo(test2);
			rent.setAttachment(attachment);
			

			result = true;
		}

		return result;
	}
	

	

	public String saveData() {
		if (rent == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("You can't even use simple system."));
			return PAGE_STAY_AT_THE_SAME;
		}

		if (!validate()) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(
							"like You think You're admin or something..."));
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (rent.getIdrent() == null) {
				rentdao.create(rent);

			} else {
				// existing record
				rentdao.merge(rent);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									"Your bosses phone number, please. You broke that. You'll pay."));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_BIND_LIST;
	}

	public String Back() {

		return PAGE_BIND_LIST;
	}

}
