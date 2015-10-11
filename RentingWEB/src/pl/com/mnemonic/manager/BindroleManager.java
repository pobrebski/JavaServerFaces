package pl.com.mnemonic.manager;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import pl.com.mnemonic.entities.Bindrole;
import pl.com.mnemonic.entities.Role;
import pl.com.mnemonic.entities.User;
import pl.com.mnemonic.entities.dao.BindroleDao;
import pl.com.mnemonic.entities.dao.RoleDao;
import pl.com.mnemonic.entities.dao.UserDao;


@ManagedBean
@SessionScoped
public class BindroleManager implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String PAGE_MAIN = "/index?faces-redirect=true";
	private static final String PAGE_BIND_EDIT = "/edit/admin/bindrole?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_STATS = "/stats?faces-redirect=true";
	@EJB
	BindroleDao bindao;
	@EJB
	UserDao userdao;
	@EJB
	RoleDao roledao;

	private List<Bindrole> list;	
	private List<User> users;
	private List<Role> roles;
	
	
	public List<User> getUsers() {
		users = userdao.getList();
		return users;
	}




	public void setUsers(List<User> users) {
		this.users = users;
	}




	public List<Role> getRoles() {
		roles = roledao.getList();
		return roles;
	}




	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}




	public List<Bindrole> getList() {
		list = bindao.getFullList();		
		return list;
	}
	

	

	public void setList(List<Bindrole> list) {
		this.list = list;
	}



	public String newBind(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		Bindrole bindr = new Bindrole();
		session.setAttribute("bindr", bindr);
				return PAGE_BIND_EDIT;
	}
	
	public String deleteBind(Bindrole bindr){
		bindao.remove(bindr);
		return PAGE_MAIN;
	}
	
	public String BindStats(Bindrole bindr){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		session.setAttribute("bindr", bindr);
		return PAGE_STATS;
	}

}
