package pl.com.mnemonic.entities;
// default package
// Generated 2015-04-12 11:10:53 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Car generated by hbm2java
 */
@Entity
@Table(name = "car", catalog = "renting")
public class Car implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idcar;
	private String carname;
	private String carrmake;
	private Integer carregistrationyear;
	private String carplates;
	private String cartype;
	private String carcolor;
	private Boolean available;
	private Set<Refueling> refuelings = new HashSet<Refueling>(0);
	private Set<Rent> rents = new HashSet<Rent>(0);

	public Car() {
	}

	public Car(String carname, String carrmake, Integer carregistrationyear,
			String carplates, String cartype, String carcolor,
			Boolean available, Set<Refueling> refuelings, Set<Rent> rents) {
		this.carname = carname;
		this.carrmake = carrmake;
		this.carregistrationyear = carregistrationyear;
		this.carplates = carplates;
		this.cartype = cartype;
		this.carcolor = carcolor;
		this.available = available;
		this.refuelings = refuelings;
		this.rents = rents;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idcar", unique = true, nullable = false)
	public Integer getIdcar() {
		return this.idcar;
	}

	public void setIdcar(Integer idcar) {
		this.idcar = idcar;
	}

	@Column(name = "carname", length = 45)
	public String getCarname() {
		return this.carname;
	}

	public void setCarname(String carname) {
		this.carname = carname;
	}

	@Column(name = "carrmake", length = 45)
	public String getCarrmake() {
		return this.carrmake;
	}

	public void setCarrmake(String carrmake) {
		this.carrmake = carrmake;
	}

	@Column(name = "carregistrationyear")
	public Integer getCarregistrationyear() {
		return this.carregistrationyear;
	}

	public void setCarregistrationyear(Integer carregistrationyear) {
		this.carregistrationyear = carregistrationyear;
	}

	@Column(name = "carplates", length = 45)
	public String getCarplates() {
		return this.carplates;
	}

	public void setCarplates(String carplates) {
		this.carplates = carplates;
	}

	@Column(name = "cartype", length = 45)
	public String getCartype() {
		return this.cartype;
	}

	public void setCartype(String cartype) {
		this.cartype = cartype;
	}

	@Column(name = "carcolor", length = 45)
	public String getCarcolor() {
		return this.carcolor;
	}

	public void setCarcolor(String carcolor) {
		this.carcolor = carcolor;
	}

	@Column(name = "available")
	public Boolean getAvailable() {
		return this.available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "car")
	public Set<Refueling> getRefuelings() {
		return this.refuelings;
	}

	public void setRefuelings(Set<Refueling> refuelings) {
		this.refuelings = refuelings;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "car")
	public Set<Rent> getRents() {
		return this.rents;
	}

	public void setRents(Set<Rent> rents) {
		this.rents = rents;
	}

}
