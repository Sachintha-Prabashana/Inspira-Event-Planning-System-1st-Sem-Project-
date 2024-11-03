package edu.ijse.inspira1stsemesterproject.dto;
import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EventDto {
    private String eventId;
    private String eventType;
    private String eventName;
    private String description;
    private Double budget;
    private String venue;
    private Date date;
    private Time time;
}
