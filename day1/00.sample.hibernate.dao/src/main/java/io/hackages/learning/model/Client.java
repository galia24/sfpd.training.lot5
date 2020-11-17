package io.hackages.learning.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "clients")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 64, nullable = false, unique = true)
	private String alias;

	@Column(length = 64, nullable = false, unique = true)
	private String email;

	@OneToMany(mappedBy = "client")
	private List<Order> orders;

	public Client(){

	}

	public Client(String alias, String email) {
		this();
		this.alias = alias;
		this.email = email;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
