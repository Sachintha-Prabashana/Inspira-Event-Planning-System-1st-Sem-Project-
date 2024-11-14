package edu.ijse.inspira1stsemesterproject.model;

import edu.ijse.inspira1stsemesterproject.db.DBConnection;
import edu.ijse.inspira1stsemesterproject.dto.BookingDto;
import edu.ijse.inspira1stsemesterproject.dto.CustomerDto;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class BookingModel {

    private final BookingServiceModel bookingServiceModel = new BookingServiceModel();

    public ArrayList<String> getAllBookingIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select booking_id from booking");

        ArrayList<String> bookingIds = new ArrayList<>();

        while (rst.next()) {
           bookingIds.add(rst.getString(1));
        }

        return bookingIds;
    }

    public String getNextBookingId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select booking_id from booking order by booking_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("B%03d", newIdIndex); // Return the new customer ID in format Cnnn
        }
        return "B001";
    }

    public boolean saveBooking(BookingDto bookingDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        if (connection == null) {
            throw new SQLException("Failed to obtain database connection.");
        }

        try {
            // Disable auto-commit for transaction control
            connection.setAutoCommit(false);

            // Insert booking
            boolean isBookingSaved = CrudUtil.execute(
                    "INSERT INTO booking VALUES (?, ?, ?, ?, ?)",
                    bookingDto.getBookingId(),
                    bookingDto.getCustomerId(),
                    bookingDto.getCapacity(),
                    bookingDto.getVenue(),
                    bookingDto.getBookingDate()
            );

            if (isBookingSaved) {
                // Save booking details
                boolean isOrderDetailListSaved = bookingServiceModel.saveBookingServiceList(bookingDto.getBookingServiceDtos());
                if (isOrderDetailListSaved) {
                    // Commit transaction if both booking and details are saved
                    connection.commit();
                    return true;
                }
            }

            // Roll back if any save operation fails
            connection.rollback();
            return false;

        } catch (Exception e) {
            // Roll back and rethrow the exception for logging or further handling
            connection.rollback();
            throw new SQLException("Failed to save booking transaction", e);

        } finally {
            // Reset auto-commit and close the connection to release resources
            if (connection != null) {
                connection.setAutoCommit(true);
                //connection.close();
            }
        }
    }

}
