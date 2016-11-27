package main.java.service;


import main.java.dao.AdvertRepository;
import main.java.dao.ImageUtil;
import main.java.dao.TagRepository;
import main.java.entity.Advert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

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

        return advertRepository.add(advert);
    }

    private boolean validate(Advert advert) {
        //TODO: сделать валидацию объявления и кидать исключение
        return true;
    }

    @Override
    public void saveImage(Integer advertId, byte[] bytes) {
        checkAdvertExisting(advertId);
        Advert advert = advertRepository.getAdvert(advertId);

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
        return new byte[0];
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
