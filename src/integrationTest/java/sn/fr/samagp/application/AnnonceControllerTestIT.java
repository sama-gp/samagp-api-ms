package sn.fr.samagp.application;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import sn.fr.samagp.mapper.ClientMapper;
import sn.fr.samagp.repository.AnnonceRepository;
import sn.fr.samagp.repository.ClientRepository;
import sn.fr.samagp.repository.dto.AnnonceDTO;
import sn.fr.samagp.repository.model.Annonce;
import sn.fr.samagp.repository.model.Client;
import sn.fr.samagp.repository.model.Profile;
import sn.fr.samagp.repository.response.AnnonceResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AnnonceControllerTestIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.8");

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    AnnonceRepository annonceRepository;

    private UUID existingAnnonceId;
    private UUID nonExistingAnnonceId;
    private Client testClient;
    private Annonce annonce;
    private AnnonceDTO annonceDTO;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    @BeforeEach
    void setUp() {

        testClient = new Client();
        testClient.setFirstName("Test");
        testClient.setLastName("User");
        testClient.setEmail("test@example.com");
        testClient.setPassword("password");
        testClient.setProfile(Profile.GP);
        testClient.setPhone("1234567890");
        testClient.setAddress("123 Test Street");
        clientRepository.save(testClient);
        // Créer une annonce pour tester le GET par ID
        annonceDTO = AnnonceDTO.builder()
                .itineraireDTO(null)
                .itineraireDetailsDepart("Détails départ : Paris Gare de Lyon")
                .itineraireDetailsArrive("Détails arrivée : Lyon Part-Dieu")
                .description("Annonce pour un trajet Paris-Lyon")
                .dateDepart(LocalDateTime.of(2025, 5, 1, 10, 0))
                .dateArrive(LocalDateTime.of(2025, 5, 1, 12, 30))
                .updatedAt(LocalDateTime.now())
                .clientDTO(clientMapper.toDto(testClient))
                .build();

        // Create and save an announcement
        annonce = new Annonce();
        annonce.setItineraireDetailsDepart("Paris Gare de Lyon");
        annonce.setItineraireDetailsArrive("Lyon Part-Dieu");
        annonce.setDescription("Trajet Paris-Lyon");
        annonce.setDateDepart(LocalDateTime.of(2025, 5, 1, 10, 0));
        annonce.setDateArrive(LocalDateTime.of(2025, 5, 1, 12, 30));
        annonce.setClient(testClient);

        annonce = annonceRepository.save(annonce);
        existingAnnonceId = annonce.getId();
        nonExistingAnnonceId = UUID.randomUUID();
    }

    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @Test
    public void testGetAllAnnonces() {
        ResponseEntity<List<AnnonceResponse>> response = restTemplate.exchange(
                "/annonces",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AnnonceResponse>>() {}
        );
        assertEquals(200, response.getStatusCodeValue());
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void testGetAnnonceById_Exists() {
        ResponseEntity<AnnonceResponse> response = restTemplate.getForEntity(
                "/annonces/" + existingAnnonceId,
                AnnonceResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(existingAnnonceId);
    }

    @Test
    void testCreateAnnonce() {
        // 2. Créer un nouveau DTO avec ce client
        AnnonceDTO newAnnonceDTO = AnnonceDTO.builder()
                .itineraireDTO(null)
                .itineraireDetailsDepart("Paris")
                .itineraireDetailsArrive("Lyon")
                .description("Nouvelle annonce")
                .dateDepart(LocalDateTime.now().plusDays(1))
                .dateArrive(LocalDateTime.now().plusDays(2))
                .updatedAt(LocalDateTime.now())
                .clientDTO(clientMapper.toDto(testClient)) // Utilise le client sauvegardé
                .build();

        ResponseEntity<AnnonceResponse> response = restTemplate.exchange(
                "/annonces",
                HttpMethod.POST,
                new HttpEntity<>(newAnnonceDTO),
                AnnonceResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getItineraireDetailsDepart()).isEqualTo("Paris");
        assertThat(response.getBody().getItineraireDetailsArrive()).isEqualTo("Lyon");
        assertThat(response.getBody().getClient().getEmail()).isEqualTo(testClient.getEmail());
    }
    @Test
    public void testGetAnnonceById_NotFound() {
        // Test pour une annonce qui n'existe pas
        ResponseEntity<Void> response = restTemplate.exchange(
                "/annonces/" + nonExistingAnnonceId,
                HttpMethod.GET,
                null,
                Void.class
        );
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateAnnonce() {
        // 1. Préparer les données de mise à jour
        AnnonceDTO updateDTO = AnnonceDTO.builder()
                .itineraireDTO(null)
                .itineraireDetailsDepart("Paris Modifié")
                .itineraireDetailsArrive("Lyon Modifié")
                .description("Description mise à jour")
                .dateDepart(LocalDateTime.now().plusDays(3))
                .dateArrive(LocalDateTime.now().plusDays(4))
                .updatedAt(LocalDateTime.now())
                .clientDTO(clientMapper.toDto(testClient))
                .build();

        // 2. Exécuter la mise à jour
        ResponseEntity<AnnonceDTO> response = restTemplate.exchange(
                "/annonces/" + existingAnnonceId,
                HttpMethod.PUT,
                new HttpEntity<>(updateDTO),
                AnnonceDTO.class
        );

        // 3. Vérifications
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDescription()).isEqualTo("Description mise à jour");
        assertThat(response.getBody().getItineraireDetailsDepart()).isEqualTo("Paris Modifié");
    }

    @Test
    void testUpdateAnnonce_NotFound() {
        AnnonceDTO updateDTO = AnnonceDTO.builder()
                .itineraireDetailsDepart("Paris")
                .itineraireDetailsArrive("Lyon")
                .build();

        ResponseEntity<AnnonceDTO> response = restTemplate.exchange(
                "/annonces/" + nonExistingAnnonceId,
                HttpMethod.PUT,
                new HttpEntity<>(updateDTO),
                AnnonceDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testGetAnnoncesByClient() {
        // 2. Récupérer les annonces du client
        ResponseEntity<List<AnnonceResponse>> response = restTemplate.exchange(
                "/annonces/client/" + testClient.getId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AnnonceResponse>>() {}
        );
        // 3. Vérifications
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSizeGreaterThanOrEqualTo(1);
        assertThat(response.getBody())
                .allMatch(a -> a.getClient().getId().equals(testClient.getId()));
    }

    @Test
    void testGetAnnoncesByClient_Empty() {
        // Créer un nouveau client sans annonces
        Client newClient = new Client();
        newClient.setFirstName("Test2");
        newClient.setLastName("User2");
        newClient.setEmail("test2@example.com");
        newClient.setPassword("password");
        newClient.setProfile(Profile.GP);
        newClient.setPhone("1234567890");
        newClient.setAddress("123 Test Street 2");
        clientRepository.save(newClient);
        UUID newClientId = clientRepository.save(newClient).getId();
        ResponseEntity<List<AnnonceResponse>> response = restTemplate.exchange(
                "/annonces/client/" + newClientId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AnnonceResponse>>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    void testDeleteAnnonce() {
        // 2. Supprimer l'annonce
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/annonces/" + existingAnnonceId.toString(),
                HttpMethod.DELETE,
                null,
                Void.class
        );
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        ResponseEntity<AnnonceResponse> getResponse = restTemplate.getForEntity(
                "/annonces/" + existingAnnonceId.toString(),
                AnnonceResponse.class
        );
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    void testDeleteAnnonce_NotFound() {
        ResponseEntity<Void> response = restTemplate.exchange(
                "/annonces/" + nonExistingAnnonceId,
                HttpMethod.DELETE,
                null,
                Void.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}
