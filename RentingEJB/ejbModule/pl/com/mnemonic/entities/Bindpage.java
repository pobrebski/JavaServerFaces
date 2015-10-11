package pl.com.mnemonic.entities;
// default package
// Generated 2015-04-12 11:10:53 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Bindpage generated by hbm2java
 */
@Entity
@Table(name = "bindpage", catalog = "renting")
public class Bindpage implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idbindpage;
	private Page page;
	private Role role;

	public Bindpage() {
	}

	public Bindpage(Page page, Role role) {
		this.page = page;
		this.role = role;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idbindpage", unique = true, nullable = false)
	public Integer getIdbindpage() {
		return this.idbindpage;
	}

	public void setIdbindpage(Integer idbindpage) {
		this.idbindpage = idbindpage;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "page_idpage", nullable = false)
	public Page getPage() {
		return this.page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_idrole", nullable = false)
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}