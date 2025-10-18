package com.seidelsoft.ERPBackend.taskManager.tasks;

import com.seidelsoft.ERPBackend.endereco.model.Cidade;
import com.seidelsoft.ERPBackend.endereco.repository.CidadeRepository;
import com.seidelsoft.ERPBackend.taskManager.model.entity.JobTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TaskCidadesComLetraA implements JobTask {

    private final CidadeRepository cidadeRepository;

    @Override
    public void execute() {
        List<Cidade> cidades = cidadeRepository.findByNomeStartingWithIgnoreCase("A");

        if (cidades.isEmpty()) {
            System.out.println("Nenhuma cidade encontrada com a letra A.");
        } else {
            cidades.forEach(c -> System.out.println("Cidade encontrada: " + c.getNome()));
        }
    }

    @Override
    public String getJobName() {
        return "Cidades com Letra A";
    }
}

