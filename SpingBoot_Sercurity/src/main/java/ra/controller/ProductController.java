package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.Catalog;
import ra.model.entity.Image;
import ra.model.entity.Product;
import ra.model.repository.ImageRepository;
import ra.model.repository.ProductRepository;
import ra.model.service.CatalogService;
import ra.model.service.ImageService;
import ra.model.service.ProductService;
import ra.payload.request.ProductRequest;



import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ImageService imageService;

    @GetMapping
    public List<Product> getAllProduct(){
        return  productService.findAll();
    }
    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable("productId") int productId){
        return productService.findById(productId);
    }
    @PostMapping
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest){
        try {
            Product pro = new Product();
            pro.setProductName(productRequest.getProductName());
            pro.setPrice(productRequest.getPrice());
            pro.setQuantity(productRequest.getQuantity());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dateNow = new Date();
            String strNow = sdf.format(dateNow);
            try {
                pro.setCreatedProduct(sdf.parse(strNow));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            pro.setDescriptions(productRequest.getDescriptions());
            pro.setProductStatus(productRequest.isProductStatus());
            Catalog catalog = catalogService.findById(productRequest.getCatalogId());
            pro.setCatalog(catalog);
            pro = productService.save(pro);
            for (String str : productRequest.getListImage()) {
                Image image = new Image();
                image.setProduct(pro);
                image.setImageLink(str);
                image = imageService.saveOrUpdate(image);
                pro.getListImage().add(image);
            }
            return ResponseEntity.ok(pro);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok("ERROR!");
        }

    }
    @PutMapping("/{productId}")
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public Product updateProduct(@PathVariable("productId") int productId, @RequestBody ProductRequest productRequest){
        Product pro = productService.findById(productId);
        List<Image> listImage = imageService.findImageByIdProduct(productId);
        for (Image image: listImage) {
            imageService.deleteById(image.getImageId());
        }
        pro.setProductName(productRequest.getProductName());
        pro.setPrice(productRequest.getPrice());
        pro.setQuantity(productRequest.getQuantity());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateNow = new Date();
        String strNow = sdf.format(dateNow);
        try {
            pro.setCreatedProduct(sdf.parse(strNow));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        pro.setDescriptions(productRequest.getDescriptions());
        pro.setProductStatus(productRequest.isProductStatus());
        Catalog catalog = catalogService.findById(productRequest.getCatalogId());
        pro.setCatalog(catalog);
        pro = productService.save(pro);
        for (String str : productRequest.getListImage()) {
            Image image = new Image();
            image.setProduct(pro);
            image.setImageLink(str);
            image = imageService.saveOrUpdate(image);
            pro.getListImage().add(image);
        }
        //Cap nhat
        return productService.save(pro);
    }
    @DeleteMapping("/{productId}")
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteProduct(@PathVariable("productId") int productId){
        List<Image> listImage = imageService.findImageByIdProduct(productId);
        for (Image image: listImage) {
            imageService.deleteById(image.getImageId());
        }
        productService.delete(productId);
    }
    @GetMapping("/search")
    public List<Product> seachByName(@RequestParam("productName") String productName){
        return productService.search(productName);
    }

    @GetMapping("/sortByName")
    public ResponseEntity<List<Product>> sortProductByProductName(@RequestParam("direction") String direction) {
        List<Product> listProduct = productService.sortProductByProductName(direction);
        return new ResponseEntity<>(listProduct, HttpStatus.OK);
    }

    @GetMapping("/sortByNameAndPrice")
    public ResponseEntity<List<Product>> sortProductByNameAndPrice(@RequestParam("directionName") String directionName,
                                                              @RequestParam("directionPrice") String directionPrice) {
        List<Product> listProduct = productService.sortByNameAndPrice(directionName, directionPrice);
        return new ResponseEntity<>(listProduct, HttpStatus.OK);
    }

    @GetMapping("/sortByNameAndPriceAndId")
    public ResponseEntity<List<Product>> sortByNameAndPriceAndId(@RequestParam("directionName") String directionName,
                                                               @RequestParam("directionPrice") String directionPrice,
                                                               @RequestParam("directionId") String directionId) {
        List<Product> listProduct = productService.sortByName_Price_Id(directionName, directionPrice,directionId);
        return new ResponseEntity<>(listProduct, HttpStatus.OK);
    }
    @GetMapping("/getPagging")
    public ResponseEntity<Map<String,Object>> getPagging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Product> pageProduct = productService.getPagging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("product",pageProduct.getContent());
        data.put("total",pageProduct.getSize());
        data.put("totalItems",pageProduct.getTotalElements());
        data.put("totalPages",pageProduct.getTotalPages());
        return  new ResponseEntity<>(data,HttpStatus.OK);
    }

    @GetMapping("/getPaggingAndSortByName")
    public ResponseEntity<Map<String,Object>> getPaggingAndSortByName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam String direction){
        Sort.Order order;
        if (direction.equals("asc")){
            order=new Sort.Order(Sort.Direction.ASC,"productName");
        }else{
            order=new Sort.Order(Sort.Direction.DESC,"productName");
        }
        Pageable pageable = PageRequest.of(page,size,Sort.by(order));
        Page<Product> pageProduct = productService.getPagging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("product",pageProduct.getContent());
        data.put("total",pageProduct.getSize());
        data.put("totalItems",pageProduct.getTotalElements());
        data.put("totalPages",pageProduct.getTotalPages());
        return  new ResponseEntity<>(data,HttpStatus.OK);
    }

}
