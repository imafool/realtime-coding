package com.coco.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT-SERVICE", fallback = PaymentFallbackService.class)
public interface IPaymentService {
    @GetMapping("/pay/ok")
    String okReturn();

    @GetMapping("/pay/timeout")
    String timeoutOrError();

    @GetMapping("/pay/circuit")
    String circuit();
}
