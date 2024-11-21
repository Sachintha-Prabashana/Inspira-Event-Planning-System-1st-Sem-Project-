package edu.ijse.inspira1stsemesterproject.model;

import edu.ijse.inspira1stsemesterproject.db.DBConnection;
import edu.ijse.inspira1stsemesterproject.dto.CustomerDto;
import edu.ijse.inspira1stsemesterproject.dto.UserDto;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public boolean isEmailExists(String text) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select email from user where email = ?";

        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, text);

        ResultSet rs = pst.executeQuery();

        return rs.next();
    }

    public boolean updateUser(UserDto userDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "update user set first_name = ?, last_name = ?, username = ?, email = ?, password = ? where user_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userDto.getFirstName());
        preparedStatement.setString(2, userDto.getLastName());
        preparedStatement.setString(3, userDto.getUsername());
        preparedStatement.setString(4, userDto.getEmail());
        preparedStatement.setString(5, userDto.getPassword());
        preparedStatement.setString(6, userDto.getUserId());

        return preparedStatement.executeUpdate()>0;
    }

    public ArrayList<UserDto> getAllUsers() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from user");

        ArrayList<UserDto> userDtos = new ArrayList<>();

        while (rst.next()) {
            UserDto userDto = new UserDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            userDtos.add(userDto);
        }
        return userDtos;
    }

    public ArrayList<String> getAllUserIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select user_id from user");

        ArrayList<String> user_ids = new ArrayList<>();

        while (rst.next()) {
            user_ids.add(rst.getString(1));
        }

        return user_ids;
    }

    public UserDto findById(String selectedUserId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from user where user_id=?", selectedUserId);

        if (rst.next()) {
            return new UserDto(
                    rst.getString(1),  // user ID
                    rst.getString(2),  // fName
                    rst.getString(3),  // lname
                    rst.getString(4), //uname
                    rst.getString(5), //email
                    rst.getString(6) //password
            );
        }
        return null;
    }
}
