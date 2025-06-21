package sn.fr.samagp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sn.fr.samagp.repository.ZoneGeoRepository;
import sn.fr.samagp.repository.dto.TypeZoneGeoDTO;
import sn.fr.samagp.repository.dto.ZoneGeoDTO;
import sn.fr.samagp.repository.model.TypeZoneGeo;
import sn.fr.samagp.repository.model.ZoneGeo;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/zones-geo")
@RequiredArgsConstructor
public class ZoneGeoController {

    private final ZoneGeoRepository zoneGeoRepository;

    @GetMapping
    public List<ZoneGeoDTO> getZonesByTypeAndParent(
            @RequestParam(required = false) String typeCode,
            @RequestParam(required = false) Long parentId) {

        if (typeCode != null && parentId != null) {
            return zoneGeoRepository.findByTypeCodeAndParentId(typeCode, parentId)
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } else if (typeCode != null) {
            return zoneGeoRepository.findByTypeCode(typeCode)
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } else if (parentId != null) {
            return zoneGeoRepository.findByParentId(parentId)
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } else {
            return zoneGeoRepository.findAll()
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("/countries")
    public List<ZoneGeoDTO> getAllCountries() {
        return zoneGeoRepository.findByTypeCode("PAYS")
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/types")
    public List<TypeZoneGeoDTO> getAllZoneTypes() {
        return zoneGeoRepository.findAllZoneTypes()
                .stream()
                .map(this::convertToTypeZoneGeoDTO)
                .collect(Collectors.toList());
    }

    // Méthodes de conversion
    private ZoneGeoDTO convertToZoneGeoDTO(ZoneGeo zoneGeo) {
        if (zoneGeo == null) {
            return null;
        }
        return new ZoneGeoDTO(
                zoneGeo.getId(),
                zoneGeo.getLibelle(),
                convertToTypeZoneGeoDTO(zoneGeo.getType()),
                convertToZoneGeoDTO(zoneGeo.getParent())
        );
    }

    private TypeZoneGeoDTO convertToTypeZoneGeoDTO(TypeZoneGeo typeZoneGeo) {
        if (typeZoneGeo == null) {
            return null;
        }
        return new TypeZoneGeoDTO(
                typeZoneGeo.getCode(),
                typeZoneGeo.getLibelle(),
                typeZoneGeo.getParent() != null ? convertToTypeZoneGeoDTO(typeZoneGeo.getParent()) : null
        );
    }

    @GetMapping("/{id}")
    public ZoneGeoDTO getZoneById(@PathVariable Long id) {
        return zoneGeoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Zone not found"));
    }

    private ZoneGeoDTO toDTO(ZoneGeo zoneGeo) {
        return new ZoneGeoDTO(
                zoneGeo.getId(),
                zoneGeo.getLibelle(),
                mapTypeZoneGeoToDTO(zoneGeo.getType()),
                zoneGeo.getParent() != null ? toDTO(zoneGeo.getParent()) : null
        );
    }

    private TypeZoneGeoDTO mapTypeZoneGeoToDTO(TypeZoneGeo typeZoneGeo) {
        if (typeZoneGeo == null) {
            return null;
        }
        return new TypeZoneGeoDTO(
                typeZoneGeo.getCode(),
                typeZoneGeo.getLibelle(),
                typeZoneGeo.getParent() != null ? mapTypeZoneGeoToDTO(typeZoneGeo.getParent()) : null
        );
    }
}