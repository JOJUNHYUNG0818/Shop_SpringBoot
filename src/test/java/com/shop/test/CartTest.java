package com.shop.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Cart;
import com.shop.entity.Member;
import com.shop.repository.CartRepository;
import com.shop.repository.MemberRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional // 100%가 아닌이상 다시 되돌려라
@TestPropertySource(locations = "classpath:application.properties" )
public class CartTest {
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@PersistenceContext
	EntityManager em;
	
	
	
	
	public Member createMember() {
	MemberFormDto memberFormDto = new MemberFormDto();
	memberFormDto.setEmail("test@email.com");
	memberFormDto.setName("홍길동");
	memberFormDto.setAddress("서울시마포구합정동");
	memberFormDto.setPassword("1234");
	return Member.createMember(memberFormDto, passwordEncoder);
	}
	
	@Test
	@DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
	public void findCartAndMemberTest() {
		Member member = createMember();
		memberRepository.save(member); // mySQL에 insert
		
		Cart cart = new Cart();
		cart.setMember(member);
		cartRepository.save(cart);
		
		em.flush(); // 트렌젝션이 끝날때 flush() 호출해서 데이터베이스에 반영해라
		em.clear(); // JPA 영속성 컨텐트를 비워라
		
		Cart savedCart = cartRepository.findById(cart.getId()).orElseThrow(EntityNotFoundException::new);
		System.out.println("savedCart.getId()=>"+savedCart.getId());
		System.out.println("member.Id()=>"+member.getId());
		
		assertEquals(savedCart.getId(), member.getId()); // 데이터베이스에 id 값과 멤버 id 값이 같으면 통과
	}
	
}
