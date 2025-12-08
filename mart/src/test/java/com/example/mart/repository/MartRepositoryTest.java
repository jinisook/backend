package com.example.mart.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;
import com.example.mart.entity.OrderItem;
import com.example.mart.entity.constant.OrderStatus;

@EnableJpaAuditing
@SpringBootTest
public class MartRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    public void insertMemberTest() {
        // 5명의 Member 삽입
        // for (int i = 1; i < 6; i++) {
        // Member member = Member.builder()
        // .name("member" + i)
        // .city("city" + i)
        // .street("street" + i)
        // .zipcode("code" + i)
        // .build();
        // memberRepository.save(member);
        // }

        IntStream.rangeClosed(1, 5).forEach(i -> {
            Member member = Member.builder()
                    .name("user" + i)
                    .city("서울")
                    .street("724-1" + i)
                    .zipcode("1650" + i)
                    .build();
            memberRepository.save(member);
        });

    }

    @Test
    public void insertItemTest() {
        // 5명의 Item 삽입
        // for (int i = 1; i < 6; i++) {
        // Item item = Item.builder()
        // .name("item" + i)
        // .price(30000)
        // .quantity(5)
        // .build();
        // itemRepository.save(item);

        // }

        IntStream.rangeClosed(1, 5).forEach(i -> {
            Item item = Item.builder()
                    .name("item" + i)
                    .price(30000)
                    .quantity(i * 5)
                    .build();

            itemRepository.save(item);
        });

    }

    @Test
    public void orderTest() {
        // 주문
        // 1번 상품을 2번 고객이 주문

        Member member = memberRepository.findById(2L).get();
        Item item = itemRepository.findById(1L).get();

        Order order = Order.builder()
                .member(member)
                .orderStatus(OrderStatus.ORDER)
                .build();
        orderRepository.save(order);

        OrderItem orderItem = OrderItem.builder()
                .item(item)
                .order(order)
                .orderPrice(30000)
                .count(1)
                .build();

        orderItemRepository.save(orderItem);

    }

    @Transactional(readOnly = true) // getOrderItems 당장 불러오기
    @Test
    public void orderReadTest() {
        // 2번 고객 주문내역조회
        // orderRepository.findById(null)
        Member member = Member.builder().id(2L).build();
        Order order = orderRepository.findByMember(member).get();

        System.out.println(order);

        // 주문상품 정보
        order.getOrderItems().forEach(i -> {
            System.out.println(i);
            // 주문 상품 상제 정보 조회
            System.out.println(i.getItem());

        });

        // 고객 상세 정보
        System.out.println(order.getMember());

    }
}
