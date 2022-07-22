package com.coco.service;

import com.coco.domain.Order;
import org.apache.ibatis.annotations.Param;

public interface OrderService {
    void create(Order order);

    void update(@Param("userId")Long userId, @Param("status")Integer status);
}
