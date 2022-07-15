package com.coco.service;

import org.springframework.stereotype.Component;

//降级处理
@Component
public class PaymentFallbackService implements IPaymentService{
    @Override
    public String okReturn() {
        return "ok, fallback";
    }

    @Override
    public String timeoutOrError() {
        return "timeout or error, fallback";
    }

    @Override
    public String circuit() {
        return "circuit, fallback";
    }
}
