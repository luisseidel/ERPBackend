package com.seidelsoft.ERPBackend.endereco.service;

import com.seidelsoft.ERPBackend.endereco.model.Cidade;
import com.seidelsoft.ERPBackend.endereco.repository.CidadeRepository;
import com.seidelsoft.ERPBackend.system.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Cidade> findAll() {
        return cidadeRepository.findAll();
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
