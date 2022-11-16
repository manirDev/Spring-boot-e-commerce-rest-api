package com.manir.springbootecommercerestapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "order_products")
public class OrderProducts extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double productPrice;
    private Integer productQuantity;
    private double totalPrice;
    private String note;
    private String status;

    //relation with user
    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private User customer;

    //relation with product
    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    //relation with order
    @ManyToOne()
    @JoinColumn(name = "order_id")
    private Order order;
}
