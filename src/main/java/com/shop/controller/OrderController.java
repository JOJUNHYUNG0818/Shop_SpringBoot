package com.shop.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.dto.OrderDto;
import com.shop.dto.OrderHistDto;
import com.shop.service.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
	
   private final OrderService orderService;
   
   @PostMapping(value="/order")
// 1.
   public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto, BindingResult bindingResult, Principal principal) {
	   if(bindingResult.hasErrors()) { //--2
		   StringBuilder sb = new StringBuilder();
		   List<FieldError> fieldErros = bindingResult.getFieldErrors();
		   for( FieldError fieldError : fieldErros) {
			   sb.append(fieldError.getDefaultMessage() );
		   }
           return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);		   // 3--
	   }
	   String email = principal.getName(); // --4
	   Long orderId;
	   try {
	   orderId = orderService.order(orderDto, email); // --5
	   }catch(Exception e) {
		   return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	   }
	   return new ResponseEntity<Long>(orderId, HttpStatus.OK); // --6
	   
   }
   
   @GetMapping(value={"/orders","/orders/{page}"})
   public String orderHist(@PathVariable("page") Optional<Integer> page,
		   Principal principal, Model model) {
	   // 한번에 가지고 올 주문의 개수는 4개로 설정
	   Pageable pageable = PageRequest.of(page.isPresent()?page.get():0,4);
	   
	   // 현재 로그인한 회원은 이메일과 페이지 객체를 파라미터로 전달하여 화면에 전달한 주문 목록 데이터를 리턴
	   Page<OrderHistDto> orderHistDtoList = orderService.getOrderList(principal.getName(), pageable);
	   
	   model.addAttribute("orders",orderHistDtoList);
	   model.addAttribute("page",pageable.getPageNumber());
	   model.addAttribute("maxPage",5);
	   return "order/orderHist";
   }
   
   @PostMapping("/order/{orderId}/cancel")
   public @ResponseBody ResponseEntity cancelOrder(
		   		@PathVariable("orderId") Long orderId, Principal principal) {
	   if(!orderService.validateOrder(orderId, principal.getName())) {
		   return new ResponseEntity<String>("주문 취소 권한이 없습니다", HttpStatus.FORBIDDEN);
	   }
	   // 주문 취소 로직 호출
	   orderService.cancelOrder(orderId);
	   return new ResponseEntity<Long>(orderId,HttpStatus.OK);
	   
   }
}
