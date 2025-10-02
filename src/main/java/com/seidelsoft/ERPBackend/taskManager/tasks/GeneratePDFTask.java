package com.seidelsoft.ERPBackend.taskManager.tasks;

import com.seidelsoft.ERPBackend.pdfGenerator.dto.PdfPayloadDTO;
import com.seidelsoft.ERPBackend.pdfGenerator.service.PdfGeneratorService;
import com.seidelsoft.ERPBackend.taskManager.model.TaskTypeEnum;
import com.seidelsoft.ERPBackend.taskManager.service.BaseTask;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Setter
@Getter
@Component
@RequiredArgsConstructor
public class GeneratePDFTask extends BaseTask {

    private final PdfGeneratorService pdfGeneratorService;
    private PdfPayloadDTO pdfPayloadDTO;

    @Value("${pdf.output.dir}")
    private String outputDir;

    @Override
    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.PDF;
    }

    @Override
    public void execute() {
        try {
            log.info("Processando relatório:");
            ByteArrayOutputStream pdfStream = pdfGeneratorService.generatePdf(
                    pdfPayloadDTO.titulo(),
                    pdfPayloadDTO.cabecalhos(),
                    pdfPayloadDTO.dados()
            );

            Path outputPath = Paths.get(outputDir);
            Files.createDirectories(outputPath);
            String fileName = "relatorio_" + UUID.randomUUID() + ".pdf";
            Path filePath = outputPath.resolve(fileName);

            try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                pdfStream.writeTo(fos);
            }

            log.info("PDF gerado e salvo em: {}", filePath);

        } catch (Exception e) {
            log.error("Erro ao gerar ou salvar o PDF: {}", e.getMessage());
        }
    }
}
