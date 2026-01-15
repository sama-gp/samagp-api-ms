package sn.fr.samagp.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import sn.fr.samagp.exceptions.FileStorageException;
import sn.fr.samagp.services.FileDeleter;
import sn.fr.samagp.services.FilePathFormatter;
import sn.fr.samagp.services.FileWriter;
import sn.fr.samagp.utils.DirectoryInitializer;
import sn.fr.samagp.utils.PathResolver;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class FileStorageServiceTest {

    @Mock
    private PathResolver pathResolver;

    @Mock
    private DirectoryInitializer directoryInitializer;

    @Mock
    private FileWriter fileWriter;

    @Mock
    private FilePathFormatter formatter;

    @Mock
    private MultipartFile multipartFile;

    @Mock
    private FileDeleter fileDeleter;

    @InjectMocks
    private FileStorageService fileStorageService;

    private Path directory;
    private Path target;

    @BeforeEach
    void setUp() {
        directory = Paths.get("/uploads/images");
    }

    @Test
    @DisplayName("Devrai sauvegarer le fichier dans le dossier images")
    void should_store_file_successfully() {
        // GIVEN
        String subDirectory = "images";
        String fileName = "photo.png";


        Path directory = Paths.get("/uploads/images");
        Path target = Paths.get("/uploads/images/photo.png");

        when(pathResolver.resolve(subDirectory)).thenReturn(directory);
        when(formatter.format(subDirectory, fileName))
                .thenReturn("images/photo.png");

        // WHEN
        String result = fileStorageService
                .storeFile(multipartFile, subDirectory, fileName);

        // THEN
        assertEquals("images/photo.png", result);

        verify(pathResolver).resolve(subDirectory);
        verify(directoryInitializer).ensureExists(directory);
        verify(fileWriter).write(multipartFile, target);
        verify(formatter).format(subDirectory, fileName);

        verifyNoMoreInteractions(
                pathResolver,
                directoryInitializer,
                fileWriter,
                formatter
        );
    }

    @Test
    void should_throw_exception_when_file_write_fails() {
        // GIVEN
        String subDirectory = "images";
        String fileName = "photo.png";

        Path directory = Paths.get("/uploads/images"); // <- MUST NOT be null

        when(pathResolver.resolve(subDirectory)).thenReturn(directory);

        doThrow(new FileStorageException("Write failed"))
                .when(fileWriter)
                .write(eq(multipartFile), any(Path.class));

        // WHEN / THEN
        assertThrows(
                FileStorageException.class,
                () -> fileStorageService.storeFile(
                        multipartFile, subDirectory, fileName
                )
        );

        verify(directoryInitializer).ensureExists(directory);
        verify(fileWriter).write(eq(multipartFile), any(Path.class));
    }



    @Test
    void should_delete_file_successfully() {
        Path path = Paths.get("/uploads/images/photo.png");

        when(pathResolver.resolve("images/photo.png")).thenReturn(path);

        fileStorageService.deleteFile("images/photo.png");

        verify(fileDeleter).delete(path);
    }



}