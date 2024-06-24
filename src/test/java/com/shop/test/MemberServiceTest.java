package com.shop.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import com.shop.service.MemberService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.properties")


public class MemberServiceTest {

	@Autowired
	MemberService memberService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public Member createMember() {
		MemberFormDto memberFromDto = new MemberFormDto();
		memberFromDto.setEmail("test@email.com");
		memberFromDto.setName("홍길동");
		memberFromDto.setAddress("서울시 강동구 천호동");
		memberFromDto.setPassword("1234");
		return Member.createMember(memberFromDto, passwordEncoder);
	}
	
	@Test
	@DisplayName("회원가입 테스트")
	public void saveMemberTest() {
		Member member = createMember();
		Member savedMember = memberService.saveMember(member);
		// 자바에 있는 이메일자료, DB에서 가지고온 Email자료가 같으면
		assertEquals(member.getEmail(),savedMember.getEmail() );
		assertEquals(member.getName(),savedMember.getName() );
		assertEquals(member.getAddress(),savedMember.getAddress() );
		assertEquals(member.getPassword(),savedMember.getPassword() );
		assertEquals(member.getRole(),savedMember.getRole() );
		// 오류 안날시 테스트 성공
		
	}
	
	@Test
	@DisplayName("중복회원가입 테스트")
	public void saveDuplicateMemberTest() {
		Member member1 = createMember();
		Member member2 = createMember();
		
		memberService.saveMember(member1);
		
		Throwable e = assertThrows(IllegalStateException.class,()-> {
			memberService.saveMember(member2);});
		assertEquals("이미 가입한 회원입니다.",e.getMessage());
		}
			
	
}
