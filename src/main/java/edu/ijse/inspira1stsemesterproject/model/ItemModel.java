package edu.ijse.inspira1stsemesterproject.model;

import edu.ijse.inspira1stsemesterproject.dto.EventSupplierDto;
import edu.ijse.inspira1stsemesterproject.dto.ItemDto;
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

    public ArrayList<String> getAllItemIds(String supplierId) throws SQLException, ClassNotFoundException {
        String query = "SELECT item_id FROM item WHERE supplier_id = ?";

        ResultSet rst = CrudUtil.execute(query, supplierId);

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
        if (eventSupplierDto.getItemQuantity() <= 0) {
            System.err.println("Invalid quantity to reduce: " + eventSupplierDto.getItemQuantity());
            return false;
        }

        // Ensure that the item ID is not null or empty
        if (eventSupplierDto.getItemId() == null || eventSupplierDto.getItemId().isEmpty()) {
            System.err.println("Invalid item ID: " + eventSupplierDto.getItemId());
            return false;
        }

        // Update the item quantity in the database
        return CrudUtil.execute(
                "UPDATE item SET quantity = quantity - ? WHERE item_id = ? AND quantity >= ?",
                eventSupplierDto.getItemQuantity(),  // Quantity to reduce
                eventSupplierDto.getItemId(),       // Item ID to identify the item
                eventSupplierDto.getItemQuantity()  // Ensure enough quantity is available
        );
    }


    public boolean deleteItem(String itemId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from item where item_id=?", itemId);
    }

    public boolean saveItem(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into item values(?,?,?,?,?,?)",
                itemDto.getItemId(),
                itemDto.getItemName(),
                itemDto.getItemDescription(),
                itemDto.getItemPrice(),
                itemDto.getItemQuantity(),
                itemDto.getSupplierId()


        );
    }

    public boolean updateItem(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update item set  item_name = ?, description = ? , cost = ?, quantity = ?, supplier_id = ? where item_id = ?",
                itemDto.getItemName(),
                itemDto.getItemDescription(),
                itemDto.getItemPrice(),
                itemDto.getItemQuantity(),
                itemDto.getSupplierId(),
                itemDto.getItemId()
        );
    }
}
