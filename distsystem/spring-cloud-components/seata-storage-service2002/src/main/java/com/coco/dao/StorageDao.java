package com.coco.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StorageDao {
    //扣减库存：根据产品ID扣除
    void decrease(@Param("productId") Long productId, @Param("count") Integer count);
}
