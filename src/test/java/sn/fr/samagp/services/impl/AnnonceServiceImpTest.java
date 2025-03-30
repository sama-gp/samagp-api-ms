package sn.fr.samagp.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sn.fr.samagp.mapper.AnnonceMapper;
import sn.fr.samagp.repository.AnnonceRepository;
import sn.fr.samagp.repository.dto.AnnonceDTO;
import sn.fr.samagp.repository.dto.AvisDTO;
import sn.fr.samagp.repository.dto.ClientDTO;
import sn.fr.samagp.repository.dto.ItineraireDTO;
import sn.fr.samagp.repository.model.Annonce;
import sn.fr.samagp.repository.response.AnnonceResponse;
import sn.fr.samagp.validator.AnnonceValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnnonceServiceImpTest {

    @Mock
    private AnnonceRepository annonceRepository;

    @Mock
    private AnnonceMapper annonceMapper;

    @Mock
    private AnnonceValidator annonceValidator;

    @InjectMocks
    private AnnonceServiceImp annonceService;

    private UUID annonceId;
    private Annonce annonce;
    private AnnonceDTO annonceDTO;
    private AnnonceResponse annonceResponse;

    @BeforeEach
    void setUp() {
        annonceId = UUID.randomUUID();
        annonce = new Annonce();
        ItineraireDTO itineraireDTO = new ItineraireDTO();
        ClientDTO clientDTO = new ClientDTO();
        List<AvisDTO> avisList = List.of();
        annonceDTO = AnnonceDTO.builder()
                .itineraireDTO(itineraireDTO)
                .itineraireDetailsDepart("Gare du Nord")
                .itineraireDetailsArrive("Champs-Élysées")
                .description("Voyage rapide et confortable")
                .dateDepart(LocalDateTime.now())
                .dateArrive(LocalDateTime.now().plusHours(2))
                .updatedAt(LocalDateTime.now())
                .build();
        annonceResponse = AnnonceResponse.builder()
                .id(annonceId)
                .itineraire(itineraireDTO)
                .itineraireDetailsDepart("Gare du Nord")
                .itineraireDetailsArrive("Champs-Élysées")
                .description("Voyage rapide et confortable")
                .dateDepart(LocalDateTime.now())
                .dateArrive(LocalDateTime.now().plusHours(2))
                .createdAt(LocalDateTime.now().minusDays(1))
                .updatedAt(LocalDateTime.now())
                .avis(avisList)
                .client(clientDTO)
                .build();

    }

    @Test
    void createAnnonceDTO() {
        when(annonceMapper.toEntity(annonceDTO)).thenReturn(annonce);
        when(annonceRepository.save(annonce)).thenReturn(annonce);
        when(annonceMapper.toDto(annonce)).thenReturn(annonceDTO);
        AnnonceResponse result = annonceService.createAnnonce(annonceDTO);
        assertNotNull(result);
        verify(annonceValidator).validate(annonce);
        verify(annonceRepository).save(annonce);
    }

    @Test
    void getAnnonceById_AnnonceExists() {
        when(annonceRepository.findById(annonceId)).thenReturn(java.util.Optional.of(annonce));
        when(annonceMapper.toResponse(annonce)).thenReturn(annonceResponse);
        Optional<AnnonceResponse> result = annonceService.getAnnonceById(annonceId);
        assertTrue(result.isPresent());
        assertEquals(annonceResponse, result.get());
        verify(annonceRepository).findById(annonceId);
    }

    @Test
    void getAnnonceById_NotFound() {
        when(annonceRepository.findById(annonceId)).thenReturn(java.util.Optional.empty());
        Optional<AnnonceResponse> result = annonceService.getAnnonceById(annonceId);
        assertFalse(result.isPresent());
        verify(annonceRepository).findById(annonceId);
    }

    @Test
    void getAllAnnonces_ListOfAnnonceResponses() {
        List<Annonce> annonces = List.of(annonce);
        List<AnnonceResponse> expectedResponses = List.of(annonceResponse);
        when(annonceRepository.findAll()).thenReturn(annonces);
        when(annonceMapper.toResponse(annonce)).thenReturn(annonceResponse);
        List<AnnonceResponse> result = annonceService.getAllAnnonces();
        assertNotNull(result);
        assertEquals(expectedResponses, result);
        verify(annonceRepository).findAll();
    }

    @Test
    void updateAnnonce_UpdatedAnnonceDTO_AnnonceExists() {
        when(annonceRepository.findById(annonceId)).thenReturn(java.util.Optional.of(annonce));
        when(annonceMapper.toDto(annonce)).thenReturn(annonceDTO);
        when(annonceRepository.save(annonce)).thenReturn(annonce);
        AnnonceDTO result = annonceService.updateAnnonce(annonceId, annonceDTO);
        assertNotNull(result);
        verify(annonceRepository).findById(annonceId);
        verify(annonceRepository).save(annonce);
    }

    @Test
    void updateAnnonce_ShouldThrowException_AnnonceNotFound() {
        when(annonceRepository.findById(annonceId)).thenReturn(java.util.Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> annonceService.updateAnnonce(annonceId, annonceDTO));
        assertEquals("Annonce non trouvée!", exception.getMessage());
        verify(annonceRepository).findById(annonceId);
    }

    @Test
    void deleteAnnonce_DeleteById() {
        doNothing().when(annonceRepository).deleteById(annonceId);
        annonceService.deleteAnnonce(annonceId);
        verify(annonceRepository).deleteById(annonceId);
    }

    @Test
    void getAnnonceByClient_ListOfAnnonceResponses() {
        UUID clientId = UUID.randomUUID();
        List<Annonce> annonces = List.of(annonce);
        List<AnnonceResponse> expectedResponses = List.of(annonceResponse);
        when(annonceRepository.findByClientId(clientId)).thenReturn(annonces);
        when(annonceMapper.toResponse(annonce)).thenReturn(annonceResponse);

        List<AnnonceResponse> result = annonceService.getAnnonceByClient(clientId);

        assertNotNull(result);
        assertEquals(expectedResponses, result);
        verify(annonceRepository).findByClientId(clientId);
    }





}