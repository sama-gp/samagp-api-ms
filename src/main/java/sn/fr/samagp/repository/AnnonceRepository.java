package sn.fr.samagp.repository;

import org.springframework.data.repository.Repository;
import sn.fr.samagp.repository.model.Annonce;

import java.util.List;

public interface AnnonceRepository extends Repository<Long, Annonce> {
    List<Annonce> findAllById(List<Long> ids);
}
