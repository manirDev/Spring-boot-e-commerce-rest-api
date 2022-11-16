package com.manir.springbootecommercerestapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "cart_item")
public class CartItem extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "status")
    private String status; //pending, ordered

    //relation with product
    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    //relation with customer
    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private User customer;

}
