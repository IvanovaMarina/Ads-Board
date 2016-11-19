package main.java.dao;


import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.util.Objects;

@Component
public class ImageUtil {


    /**
     * @param imagePath is absolute path to image
     * @param imageBytes is binary image
     */
    public void savePicture(byte[] imageBytes, String imagePath) throws IOException{
        Objects.requireNonNull(imageBytes);
        Objects.requireNonNull(imagePath);

        File image = new File(imagePath);


        boolean isCreated = image.createNewFile();
        if(!isCreated){
            throw new IOException("File has not bean created.");
        }
        OutputStream fileOutputStream = new FileOutputStream(image);

        fileOutputStream.write(imageBytes);

        Objects.requireNonNull(fileOutputStream);
        fileOutputStream.close();

    }

    public byte[] getImage(String imagePath) throws IOException{
        File file = new File(imagePath);
        byte[] imageBytes = Files.readAllBytes(file.toPath());
        //TODO: доделать
        return imageBytes;
    }
}
