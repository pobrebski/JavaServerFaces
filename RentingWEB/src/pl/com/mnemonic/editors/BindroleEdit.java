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

import pl.com.mnemonic.entities.Bindrole;
import pl.com.mnemonic.entities.Refueling;
import pl.com.mnemonic.entities.Rent;
import pl.com.mnemonic.entities.Role;
import pl.com.mnemonic.entities.User;
import pl.com.mnemonic.entities.dao.BindroleDao;
import pl.com.mnemonic.entities.dao.RoleDao;
import pl.com.mnemonic.entities.dao.UserDao;

@ManagedBean
@ViewScoped
public class BindroleEdit implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final String PAGE_BIND_LIST = "/index?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	
	@EJB
	BindroleDao bindao;
	@EJB
	UserDao userdao;
	@EJB
	RoleDao roledao;
		
	
	private String idbindrole;
	private User user;
	private Role role;
	private Set<Refueling> refuelings = new HashSet<Refueling>(0);
	private Set<Rent> rents = new HashSet<Rent>(0);
	public String getIdbindrole() {
		return idbindrole;
	}
	public void setIdbindrole(String idbindrole) {
		this.idbindrole = idbindrole;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Set<Refueling> getRefuelings() {
		return refuelings;
	}
	public void setRefuelings(Set<Refueling> refuelings) {
		this.refuelings = refuelings;
	}
	public Set<Rent> getRents() {
		return rents;
	}
	public void setRents(Set<Rent> rents) {
		this.rents = rents;
	}
	
	private List<User> users;
	private List<Role> roles;
	
	private List<User> comparedu;
	private List<Role> comparedr;
	
	private Map<Integer, Boolean> checkedu = new HashMap<Integer, Boolean>();
	private Map<Integer, Boolean> checkedr = new HashMap<Integer, Boolean>();
	
	
	public Map<Integer, Boolean> getCheckedu() {
		return checkedu;
	}
	public void setCheckedu(Map<Integer, Boolean> checkedu) {
		this.checkedu = checkedu;
	}
	public Map<Integer, Boolean> getCheckedr() {
		return checkedr;
	}
	public void setCheckedr(Map<Integer, Boolean> checkedr) {
		this.checkedr = checkedr;
	}
	public List<User> getComparedu() {
		return comparedu;
	}
	public void setComparedu(List<User> comparedu) {
		this.comparedu = comparedu;
	}
	public List<Role> getComparedr() {
		return comparedr;
	}
	public void setComparedr(List<Role> comparedr) {
		this.comparedr = comparedr;
	}
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

	private Bindrole bindrole;

	@PostConstruct
	public void postConstruct() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);

		bindrole = (Bindrole) session.getAttribute("bindr");
		if (bindrole != null) {
			session.removeAttribute("bindr");
		}
		if (bindrole == null) {
			HttpServletRequest req = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			idbindrole = req.getParameter("p");
			if (idbindrole != null) {
				Integer id = null;
				try {
					id = Integer.parseInt(idbindrole);
				} catch (NumberFormatException e) {
				}
				if (id != null) {
					bindrole = bindao.find(id);
				}
			}
		}

		if (bindrole != null && bindrole.getIdbindrole() != null) {
			setUser(bindrole.getUser());
			setRole(bindrole.getRole());
			setRents(bindrole.getRents());
			setRefuelings(bindrole.getRefuelings());
			setIdbindrole(bindrole.getIdbindrole().toString());
		}

	}

	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;

		if (user == null) {
			if (comparedu != null && comparedu.size() > 0) {
				setUser(comparedu.get(0));
			} else {
				ctx.addMessage(null, new FacesMessage("Wybierz uzytkownika"));
			}
		}
		if (role == null) {
			if (comparedr != null && comparedr.size() > 0) {
				setRole(comparedr.get(0));
			} else {
				ctx.addMessage(null, new FacesMessage("Wybierz role"));
			}
		}
		if (ctx.getMessageList().isEmpty()) {
			bindrole.setRefuelings(refuelings);
			bindrole.setRents(rents);
			bindrole.setRole(role);
			bindrole.setUser(user);
		
			result = true;
		}

		return result;
	}

	public String saveData() {
		if (bindrole == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("CAN'T ADD ROLE - NULL! Welcome to the system. Back off."));
			return PAGE_STAY_AT_THE_SAME;
		}

		if (!validate()) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (bindrole.getIdbindrole() ==null) {
				bindao.create(bindrole);
			} else {
				bindao.merge(bindrole);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("YOU BROKE IT. AGAIN. YOU broke it. It is ONLY YOUR fault."));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_BIND_LIST;
	}

	
	

}
