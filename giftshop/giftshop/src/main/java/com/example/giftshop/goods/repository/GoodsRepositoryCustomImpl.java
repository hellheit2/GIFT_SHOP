package com.example.giftshop.goods.repository;

import com.example.giftshop.goods.constant.GoodsSellStatus;
import com.example.giftshop.goods.dto.GoodsSearchDTO;
import com.example.giftshop.goods.entity.Goods;
import com.example.giftshop.goods.entity.QGoods;
import com.example.giftshop.goods.entity.QGoodsImage;
import com.example.giftshop.main.dto.MainGoodsDTO;
import com.example.giftshop.main.dto.QMainGoodsDTO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class GoodsRepositoryCustomImpl implements GoodsRepositoryCustom{

    private JPAQueryFactory queryFactory; //동적 쿼리 생성

    public GoodsRepositoryCustomImpl(EntityManager em){
        this.queryFactory =new JPAQueryFactory(em); //JPAQueryFactory 생성자 지정
    }

    private BooleanExpression searchByRegDate(String searchDateType){ //상품 등록일 기준
        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all", searchDateType) || searchDateType == null){ //전체 날짜
            return null;
        } else if(StringUtils.equals("1d", searchDateType)){ //1일
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)){ //1주
            dateTime = dateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDateType)){ //1달
            dateTime = dateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDateType)){ //6개월
            dateTime = dateTime.minusMonths(6);
        }
        return QGoods.goods.regTime.after(dateTime);
    }

    private BooleanExpression searchSellStatusEq(GoodsSellStatus searchSellstatus){ //판매 상태 검색
        return searchSellstatus == null ? null  //판매 조건 null일 경우, where 절 해당 조건 무시
                : QGoods.goods.goodsSellStatus.eq(searchSellstatus); //판매 상태 해당하는 Goods
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("goodsName", searchBy)){
            return QGoods.goods.goodsName.like("%" + searchQuery + "%"); //키워드 포함 Goods
        }else if(StringUtils.equals("categoryList", searchBy)){
            return QGoods.goods.goodsName.like("%" + searchQuery + "%"); //카테고리 포함 변경 필요
        }
        return null;
    }

    private BooleanExpression searchByKeyword(String searchQuery){ //키워드 검색
        return StringUtils.isEmpty(searchQuery) ? null : //키워드 없을 경우
                QGoods.goods.goodsName.like("%" + searchQuery + "%"); //키워드 포함 Goods
    }

    private BooleanExpression searchByCategory(){
        return null;
    }
    @Override
    public Page<Goods> getAdminGoodsPage(GoodsSearchDTO goodsSearchDTO, Pageable pageable) {

        List<Goods> results = queryFactory //쿼리 생성
                .selectFrom(QGoods.goods) //엔티티 지정
                .where( searchByRegDate(goodsSearchDTO.getSearchDateType()), //where 조건 (날짜)
                        searchSellStatusEq(goodsSearchDTO.getSearchSellStatus()),  //and (판매 상태)
                        searchByLike(goodsSearchDTO.getSearchBy(), goodsSearchDTO.getSearchQuery())) //and (조회 유형)
                .orderBy(QGoods.goods.id.desc()) //아이디 기준 내림차순
                .offset(pageable.getOffset()) //데이터 조회 시작 인덱스
                .limit(pageable.getPageSize()) //가지고 올 데이터 최대 개수
                .fetch();

        long total = queryFactory //쿼리 생성
                .select(Wildcard.count) //카운트
                .from(QGoods.goods) //엔티티 지정
                .where( searchByRegDate(goodsSearchDTO.getSearchDateType()), //where 조건 (날짜)
                        searchSellStatusEq(goodsSearchDTO.getSearchSellStatus()),  //and (판매 상태)
                        searchByLike(goodsSearchDTO.getSearchBy(), goodsSearchDTO.getSearchQuery())) //and (조회 유형)
                .fetchOne(); // 총 검색 결과

        return new PageImpl<>(results,pageable,total);
    }

    public Page<Goods> getAllGoodsList(GoodsSearchDTO goodsSearchDTO, Pageable pageable) {

        List<Goods> results = queryFactory //쿼리 생성
                .selectFrom(QGoods.goods) //엔티티 지정
                .orderBy(QGoods.goods.regTime.desc()) //등록일 기준
                .offset(pageable.getOffset()) //데이터 조회 시작 인덱스
                .limit(pageable.getPageSize()) //가지고 올 데이터 최대 개수
                .fetch();

        long total = queryFactory //쿼리 생성
                .select(Wildcard.count) //카운트
                .from(QGoods.goods) //엔티티 지정
                .fetchOne(); // 총 검색 결과

        return new PageImpl<>(results,pageable,total);
    }

    @Override
    public Page<MainGoodsDTO> getMainGoodsPage(GoodsSearchDTO goodsSearchDTO, Pageable pageable) {
        // 메인페이지 상품리스트
        QGoods goods = QGoods.goods;
        QGoodsImage goodsImage = QGoodsImage.goodsImage;

        List<MainGoodsDTO> content = queryFactory
                .select( //@QueryProjection select절에 대상 설정
                        new QMainGoodsDTO(
                                goods.id,
                                goods.goodsName,
                                goods.goodsDetail,
                                goodsImage.imgUrl,
                                goods.goodsPrice)
                )
                .from(goodsImage) // 상품 이미지 테이블
                .join(goodsImage.goods, goods) //내부 조인 (이미지 테이블의 상품 조인)
                .where(goodsImage.repImgYn.eq("Y"))
                .where(searchByKeyword(goodsSearchDTO.getSearchQuery()))
                .orderBy(goods.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count) //카운트
                .from(goodsImage)
                .join(goodsImage.goods, goods)
                .where(goodsImage.repImgYn.eq("Y"))
                .where(searchByKeyword(goodsSearchDTO.getSearchQuery()))
                .fetch().size(); // 총 검색 결과

        return new PageImpl<>(content, pageable, total);
    }
}
