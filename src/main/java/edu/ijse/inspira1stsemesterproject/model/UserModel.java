package edu.ijse.inspira1stsemesterproject.model;

import edu.ijse.inspira1stsemesterproject.db.DBConnection;
import edu.ijse.inspira1stsemesterproject.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public boolean validateUser(String username, String password) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet rst = statement.executeQuery();

        if (rst.next()) {
            return true;
        }
        return false;
    }
    public String getNextUserId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select user_id from user order by user_id desc limit 1";

        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String string = rs.getString(1);
            String subString = string.substring(1);
            int lastIdIndex = Integer.parseInt(subString);
            int nextIdIndex = lastIdIndex + 1;

            String nextUserId = String.format("U%03d", nextIdIndex);
            return nextUserId;
        }
        return "U001";
    }
    public boolean saveUser(UserDto userDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "insert into user values (?,?,?,?,?,?)";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, userDto.getUserId());
        pst.setString(2, userDto.getFirstName());
        pst.setString(3, userDto.getLastName());
        pst.setString(4, userDto.getUsername());
        pst.setString(5, userDto.getEmail());
        pst.setString(6, userDto.getPassword());

        return pst.executeUpdate() > 0;
    }
}
