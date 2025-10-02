package com.seidelsoft.ERPBackend.pdfGenerator.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

@Service
public class PdfGeneratorService {

    public ByteArrayOutputStream generatePdf(String titulo, List<String> cabecalhos, List<Map<String, Object>> dados)
            throws DocumentException {

        if (dados == null || dados.isEmpty() || cabecalhos == null || cabecalhos.isEmpty()) {
            return new ByteArrayOutputStream();
        }

        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
        Paragraph title = new Paragraph(titulo, titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(cabecalhos.size());
        table.setWidthPercentage(100);

        // Adiciona os cabeçalhos da tabela
        for (String cabecalho : cabecalhos) {
            table.addCell(new Phrase(cabecalho, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        }

        // Adiciona os dados à tabela
        for (Map<String, Object> linha : dados) {
            for (String cabecalho : cabecalhos) {
                Object valor = linha.get(cabecalho);
                table.addCell(valor != null ? valor.toString() : "");
            }
        }

        document.add(table);
        document.close();
        return outputStream;
    }
}
