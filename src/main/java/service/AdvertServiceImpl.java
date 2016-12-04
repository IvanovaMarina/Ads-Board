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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        checkAdvertExisting(id);
        return advertRepository.getOne(id);
    }

    @Override
    public Advert remove(Integer id) {
        checkAdvertExisting(id);
        Advert advert = advertRepository.remove(id);
        imageUtil.deleteImage(advert.getPhotoPath());
        return advert;
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
        Advert existingAdvert = advertRepository.getOne(advertId);
        if(existingAdvert == null){
            throw new AdvertNotFoundException("Advert not found.");
        }
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

    private void checkPageParams(int page, int size){
        if(page < 1){
            throw new PageException("Page number must be more than 0.");
        }
        if(size < 1){
            throw new PageException("Page size must be more than 0.");
        }
    }

    @Override
    public List<Advert> getAdverts(int page, int size) {
        checkPageParams(page, size);
        int limit = size;
        int offset = (page - 1) * size;
        return advertRepository.getAdverts(limit, offset);
    }

    @Override
    public List<Advert> getAdvertsByTag(int page, int size, String tagName) {
        checkPageParams(page, size);
        int limit = size;
        int offset = (page - 1) * size;
        return advertRepository.getAdvertsByTag(limit, offset, tagName);
    }

    @Override
    public List<Advert> getAdvertsByCategory(int page, int size, Integer categoryId) {
        checkPageParams(page, size);
        int limit = size;
        int offset = (page - 1) * size;
        return advertRepository.getAdvertsByCategory(limit, offset, categoryId);
    }

    @Override
    public List<Advert> getAdvertsBySubcategory(int page, int size, Integer subcategoryId) {
        checkPageParams(page, size);
        int limit = size;
        int offset = (page - 1) * size;
        return advertRepository.getAdvertsBySubcategory(limit, offset, subcategoryId);
    }

    @Override
    public List<Advert> getAdvertsByTitle(int page, int size, String title) {
        checkPageParams(page, size);
        int limit = size;
        int offset = (page - 1) * size;
        return advertRepository.getAdvertsByTitle(limit, offset, title);
    }

    @Override
    public int getLastPage(int pageSize) {
        return calculateLastPage(advertRepository.count(), pageSize);
    }

    @Override
    public int getTagLastPage(int pageSize, String tagName){
        return calculateLastPage(advertRepository.countWithTag(tagName), pageSize);
    }

    @Override
    public int getCategoryLastPage(int pageSize, Integer categoryId) {
        return calculateLastPage(advertRepository.countWithCategory(categoryId), pageSize);
    }

    @Override
    public int getSubcategoryLastPage(int pageSize, Integer subcategoryId) {
        return calculateLastPage(advertRepository.countWithSubcategory(subcategoryId), pageSize);
    }

    @Override
    public int getTitleLastPage(int pageSize, String title) {
        return calculateLastPage(advertRepository.countWithTitle(title), pageSize);
    }

    private int calculateLastPage(int amount, int pageSize) {
        double fractionalLastPage = amount*1.0/pageSize;
        int lastPage = (int) fractionalLastPage;
        if(fractionalLastPage > lastPage){
            lastPage += 1;
        }
        return lastPage;
    }

    @Override
    public void incrementAdvertViews(Integer id) {
        advertRepository.incrementAdvertViews(id);
    }

    @Override
    public Advert update(Advert advert) {
        checkAdvertExisting(advert.getId());
        Advert existingAdvert = advertRepository.getOne(advert.getId());
        existingAdvert.setTitle(advert.getTitle());
        existingAdvert.setDescription(advert.getDescription());
        existingAdvert.getSubcategory().setId(advert.getSubcategory().getId());
        existingAdvert.getMarker().setId(advert.getMarker().getId());
        existingAdvert.setPrice(advert.getPrice());
        existingAdvert.getCurrency().setId(advert.getCurrency().getId());

        for(Tag tag: advert.getTags()){
            boolean exists = tagRepository.getTag(tag.getName()) != null;
            if(!exists){
                tagRepository.addTag(tag);
            }
            tag.setId(tagRepository.getTag(tag.getName()).getId());
        }
        existingAdvert.setTags(advert.getTags());

        return advertRepository.update(existingAdvert);
    }

    @Override
    public List<Tag> getRandomTags(int amount) {
        if(amount <= 0){
            throw new TagException("Tag amount must be more then 0.");
        }
        List<Tag> allTags = tagRepository.getAllTags();
        List<Tag> tags = new ArrayList<>();
        Random random = new Random();

        for(int i = Math.min(amount, allTags.size()); i > 0; i--){
            int tagToAddIndex = random.nextInt(allTags.size());
            tags.add(allTags.get(tagToAddIndex));
            allTags.remove(tagToAddIndex);
        }
        return tags;
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
