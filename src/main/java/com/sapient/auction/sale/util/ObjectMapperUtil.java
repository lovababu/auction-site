package com.sapient.auction.sale.util;

import com.sapient.auction.common.model.SaleVO;
import com.sapient.auction.sale.entity.Sale;
import org.springframework.beans.BeanUtils;

/**
 * Object conversion util class, used to convert Entity to Model and vice versa.
 *
 * Created by dpadal on 11/14/2016.
 */
public final class ObjectMapperUtil {

    public static Sale saleEntity(SaleVO saleVO) {
        Sale saleEntity = new Sale();
        BeanUtils.copyProperties(saleVO, saleEntity);
        return saleEntity;
    }

    public static SaleVO saleEntity(Sale saleEntity) {
        SaleVO saleVO = new SaleVO();
        BeanUtils.copyProperties(saleEntity, saleVO);
        return saleVO;
    }
}
