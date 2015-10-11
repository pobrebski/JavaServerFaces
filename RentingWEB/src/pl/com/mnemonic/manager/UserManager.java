package pl.com.mnemonic.manager;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import pl.com.mnemonic.entities.Bindrole;
import pl.com.mnemonic.entities.Role;
import pl.com.mnemonic.entities.User;
import pl.com.mnemonic.entities.dao.RoleDao;
import pl.com.mnemonic.entities.dao.UserDao;

@ManagedBean
@RequestScoped
public class UserManager implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String PAGE_USER_EDIT = "edit/user?faces-redirect=true";
	private static final String PAGE_USER_LIST = "/edit/admin/views/users?faces-redirect=true";
	private static final String PAGE_SYSUSER_EDIT = "edit/bindrole?faces-redirect=true";
	private static final String PAGE_AUSER_LIST = "/users?faces-redirect=true";
	private static final String PAGE_AdUSER_LIST = "/edit/admin/editusers?faces-redirect=true";
	private static final String PAGE_AdUSER_EDIT = "/edit/admin/user?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_INDEX = "index?faces-redirect=true";

	@EJB
	UserDao userdao;
	
	
	
	public List<User> getList(){
		
		return userdao.getList();	
	}
	
	public String newUser(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		User user = new User();
		session.setAttribute("usernew", user);
		return PAGE_AdUSER_EDIT;
	}
	
	public String newSystemUser(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		Bindrole sysuser = new Bindrole();
		session.setAttribute("sysusernew", sysuser);
		return PAGE_SYSUSER_EDIT;
	}
	
	/*	BIND USERA
	public String addCarBind(Caruser user){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		
		session.setAttribute("user", user);
		return PAGE_BIND;
	}*/
	
	
	public String editUser(User user){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		session.setAttribute("usernew", user);
		return PAGE_USER_EDIT;
	}
	
	public String deleteUser(User user){
	/* 	ZNAJDZ POWIAZANIA I USUN ZALEZNOSCI
		usbinds = bindao.getCarUsers(car);
		if(usbinds!=null){
		for(Carbind bind : usbinds){
			bindao.remove(bind);
		}
		refs = refdao.getCarRefs(car);
		for(Refueling re:refs){
			refdao.remove(re);
		}}*/
		userdao.remove(user);
		return PAGE_STAY_AT_THE_SAME;
	}
	
	/* TO MI PRZYPISYWA£O DO CARBIND REFUELING 
	public String newAssignement(Bindrole bindrole){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		Role role = bindrole.getRole();
		User user = bindrole.getUser();
		Refueling ref = new Refueling();
		ref.setCar(car);
		ref.setCaruser(user);
		session.setAttribute("ref", ref);
				return PAGE_REF_EDIT;
	}
	*/
	
	public String viewUserList(){
		return PAGE_USER_LIST;
	}
	
	public String viewAdminUserList(){
		return PAGE_AUSER_LIST;
	}
	
	public String viewAdminEditUserList(){
		return PAGE_AdUSER_LIST;
	}
}
