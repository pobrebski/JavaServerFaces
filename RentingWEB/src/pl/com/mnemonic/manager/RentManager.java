package pl.com.mnemonic.manager;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import pl.com.mnemonic.entities.Car;
import pl.com.mnemonic.entities.Rent;
import pl.com.mnemonic.entities.dao.RentDao;

@ManagedBean
@ViewScoped
public class RentManager implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String PAGE_MAIN = "index?faces-redirect=true";
	private static final String PAGE_RENT_LIST = "/edit/admin/views/rents?faces-redirect=true";
	private static final String PAGE_RENT_EDIT = "/edit/admin/rent?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_STATS = "stats?faces-redirect=true";
	@EJB
	RentDao rentdao;
	//public List<Carbind> getList(){return bindao.getList();}
	private List<Rent> list;	
	
	
	public List<Rent> getList() {
		list = rentdao.getgroupList();

		
		return list;
	}
	

	

	public void setList(List<Rent> list) {
		this.list = list;
	}



	public String newRent(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		Rent rent = new Rent();
		session.setAttribute("rent", rent);
				return PAGE_RENT_EDIT;
	}
	
	public String deleteRent(Rent rent){
		rentdao.remove(rent);
		return PAGE_MAIN;
	}
	
	public String BindStats(Rent rent){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		session.setAttribute("rent", rent);
		return PAGE_STATS;
	}
	public String viewRentList(){
		return PAGE_RENT_LIST;
	}

}
