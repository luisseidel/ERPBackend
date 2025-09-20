package com.seidelsoft.ERPBackend.endereco.service;

import com.seidelsoft.ERPBackend.endereco.model.Cidade;
import com.seidelsoft.ERPBackend.endereco.repository.CidadeRepository;
import com.seidelsoft.ERPBackend.system.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class CidadeService extends BaseService<Cidade, CidadeRepository> {

    @Override
    public boolean validar(Cidade entity, StringBuilder msgValidacao) {
        if (entity.getNome() == null || entity.getNome().isEmpty()) {
            return false;
        }
        if (entity.getEstado() == null || entity.getEstado().getUf() == null || entity.getEstado().getUf().isEmpty()) {
            return false;
        }
        if (entity.getIbge() == null || entity.getIbge().isEmpty()) {
            return false;
        }
        return msgValidacao.isEmpty();
    }

    public Cidade getByIBGE(String ibge) {
        return getSpecificRepository().findByIbge(ibge);
    }

    public Cidade findByNomeAndUF(String nome, String uf) {
        return getSpecificRepository().findByNomeAndEstado_Uf(nome, uf);
    }

}
