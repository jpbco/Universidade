package com.uevora.tweb.trabalho2.Entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Product {
	private Long id, stock;
	private String name, description, pictureUrl, delivery_date;
	private float price, fee;

	public Product() {
	}

	@OneToMany(mappedBy = "product")
	private List<Orders> orders;

	public Product(String name, String description, String pictureUrl, float price, float fee, long stock,
			String delivery_date) {

		this.name = name;
		this.description = description;
		this.pictureUrl = pictureUrl;
		this.price = price;
		this.fee = fee;
		this.stock = stock;
		this.delivery_date = delivery_date;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getFee() {
		return fee;
	}

	public Long getStock() {
		return stock;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public String getDelivery_date() {
		return delivery_date;
	}

	public void setDelivery_date(String delivery_date) {
		this.delivery_date = delivery_date;
	}
}
