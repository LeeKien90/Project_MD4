package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.model.entity.Product;
import ra.model.repository.ProductRepository;
import ra.model.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(int id) {
        return productRepository.findById(id).get();
    }

    @Override
    public Product save(Product product) {
        productRepository.save(product);
        return product;
    }

    @Override
    public void delete(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> searchProductByCatalogid(int catalogId) {
        return productRepository.findByCatalog_CatalogId(catalogId);
    }

    @Override
    public List<Product> search(String name) {
        return productRepository.searchByProductNameContains(name);

    }

    @Override
    public List<Product> sortProductByProductName(String direction) {
        if (direction.equals("asc")) {
            return productRepository.findAll(Sort.by("productName").ascending());
        } else {
            return productRepository.findAll(Sort.by("productName").descending());
        }
    }

    @Override
    public List<Product> sortByNameAndPrice(String directionName, String directionPrice) {
        if (directionName.equals("asc")) {
            if (directionPrice.equals("asc")) {
                return productRepository.findAll(Sort.by("productName").and(Sort.by("price")));
            } else {
                return productRepository.findAll(Sort.by("productName").and(Sort.by("price").descending()));
            }
        } else {
            if (directionPrice.equals("asc")) {
                return productRepository.findAll(Sort.by("productName").descending().and(Sort.by("price")));
            } else {
                return productRepository.findAll(Sort.by("productName").descending().and(Sort.by("price").descending()));
            }
        }

    }

    @Override
    public List<Product> sortByName_Price_Id(String directionName, String directionPrice, String directionId) {
        List<Sort.Order> listOrder = new ArrayList<>();
        if (directionName.equals("asc")){
            listOrder.add(new Sort.Order(Sort.Direction.ASC,"productName"));
        }else{
            listOrder.add(new Sort.Order(Sort.Direction.DESC,"productName"));
        }
        if (directionPrice.equals("asc")){
            listOrder.add(new Sort.Order(Sort.Direction.ASC,"price"));
        }else{
            listOrder.add(new Sort.Order(Sort.Direction.DESC,"price"));
        }
        if (directionId.equals("id")){
            listOrder.add(new Sort.Order(Sort.Direction.ASC,"productId"));
        }else{
            listOrder.add(new Sort.Order(Sort.Direction.DESC,"productId"));
        }

        return productRepository.findAll(Sort.by(listOrder));
    }

    @Override
    public Page<Product> getPagging(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
