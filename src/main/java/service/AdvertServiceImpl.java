package main.java.service;


import main.java.dao.AdvertRepository;
import main.java.dao.ImageUtil;
import main.java.dao.TagRepository;
import main.java.entity.Advert;
import main.java.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service("advertService")
public class AdvertServiceImpl implements AdvertService{

    private static String IMAGE_PATH = "C:/advert_images/";

    @Autowired
    @Qualifier("advertRepository")
    private AdvertRepository advertRepository;


    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ImageUtil imageUtil;

    @Override
    public Advert add(Advert advert) {
        validate(advert);

        advert.setAddTime(LocalDateTime.now());

        //adding tags, which are not existing
        //setting id for tags, to add them to advert in DB
        for(Tag tag: advert.getTags()){
            boolean exists = tagRepository.getTag(tag.getName()) != null;
            if(!exists){
                tagRepository.addTag(tag);
            }
            tag.setId(tagRepository.getTag(tag.getName()).getId());
        }

        return advertRepository.add(advert);
    }

    @Override
    public Advert getOne(Integer id) {
        return advertRepository.getOne(id);
    }

    private boolean validate(Advert advert) {
        //TODO: сделать валидацию объявления и кидать исключение
        return true;
    }

    @Override
    public void saveImage(Integer advertId, byte[] bytes) {
        checkAdvertExisting(advertId);
        Advert advert = advertRepository.getOne(advertId);

        advert.setPhotoPath(IMAGE_PATH + advert.getId() + ".png");
        advertRepository.update(advert);

        try{
            imageUtil.savePicture(bytes, advert.getPhotoPath());
        }catch(IOException ex){
            //TODO: обработать
            ex.printStackTrace();
        }
    }

    private void checkAdvertExisting(Integer advertId) {
        //TODO: проверит на наличие Advert
    }

    @Override
    public byte[] getImage(Integer advertId) {
        checkAdvertExisting(advertId);
        Advert advert = advertRepository.getOne(advertId);
        byte[] imageBytes =  null;
        try {
            imageBytes = imageUtil.getImage(advert.getPhotoPath());
        }catch(IOException ex){
            //TODO: обработать
            ex.printStackTrace();
        }
        return imageBytes;
    }

    @Override
    public List<Advert> getAdverts(int page, int size) {
        if(page < 1){
            throw new PageException("Page number must be more than 0.");
        }
        if(size < 1){
            throw new PageException("Page size must be more than 0.");
        }
        int limit = size;
        int offset = (page - 1) * size;
        return advertRepository.getAdverts(limit, offset);
    }

    @Override
    public int getLastPage(int pageSize) {
        double fractionalLastPage = advertRepository.count()*1.0/pageSize;
        int lastPage = (int) fractionalLastPage;
        if(fractionalLastPage > lastPage){
            lastPage += 1;
        }
        return lastPage;
    }

    public void setAdvertRepository(AdvertRepository advertRepository) {
        this.advertRepository = advertRepository;
    }

    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public void setImageUtil(ImageUtil imageUtil) {
        this.imageUtil = imageUtil;
    }
}
