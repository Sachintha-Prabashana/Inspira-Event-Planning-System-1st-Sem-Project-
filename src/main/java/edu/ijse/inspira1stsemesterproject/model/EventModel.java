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
    

    public boolean saveEvent(EventDto eventDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            // @autoCommit: Disables auto-commit to manually control the transaction
            connection.setAutoCommit(false); // 1

            // @isOrderSaved: Saves the order details into the orders table
            boolean isOrderSaved = CrudUtil.execute(
                    "insert into event values (?,?,?,?,?,?,?)",
                    eventDto.getEventId(),
                    eventDto.getBookingId(),
                    eventDto.getEventType(),
                    eventDto.getEventName(),
                    eventDto.getBudget(),
                    eventDto.getVenue(),
                    eventDto.getDate()
            );
            // If the order is saved successfully
            if (isOrderSaved) {
                // @isOrderDetailListSaved: Saves the list of order details
                boolean isOrderDetailListSaved = eventSupplierModel.saveEventSuppliersList(eventDto.getEventSupplierDtos());
                if (isOrderDetailListSaved) {
                    // @commit: Commits the transaction if both order and details are saved successfully
                    connection.commit(); // 2
                    return true;
                }
            }
            // @rollback: Rolls back the transaction if order details saving fails
            connection.rollback(); // 3
            return false;
        } catch (Exception e) {
            // @catch: Rolls back the transaction in case of any exception
            connection.rollback();
            return false;
        } finally {
            // @finally: Resets auto-commit to true after the operation
            connection.setAutoCommit(true); // 4
        }
    }

}
