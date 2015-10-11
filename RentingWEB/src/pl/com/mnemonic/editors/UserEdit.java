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
import pl.com.mnemonic.entities.User;
import pl.com.mnemonic.entities.dao.RoleDao;
import pl.com.mnemonic.entities.dao.UserDao;

@ManagedBean
@ViewScoped
public class UserEdit implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final String PAGE_USER_EDIT = "edit/user?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_INDEX = "/index?faces-redirect=true";

	@EJB
	UserDao userdao;
	
	private String iduser;
	private String username;
	private String password;
	private String name;
	private String surname;
	private String userEmail;
	private String license;
	private String address;
	private Boolean isactive;
	private Set<Bindrole> bindroles =null;
	
	private String pswdcompare;

	
	
	public String getPswdcompare() {
		return pswdcompare;
	}

	public void setPswdcompare(String pswdcompare) {
		this.pswdcompare = pswdcompare;
	}

	public String getIduser() {
		return iduser;
	}

	public void setIduser(String iduser) {
		this.iduser = iduser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public Set<Bindrole> getBindroles() {
		return bindroles;
	}

	public void setBindroles(Set<Bindrole> bindroles) {
		this.bindroles = bindroles;
	}




	private User user = null;

	@PostConstruct
	public void postConstruct() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);

		user = (User) session.getAttribute("usernew");
		if (user != null) {
			session.removeAttribute("usernew");
		}
		if (user == null) {
			HttpServletRequest req = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			iduser = req.getParameter("u");
			if (iduser != null) {
				Integer id = null;
				try {
					id = Integer.parseInt(iduser);
				} catch (NumberFormatException e) {
				}
				if (id != null) {
					user = userdao.find(id);
				}
			}
		}
		if (user != null && user.getIduser()!=null) {
			setName(user.getName());
			setSurname(user.getSurname());
			setAddress(user.getAddress());
			setIsactive(user.getIsactive());
			setLicense(user.getLicense());
			setUsername(user.getUsername());
			setUserEmail(user.getUserEmail());
			setPassword(user.getPassword());
			setBindroles(user.getBindroles());
			setIduser(user.getIduser().toString());
			
		}
		
	}

	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;

		if (name == null || name.trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage("Wprowadü imiÍ"));
		}
		if (surname == null || surname.trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage("Wprowadü nazwisko"));
		}
		if (address == null || address.trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage("Wprowadü adres"));
		}
		if (license == null || license.trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage("Wprowadü numer prawa jazdy"));
		}
		if (username == null || username.trim().length() == 0) {
			username = surname;
		}
		
		if (userEmail == null || userEmail.trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage("Wprowadü adres mail"));
		}
		if (password == null || password.trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage("Wprowadü haslo"));
			if(password.trim().length()<4){ctx.addMessage(null, new FacesMessage("Has≥o musi siÍ sk≥adaÊ z minimum 4 znakÛw"));}
		}
		if (pswdcompare == null || pswdcompare.trim().length() == 0) {
			ctx.addMessage(null, new FacesMessage("Wprowadü ponownie has≥o"));
			if(pswdcompare.trim().compareTo(password.trim())!=0){ctx.addMessage(null, new FacesMessage("potwierdü has≥o"));}
		}
		

		if (ctx.getMessageList().isEmpty()) {			
			user.setBindroles(bindroles);
			user.setUsername(username.trim());
			user.setName(name.trim());
			user.setSurname(surname.trim());
			user.setAddress(address);
			user.setLicense(license.trim().toUpperCase());
			user.setPassword(password.trim());
			user.setUserEmail(userEmail.trim());			
			if (iduser == null || iduser.trim().length() == 0) {
				user.setIsactive(false);
			}

			result = true;
		}

		return result;
	}

	public String saveData() {
		if (user == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Welcome to the system. Back off."));
			return PAGE_STAY_AT_THE_SAME;
		}

		if (!validate()) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (user.getIduser() ==null) {
				userdao.create(user);

			} else {
				userdao.merge(user);
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
