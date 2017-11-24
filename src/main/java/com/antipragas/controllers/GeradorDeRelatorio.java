package com.antipragas.controllers;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class GeradorDeRelatorio {
    private Connection conexao;

    public GeradorDeRelatorio() {
        conexao = DataSourceUtils.getConnection(DataSourceBuilder.create().build());
    }

    public void gerarPdf(String jrxml, // nome do template do relatório
                         Map<String, Object> parametros,
                         OutputStream saida) {
        System.out.println(saida);
        System.out.println(conexao);

        try {
            // compila o template do relatório
            JasperReport jasper = JasperCompileManager.compileReport(jrxml);

            // preenche o relatório com os dados do BD
            JasperPrint print = JasperFillManager.fillReport(jasper, parametros, conexao);

            // exporta o relatório para pdf
            JRExporter exportador = new JRPdfExporter();
            exportador.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exportador.setParameter(JRExporterParameter.OUTPUT_STREAM, saida);

            exportador.exportReport();

        } catch (JRException e) {
            // caso não seja possível encontrar o template
            e.printStackTrace();
        }
    }
}
