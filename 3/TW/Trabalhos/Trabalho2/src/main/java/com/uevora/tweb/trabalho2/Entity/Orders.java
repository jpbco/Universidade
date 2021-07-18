package com.uevora.tweb.trabalho2.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "clientID")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "productID")
    private Product product;

    public Orders() {

    }

    public Orders(Client client, Product product) {
        this.client = client;
        this.product = product;
    }

    @Override
    public String toString() {
        return String.format("Orders[id=%d, clientID=%d, productID=%d]", id, client.getId(), product.getId());
    }

    public Client getClient() {
        return client;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Long getClientID() {
        return client.getId();
    }

    public Long getProductID() {
        return product.getId();
    }

    public String getProductName() {
        return product.getName();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
