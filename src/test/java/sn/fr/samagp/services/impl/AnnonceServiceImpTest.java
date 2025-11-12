package sn.fr.samagp.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import sn.fr.samagp.mapper.AnnonceMapper;
import sn.fr.samagp.mapper.ClientMapper;
import sn.fr.samagp.repository.AnnonceRepository;
import sn.fr.samagp.repository.ClientRepository;
import sn.fr.samagp.repository.dto.AnnonceDTO;
import sn.fr.samagp.repository.dto.AvisDTO;
import sn.fr.samagp.repository.dto.ClientDTO;
import sn.fr.samagp.repository.model.*;
import sn.fr.samagp.controller.response.AnnonceResponse;
import sn.fr.samagp.services.inter.ISecurityService;
import sn.fr.samagp.validator.AnnonceValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    private ClientMapper clientMapper;

    @Mock
    private AnnonceValidator annonceValidator;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ISecurityService securityService;


    @InjectMocks
    private AnnonceServiceImp annonceService;

    private UUID annonceId;
    private Annonce annonce;
    private AnnonceDTO annonceDTO;
    private AnnonceResponse annonceResponse;
    private Client client;


    @BeforeEach
    void setUp() {
        annonceId = UUID.randomUUID();
        annonce = new Annonce();

        // Création d'un ClientDTO valide
        ClientDTO clientDTO = new ClientDTO(
                UUID.randomUUID(),
                "John",
                "Doe",
                "john.doe@example.com",
                "password",
                List.of("0123456789"),
                List.of(new Adresse("123 Main St", "Apt 1", "Paris", "75001", "France", AdresseType.DOMICILE, true)),
                Profile.USER,
                LocalDateTime.now(),
                List.of(),
                List.of(),
                "keycloak123",
                Set.of(),
                Set.of(),
                0,
                0,
                false
        );

        client = new Client();
        client.setId(clientDTO.getId());
        client.setFirstName(clientDTO.getFirstName());
        client.setLastName(clientDTO.getLastName());
        client.setEmail(clientDTO.getEmail());
        client.setPassword(clientDTO.getPassword());
        client.setPhone(clientDTO.getPhone());
        client.setAddress(clientDTO.getAddress());
        client.setProfile(clientDTO.getProfile());

        // Construction de l'annonceDTO avec un clientDTO valide
        annonceDTO = AnnonceDTO.builder()
                .clientDTO(clientDTO)
                .itineraireDTO(null)
                .itineraireDetailsDepart("Gare du Nord")
                .itineraireDetailsArrive("Champs-Élysées")
                .description("Voyage rapide et confortable")
                .dateDepart(LocalDateTime.now())
                .dateArrive(LocalDateTime.now().plusHours(2))
                .updatedAt(LocalDateTime.now())
                .build();

        annonceResponse = AnnonceResponse.builder()
                .id(annonceId)
                .itineraire(null)
                .itineraireDetailsDepart("Gare du Nord")
                .itineraireDetailsArrive("Champs-Élysées")
                .description("Voyage rapide et confortable")
                .dateDepart(LocalDateTime.now())
                .dateArrive(LocalDateTime.now().plusHours(2))
                .createdAt(LocalDateTime.now().minusDays(1))
                .updatedAt(LocalDateTime.now())
                .avis(List.of())
                .client(clientDTO)
                .build();
    }

    @Test
    void createAnnonceDTO() {
        // 1. Mock de l'authentification
        JwtAuthenticationToken authentication = mock(JwtAuthenticationToken.class);
        Jwt jwt = mock(Jwt.class);

        when(securityService.getAuthentication()).thenReturn(authentication);
        when(authentication.getToken()).thenReturn(jwt);
        when(jwt.getSubject()).thenReturn("user123");
        when(jwt.getClaimAsString("email")).thenReturn("john.doe@example.com");

        // 2. Mock du clientRepository
        when(clientRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(client));

        // 3. Mock des autres dépendances
        when(annonceMapper.toEntity(annonceDTO)).thenReturn(annonce);
        when(annonceRepository.save(annonce)).thenReturn(annonce);
        when(annonceMapper.toResponse(annonce)).thenReturn(annonceResponse);

        // 4. Exécution du test
        AnnonceResponse result = annonceService.createAnnonce(annonceDTO);

        // 5. Vérifications
        assertNotNull(result);
        assertEquals(annonceResponse, result);
        verify(securityService).getAuthentication();
        verify(clientRepository).findByEmail("john.doe@example.com");
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

//    @Test
//    void updateAnnonce_UpdatedAnnonceDTO_AnnonceExists() {
//        when(annonceRepository.findById(annonceId)).thenReturn(Optional.of(annonce));
//        //when(clientMapper.toEntity(annonceDTO.clientDTO())).thenReturn(client);
//        when(annonceMapper.toDto(any(Annonce.class))).thenReturn(annonceDTO);
//        when(annonceRepository.save(any(Annonce.class))).thenReturn(annonce);
//
//        AnnonceDTO result = annonceService.updateAnnonce(annonceId, annonceDTO);
//
//        assertNotNull(result);
//        verify(annonceRepository).findById(annonceId);
//        //verify(clientMapper).toEntity(annonceDTO.clientDTO()); // Vérification du mapping client
//        verify(annonceRepository).save(any(Annonce.class));
//    }

//    @Test
//    void updateAnnonce_ShouldThrowException_AnnonceNotFound() {
//        when(annonceRepository.findById(annonceId)).thenReturn(java.util.Optional.empty());
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> annonceService.updateAnnonce(annonceId, annonceDTO));
//        assertEquals("Annonce non trouvée!", exception.getMessage());
//        verify(annonceRepository).findById(annonceId);
//    }

    @Test
    void deleteAnnonce_DeleteById() {
        when(annonceRepository.findById(annonceId)).thenReturn(Optional.of(annonce));
        doNothing().when(annonceRepository).deleteById(annonceId);
        annonceService.deleteAnnonce(annonceId);
        verify(annonceRepository).findById(annonceId);
        verify(annonceRepository).deleteById(annonceId);
    }

    @Test
    void deleteAnnonce_ShouldThrowException_AnnonceNotFound() {
        when(annonceRepository.findById(annonceId)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> annonceService.deleteAnnonce(annonceId));
        assertEquals("Annonce non trouvée!", exception.getMessage());
        verify(annonceRepository).findById(annonceId);
        verify(annonceRepository, never()).deleteById(any(UUID.class));
    }


    @Test
    void getAnnonceByClient_ListOfAnnonceResponses() {
        UUID clientId = UUID.randomUUID();
        List<Annonce> annonces = List.of(annonce);
        List<AnnonceResponse> expectedResponses = List.of(annonceResponse);
        when(annonceRepository.findByClientId(clientId)).thenReturn(annonces);
        when(annonceMapper.toResponse(annonce)).thenReturn(annonceResponse);
        List<AnnonceResponse> result = annonceService.getAnnonceByKeycloakClient(clientId.toString());
        assertNotNull(result);
        assertEquals(expectedResponses, result);
        verify(annonceRepository).findByClientId(clientId);
    }





}