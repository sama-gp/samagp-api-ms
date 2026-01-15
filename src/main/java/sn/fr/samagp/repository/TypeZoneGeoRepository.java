package sn.fr.samagp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.fr.samagp.repository.model.TypeZoneGeo;

import java.util.List;

public interface TypeZoneGeoRepository extends JpaRepository<TypeZoneGeo, Long> {
    List<TypeZoneGeo> findByCode(String code);
    // Méthode pour trouver tous les types de zone
    List<TypeZoneGeo> findAll();

    // Méthode pour trouver par code parent
    List<TypeZoneGeo> findByParentCode(String parentCode);
}
