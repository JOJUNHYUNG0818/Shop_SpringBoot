package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.ItemImg;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long>{
	// select * from itemImg where itemId = ?(itemId) order by itemid asc
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);

    ItemImg findByItemIdAndRepimgYn(Long itemId, String repimgYn);
}