package sn.fr.samagp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.fr.samagp.services.inter.IProfilePictureService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/profile/picture")
@RequiredArgsConstructor
public class ProfilePictureController {

    private final IProfilePictureService profilePictureService;

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadProfilePicture(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal Jwt jwt) {

        String keycloakId = jwt.getSubject();
        String filePath = profilePictureService.updateProfilePicture(file, keycloakId);

        // Retourner un objet JSON
        Map<String, String> response = new HashMap<>();
        response.put("filePath", filePath);
        response.put("message", "Photo uploadée avec succès");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProfilePicture(@AuthenticationPrincipal Jwt jwt) {
        String keycloakId = jwt.getSubject();
        profilePictureService.deleteProfilePicture(keycloakId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{keycloakId}")
    public ResponseEntity<String> getProfilePictureUrl(@PathVariable String keycloakId) {
        try {
            String pictureUrl = profilePictureService.getProfilePictureUrl(keycloakId);
            return ResponseEntity.ok(pictureUrl);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir, "profile-pictures").resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = determineContentType(filename);

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    private String determineContentType(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "application/octet-stream";
        }

        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        return switch (extension) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "webp" -> "image/webp";
            default -> "application/octet-stream";
        };
    }
}