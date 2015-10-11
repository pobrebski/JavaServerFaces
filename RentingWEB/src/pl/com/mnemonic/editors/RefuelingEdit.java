package pl.com.mnemonic.editors;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pl.com.mnemonic.entities.Bindrole;
import pl.com.mnemonic.entities.Car;
import pl.com.mnemonic.entities.Refueling;
import pl.com.mnemonic.entities.dao.BindroleDao;
import pl.com.mnemonic.entities.dao.CarDao;
import pl.com.mnemonic.entities.dao.RefuelingDao;



@ManagedBean
@ViewScoped
public class RefuelingEdit implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final String PAGE_BIND_LIST = "/index?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	
	@EJB
	RefuelingDao refdao;
	@EJB
	CarDao cardao;
	@EJB
	BindroleDao userdao;
	

	private Refueling ref = null;
	private String idrefueling;
	private float litre;
	private float kilometers;
	private Date dateref;
	private Car car;
	private Bindrole user;
	private List<Refueling> cars;
	private List<Refueling> users;
	
	
	public String getIdrefueling() {
		return idrefueling;
	}


	public void setIdrefueling(String idrefueling) {
		this.idrefueling = idrefueling;
	}


	public Date getDateref() {
		return dateref;
	}


	public void setDateref(Date dateref) {
		this.dateref = dateref;
	}


	public List<Refueling> getCars() {
		cars = refdao.getCarRefs(car);
		return cars;
	}


	public void setCars(List<Refueling> cars) {
		this.cars = cars;
	}


	public List<Refueling> getUsers() {
		users = refdao.getUserRefs(user);
		return users;
	}


	public void setUsers(List<Refueling> users) {
		this.users = users;
	}


	public float getLitre() {
		return litre;
	}


	public void setLitre(float litre) {
		this.litre = litre;
	}


	public float getKilometers() {
		return kilometers;
	}


	public void setKilometers(float kilometers) {
		this.kilometers = kilometers;
	}



	public Car getCar() {
		return car;
	}


	public void setCar(Car car) {
		this.car = car;
	}


	public Bindrole getBindrole() {
		return user;
	}


	public void setBindrole(Bindrole user) {
		this.user = user;
	}


	@PostConstruct
	public void postConstruct(){
		
	
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		
			ref = (Refueling) session.getAttribute("ref");
			if (ref != null) {
				session.removeAttribute("ref");			
			}
			
			if (ref == null) {
					HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
					idrefueling = req.getParameter("rf");
					if (ref != null) {

						Integer id=null;
						try {
							id = Integer.parseInt(idrefueling);
						} catch (NumberFormatException e) {
						}
						if (id!=null) {
							ref = refdao.find(id);
						}
						
					}

				}
				
		
				if (ref != null) {
					setIdrefueling(ref.getIdrefueling()+"");
					setCar(ref.getCar());
					setBindrole(ref.getBindrole());
					if(ref.getIdrefueling()!=null){
					setLitre(ref.getLitre());
					setKilometers(ref.getKilometers());
					setDateref((Date) ref.getDate());}
				}
						
							
	}
		
	
	private boolean validate() {
		
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;
		
		
		if (user == null) {
			ctx.addMessage(null, new FacesMessage("user required"));
		}
			
		if(car == null){ctx.addMessage(null, new FacesMessage("car required"));}
		
		if (litre <=0) {
			ctx.addMessage(null, new FacesMessage("litry"));
		}
		
		if (kilometers <=0) {
			ctx.addMessage(null, new FacesMessage("kilometry to gdzie?"));
		}
	
		if (ctx.getMessageList().isEmpty()) {
			ref.setCar(car);
			ref.setBindrole(user);
			ref.setKilometers(kilometers);
			ref.setLitre(litre);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String date = sdf.format(new Date()); 
			try {
				ref.setDate(sdf.parse(date));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result = true;
		}

		return result;
	}

	
	public String saveData() {
		if (ref == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("bledne uzycie systemu"));
			return PAGE_STAY_AT_THE_SAME;
		}

		if (!validate()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("rak walidacji"));
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (ref.getIdrefueling()==null) {
				
				refdao.create(ref);
				
			} else {
				refdao.merge(ref);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("blad podczas zapisu"));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_BIND_LIST;
	}
	
	
	public String Back(){
		
		return PAGE_BIND_LIST;
	}
	
	}

	
