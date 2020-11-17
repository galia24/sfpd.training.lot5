package io.hackages.learning.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "products")
public class Product {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 64, nullable = false, unique = true)
	private String name;
	@Column
	private float price;
	@Column
	private LocalDate createdAt;
	@Column
	//@Version
	private LocalDateTime updatedAt;
	@Column
	private Enum<Category> category;


	public Product() {
		this.createdAt = LocalDate.now();
		this.updatedAt = LocalDateTime.now();

	}

	public Product(String name, float price, Enum<Category> category) {
		// this(null, name, price, createdAt, updatedAt, category);
		this();
		this.name = name;
		this.price = price;
		this.category = category;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Enum<Category> getCategory() {
		return category;
	}

	public void setCategory(Enum<Category> category) {
		this.category = category;
	}
}
