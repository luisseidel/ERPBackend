package com.seidelsoft.ERPBackend.endereco.service;

import com.seidelsoft.ERPBackend.endereco.model.Cidade;
import com.seidelsoft.ERPBackend.endereco.repository.CidadeRepository;
import com.seidelsoft.ERPBackend.system.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CidadeService extends BaseService<Cidade> {

    @Autowired
    CidadeRepository cidadeRepository;

    @Override
    public boolean validar(Cidade entity) {
        if (entity.getNome() == null || entity.getNome().isEmpty()) {
            return false;
        }
        if (entity.getEstado() == null || entity.getEstado().getUf() == null || entity.getEstado().getUf().isEmpty()) {
            return false;
        }
        if (entity.getIbge() == null || entity.getIbge().isEmpty()) {
            return false;
        }
        
        return true;
    }

    @Override
    public Optional<Cidade> getById(Long id) {
        return cidadeRepository.findById(id);
    }

    @Override
    public List<Cidade> findAll(Sort sort) {
        if (sort != null) {
            return cidadeRepository.findAll(sort);
        }
        return null;
    }

    @Override
    public Page<Cidade> findAllPaged(Pageable pageable) {
        return cidadeRepository.findAll(pageable);
    }

    @Override
    public void save(Cidade entity) {
        if (!validar(entity)) {
            throw new IllegalArgumentException("Dados inválidos para a cidade");
        }
        cidadeRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if (id == null || !cidadeRepository.existsById(id)) {
            throw new IllegalArgumentException("Estado não encontrado");
        }
        cidadeRepository.deleteById(id);
    }

    public Cidade getByIBGE(String ibge) {
        return cidadeRepository.findByIbge(ibge);
    }

    public Cidade findByNomeAndUF(String nome, String uf) {
        return cidadeRepository.findByNomeAndEstado_Uf(nome, uf);
    }

}
