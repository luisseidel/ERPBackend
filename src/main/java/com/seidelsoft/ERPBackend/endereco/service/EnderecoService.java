package com.seidelsoft.ERPBackend.endereco.service;

import com.seidelsoft.ERPBackend.buscacep.service.BuscaCepService;
import com.seidelsoft.ERPBackend.endereco.model.Endereco;
import com.seidelsoft.ERPBackend.endereco.repository.EnderecoRepository;
import com.seidelsoft.ERPBackend.system.exception.ValidacaoException;
import com.seidelsoft.ERPBackend.system.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class EnderecoService extends BaseService<Endereco, EnderecoRepository> {

	@Autowired
	private BuscaCepService buscaCepService;

    @Override
    public boolean validar(Endereco entity) {
        return true;
    }

	public ResponseEntity findByCep(String cep) {
		try {
			Endereco e = buscaByCep(cep);
			return ResponseEntity.ok(e);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	public Endereco buscaByCep(String cep) throws ValidacaoException {
        Endereco e = getSpecificRepository().findByCep(cep);

		if (e == null || e.getId() == null) {
			e = buscaCepService.buscar(cep);
		}

		return e;
	}

    @Override
    public void beforeSave(Endereco item) {

    }

    @Override
    public void afterSave(Endereco savedItem) {

    }

    @Override
    public void beforeDelete(Optional<Endereco> item) {

    }

    @Override
    public void afterDelete() {

    }
}
