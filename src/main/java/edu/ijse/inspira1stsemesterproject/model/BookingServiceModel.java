package edu.ijse.inspira1stsemesterproject.model;

import edu.ijse.inspira1stsemesterproject.dto.BookingServiceDto;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class BookingServiceModel {
    public boolean saveBookingServiceList(ArrayList<BookingServiceDto> bookingServiceDtos) throws SQLException, ClassNotFoundException {
        for (BookingServiceDto bookingServiceDto : bookingServiceDtos) {
            try {
                // Save individual booking service details
                boolean isOrderDetailsSaved = saveBookingServiceDetails(bookingServiceDto);
                if (!isOrderDetailsSaved) {
                    // Log the error if saving fails
                    System.err.println("Failed to save booking service details for booking ID: " + bookingServiceDto.getBookingId());
                    return false;
                }
            } catch (SQLException | ClassNotFoundException e) {
                // Log the exception details
                System.err.println("Error saving booking service details: " + e.getMessage());
                return false;
            }
        }
        return true;
    }

    private boolean saveBookingServiceDetails(BookingServiceDto bookingServiceDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO BookingService (booking_id, service_id, date) VALUES (?, ?, ?)",  // Match the column names if needed
                bookingServiceDto.getBookingId(),
                bookingServiceDto.getServiceId(),
                bookingServiceDto.getBookingDate()
        );
    }

}
