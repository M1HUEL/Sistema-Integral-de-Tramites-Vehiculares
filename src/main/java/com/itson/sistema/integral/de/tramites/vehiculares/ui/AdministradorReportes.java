package com.itson.sistema.integral.de.tramites.vehiculares.ui;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.FileOutputStream;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import com.itson.sistema.integral.de.tramites.vehiculares.ui.componentes.EncabezadoComponente;
import com.itson.sistema.integral.de.tramites.vehiculares.ui.formularios.FormularioReportes;
import com.itson.sistema.integral.de.tramites.vehiculares.ui.tablas.TablaReportesPanel;
import java.awt.HeadlessException;
import java.io.FileNotFoundException;

public class AdministradorReportes extends javax.swing.JFrame {

    public AdministradorReportes() {
        initComponents();

        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel contenido = new JPanel(new BorderLayout(10, 10));

        FormularioReportes formulario = new FormularioReportes();
        TablaReportesPanel tablaReportes = new TablaReportesPanel();

        formulario.btnBuscar.addActionListener(e -> {
            tablaReportes.limpiarTabla();
            tablaReportes.agregarFila("2025-01-14", "Expedición de Licencia", "Juan Pérez", 900);
            tablaReportes.agregarFila("2025-02-20", "Expedición de Placas", "María López", 1500);
        });

        contenido.add(formulario, BorderLayout.NORTH);
        contenido.add(tablaReportes, BorderLayout.CENTER);

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton btnExportar = new JButton("Exportar PDF");
        JButton btnCerrar = new JButton("Cerrar");

        controles.add(btnExportar);
        controles.add(btnCerrar);

        btnCerrar.addActionListener(e -> dispose());

        btnExportar.addActionListener(e -> {
            exportarTablaAPDF(tablaReportes);
        });

        EncabezadoComponente encabezado = new EncabezadoComponente();

        panel.add(encabezado, BorderLayout.NORTH);
        panel.add(contenido, BorderLayout.CENTER);
        panel.add(controles, BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);
    }

    private void exportarTablaAPDF(TablaReportesPanel tablaPanel) {
        Document documento = new Document(PageSize.A4, 50, 50, 50, 50);

        try {
            String nombreArchivo = "ReporteTramites_" + LocalDate.now() + ".pdf";
            PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));

            writer.setPageEvent(new PdfPageEventHelper() {
                @Override
                public void onEndPage(PdfWriter writer, Document document) {
                    ColumnText.showTextAligned(writer.getDirectContent(),
                            Element.ALIGN_RIGHT,
                            new Phrase(String.format("Página %d", writer.getPageNumber())),
                            document.right() - 50,
                            document.bottom() - 20, 0);
                }
            });

            documento.open();

            Font titulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph p = new Paragraph("Reporte de Trámites Realizados", titulo);
            p.setAlignment(Element.ALIGN_CENTER);
            documento.add(p);

            documento.add(new Paragraph("Fecha de generación: " + LocalDate.now()));
            documento.add(Chunk.NEWLINE);

            PdfPTable tabla = new PdfPTable(tablaPanel.tabla.getColumnCount());
            tabla.setWidthPercentage(100);

            for (int i = 0; i < tablaPanel.tabla.getColumnCount(); i++) {
                PdfPCell celda = new PdfPCell(new Phrase(tablaPanel.tabla.getColumnName(i)));
                celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(celda);
            }

            for (int i = 0; i < tablaPanel.tabla.getRowCount(); i++) {
                for (int j = 0; j < tablaPanel.tabla.getColumnCount(); j++) {
                    tabla.addCell(tablaPanel.tabla.getValueAt(i, j).toString());
                }
            }

            documento.add(tabla);
            documento.close();

            JOptionPane.showMessageDialog(this, "PDF generado correctamente:\n" + nombreArchivo);

        } catch (DocumentException | HeadlessException | FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error al generar el PDF: " + ex.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1440, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
