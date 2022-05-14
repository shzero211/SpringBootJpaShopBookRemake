package com.jpabook.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.jpabook.constant.ItemSellStatus;
import com.jpabook.dto.ItemSearchDto;
import com.jpabook.dto.MainItemDto;
import com.jpabook.dto.QMainItemDto;
import com.jpabook.entity.Item;
import com.jpabook.entity.QItem;
import com.jpabook.entity.QItemImg;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {
	private JPAQueryFactory queryFactory;
	public ItemRepositoryCustomImpl(EntityManager em) {
		this.queryFactory=new JPAQueryFactory(em);
	}
	
	private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
		return searchSellStatus==null?null:QItem.item.itemSellStatus.eq(searchSellStatus);
	}
	private BooleanExpression regDtsAfter(String searchDateType) {
		LocalDateTime dateTime=LocalDateTime.now();
		if(StringUtils.equals("all", searchDateType)||searchDateType==null) {
			return null;
		}else if(StringUtils.equals("1d", searchDateType)) {
			dateTime=dateTime.minusDays(1);
		}else if(StringUtils.equals("1w", searchDateType)) {
			dateTime=dateTime.minusWeeks(1);
		}else if(StringUtils.equals("1m", searchDateType)) {
			dateTime=dateTime.minusMonths(1);
		}else if(StringUtils.equals("6m", searchDateType)) {
			dateTime=dateTime.minusMonths(6);
		}
		return QItem.item.regTime.after(dateTime);
	}
	
	private BooleanExpression searchByLike(String searchBy,String searchQuery) {
		if(StringUtils.equals("itemNm", searchBy)) {
			return QItem.item.itemNm.like("%"+searchQuery+"%");
		}else if (StringUtils.equals("createdBy", searchBy)) {
			return QItem.item.createdBy.like("%"+searchQuery+"%");
		}
		return null;
	}
	@Override
	public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		QueryResults<Item> results=queryFactory
				.selectFrom(QItem.item)
				.where(regDtsAfter(itemSearchDto.getSearchDateType()),
						searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
						searchByLike(itemSearchDto.getSearchBy(),itemSearchDto.getSearchQuery()))
				.orderBy(QItem.item.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();
			List<Item> content=results.getResults();	
			long total =results.getTotal();
			
		return new PageImpl<>(content,pageable,total);
	}
	
	private BooleanExpression itemNmLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery)?null:QItem.item.itemNm.like("%"+searchQuery+"%");
	}
	
	@Override
	public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		QItem item=QItem.item;
		QItemImg itemImg=QItemImg.itemImg;
		
		QueryResults<MainItemDto> results=
				queryFactory.select(
						new QMainItemDto(
								item.id,
								item.itemNm,
								itemImg.imgUrl,
								item.itemDetail,
								item.price))
				.from(itemImg)
				.join(itemImg.item(),item)
				.where(itemImg.repimgYn.eq("Y"))
				.where(itemNmLike(itemSearchDto.getSearchQuery()))
				.orderBy(item.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();
		
		List<MainItemDto> content=results.getResults();
		long total=results.getTotal();
		return new PageImpl<>(content,pageable,total);
	}

}
