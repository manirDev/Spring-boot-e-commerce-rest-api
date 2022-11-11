package com.manir.springbootecommercerestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "cart_item")
public class CartItem {
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

    //relation with user
    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
