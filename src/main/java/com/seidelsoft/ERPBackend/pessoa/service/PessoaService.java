package com.seidelsoft.ERPBackend.pessoa.service;

import com.seidelsoft.ERPBackend.endereco.model.Endereco;
import com.seidelsoft.ERPBackend.endereco.service.EnderecoService;
import com.seidelsoft.ERPBackend.pessoa.model.Pessoa;
import com.seidelsoft.ERPBackend.pessoa.repository.PessoaRepository;
import com.seidelsoft.ERPBackend.system.exception.ValidacaoException;
import com.seidelsoft.ERPBackend.system.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaService extends BaseService<Pessoa, PessoaRepository> {

	@Autowired
	private EnderecoService enderecoService;

	public Pessoa createUpdate(Pessoa dto) throws ValidacaoException {
		Endereco endereco = dto.getEndereco();
		if (StringUtils.isNotEmpty(dto.getEndereco().getCep())) {
			endereco = enderecoService.buscaByCep(dto.getEndereco().getCep());
			endereco.setNumero(dto.getEndereco().getNumero());
			endereco.setComplemento(dto.getEndereco().getComplemento());
			endereco.setPontoReferencia(dto.getEndereco().getPontoReferencia());
            endereco = enderecoService.save(endereco);
		}

        Pessoa pessoa = getSpecificRepository().findByCpfCnpj(dto.getCpfCnpj());
        if (pessoa == null || pessoa.getId() == null) {
            pessoa = new Pessoa();
        }
        pessoa.setCpfCnpj(dto.getCpfCnpj());
        pessoa.setNome(dto.getNome());
        pessoa.setSexo(dto.getSexo());
        pessoa.setEndereco(endereco);
        pessoa.setEmail(dto.getEmail());
        pessoa = save(pessoa);

		return pessoa;
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

    @Override
    public boolean validar(Pessoa pessoa, StringBuilder msgValidacao) {
        if (pessoa != null && pessoa.getId() == null &&
            StringUtils.isNotEmpty(pessoa.getCpfCnpj()) &&
            getSpecificRepository().existsByCpfCnpj(pessoa.getCpfCnpj())
        ) {
            msgValidacao.append("Já existe um cadastro com esse CPF!");
        }
        return msgValidacao.isEmpty();
    }

}
