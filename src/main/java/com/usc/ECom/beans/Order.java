package com.usc.ECom.beans;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
@Entity
@Table(name = "usc_order")
public class Order {	  

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ORDER_SEQ_GEN")
    @SequenceGenerator(name = "ORDER_SEQ_GEN", sequenceName = "ORDER_SEQ", allocationSize = 1)//if the first entity has an ID of 1, the next should be 2, then 3, and so on, with no skipped numbers.
    private int id;	
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd")//?
    private Date purchase_date;

//in OP orderProduct Class：
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "order_id")
//    @JsonIgnoreProperties("purchases")
//    private Order order;
    @OneToMany(mappedBy= "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)//"order" is private Order order; in OP,orderProduct
    private List<OrderProduct> purchases;//list of  OP , OrderProduct
    
//    PERSIST: If the owner is persisted, the child entities will also be persisted.
//    MERGE: If the owner is merged, the child entities will also be merged.
//    REMOVE: If the owner is removed, the child entities will also be removed.
//    REFRESH: If the owner is refreshed, the child entities will also be refreshed.
//    DETACH: If the owner is detached, the child entities will also be detached.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

//    @ManyToOne
//    @JoinTable(
//        name = "user_orders",
//        joinColumns = { @JoinColumn(name = "order_id", referencedColumnName = "id") },
//        inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }
//    )
//    private User user;
    public Order() {
    }


    public Order(Date purchase_date, List<OrderProduct> purchases) {
        super();
        this.purchase_date  = purchase_date;
        this.purchases = purchases;
    }
    public int getId() {
        return id;
    }

	public void setId(int id) {
		this.id = id;
	}


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


	public Date getPurchase_date() {
		return purchase_date;
	}


	public void setPurchase_date(Date purchase_date) {
		this.purchase_date = purchase_date;
	}


	public List<OrderProduct> getPurchases() {
		return purchases;
	}


	public void setPurchases(List<OrderProduct> purchases) {
		this.purchases = purchases;
	}




	@Override
	public String toString() {
		return "Order [id=" + id + ", purchase_date=" + purchase_date + ", purchases=" + purchases + ", user=" + user
				+ "]";
	}

}
//@Column(name = "order_date", nullable = false)
//@Temporal(TemporalType.TIMESTAMP) //?
//private Date orderDate;
//chatgpt
