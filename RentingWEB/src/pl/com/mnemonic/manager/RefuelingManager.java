package pl.com.mnemonic.manager;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import pl.com.mnemonic.entities.Bindrole;
import pl.com.mnemonic.entities.Car;
import pl.com.mnemonic.entities.Refueling;
import pl.com.mnemonic.entities.Rent;

@ManagedBean
@ViewScoped
public class RefuelingManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String PAGE_INDEX = "/index?faces-redirect=true";
	private static final String PAGE_REF_EDIT = "edit/ref?faces-redirect=true";
	
	public String newRefueling(Rent rent){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		Car car = rent.getCar();
		Bindrole user = rent.getBindrole();
		Refueling ref = new Refueling();
		ref.setCar(car);
		ref.setBindrole(user);
		session.setAttribute("ref", ref);
				return PAGE_REF_EDIT;
	}
	
}
