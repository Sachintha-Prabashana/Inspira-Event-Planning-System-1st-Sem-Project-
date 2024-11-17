package edu.ijse.inspira1stsemesterproject.dto.tm;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class PaymentTM {
    private String paymentId;
    private Date paymentDate;
    private Double paymentAmount;
    private String bookingId;
}
