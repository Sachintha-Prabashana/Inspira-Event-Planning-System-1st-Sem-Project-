package edu.ijse.inspira1stsemesterproject.model;

import edu.ijse.inspira1stsemesterproject.dto.EventSupplierDto;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class EventSupplierModel {
    private final ItemModel itemModel = new ItemModel();
    public boolean saveEventSuppliersList(ArrayList<EventSupplierDto> eventSupplierDtos) throws SQLException, ClassNotFoundException {

        for (EventSupplierDto eventSupplierDto : eventSupplierDtos) {
            boolean isEventSupplierSaved = saveEventSuppliers(eventSupplierDto);
            if (!isEventSupplierSaved) {
                return false;
            }

            boolean isItemUpdated = itemModel.reduceQty(eventSupplierDto);
            if (!isItemUpdated) {
                return false;
            }
        }
        return true;
    }

    private boolean saveEventSuppliers(EventSupplierDto eventSupplierDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO EventSupplier (event_id, supplier_id, itemQty, price) VALUES (?,?,?,?)",
                eventSupplierDto.getEventId(),
                eventSupplierDto.getSupplierId(),
                eventSupplierDto.getItemQuantity(),
                eventSupplierDto.getTotalPrice()
        );
    }

}
