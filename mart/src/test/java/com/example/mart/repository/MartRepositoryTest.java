package com.example.mart.repository;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.example.mart.entity.Category;
import com.example.mart.entity.CategoryItem;
import com.example.mart.entity.Delivery;
import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;
import com.example.mart.entity.OrderItem;
import com.example.mart.entity.constant.DeliveryStatus;
import com.example.mart.entity.constant.OrderStatus;

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

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CatedoryItemRepository catedoryItemRepository;

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

    @Commit
    @Transactional // getOrderItems 당장 불러오기
    @Test
    public void orderCascadeTest() {
        // orderTest() -> Cascade 사용
        // 주문
        // 3번 고객이 2번 상품을 주문

        Member member = memberRepository.findById(3L).get();
        Item item = itemRepository.findById(2L).get();

        Order order = Order.builder()
                .member(member)
                .orderStatus(OrderStatus.ORDER)
                .build();
        OrderItem orderItem = OrderItem.builder()
                .item(item)
                // .order(order)
                .orderPrice(33000)
                .count(1)
                .build();

        order.getOrderItems().add(orderItem);

        order.addOrderItem(orderItem); // .order(order) 대신
        orderRepository.save(order);

        // orderItemRepository.save(orderItem);
        // OrderItem를 따로 만들어서 저장하던 것을 Cascade를 사용하면,
        // order(부모)를 저장하면 orderitem(자식)도 같이 저장되는 개념 사용
    }

    @Commit
    @Transactional
    @Test
    public void testUpdate() {
        // 3번 고객의 city 변경
        Member member = memberRepository.findById(3L).get();
        member.changeCity("부산"); // managed entity 는 변경사항 감지 기능(dirty checking)
        // memberRepository.save(member); X -> dirty checking 때문에

        // 3번 고객 item 수량 변경 => 10
        Item item = itemRepository.findById(3L).get();
        item.changeQuantity(10);

        // 1번 고객의 주문 상태 변경 => CANCEL
        Order order = orderRepository.findById(1L).get();
        order.changeOrderStatus(OrderStatus.CANCEL);
    }

    @Commit
    @Transactional
    @Test
    public void testDelete() {
        // 주문 제거
        // orders, order_item 제거

        // 방법 1. 개별 제거(자식 먼저 삭제 => 부모 삭제)
        orderItemRepository.deleteById(2L);
        orderRepository.deleteById(2L);

    }

    @Commit
    @Transactional
    @Test
    public void testCascadeDelete() {
        // 주문 제거
        // orders, order_item 제거

        // 방법 2. 부모 삭제 시 자식 같이 삭제(sql => ON DELETE CASCADE)
        // 부모 클래스에 CascadeType.REMOVE
        orderRepository.deleteById(1L); // 부모만 삭제

    }

    @Commit
    @Transactional
    @Test
    public void testOrphanDelete() {
        // 주문 조회
        Order order = orderRepository.findById(3L).get();
        System.out.println(order);
        // 주문 아이템 조회
        System.out.println(order.getOrderItems());

        // orphanRemoval = true 안써주면, db에 반영 X
        // remove -> db 반영하고 싶으면, 부모 클래스에 orphanRemoval = true 작성
        order.getOrderItems().remove(0);
        System.out.println("삭제 후 " + order.getOrderItems());

        // orderRepository.save(order); -> dirty checking 때문에 상관없음
    }

    @Commit
    @Transactional
    @Test
    public void testDelivery() {
        Member member = memberRepository.findById(4L).get();
        Item item = itemRepository.findById(2L).get();

        Delivery delivery = Delivery.builder()
                .city("안산")
                .street("956")
                .zipcode("11061")
                .deliveryStatus(DeliveryStatus.COMP)
                .build();

        deliveryRepository.save(delivery);

        Order order = Order.builder()
                .member(member)
                .orderStatus(OrderStatus.ORDER)
                .delivery(delivery)
                .build();
        OrderItem orderItem = OrderItem.builder()
                .item(item)
                // .order(order)
                .orderPrice(33000)
                .count(1)
                .build();

        order.getOrderItems().add(orderItem);

        order.addOrderItem(orderItem); // .order(order) 대신
        orderRepository.save(order);

    }

    @Commit
    @Transactional
    @Test
    public void testCascadeDelivery() {
        Member member = memberRepository.findById(4L).get();
        Item item = itemRepository.findById(2L).get();

        Delivery delivery = Delivery.builder()
                .city("안산")
                .street("956")
                .zipcode("11061")
                .deliveryStatus(DeliveryStatus.COMP)
                .build();

        // deliveryRepository.save(delivery);

        Order order = Order.builder()
                .member(member)
                .orderStatus(OrderStatus.ORDER)
                .delivery(delivery)
                .build();
        OrderItem orderItem = OrderItem.builder()
                .item(item)
                // .order(order)
                .orderPrice(33000)
                .count(1)
                .build();

        order.getOrderItems().add(orderItem);

        order.addOrderItem(orderItem); // .order(order) 대신
        orderRepository.save(order);

    }

    @Transactional(readOnly = true)
    @Test
    public void orderReadTest2() {
        Order order = orderRepository.findById(4L).get();

        // order 조회
        System.out.println(order);

        // 주문한 고객 조회
        System.out.println(order.getMember());
        System.out.println(order.getMember().getName());

        // 주문제품 조회
        System.out.println(order.getOrderItems());
        System.out.println(order.getOrderItems().get(0));

        // 배송조회
        System.out.println(order.getDelivery());
    }

    @Transactional(readOnly = true)
    @Test
    public void memberReadTest() {
        Member member = memberRepository.findById(4L).get();

        // 고객 조회
        System.out.println(member);

        // order 조회
        System.out.println(member.getOrders());

        List<Order> orders = member.getOrders();
        orders.forEach(order -> {
            System.out.println(order.getDelivery());
            System.out.println(order.getMember());
            System.out.println(order.getOrderItems());
        });

    }

    @Transactional(readOnly = true)
    @Test
    public void orderItemReadTest() {
        OrderItem orderItem = orderItemRepository.findById(4L).get();

        // orderitem 조회
        System.out.println(orderItem);
        // order 자세하게 조회 -> order를 데려오면,
        // 배송, 고객, 주문상품 조회 전부 가능
        Order order = orderItem.getOrder();
        System.out.println(order.getDelivery());
        System.out.println(order.getMember());
        System.out.println(order.getOrderItems());

        // order 조회
        System.out.println(orderItem.getOrder());

        // 상품 조회
        System.out.println(orderItem.getItem());

    }

    // -----------------------------------------
    // ManyToMany 설정을 JPA에게 시킨 경우
    // 테스트 구문
    // -----------------------------------------

    @Test
    public void categoryTest() {
        Item item = itemRepository.findById(3L).get();

        Category category = Category.builder().name("가전제품").build();
        // category.getItems().add(item);
        categoryRepository.save(category);

        category = Category.builder().name("생활용품").build();
        // category.getItems().add(item);
        categoryRepository.save(category);

    }

    @Test
    public void categoryReadTest() {
        Category category = categoryRepository.findById(1L).get();

        // 카테고리 조회
        System.out.println(category);

        // 카태고리에 속한 아이템 조회
        // System.out.println(category.getItems());

    }

    @Transactional(readOnly = true)
    @Test
    public void itemReadTest() {
        // 아이템 쪽에서 카테고리 조회

        Item item = itemRepository.findById(3L).get();

        // 아이템 조회
        System.out.println(item);

        // 카태고리 조회
        // System.out.println(item.getCategories());

    }
    // -----------------------------------------
    // ManyToMany 설정을 ManyToOne 관계로 작성 후
    // 테스트 구문
    // -----------------------------------------

    @Test
    public void categoryTest2() {
        Item item = itemRepository.findById(4L).get();

        Category category = Category.builder().name("신혼용품").build();
        // category.getItems().add(item);
        categoryRepository.save(category);

        CategoryItem categoryItem = CategoryItem.builder()
                .category(category)
                .item(item)
                .build();
        catedoryItemRepository.save(categoryItem);

        // 기존카테고리에 아이템만 추가
        category = categoryRepository.findById(1L).get();
        categoryItem = CategoryItem.builder()
                .category(category)
                .item(item)
                .build();

        catedoryItemRepository.save(categoryItem);

    }

    @Transactional(readOnly = true)
    @Test
    public void categoryItemReadTest() {

        CategoryItem categoryItem = catedoryItemRepository.findById(1L).get();

        // 카테고리아이템 조회
        System.out.println(categoryItem);

        // 키테고리 정보 조회
        System.out.println(categoryItem.getCategory());

        // 아이템 정보 조회
        System.out.println(categoryItem.getItem());

        // 양방향 연 뒤
        Category category = categoryRepository.findById(1L).get();
        System.out.println(category);
        System.out.println(category.getCategoryItems());

        Item item = itemRepository.findById(3L).get();
        System.out.println(item);
        System.out.println(item.getCategoryItems());

    }

}
