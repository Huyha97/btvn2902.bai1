package cg.service.impl;


import cg.model.Human;
import cg.repository.IHumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import cg.service.IHumanService;

import java.util.Optional;

@Service
public class HumanService implements IHumanService {

    @Autowired
    private IHumanRepository iHumanRepository;

    @Override
    public Page<Human> findAll(Pageable pageable) {
        return iHumanRepository.findAll(pageable);
    }

    @Override
    public void save(Human human) {
    iHumanRepository.save(human);

    }

    @Override
    public void delete(Long id) {
        iHumanRepository.deleteById(id);

    }

    @Override
    public Optional<Human> findById(Long id) {
        return iHumanRepository.findById(id);
    }

    @Override
    public Page<Human> findByName(Pageable pageable, String search) {
        return iHumanRepository.findAllByNameContaining(pageable, search);
    }
}
