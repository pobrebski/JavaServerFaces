package pl.com.mnemonic.editors;

import java.io.Serializable;
import java.util.HashSet;
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
import pl.com.mnemonic.entities.Role;
import pl.com.mnemonic.entities.dao.RoleDao;


@ManagedBean
@ViewScoped
public class RoleEdit implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String PAGE_ROLE_EDIT = "edit/role?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_INDEX = "/index?faces-redirect=true";

	@EJB
	RoleDao roledao;
	private String idrole;
	private String roleCode;
	private String roleName;
	private String roleDescription;
	
	private Set<Bindrole> bindroles = null;

	public String getIdrole() {
		return idrole;
	}

	public void setIdrole(String idrole) {
		this.idrole = idrole;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	public Set<Bindrole> getBindroles() {
		return bindroles;
	}

	public void setBindroles(Set<Bindrole> bindroles) {
		this.bindroles = bindroles;
	}


	private Role role = null;

	@PostConstruct
	public void postConstruct() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);

		role = (Role) session.getAttribute("rolenew");
		if (role != null) {
			session.removeAttribute("rolenew");
		}
		if (role == null) {
			HttpServletRequest req = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			idrole = req.getParameter("p");
			if (idrole != null) {
				Integer id = null;
				try {
					id = Integer.parseInt(idrole);
				} catch (NumberFormatException e) {
				}
				if (id != null) {
					role = roledao.find(id);
				}
			}
		}
		if (role != null && role.getIdrole()!=null) {
			setRoleCode(role.getRoleCode());
			setRoleDescription(role.getRoleDescription());
			setRoleName(role.getRoleName());
			setBindroles(role.getBindroles());
		}
		
	}

	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;

		if (roleName == null || roleName.trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage("Nazwa roli jest konieczna"));
		}
		if (roleDescription == null || roleDescription.trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage("Podaj opis uprawnien grupy"));
		}

		if (ctx.getMessageList().isEmpty()) {
			role.setRoleName(roleName.trim());
			role.setRoleDescription(roleDescription.trim());
			//bindrole = new Bindrole();			
			role.setBindroles(bindroles);

			String code = roleName.length() + "" + roleName + ""
					+ roleDescription.length();
			role.setRoleCode(code);

			result = true;
		}

		return result;
	}

	public String saveData() {
		if (role == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Welcome to the system. Back off."));
			return PAGE_STAY_AT_THE_SAME;
		}

		if (!validate()) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (role.getIdrole() ==null) {
				roledao.create(role);

			} else {
				roledao.merge(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("YOU broke it. It is ONLY YOUR fault."));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_INDEX;
		// return PAGE_STAY_AT_THE_SAME;
	}

}
