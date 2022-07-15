package com.coco.service.impl;

import com.coco.dao.PaymentDao;
import com.coco.entites.Payment;
import com.coco.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {

    //Java自带的，依赖注入
    //@Autowired是Spring的
    @Resource
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
