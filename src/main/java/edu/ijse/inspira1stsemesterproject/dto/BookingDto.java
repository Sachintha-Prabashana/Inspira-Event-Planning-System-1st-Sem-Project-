package edu.ijse.inspira1stsemesterproject.dto;


import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private String bookingId;
    private String customerId;
    private int capacity;
    private String venue;
    private Date bookingDate;

    private ArrayList<BookingServiceDto> bookingServiceDtos;

}
