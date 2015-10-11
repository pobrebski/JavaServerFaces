package pl.com.mnemonic.manager;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import pl.com.mnemonic.entities.Role;
import pl.com.mnemonic.entities.dao.RoleDao;


@ManagedBean
@ViewScoped
public class RoleManager implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String PAGE_ROLE_EDIT = "/edit/admin/role?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_INDEX = "index?faces-redirect=true";

	@EJB
	RoleDao roledao;
	
	private List<Role> list;
	
	public void setList(List<Role> list) {
		this.list = list;
	}

	public List<Role> getList(){
		
		return roledao.getList();	
	}
	
	public String newRole(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		Role role = new Role();
		session.setAttribute("rolenew", role);
		return PAGE_ROLE_EDIT;
	}
	
	/*	BIND USERA
	public String addCarBind(Caruser user){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		
		session.setAttribute("user", user);
		return PAGE_BIND;
	}*/
	
	
	public String editRole(Role role){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		session.setAttribute("role", role);
		return PAGE_ROLE_EDIT;
	}
	
	public String deleteRole(Role role){
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
		roledao.remove(role);
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
	
}
