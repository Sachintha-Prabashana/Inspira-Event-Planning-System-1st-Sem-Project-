package edu.ijse.inspira1stsemesterproject.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ServiceDto {
    private String service_id;
    private String service_type;
    private double price;

}
