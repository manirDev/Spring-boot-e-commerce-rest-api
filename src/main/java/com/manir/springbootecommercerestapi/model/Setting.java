package com.manir.springbootecommercerestapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "settings")
public class Setting extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String keywords;
    private String description;
    private String company;
    private String address;
    private String phone;
    private String fax;
    private String email;
    private String smtpServer;
    private String smtpEmail;
    private String smtpPassword;
    private Integer smtpPort;
    private String facebook;
    private String instagram;
    private String twitter;
    private String aboutUs;
    private String contact;
    private String reference;
    private String status;

}
