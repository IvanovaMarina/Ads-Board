package main.java.dao;


import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.util.Objects;

@Component
public class ImageUtil {
    public static final int BYTES_TO_WRITE = 1000;

    //public static final int BUFFER_SIZE = 1000;
    //private final byte[] buffer = new byte[BUFFER_SIZE];

    /**
     * @param imagePath is absolute path to image
     * @return amount of written bytes
     */
    public int savePicture(byte[] imageBytes, String imagePath) throws IOException{
        Objects.requireNonNull(imageBytes);
        Objects.requireNonNull(imagePath);

        File image = new File(imagePath);
        int offset = 0;

        boolean isCreated = image.createNewFile();
        if(!isCreated){
            throw new IOException("File has not bean created.");
        }
        FileOutputStream fileOutputStream = new FileOutputStream(image);


        while(offset < imageBytes.length){
            int writeBytes = Math.min(imageBytes.length - offset, BYTES_TO_WRITE);
            fileOutputStream.write(imageBytes, offset, writeBytes);
            offset += writeBytes;
        }


        Objects.requireNonNull(fileOutputStream);
        fileOutputStream.close();

        return offset;
    }

    public byte[] getImage(String imagePath) throws IOException{
        File file = new File(imagePath);
        byte[] imageBytes = Files.readAllBytes(file.toPath());
        //TODO: доделать
        return imageBytes;
    }
}
