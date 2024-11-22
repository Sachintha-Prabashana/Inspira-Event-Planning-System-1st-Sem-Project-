package edu.ijse.inspira1stsemesterproject.model;

import edu.ijse.inspira1stsemesterproject.dto.EmployeeDto;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {
    public String getNextEmployeeId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT employee_id FROM employee ORDER BY employee_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last employee ID, e.g., "EM001"

            if (lastId != null && lastId.startsWith("EM")) { // Check if the format is correct
                String numericPart = lastId.substring(2); // Extract the numeric part, e.g., "001"

                try {
                    int idNumber = Integer.parseInt(numericPart); // Convert to integer
                    int newIdIndex = idNumber + 1; // Increment the number
                    return String.format("EM%03d", newIdIndex); // Format back to "EMnnn"
                } catch (NumberFormatException e) {
                    // Handle case where numeric part is invalid
                    System.out.println("Invalid numeric part in employee ID: " + numericPart);
                    return "EM001"; // Return default in case of format error
                }
            }
        }
        return "EM001"; // Return default ID if no records are found
    }

    public ArrayList<EmployeeDto> getAllEmployees() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from employee");

        ArrayList<EmployeeDto> employeeDtos = new ArrayList<>();

        while (rst.next()) {
            EmployeeDto dto = new EmployeeDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getDouble(6),
                    rst.getString(7),
                    rst.getString(8)

            );
            employeeDtos.add(dto);
        }
        return employeeDtos;
    }

    public boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into employee values(?,?,?,?,?,?,?,?)",
                dto.getEmployeeId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getJobPosition(),
                dto.getJoinDate(),
                dto.getSalary(),
                dto.getEmail(),
                dto.getBookingId()
        );
    }

    public boolean updateCustomer(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update employee set  first_name = ?,  last_name = ?, position = ?, Join_date = ?, salary = ?, email = ?, booking_id  = ? where employee_id = ?",
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getJobPosition(),
                employeeDto.getJoinDate(),
                employeeDto.getSalary(),
                employeeDto.getEmail(),
                employeeDto.getBookingId(),
                employeeDto.getEmployeeId()
        );
    }

    public boolean deleteEmployee(String employeeId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from employee where employee_id =?", employeeId);
    }
}
