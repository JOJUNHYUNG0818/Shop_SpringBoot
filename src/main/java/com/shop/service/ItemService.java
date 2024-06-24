package com.shop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.shop.dto.ItemFormDto;
import com.shop.dto.ItemImgDto;
import com.shop.dto.ItemSearchDto;
import com.shop.dto.MainItemDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
   private final ItemRepository itemRepository;
   private final ItemImgRepository itemImgRepository;
   private final ItemImgService itemImgService;
   
   public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
	    //상품 등록
	   Item item = itemFormDto.createItem(); //1 상품 등록 폼으로부터 입력 받은 데이터를 이용하여 item객체를 생성합니다
	   itemRepository.save(item); //2. 상품 데이터를 저장합니다
	   
	   //이미지등록
	   for(int i=0; i<itemImgFileList.size(); i++) {
		   ItemImg itemImg = new ItemImg();
		   itemImg.setItem(item);
		   if( i==0 ) { //3. 첫번째 이미지일 경우 대표 상품 이미지 여부값을 'Y'로 세팅합니다. 나머지 상품 이미지는 'N'으로 설정합니다.
			   itemImg.setRepimgYn("Y");
		   }else {
			   itemImg.setRepimgYn("N");
		   }
		   itemImgService.saveItemImg(itemImg, itemImgFileList.get(i)); //4.상품 이미지 정보를 저장합니다.
	   }
	    return item.getId();
   }
   
   @Transactional(readOnly = true)
   public ItemFormDto getItemDto(Long itemId) {
	   List<ItemImg> itemImgList = 
			   itemImgRepository.findByItemIdOrderByIdAsc(itemId); // db에 검색해서 itemImgList에 넣어줌
	   List<ItemImgDto> itemImgDtoList = new ArrayList();
	   for(ItemImg itemImg : itemImgList) {
		   ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
		   itemImgDtoList.add(itemImgDto);
		   //DB에서 itemImgList로 itemImgList에서 itemDto로 itemDto 에서 itemDtoList로 연결
		   
	   }
	   Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
	   ItemFormDto itemFormDto = ItemFormDto.of(item);
	   itemFormDto.setItemImgDtoList(itemImgDtoList);
	   return itemFormDto;
			   
   }
   public Long updateItem(ItemFormDto itemFormDto,
		   	List<MultipartFile> itemImgFileList) throws Exception {
	   Item item = itemRepository.findById(itemFormDto.getId()).orElseThrow(EntityNotFoundException::new);
	   item.updateItem(itemFormDto);
	   List<Long> itemImgIds = itemFormDto.getItemImgIds();
	   for(int i=0; i<itemImgFileList.size(); i++) {
		   itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
	   }
	   return item.getId();
	   
   }
   
   @Transactional(readOnly=true)
	public Page<Item> getAdminItemPage( ItemSearchDto itemSearchDto,  Pageable pageable) {
		return itemRepository.getAdminItemPage(itemSearchDto, pageable);
	}
   
   @Transactional(readOnly = true)
   public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
	   return itemRepository.getMainItemPage(itemSearchDto, pageable);
   }
}