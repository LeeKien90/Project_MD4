package ra.model.service;

import ra.model.entity.Catalog;

import java.util.List;

public interface CatalogService {
    List<Catalog> findAll();
    Catalog findById(int id);
    Catalog save(Catalog catalog);
    void delete(int id);
    List<Catalog> search(String name);
}
