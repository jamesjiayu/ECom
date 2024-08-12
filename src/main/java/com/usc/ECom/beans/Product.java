package com.usc.ECom.beans;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
@Entity //jpa or mapping
@Table(name = "usc_product") //default use class name  if nothing here
public class Product  { 	
    @Id  
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ")// @GeneratedValue(strategy = GenerationType.IDENTITY) db identity is good enough in mysql
    @SequenceGenerator(name = "SEQ", sequenceName = "PRODUCT_SEQ", allocationSize = 1) //generator = "SEQ" --- name = "SEQ"
	private int id;   
    @Column(name = "name", unique = true, nullable = false)
	private String name;
    
//  @NotEmpty:  Ensures that the field is not null and also has a non-zero length.
//    Primarily used for collections, arrays, and strings.
    @Column
    @NotEmpty
    private String brand;
    
    @Positive
    @NotNull
    @Column(name = "price", nullable = false)
	private Double price;
    @Column
    @NotNull
    @PositiveOrZero
    private int stock;
    @Column
    private String image;//????????????   

	public Product() {
		super();
	}
	public Product(int id, String name, @NotEmpty String brand, @Positive Double price,
			@NotNull @PositiveOrZero int stock) {
		super();
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.stock = stock;
	}
	public Product(int id, String name, @NotEmpty String brand, @Positive Double price,
			@NotNull @PositiveOrZero int stock, String image) {
		super();
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.stock = stock;
		this.image = image;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", brand=" + brand + ", price=" + price + ", stock=" + stock
				+ ", image=" + image + "]";
	}
    
    
}

//@Column(name = "description", unique = true, nullable = true)
//private String description;
//
