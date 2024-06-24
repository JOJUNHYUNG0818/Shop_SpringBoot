package com.shop.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.dto.Information;
import com.shop.dto.ItemDto;

@Controller
@RequestMapping(value="/thymeleaf")

public class ThymleafExController {
	
	@GetMapping(value="/ex01")
	public String thymeleafEx01(Model model) {
		model.addAttribute("data","화이팅");
		model.addAttribute("data2","화이팅2");
		return "thymeleafEx/thymeleafEx01";
	}

	@GetMapping(value = "/ex02")
	public String thmeleafEx02(Model model) {
		ItemDto itemDto = new ItemDto();
		itemDto.setItemDetail("상세 설명");
		itemDto.setItemNm("테스트 상품1");
		itemDto.setPrice(10000);
		itemDto.setRegTime(LocalDateTime.now());
		model.addAttribute("itemDto", itemDto);
		return "thymeleafEx/thymeleafEx02";
		
	}
	
	@GetMapping(value = "/ex05")
	public String thymeleafEx(Model model) {
		List itemDtoList = new ArrayList<>();
		for(int i=1; i<10; i++) {
			ItemDto itemDto = new ItemDto();
			itemDto.setItemDetail("상품 상세 설명"+i);
			itemDto.setItemNm("테스트 상품" + i);
			itemDto.setPrice(10000 + i);
			itemDto.setRegTime(LocalDateTime.now());
			itemDtoList.add(itemDto);
		}
		model.addAttribute("itemDtoList", itemDtoList);
		model.addAttribute("test","test1"); // 중요!
		return "thymeleafEx/thymeleafEx05";
			
	}
	@GetMapping(value = "/ex06")
	public String thymeleafEx06(Model model) {
		return "thymeleafEx/thymeleafEx06";
			
	}
	
	@GetMapping(value = "/ex07")
	public String thymeleafEx07() {
		return "thymeleafEx07";
	}
	
	@GetMapping(value="/ex08")
	public String thymeleafEx08(Model model) {
		model.addAttribute("data","조준형");
		return "thymeleafEx07";
	}
		
	@GetMapping(value = "/ex09")
	public String thymeleafEx09(Model model) {
		List itemDtoList = new ArrayList<>();
		for(int i=1; i<10; i++) {
			ItemDto itemDto = new ItemDto();
			itemDto.setName("조준형"+i);
			itemDtoList.add(itemDto);
		}
		model.addAttribute("itemDtoList", itemDtoList);
		return "thymeleafEx/thymeleafEx09";
			
	}
	@GetMapping(value = "/ex10")
	public String thymeleafEx10(Model model) {
		List informationList = new ArrayList<>();
		for(int i=1; i<5; i++) {
			Information info = new Information();
			info.setName("조준형"+i);
			info.setPhoneNum(0101);
			info.setHobby("스프링"+i);
			informationList.add(info);
		}
		model.addAttribute("information1", informationList);
		return "thymeleafEx/thymeleafEx10";
			
	}
	
	@GetMapping(value = "/ex11")
	public String thymeleafEx11(String param1, String param2, Model model) {
		System.out.println("param1 ==> "+param1 );
		System.out.println("param2 ==> "+param2 );
		String answer = param1+"!!!###+"+param2;
		model.addAttribute("response",answer);
		return "thymeleafEx/th1101";
	}	
	
}

