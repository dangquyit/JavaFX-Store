package utc2.itk62.sneaker.services;

import utc2.itk62.sneaker.models.Product;
import utc2.itk62.sneaker.repositories.ProductRepo;

import java.util.List;

public class ProductService {
    private static final ProductRepo productRepo = new ProductRepo();

    public List<Product> getProductsByIdCategory(int idCategory) {
        return productRepo.getProductListByIdCategory(idCategory);
    }
}