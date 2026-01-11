package sn.fr.samagp.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import sn.fr.samagp.repository.model.TypePieces;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDocumentsDTO {
    private TypePieces typePieces;
    private String ninea;
    private MultipartFile rectoFile;
    private MultipartFile versoFile;
}