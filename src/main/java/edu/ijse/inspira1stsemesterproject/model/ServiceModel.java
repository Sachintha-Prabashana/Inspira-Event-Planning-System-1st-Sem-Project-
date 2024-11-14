package edu.ijse.inspira1stsemesterproject.model;

import edu.ijse.inspira1stsemesterproject.dto.CustomerDto;
import edu.ijse.inspira1stsemesterproject.dto.ServiceDto;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceModel {
    public ArrayList<String> getAllServiceIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select service_id from service");

        ArrayList<String> serviceIds = new ArrayList<>();

        while (rst.next()) {
            serviceIds.add(rst.getString(1));
        }

        return serviceIds;
    }

    public ServiceDto findById(String selectedServiceId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from service where service_id=?", selectedServiceId);

        if (rst.next()) {
            return new ServiceDto(
                    rst.getString(1),
                    rst.getDouble(2),
                    rst.getString(3)
            );
        }
        return null;
    }
}
