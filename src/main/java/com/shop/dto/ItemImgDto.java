package com.shop.dto;

import org.modelmapper.ModelMapper;

import com.shop.entity.ItemImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemImgDto {
  private Long id;
  private String imgName;
  private String oriImgName;
  private String imgUrl;
  private String repImgYn;
  private static ModelMapper modelMapper = new ModelMapper();
  
  public static ItemImgDto of(ItemImg itemImg) {
	  return modelMapper.map(itemImg, ItemImgDto.class);
// ItemImg엔티티 객체를 파라미터롤 받아서 ItemImg 객체의 자료형와 멤버변수의 이름이 같을 떄 
// ItemImgDto로 값을 복사해서 반환합니다.
  }
}