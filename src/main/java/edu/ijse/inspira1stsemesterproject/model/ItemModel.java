package edu.ijse.inspira1stsemesterproject.model;

import edu.ijse.inspira1stsemesterproject.dto.EventDto;
import edu.ijse.inspira1stsemesterproject.dto.EventSupplierDto;
import edu.ijse.inspira1stsemesterproject.dto.ItemDto;
import edu.ijse.inspira1stsemesterproject.dto.SupplierDto;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemModel {
    public String getNextItemId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select item_id from item order by item_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("I%03d", newIdIndex); // Return the new customer ID in format Cnnn
        }
        return "I001";
    }

    public ArrayList<ItemDto> getAllItems() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from item");

        ArrayList<ItemDto> itemDtos = new ArrayList<>();

        while (rst.next()) {
            ItemDto itemDto = new ItemDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getString(6)

            );
            itemDtos.add(itemDto);
        }
        return itemDtos;
    }

    public ArrayList<String> getAllItemIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select item_id from item");

        ArrayList<String> itemIds = new ArrayList<>();

        while (rst.next()) {
            itemIds.add(rst.getString(1));
        }

        return itemIds;
    }

    public ItemDto findById(String selectedItemId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from item where item_id=?", selectedItemId);

        if (rst.next()) {
            return new ItemDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getString(6)
            );
        }
        return null;
    }

    public boolean reduceQty(EventSupplierDto eventSupplierDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update item set quantity = quantity - ? where item_id = ?",
                eventSupplierDto.getItemQuantity(),  // Quantity to reduce
                eventSupplierDto.getItemId()        // Item ID to identify the item

        );
    }
}
