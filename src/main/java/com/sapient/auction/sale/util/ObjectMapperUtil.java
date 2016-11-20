package com.sapient.auction.sale.util;

import com.sapient.auction.common.model.BidVO;
import com.sapient.auction.common.model.SaleVO;
import com.sapient.auction.common.model.UserVO;
import com.sapient.auction.sale.entity.Bid;
import com.sapient.auction.sale.entity.Sale;
import com.sapient.auction.user.entity.User;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

/**
 * Object conversion util class, used to convert Entity to Model and vice versa.
 * <p>
 * Created by dpadal on 11/14/2016.
 */
public final class ObjectMapperUtil {

    public static Sale saleEntity(SaleVO saleVO) {
        Sale saleEntity = new Sale();
        BeanUtils.copyProperties(saleVO, saleEntity);
        return saleEntity;
    }

    public static SaleVO saleVO(Sale saleEntity) {
        SaleVO saleVO = SaleVO.builder()
                .withId(saleEntity.getId())
                .withStartTime(saleEntity.getStartTime())
                .withEndTime(saleEntity.getEndTime())
                .withPrice(saleEntity.getPrice())
                .withProductId(saleEntity.getProductId())
                .withProductName(saleEntity.getProductName())
                .withProductDesc(saleEntity.getProductDesc())
                .withProductType(saleEntity.getProductType())
                .withProductImageUrl(saleEntity.getProductImageUrl())
                .withUserVO(
                        UserVO.builder().withEmail(saleEntity.getUser().getEmail())
                                .withContact(saleEntity.getUser().getContact()).build()
                ).withBids(
                        saleEntity.getBids().stream().map(bid -> BidVO.builder()
                                .withId(bid.getId())
                                .withPrice(bid.getPrice())
                                .withTime(bid.getTime())
                                .withUser(
                                        UserVO.builder().withEmail(bid.getUser().getEmail()).build()
                                ).build()
                        ).collect(Collectors.toSet())
                ).build();
        return saleVO;
    }
    
    public static Bid bidEntity(BidVO bidVO) {
        Bid bid = new Bid();
        BeanUtils.copyProperties(bidVO, bid);
        User userEntity = new User();
        BeanUtils.copyProperties(bidVO.getUser(), userEntity);
        bid.setUser(userEntity);
        Sale sale = new Sale();
        BeanUtils.copyProperties(bidVO.getSale(), sale);
        bid.setSale(sale);
        return bid;
    }
    
    public static BidVO bidVO(Bid bid) {
    	BidVO bidVO = BidVO.builder()
    					.withId(bid.getId())
    					.withPrice(bid.getPrice())
    					.withTime(bid.getTime())
    					.withUser(UserVO.builder()
    								.withEmail(bid.getUser().getEmail())
                                	.withContact(bid.getUser().getContact()).build())
    					.withSale(SaleVO.builder()
			    	                .withId(bid.getSale().getId())
			    	                .withStartTime(bid.getSale().getStartTime())
			    	                .withEndTime(bid.getSale().getEndTime())
			    	                .withPrice(bid.getSale().getPrice())
			    	                .withProductId(bid.getSale().getProductId())
			    	                .withProductName(bid.getSale().getProductName())
			    	                .withProductDesc(bid.getSale().getProductDesc())
			    	                .withProductType(bid.getSale().getProductType())
			    	                .withProductImageUrl(bid.getSale().getProductImageUrl()).build()).build();

        return bidVO;
    }
    
}
