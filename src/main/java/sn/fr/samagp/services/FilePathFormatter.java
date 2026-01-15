package sn.fr.samagp.services;


import org.springframework.stereotype.Component;

@Component
public class FilePathFormatter {


    public String format(String subDirectory, String fileName) {
        return subDirectory + "/" + fileName;
    }
}
