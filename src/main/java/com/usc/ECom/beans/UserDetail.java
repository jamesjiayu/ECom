package com.usc.ECom.beans;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="usc_user_detail")
public class UserDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_DETAIL_SEQ_GEN")
    @SequenceGenerator(name = "USER_DETAIL_SEQ_GEN",  sequenceName = "USER_DETAIL_SEQ", allocationSize = 1)
	private int id;
	@Column
	@NotNull
	private String name;
	@Column
	private String phone;
	@Column
	@NotNull
	private String email;
	@Column
	private String address1;
	@Column
	private String address2;
	@Column 
	private String state;	
	@Column 
	private String city;	
	@Column 
	private String zipcode;
	
	@JoinColumn(name="user_id")
	@Column
	@OneToOne
	private	User User;
	//gender, women:0, men: 1. converter if necessary, ex: it's enum in java, but in db it's...
	public UserDetail() {}
	
	public UserDetail(int id, @NotEmpty String name, @NotEmpty String email, com.usc.ECom.beans.User user) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		User = user;//why no this.User? cuz it's class?
	}

	public UserDetail(int id, @NotEmpty String name, String phone, @NotEmpty String email, String address1,
			String address2, String state, String city, String zipcode, com.usc.ECom.beans.User user) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.address1 = address1;
		this.address2 = address2;
		this.state = state;
		this.city = city;
		this.zipcode = zipcode;
		User = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}

	@Override
	public String toString() {
		return "UserDetail [id=" + id + ", name=" + name + ", phone=" + phone + ", email=" + email + ", address1="
				+ address1 + ", address2=" + address2 + ", state=" + state + ", city=" + city + ", zipcode=" + zipcode
				+ ", User=" + User + "]";
	}
	
}
