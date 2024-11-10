package edu.ijse.inspira1stsemesterproject.dto.tm;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ServiceTM {
    private String service_id;
    private String service_type;
    private double price;
}
