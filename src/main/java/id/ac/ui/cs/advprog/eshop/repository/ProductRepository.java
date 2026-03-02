package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.Iterator;

public interface ProductRepository {
    public Product create(Product product);
    public Product findById(String id);
    public Product update(String id, Product updatedProduct);
    public void delete(String id);
    public Iterator<Product> findAll();
}