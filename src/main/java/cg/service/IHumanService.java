package cg.service;

import cg.model.Human;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IHumanService {
    Page<Human> findAll(Pageable pageable);

    void save(Human human);

    void delete (Long id);

    Optional<Human> findById(Long id);

    Page<Human> findByName(Pageable  pageable, String search );
}
