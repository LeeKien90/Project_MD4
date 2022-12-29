package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.Image;
import ra.model.repository.ImageRepository;
import ra.model.service.ImageService;

import java.util.List;

@Service
public class ImageServiceImp implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<Image> findImageByIdProduct(int productId) {
        return imageRepository.findImageByProductProductId(productId);
    }

    @Override
    public Image saveOrUpdate(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public void deleteById(int id) {
        imageRepository.deleteById(id);
    }
}
