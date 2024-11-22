package edu.ijse.inspira1stsemesterproject.model;

import edu.ijse.inspira1stsemesterproject.db.DBConnection;
import edu.ijse.inspira1stsemesterproject.dto.EventDto;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;

public class EventModel {

    private final EventSupplierModel  eventSupplierModel = new EventSupplierModel();

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

    public boolean isVenueAvailable(String venue, java.sql.Date date) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT COUNT(*) FROM Event WHERE venue = ? AND event_date = ?",
                venue, date
        );
        if (resultSet.next()) {
            return resultSet.getInt(1) == 0; // Venue is available if count is 0
        }
        return false;
    }



    public boolean saveEvent(EventDto eventDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            boolean isEventSaved = CrudUtil.execute(
                    "INSERT INTO event (event_id, booking_id, event_type, event_name, budget, venue, event_date) VALUES (?,?,?,?,?,?,?)",
                    eventDto.getEventId(),
                    eventDto.getBookingId(),
                    eventDto.getEventType(),
                    eventDto.getEventName(),
                    eventDto.getBudget(),
                    eventDto.getVenue(),
                    eventDto.getDate()
            );

            if (!isEventSaved) {
                connection.rollback();
                return false;
            }

            boolean isEventSupplierListSaved = eventSupplierModel.saveEventSuppliersList(eventDto.getEventSupplierDtos());
            if (!isEventSupplierListSaved) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

}
