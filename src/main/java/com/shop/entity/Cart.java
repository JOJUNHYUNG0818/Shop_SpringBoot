package com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cart")
@Getter
@Setter
@ToString

public class Cart extends BaseEntity {
	@Id
	@Column(name="cart_id")
	@GeneratedValue(strategy = GenerationType.AUTO) // 자동으로 번호생성
	private Long id;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member; // 외래키 Member 테이블의 pk키
	
    public static Cart createCart(Member member){
        Cart cart = new Cart();
        cart.setMember(member);
        return cart;
    }
	
}
