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
                    return false;
                }
            } catch (Exception e) {
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
