package sn.fr.samagp.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import sn.fr.samagp.repository.model.Annonce;
import sn.fr.samagp.repository.model.Client;
import sn.fr.samagp.repository.model.Profile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
public class AnnonceRepositoryTestIT {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:16.8")
                    .withDatabaseName("testdb")
                    .withUsername("testuser")
                    .withPassword("testpass");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private ClientRepository clientRepository;

    private Client client;
    private Annonce annonce1;
    private Annonce annonce2;

    @BeforeEach
    void setUp() {
        // Create and save client
        client = new Client(
                null,
                "ousmane",
                "Dione",
                "ousmanevincent@gmail.com",
                "password123",
                Profile.GP,
                "0789456123",
                "Dakar, Sénégal",
                new ArrayList<>()
        );
        client = clientRepository.save(client);

        // Create annonces
        annonce1 = new Annonce(
                null,
                "Dakar, Sénégal",
                "Saint-Louis, Sénégal",
                "Voyage confortable en bus climatisé",
                LocalDateTime.of(2024, 4, 10, 8, 30),
                LocalDateTime.of(2024, 4, 10, 12, 45),
                LocalDateTime.now(),
                LocalDateTime.now(),
                new ArrayList<>(),
                client
        );

        annonce2 = new Annonce(
                null,
                "Thiès, Sénégal",
                "Ziguinchor, Sénégal",
                "Trajet en voiture privée avec chauffeur",
                LocalDateTime.of(2024, 5, 5, 7, 0),
                LocalDateTime.of(2024, 5, 5, 20, 30),
                LocalDateTime.now(),
                LocalDateTime.now(),
                new ArrayList<>(),
                client
        );

        // Save annonces
        annonceRepository.save(annonce1);
        annonceRepository.save(annonce2);
    }

    @AfterEach
    void tearDown() {
        // No need to manually delete as @DataJpaTest rolls back transactions
    }

    @Test
    void connectionEstablished() {
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        assertThat(postgreSQLContainer.isRunning()).isTrue();
    }

    @Test
    void testFindByClientId() {
        List<Annonce> result = annonceRepository.findByClientId(client.getId());

        assertThat(result).hasSize(2);
        assertThat(result)
                .extracting(Annonce::getDescription)
                .containsExactlyInAnyOrder(
                        "Voyage confortable en bus climatisé",
                        "Trajet en voiture privée avec chauffeur"
                );
    }
}