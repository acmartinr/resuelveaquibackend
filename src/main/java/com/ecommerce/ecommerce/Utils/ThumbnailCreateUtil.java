package com.ecommerce.ecommerce.Utils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ThumbnailCreateUtil {

    public static void thumbCreate(String imgPath) throws IOException {
        File file= new File(imgPath);
        Path thumbnailPath=Paths.get(imgPath).getParent();
        Path fileName=Paths.get(imgPath).getFileName();
        String thumbnailFullPath=thumbnailPath+File.separator+"Thumbs"+File.separator+fileName;
        File thumbnailFile=new File(thumbnailFullPath);
        try {
            Thumbnails.of(file )
                    .size(100, 100)
                    .toFile(thumbnailFile);
        }
        catch (IOException e){
            e.printStackTrace();

        }
    }
}
