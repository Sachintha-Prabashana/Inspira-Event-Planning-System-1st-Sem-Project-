package edu.ijse.inspira1stsemesterproject.model;

import edu.ijse.inspira1stsemesterproject.dto.CustomerDto;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {
    public String getNextCustomerId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select customer_id from customer order by customer_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("C%03d", newIdIndex); // Return the new customer ID in format Cnnn
        }
        return "C001";
    }

    public ArrayList<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from customer");

        ArrayList<CustomerDto> customerDTOS = new ArrayList<>();

        while (rst.next()) {
            CustomerDto customerDTO = new CustomerDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getDate(7)
            );
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    public boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into customer values(?,?,?,?,?,?,?)",
                customerDto.getCustomerId(),
                customerDto.getCustomerTitle(),
                customerDto.getFirstName(),
                customerDto.getLastName(),
                customerDto.getNic(),
                customerDto.getEmail(),
                customerDto.getRegistrationDate()
        );
    }

    public boolean deleteEvent(String customerId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from customer where customer_id=?", customerId);
    }
}
