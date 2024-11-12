package com.udemy.learn.blogging.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udemy.learn.blogging.service.FileUploadService;
@Service
public class FileServiceImplementation implements FileUploadService {

    @Override
    public String uploadfile(String path, MultipartFile file) throws IOException {
        String name = file.getOriginalFilename();
        String fileExtension = getFileExtension(name);
        
        String randomId = UUID.randomUUID().toString() + fileExtension;
        String filepath = path + File.separator + randomId;
        
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs(); // Create directory including parent directories if not exists
        }
        
        Files.copy(file.getInputStream(), Paths.get(filepath));
        return randomId;
    }

    private String getFileExtension(String filename) {
        if (filename != null && filename.lastIndexOf(".") != -1) {
            return filename.substring(filename.lastIndexOf("."));
        }
        return ""; // Handle if no extension found or filename is null
    }
}
