package edu.ijse.inspira1stsemesterproject.model;

import edu.ijse.inspira1stsemesterproject.db.DBConnection;
import edu.ijse.inspira1stsemesterproject.dto.BookingDto;
import edu.ijse.inspira1stsemesterproject.dto.CustomerDto;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
            connection.setAutoCommit(false);

            boolean isBookingSaved = CrudUtil.execute(
                    "INSERT INTO booking (booking_id, customer_id, capacity, venue, booking_date, status) VALUES (?, ?, ?, ?, ?, ?)",
                    bookingDto.getBookingId(),
                    bookingDto.getCustomerId(),
                    bookingDto.getCapacity(),
                    bookingDto.getVenue(),
                    bookingDto.getBookingDate(),
                    "available" // Default status for a new booking
            );

            if (isBookingSaved) {
                boolean isOrderDetailListSaved = bookingServiceModel.saveBookingServiceList(bookingDto.getBookingServiceDtos());
                if (isOrderDetailListSaved) {
                    connection.commit();
                    return true;
                }
            }

            connection.rollback();
            return false;

        } catch (Exception e) {
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

    public boolean updateBookingStatusToUsed(String bookingId) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        if (connection == null) {
            throw new SQLException("Failed to obtain database connection.");
        }

        try {
            String query = "UPDATE booking SET status = ? WHERE booking_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "used");
            preparedStatement.setString(2, bookingId);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public ArrayList<String> getAvailableBookingIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT booking_id FROM booking WHERE status = 'available'");

        ArrayList<String> bookingIds = new ArrayList<>();

        while (rst.next()) {
            bookingIds.add(rst.getString(1));
        }

        return bookingIds;
    }
}
