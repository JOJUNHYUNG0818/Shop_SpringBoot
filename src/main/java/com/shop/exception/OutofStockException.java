package com.shop.exception;

public class OutofStockException extends RuntimeException {
	public OutofStockException(String message) {
		super(message); // 부모 생성자 호출
	}

}
