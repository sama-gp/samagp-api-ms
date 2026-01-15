package sn.fr.samagp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sn.fr.samagp.repository.model.TypeZoneGeo;
import sn.fr.samagp.repository.model.ZoneGeo;

import java.util.List;

public interface ZoneGeoRepository extends JpaRepository<ZoneGeo, Long> {
    List<ZoneGeo> findByTypeCodeAndParentId(String typeCode, Long parentId);
    List<ZoneGeo> findByTypeCode(String typeCode);

    List<ZoneGeo> findByParentId(Long parentId);

    @Query("SELECT DISTINCT z.type FROM ZoneGeo z")
    List<TypeZoneGeo> findAllZoneTypes();
}
