package edu.ijse.inspira1stsemesterproject.dto.tm;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ServiceTM {
    private String serviceId;
    private String serviceType;
    private double price;
}
