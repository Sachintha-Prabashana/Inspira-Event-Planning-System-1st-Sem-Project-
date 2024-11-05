package edu.ijse.inspira1stsemesterproject.model;

import edu.ijse.inspira1stsemesterproject.dto.EventDto;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EventModel {
    public String getNextEventId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select event_id from event order by event_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("E%03d", newIdIndex); // Return the new customer ID in format Cnnn
        }
        return "E001";
    }

    public ArrayList<EventDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from event");

        ArrayList<EventDto> eventDtos = new ArrayList<>();

        while (rst.next()) {
            EventDto eventDto = new EventDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getString(6),
                    rst.getDate(7),
                    rst.getTime(8)

            );
            eventDtos.add(eventDto);
        }
        return eventDtos;
    }

    public boolean saveEvent(EventDto eventDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into event values(?,?,?,?,?,?,?,?)",
            eventDto.getEventId(),
            eventDto.getEventType(),
            eventDto.getEventName(),
            eventDto.getDescription(),
            eventDto.getBudget(),
            eventDto.getVenue(),
            eventDto.getDate(),
            eventDto.getTime()
        );
    }

    public boolean deleteEvent(String eventId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from event where event_id = ?", eventId);
    }

    public EventDto searchEvent(String eventId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from event where event_id = ?", eventId);

        if(resultSet.next()){
            return new EventDto(
                    resultSet.getString("event_id"),
                    resultSet.getString("event_type"),
                    resultSet.getString("event_name"),
                    resultSet.getString("description"),
                    resultSet.getDouble("budget"),
                    resultSet.getString("venue"),
                    resultSet.getDate("event_date"),
                    resultSet.getTime("event_time")
            );
        }
        return null;
    }
}
