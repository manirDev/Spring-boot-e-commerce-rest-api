package com.manir.springbootecommercerestapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private double totalPrice;
    private String note;
    private  String status;

    //relation with user
    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private User customer;

    //relation with order_products
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true,
            mappedBy = "order")
    private Set<OrderProducts> orderProducts;
}
