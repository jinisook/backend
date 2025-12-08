package com.example.mart.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(exclude = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "mart_member")
@Entity
public class Member extends BaseEntity {
    // id, name, city, street, zipcode

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String city;

    @Column
    private String street;

    @Column
    private String zipcode;

    // Many를 가지고 올땐 -> LIST
    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
