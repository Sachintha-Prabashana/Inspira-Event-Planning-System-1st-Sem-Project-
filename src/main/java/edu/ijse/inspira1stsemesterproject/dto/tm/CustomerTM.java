package edu.ijse.inspira1stsemesterproject.dto.tm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerTM {
    private String customerId;
    private String customerTitle;
    private String firstName;
    private String lastName;
    private String nic;
    private String email;
    private Date registrationDate;
}
