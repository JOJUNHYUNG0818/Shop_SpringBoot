package com.shop.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.shop.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="orders")
public class Order extends BaseEntity{
	
	@Id
	@Column(name="order_id")
	@GeneratedValue
	private  Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;
	
	private LocalDateTime orderDate; // 주문일
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus; // 주문상태
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true , fetch = FetchType.LAZY) 
	// order랑 orderItems 와 묶음
	private List<OrderItem> orderItems = new ArrayList<>();
	
	private LocalDateTime regTime; // 배송기한
	
	private LocalDateTime updateTime; // 수정일
	
	public void addOrderItem(OrderItem orderItem) {
		// 주문 상품 정보들을 담아줌
		orderItems.add(orderItem);
		orderItem.setOrder(this); 
	}
	
	public static Order createdOrder(Member member, List<OrderItem> orderItemList) {
		Order order = new Order();
		order.setMember(member);
		for( OrderItem orderItem : orderItemList ) {
			order.addOrderItem(orderItem);
		}
		order.setOrderStatus(OrderStatus.ORDER);
		order.setOrderDate(LocalDateTime.now());
		return order;
	}
	
	public int getTotalPrice() {
		int totalPrice = 0;
		for(OrderItem orderItem : orderItems) {
			totalPrice += orderItem.getTotalPrice();
		}
		return totalPrice;
	}
	
	public void cancelOrder(){
		this.orderStatus = OrderStatus.CANCEL;
		for(OrderItem orderItem : orderItems) {
			orderItem.cancel();
		}
		
	}
}
