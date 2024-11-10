package edu.ijse.inspira1stsemesterproject.model;

import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookingModel {
    public ArrayList<String> getAllBookingIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select booking_id from booking");

        ArrayList<String> bookingIds = new ArrayList<>();

        while (rst.next()) {
           bookingIds.add(rst.getString(1));
        }

        return bookingIds;
    }
}
