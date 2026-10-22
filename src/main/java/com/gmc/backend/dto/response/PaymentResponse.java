package com.gmc.backend.dto.response;

import com.gmc.backend.model.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {

    private Long paymentId;
    private Long invoiceId;
    private BigDecimal amount;
    private String method;
    private PaymentStatus status;
    private String stripeClientSecret;
    private LocalDateTime createdAt;
}
