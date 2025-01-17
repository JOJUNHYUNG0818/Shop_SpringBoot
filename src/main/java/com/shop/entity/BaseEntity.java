package com.shop.entity;

import jakarta.persistence.*;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@EntityListeners(value= {AuditingEntityListener.class})
@MappedSuperclass
@Getter
public class BaseEntity extends BaseTimeEntity{
   @CreatedBy
   @Column(updatable = false)
   private String createdBy;
   
   @LastModifiedBy
   private String modifedBy;
   
}