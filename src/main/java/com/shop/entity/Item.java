package com.shop.entity;
import java.time.LocalDateTime;
import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import com.shop.exception.OutofStockException;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="item")
@Setter
@Getter
@ToString

public class Item extends BaseEntity {
	@Id
	@Column(name = "item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String itemNm;
	
	@Column(name = "price", nullable = false)
	private int price;
	
	@Column(nullable = false)
	private int stockNumber;
	
	@Lob
	@Column(nullable = false)
	private String itemDetail;
	
	private ItemSellStatus itemSellStatus;
	
	//private LocalDateTime regTime;
	//private LocalDateTime updateTime;

	public void updateItem(ItemFormDto itemFormDto) {
		this.itemNm = itemFormDto.getItemNm();
		this.price = itemFormDto.getPrice();
		this.stockNumber = itemFormDto.getStockNumber();
		this.itemDetail = itemFormDto.getItemDetail();
		this.itemSellStatus = itemFormDto.getItemSellStatus();
		
	}
	
	public void removeStock(int stockNumber) {
		int restStock = this.stockNumber - stockNumber;
		if(restStock<0) {
			throw new OutofStockException("상품의 재고가 부족 합니다. (현재 재고 수량 "+ this.stockNumber+")");
		}
		this.stockNumber=restStock;
	}
	
	public void addStock(int stockNumber) {
		this.stockNumber += stockNumber;
	}

}
