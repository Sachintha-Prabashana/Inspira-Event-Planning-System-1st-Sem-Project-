package edu.ijse.inspira1stsemesterproject.model;

import edu.ijse.inspira1stsemesterproject.dto.EventSupplierDto;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class EventSupplierModel {
    private final ItemModel itemModel = new ItemModel();
    public boolean saveEventSuppliersList(ArrayList<EventSupplierDto> eventSupplierDtos) throws SQLException, ClassNotFoundException {
        // Iterate through each order detail in the list
        for (EventSupplierDto eventSupplierDto : eventSupplierDtos) {
            // @isOrderDetailsSaved: Saves the individual order detail
            boolean isEventSuppliersSaved = saveEventSuppliers(eventSupplierDto);
            if (!isEventSuppliersSaved) {
                // Return false if saving any order detail fails
                return false;
            }

            // @isItemUpdated: Updates the item quantity in the stock for the corresponding order detail
            boolean isItemUpdated = itemModel.reduceQty(eventSupplierDto);
            if (!isItemUpdated) {
                // Return false if updating the item quantity fails
                return false;
            }
        }
        // Return true if all order details are saved and item quantities updated successfully
        return true;
    }

    private boolean saveEventSuppliers(EventSupplierDto eventSupplierDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into EventSupplier (event_id, supplier_id, itemQty, price) values (?,?,?,?)",
                eventSupplierDto.getEventId(),
                eventSupplierDto.getSupplierId(),
                eventSupplierDto.getItemQuantity(),
                eventSupplierDto.getTotalPrice()
        );
    }
}
