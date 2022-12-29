package ra.model.service;

import ra.model.entity.Image;

import java.util.List;

public interface ImageService {
    List<Image> findImageByIdProduct(int productId);
    Image saveOrUpdate(Image image);
    void deleteById(int id);
}
