package com.example.giftshop.review.entity;

import com.example.giftshop.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Review extends BaseEntity {

    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;//pk
    Long goods_id;//book pk
    String user_id;//user pk
    String content;
    Long prno;//계층구조 위한 prno
}
