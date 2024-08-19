package com.usc.ECom.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;


@Entity
@Table(name = "usc_order_product")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "order_product_seq_gen")
    @SequenceGenerator(name = "order_product_seq_gen", sequenceName = "order_product_seq", allocationSize = 1)//?allocationSize
    private int id;

    @Column(name = "quantity")
    private int quantity;

//    @Column(name = "created_at")
//    private LocalDateTime createdAt;
//    EAGER: The child entities are loaded immediately when the owner entity is loaded. This can impact performance, especially for large datasets.
//    LAZY: The child entities are loaded only when explicitly accessed. This can improve performance but can lead to LazyInitializationException if not handled correctly.
    @ManyToOne(fetch = FetchType.EAGER)//diff from LAZY?
    @JoinColumn(name = "order_id")
    @JsonIgnoreProperties("purchases")// not a 注入
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)//@OneToMany?
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderProduct() {
    }

    public OrderProduct(int quantity, Order order, Product product) {
        this.quantity = quantity;
        this.order = order;
        this.product = product;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;

    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

	@Override
	public String toString() {
		return "OrderProduct [id=" + id + ", quantity=" + quantity + ", order=" + order + ", product=" + product + "]";
	}
}