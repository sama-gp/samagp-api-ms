package sn.fr.samagp.services.inter;

import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {
    String storeFile(MultipartFile file, String subDirectory);
    void deleteFile(String filePath);
}
