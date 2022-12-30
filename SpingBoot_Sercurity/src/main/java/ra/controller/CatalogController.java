package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.Catalog;
import ra.model.service.CatalogService;
import ra.model.service.ProductService;
import ra.payload.request.CatalogRequest;
import ra.payload.response.CatalogDTO;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Catalog> getAllCatalog(){
        return  catalogService.findAll();
    }
    @GetMapping("/{catalogId}")
    public CatalogDTO getCatalogById(@PathVariable("catalogId") int catalogId){
        CatalogDTO catalogDTO = new CatalogDTO();
        Catalog catalog = catalogService.findById(catalogId);
        catalogDTO.setCatalogId(catalog.getCatalogId());
        catalogDTO.setCatalogName(catalog.getCatalogName());
        catalogDTO.setCatalogStatus(catalog.isCatalogStatus());
        catalogDTO.setListProduct(productService.searchProductByCatalogid(catalogId));
        return catalogDTO;
    }
    @PostMapping
    public Catalog createCatalog(@RequestBody Catalog catalog){
        return catalogService.save(catalog);
    }
    @PutMapping("/{catalogId}")
    public Catalog updateCatalog(@PathVariable("catalogId") int catalogId,@RequestBody Catalog catalog){
        Catalog catalogUpdate = catalogService.findById(catalogId);
        catalogUpdate.setCatalogName(catalog.getCatalogName());
        catalogUpdate.setCatalogStatus(catalog.isCatalogStatus());

        return catalogService.save(catalogUpdate);
    }
//    @DeleteMapping("/{catalogId}")
//    public void deleteCatalog(@PathVariable("catalogId") int catalogId){
//        catalogService.delete(catalogId);
//    }
//
    @GetMapping("/search")
    public List<Catalog> seachByName(@RequestParam("catalogName") String catalogName){
        return catalogService.search(catalogName);
    }
    @PostMapping("/delete/{catalogId}")
    public ResponseEntity<?> deleteUser(@PathVariable("catalogId") int catalogId, @RequestBody CatalogRequest catalogRequest){
        Catalog catalog = catalogService.findById(catalogId);
        if (!catalogRequest.isCatalogStatus()){
            catalog.setCatalogStatus(true);
            catalogService.save(catalog);
        }else {
            catalog.setCatalogStatus(false);
            catalogService.save(catalog);
        }
        return ResponseEntity.ok(catalog);
    }
}
