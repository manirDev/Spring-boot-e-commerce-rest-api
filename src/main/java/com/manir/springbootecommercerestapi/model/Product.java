package com.manir.springbootecommercerestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "keywords")
    private String keywords;
    @Column(name = "description")
    private String description;
    @Column(name = "detail")
    private String detail;
    @Column(name = "price")
    private double price;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "status")
    private String status;

    //product image
    @Lob
    @Column(name = "image")
    private String image;


    //relation between category and product
    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

    //relation image gallery
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ImageData> images;

    //relation to product comment
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;


    //relation to cart item
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems;

    //relation with order_products
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true,
            mappedBy = "product")
    private Set<OrderProducts> orderProducts;
}
