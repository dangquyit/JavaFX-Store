package utc2.itk62.store.services;

import utc2.itk62.store.common.Paging;
import utc2.itk62.store.models.Supplier;
import utc2.itk62.store.repositories.SupplierRepo;

import java.util.List;

public class SupplierService {
    private static final SupplierRepo supplierRepo = new SupplierRepo();

    public List<Supplier> getAllSuppliers() {
        return supplierRepo.getAllSupplier(new Paging(0, 0));
    }

    public boolean createSupplier(Supplier supplier) {
        if (supplierRepo.createSupplier(supplier) <= 0) {
            return false;
        }
        return true;
    }
}
