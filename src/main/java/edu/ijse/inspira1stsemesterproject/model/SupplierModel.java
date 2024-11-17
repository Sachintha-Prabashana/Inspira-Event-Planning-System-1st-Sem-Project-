package edu.ijse.inspira1stsemesterproject.model;

import edu.ijse.inspira1stsemesterproject.dto.SupplierDto;
import edu.ijse.inspira1stsemesterproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {
    public String getNextSupplierId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select supplier_id from supplier order by supplier_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("S%03d", newIdIndex); // Return the new customer ID in format Cnnn
        }
        return "S001";
    }

    public ArrayList<SupplierDto> getAllSuppliers() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from supplier");

        ArrayList<SupplierDto> supplierDtos = new ArrayList<>();

        while (rst.next()) {
            SupplierDto supplierDto = new SupplierDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
            supplierDtos.add(supplierDto);
        }
        return supplierDtos;
    }

    public ArrayList<String> getAllSupplierIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select supplier_id from supplier");

        ArrayList<String> supplierIds = new ArrayList<>();

        while (rst.next()) {
            supplierIds.add(rst.getString(1));
        }

        return supplierIds;
    }

    public SupplierDto findById(String selectedSupplierId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from supplier where supplier_id=?", selectedSupplierId);

        if (rst.next()) {
            return new SupplierDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
        }
        return null;

    }

    public boolean saveSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into supplier values(?,?,?)",
                supplierDto.getSupplierId(),
                supplierDto.getSupplierName(),
                supplierDto.getEmail()

        );
    }

    public boolean updateSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update supplier set  supplier_name = ?, email = ? where supplier_id = ?",
                supplierDto.getSupplierName(),
                supplierDto.getEmail(),
                supplierDto.getSupplierId()
        );
    }

    public boolean deleteSupplier(String supplierId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from supplier where supplier_id=?", supplierId);
    }
}
