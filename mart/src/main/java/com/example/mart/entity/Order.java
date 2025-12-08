package com.example.mart.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.mart.entity.constant.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(exclude = { "member", "orderItems" })
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
@Entity
public class Order extends BaseEntity {
    // id, OrderStatus 선언

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "order") // 기본 fetch : LAZY
    private List<OrderItem> orderItems = new ArrayList<>();

}
