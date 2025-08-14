package com.seidelsoft.ERPBackend.endereco.service;

import com.seidelsoft.ERPBackend.endereco.model.Cidade;
import com.seidelsoft.ERPBackend.endereco.repository.CidadeRepository;
import com.seidelsoft.ERPBackend.system.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CidadeService extends BaseService<Cidade> {

    @Autowired
    CidadeRepository cidadeRepository;

    @Override
    public Optional<Cidade> getById(Long id) {
        return cidadeRepository.findById(id);
    }

    @Override
    public Page<Cidade> findAll(Pageable pageable) {
        return cidadeRepository.findAll(pageable);
    }

    @Override
    public void save(Cidade entity) {
        cidadeRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        cidadeRepository.deleteById(id);
    }

    public Cidade getByIBGE(String ibge) {
        return cidadeRepository.findByIbge(ibge);
    }

    public Cidade findByNomeAndUF(String nome, String uf) {
        return cidadeRepository.findByNomeAndEstado_Uf(nome, uf);
    }

}
