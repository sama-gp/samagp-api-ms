package sn.fr.samagp.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sn.fr.samagp.mapper.AbonnementMapper;
import sn.fr.samagp.repository.PlanAbonnementRepository;
import sn.fr.samagp.repository.dto.PlanAbonnementDTO;
import sn.fr.samagp.repository.model.DureeAbonnement;
import sn.fr.samagp.repository.model.PlanAbonnement;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static sn.fr.samagp.services.utils.PlanAbonnementUtils.createPlanAbonnement;
import static sn.fr.samagp.services.utils.PlanAbonnementUtils.createPlanAbonnementDTO;


@ExtendWith(MockitoExtension.class)
class AbonnementServiceImplTest {
    @Mock
    private PlanAbonnementRepository planAbonnementRepository;

    @Mock
    private AbonnementMapper abonnementMapper;

    @InjectMocks
    private AbonnementServiceImpl abonnementService;

    private PlanAbonnement planBasic;
    private PlanAbonnement planPremium;
    private PlanAbonnementDTO planBasicDTO;
    private PlanAbonnementDTO planPremiumDTO;

    @BeforeEach
    void setUp() {

        planBasic = createPlanAbonnement(
                UUID.randomUUID(),
                "BASIC",
                "Plan Basic",
                DureeAbonnement.MENSUEL,
                new BigDecimal("9.99"),
                10
        );

        planPremium = createPlanAbonnement(
                UUID.randomUUID(),
                "PREMIUM",
                "Plan Premium",
                DureeAbonnement.ANNUEL,
                new BigDecimal("99.99"),
                100
        );


        planBasicDTO = createPlanAbonnementDTO(
                planBasic.getId(),
                "BASIC",
                "Plan Basic",
                DureeAbonnement.MENSUEL,
                new BigDecimal("9.99"),
                10
        );

        planPremiumDTO = createPlanAbonnementDTO(
                planPremium.getId(),
                "PREMIUM",
                "Plan Premium",
                DureeAbonnement.ANNUEL,
                new BigDecimal("99.99"),
                100
        );
    }

    @Test
    @DisplayName("Devrait retourner une liste de plans actifs quand des plans existent")
    void shouldReturnActivePlans_WhenPlansExist() {
        // Given
        List<PlanAbonnement> plansEntites = List.of(planBasic, planPremium);
        List<PlanAbonnementDTO> plansDTOs = Arrays.asList(planBasicDTO, planPremiumDTO);

        when(planAbonnementRepository.findByActifTrue()).thenReturn(plansEntites);
        when(abonnementMapper.toPlanDtos(plansEntites)).thenReturn(plansDTOs);

        // When
        List<PlanAbonnementDTO> result = abonnementService.getPlansActifs();

        // Then
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .containsExactly(planBasicDTO, planPremiumDTO);

        // Vérification des interactions
        verify(planAbonnementRepository, times(1)).findByActifTrue();
        verify(abonnementMapper, times(1)).toPlanDtos(plansEntites);
        verifyNoMoreInteractions(planAbonnementRepository, abonnementMapper);
    }

    @Test
    @DisplayName("Devrait retourner une liste vide quand aucun plan actif n'existe")
    void shouldReturnEmptyList_WhenNoActivePlansExist() {
        // Given
        List<PlanAbonnement> emptyList = Collections.emptyList();

        when(planAbonnementRepository.findByActifTrue()).thenReturn(emptyList);
        when(abonnementMapper.toPlanDtos(emptyList)).thenReturn(Collections.emptyList());

        // When
        List<PlanAbonnementDTO> result = abonnementService.getPlansActifs();

        // Then
        assertThat(result)
                .isNotNull()
                .isEmpty();

        verify(planAbonnementRepository, times(1)).findByActifTrue();
        verify(abonnementMapper, times(1)).toPlanDtos(emptyList);
    }

    @Test
    @DisplayName("Devrait retourner un seul plan quand un seul plan est actif")
    void shouldReturnSinglePlan_WhenOnlyOnePlanIsActive() {
        // Given
        List<PlanAbonnement> singlePlanList = Collections.singletonList(planBasic);
        List<PlanAbonnementDTO> singleDTOList = Collections.singletonList(planBasicDTO);

        when(planAbonnementRepository.findByActifTrue()).thenReturn(singlePlanList);
        when(abonnementMapper.toPlanDtos(singlePlanList)).thenReturn(singleDTOList);

        // When
        List<PlanAbonnementDTO> result = abonnementService.getPlansActifs();

        // Then
        assertThat(result)
                .isNotNull()
                .hasSize(1)
                .containsExactly(planBasicDTO);

        verify(planAbonnementRepository, times(1)).findByActifTrue();
        verify(abonnementMapper, times(1)).toPlanDtos(singlePlanList);
    }

    @Test
    @DisplayName("Devrait vérifier que le mapper est appelé avec la bonne liste")
    void shouldVerifyMapperIsCalledWithCorrectList() {
        // Given
        List<PlanAbonnement> plansEntites = Arrays.asList(planBasic, planPremium);
        List<PlanAbonnementDTO> plansDTOs = Arrays.asList(planBasicDTO, planPremiumDTO);

        when(planAbonnementRepository.findByActifTrue()).thenReturn(plansEntites);
        when(abonnementMapper.toPlanDtos(plansEntites)).thenReturn(plansDTOs);

        // When
        abonnementService.getPlansActifs();

        // Then - Vérification que le mapper reçoit exactement la liste du repository
        verify(abonnementMapper).toPlanDtos(argThat(list ->
                list != null &&
                        list.size() == 2 &&
                        list.contains(planBasic) &&
                        list.contains(planPremium)
        ));
    }

    @Test
    @DisplayName("Devrait préserver l'ordre des plans retournés par le repository")
    void shouldPreserveOrderOfPlansFromRepository() {
        // Given - L'ordre est important
        List<PlanAbonnement> plansEntites = Arrays.asList(planPremium, planBasic);
        List<PlanAbonnementDTO> plansDTOs = Arrays.asList(planPremiumDTO, planBasicDTO);

        when(planAbonnementRepository.findByActifTrue()).thenReturn(plansEntites);
        when(abonnementMapper.toPlanDtos(plansEntites)).thenReturn(plansDTOs);

        // When
        List<PlanAbonnementDTO> result = abonnementService.getPlansActifs();

        // Then
        assertThat(result)
                .containsExactly(planPremiumDTO, planBasicDTO);
    }
}