package sn.fr.samagp.services.impl;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sn.fr.samagp.exceptions.ResourceNotFoundException;
import sn.fr.samagp.repository.PaiementRepository;
import sn.fr.samagp.repository.model.Paiement;
import sn.fr.samagp.services.inter.IFactureService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.text.DecimalFormat;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FactureServiceImpl implements IFactureService {

    private final PaiementRepository paiementRepository;

    // Couleurs personnalisées
    private static final Color PRIMARY_COLOR = new DeviceRgb(59, 130, 246); // Blue-600
    private static final Color SECONDARY_COLOR = new DeviceRgb(107, 114, 128); // Gray-500
    private static final Color SUCCESS_COLOR = new DeviceRgb(34, 197, 94); // Green-500
    private static final Color BACKGROUND_COLOR = new DeviceRgb(249, 250, 251); // Gray-50

    @Override
    public ByteArrayInputStream genererFacturePdf(UUID paiementId) {
        try {
            // Récupérer le paiement
            Paiement paiement = paiementRepository.findById(paiementId)
                    .orElseThrow(() -> new ResourceNotFoundException("Paiement non trouvé: " + paiementId));

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(out));
            Document document = new Document(pdfDoc, PageSize.A4);
            document.setMargins(50, 50, 50, 50);

            // Fonts
            PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont fontRegular = PdfFontFactory.createFont(StandardFonts.HELVETICA);

            // En-tête avec logo et informations
            document.add(createHeader(paiement, fontBold, fontRegular));
            document.add(new Paragraph("\n"));

            // Informations client et facture
            document.add(createInfoSections(paiement, fontBold, fontRegular));
            document.add(new Paragraph("\n"));

            // Détails de l'abonnement
            document.add(createAbonnementDetails(paiement, fontBold, fontRegular));
            document.add(new Paragraph("\n"));

            // Résumé du paiement
            document.add(createPaymentSummary(paiement, fontBold, fontRegular));
            document.add(new Paragraph("\n"));

            // Notes et remerciements
            document.add(createFooter(paiement, fontRegular));

            document.close();
            return new ByteArrayInputStream(out.toByteArray());

        } catch (Exception e) {
            log.error("Erreur lors de la génération de la facture", e);
            throw new RuntimeException("Erreur lors de la génération de la facture: " + e.getMessage());
        }
    }

    private Table createHeader(Paiement paiement, PdfFont fontBold, PdfFont fontRegular) throws Exception {
        float[] columnWidths = {1, 2, 1};
        Table headerTable = new Table(columnWidths);
        headerTable.setWidth(UnitValue.createPercentValue(100));

        // Logo (à gauche)
        Cell logoCell = new Cell();
        logoCell.setBorder(Border.NO_BORDER);
        try {
            // Vous pouvez remplacer cette URL par le chemin de votre logo
            URL logoUrl = new URL("https://via.placeholder.com/150x50/3B82F6/FFFFFF?text=SAMAGP");
            ImageData imageData = ImageDataFactory.create(logoUrl);
            Image logo = new Image(imageData);
            logo.setWidth(100);
            logoCell.add(logo);
        } catch (Exception e) {
            // Logo de remplacement si l'URL n'est pas accessible
            Text logoText = new Text("SAMAGP\nTransport & Voyage")
                    .setFont(fontBold)
                    .setFontColor(PRIMARY_COLOR)
                    .setFontSize(14);
            logoCell.add(new Paragraph(logoText));
        }
        headerTable.addCell(logoCell);

        // Titre (centre)
        Cell titleCell = new Cell();
        titleCell.setBorder(Border.NO_BORDER);
        titleCell.setTextAlignment(TextAlignment.CENTER);

        Text title = new Text("FACTURE")
                .setFont(fontBold)
                .setFontSize(20)
                .setFontColor(PRIMARY_COLOR);
        Text subtitle = new Text("\nService d'Abonnement")
                .setFont(fontRegular)
                .setFontSize(12)
                .setFontColor(SECONDARY_COLOR);

        titleCell.add(new Paragraph(title));
        titleCell.add(new Paragraph(subtitle));
        headerTable.addCell(titleCell);

        // Numéro de facture (droite)
        Cell invoiceCell = new Cell();
        invoiceCell.setBorder(Border.NO_BORDER);
        invoiceCell.setTextAlignment(TextAlignment.RIGHT);

        Text invoiceText = new Text("Facture N°: " + paiement.getReference())
                .setFont(fontBold)
                .setFontSize(12);
        Text dateText = new Text("\nDate: " + formatDate(paiement.getDatePaiement()))
                .setFont(fontRegular)
                .setFontSize(10);

        invoiceCell.add(new Paragraph(invoiceText));
        invoiceCell.add(new Paragraph(dateText));
        headerTable.addCell(invoiceCell);

        return headerTable;
    }

    private Table createInfoSections(Paiement paiement, PdfFont fontBold, PdfFont fontRegular) {
        float[] columnWidths = {1, 1};
        Table infoTable = new Table(columnWidths);
        infoTable.setWidth(UnitValue.createPercentValue(100));

        // Informations client
        Cell clientCell = new Cell();
        clientCell.setBackgroundColor(BACKGROUND_COLOR);
        clientCell.setPadding(10);

        Text clientTitle = new Text("CLIENT")
                .setFont(fontBold)
                .setFontSize(12)
                .setFontColor(PRIMARY_COLOR);
        clientCell.add(new Paragraph(clientTitle));

        String clientInfo = String.format(
                "%s %s\n%s\nID: %s",
                paiement.getAbonnementClient().getClient().getFirstName(),
                paiement.getAbonnementClient().getClient().getLastName(),
                paiement.getAbonnementClient().getClient().getEmail(),
                paiement.getAbonnementClient().getClient().getKeycloakId()
        );
        Text clientDetails = new Text(clientInfo)
                .setFont(fontRegular)
                .setFontSize(10);
        clientCell.add(new Paragraph(clientDetails));

        infoTable.addCell(clientCell);

        // Informations facture
        Cell invoiceInfoCell = new Cell();
        invoiceInfoCell.setBackgroundColor(BACKGROUND_COLOR);
        invoiceInfoCell.setPadding(10);

        Text invoiceInfoTitle = new Text("INFORMATIONS FACTURE")
                .setFont(fontBold)
                .setFontSize(12)
                .setFontColor(PRIMARY_COLOR);
        invoiceInfoCell.add(new Paragraph(invoiceInfoTitle));

        String invoiceInfo = String.format(
                "Statut: %s\nMéthode: %s\nTransaction: %s",
                paiement.getStatut().toString(),
                paiement.getMethode().toString(),
                paiement.getIdTransactionFournisseur()
        );
        Text invoiceDetails = new Text(invoiceInfo)
                .setFont(fontRegular)
                .setFontSize(10);
        invoiceInfoCell.add(new Paragraph(invoiceDetails));

        infoTable.addCell(invoiceInfoCell);

        return infoTable;
    }

    private Table createAbonnementDetails(Paiement paiement, PdfFont fontBold, PdfFont fontRegular) {
        Table detailsTable = new Table(new float[]{3, 2, 2, 2});
        detailsTable.setWidth(UnitValue.createPercentValue(100));
        detailsTable.setMarginBottom(20);

        // En-tête du tableau
        String[] headers = {"DESCRIPTION", "DURÉE", "QUANTITÉ", "MONTANT"};
        for (String header : headers) {
            Cell headerCell = new Cell();
            headerCell.setBackgroundColor(PRIMARY_COLOR);
            headerCell.setPadding(8);
            headerCell.add(new Paragraph(header)
                    .setFont(fontBold)
                    .setFontColor(ColorConstants.WHITE)
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER));
            detailsTable.addCell(headerCell);
        }

        // Détails de l'abonnement
        var abonnement = paiement.getAbonnementClient();
        var plan = abonnement.getPlan();

        // Description
        Cell descCell = new Cell();
        descCell.setPadding(8);
        descCell.add(new Paragraph(plan.getNom() + "\n" + plan.getDescription())
                .setFont(fontRegular)
                .setFontSize(10));
        detailsTable.addCell(descCell);

        // Durée
        Cell dureeCell = new Cell();
        dureeCell.setPadding(8);
        dureeCell.add(new Paragraph(plan.getDuree().toString())
                .setFont(fontRegular)
                .setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER));
        detailsTable.addCell(dureeCell);

        // Quantité
        Cell qteCell = new Cell();
        qteCell.setPadding(8);
        qteCell.add(new Paragraph("1")
                .setFont(fontRegular)
                .setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER));
        detailsTable.addCell(qteCell);

        // Montant
        Cell montantCell = new Cell();
        montantCell.setPadding(8);
        montantCell.add(new Paragraph(formatCurrency(paiement.getMontant(), paiement.getDevise()))
                .setFont(fontRegular)
                .setFontSize(10)
                .setTextAlignment(TextAlignment.RIGHT));
        detailsTable.addCell(montantCell);

        return detailsTable;
    }

    private Table createPaymentSummary(Paiement paiement, PdfFont fontBold, PdfFont fontRegular) {
        float[] columnWidths = {2, 1};
        Table summaryTable = new Table(columnWidths);
        summaryTable.setWidth(UnitValue.createPercentValue(60));
        summaryTable.setHorizontalAlignment(HorizontalAlignment.RIGHT);

        // Sous-total
        addSummaryRow(summaryTable, "Sous-total:",
                formatCurrency(paiement.getMontant(), paiement.getDevise()),
                fontRegular, fontRegular);

        // TVA (exemple 20%)
        double tva = paiement.getMontant().doubleValue() * 0.20;
        addSummaryRow(summaryTable, "TVA (20%):",
                formatCurrency(tva, paiement.getDevise()),
                fontRegular, fontRegular);

        // Ligne séparatrice
        Cell separatorCell = new Cell(1, 2);
        separatorCell.setBorder(new SolidBorder(SECONDARY_COLOR, 0.5f));
        separatorCell.setHeight(10);
        separatorCell.setBorder(Border.NO_BORDER);
        summaryTable.addCell(separatorCell);

        // Total
        double total = paiement.getMontant().doubleValue() + tva;
        addSummaryRow(summaryTable, "TOTAL:",
                formatCurrency(total, paiement.getDevise()),
                fontBold, fontBold);

        // Statut payé
        Cell statusCell = new Cell(1, 2);
        statusCell.setBorder(Border.NO_BORDER);
        statusCell.setTextAlignment(TextAlignment.RIGHT);
        statusCell.setMarginTop(10);

        Text statusText = new Text("STATUT: PAYÉ")
                .setFont(fontBold)
                .setFontColor(SUCCESS_COLOR)
                .setFontSize(12);
        statusCell.add(new Paragraph(statusText));
        summaryTable.addCell(statusCell);

        return summaryTable;
    }

    private Paragraph createFooter(Paiement paiement, PdfFont fontRegular) {
        Paragraph footer = new Paragraph();
        footer.setMarginTop(30);

        // Période de validité
        Text validityText = new Text("Période de validité: ")
                .setFont(fontRegular)
                .setFontSize(9);
        Text validityDates = new Text(
                formatDate(paiement.getAbonnementClient().getDateDebut()) + " - " +
                        formatDate(paiement.getAbonnementClient().getDateFin()))
                .setFont(fontRegular)
                .setFontSize(9)
                .setFontColor(PRIMARY_COLOR);

        footer.add(validityText);
        footer.add(validityDates);
        footer.add(new Text("\n\n"));

        // Notes
        Text notesTitle = new Text("Notes:\n")
                .setFont(fontRegular)
                .setFontSize(9)
                .setFontColor(SECONDARY_COLOR);
        Text notesContent = new Text(
                "• Cette facture est un justificatif de paiement officiel.\n" +
                        "• Conservez cette facture pour vos archives.\n" +
                        "• Pour toute question, contactez notre service client.\n" +
                        "• Merci de votre confiance !")
                .setFont(fontRegular)
                .setFontSize(8);

        footer.add(notesTitle);
        footer.add(notesContent);
        footer.add(new Text("\n\n"));

        // Signature
        Text signature = new Text("L'équipe SAMAGP\nService de Transport et Voyage")
                .setFont(fontRegular)
                .setFontSize(10)
                .setFontColor(PRIMARY_COLOR)
                .setTextAlignment(TextAlignment.CENTER);

        footer.add(signature);
        footer.setTextAlignment(TextAlignment.CENTER);

        return footer;
    }

    private void addSummaryRow(Table table, String label, String value,
                               PdfFont labelFont, PdfFont valueFont) {
        // Label
        Cell labelCell = new Cell();
        labelCell.setBorder(Border.NO_BORDER);
        labelCell.setPadding(5);
        labelCell.add(new Paragraph(label)
                .setFont(labelFont)
                .setFontSize(10)
                .setTextAlignment(TextAlignment.RIGHT));
        table.addCell(labelCell);

        // Value
        Cell valueCell = new Cell();
        valueCell.setBorder(Border.NO_BORDER);
        valueCell.setPadding(5);
        valueCell.add(new Paragraph(value)
                .setFont(valueFont)
                .setFontSize(10)
                .setTextAlignment(TextAlignment.RIGHT));
        table.addCell(valueCell);
    }

    private String formatDate(java.time.LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    private String formatCurrency(Number amount, sn.fr.samagp.repository.model.Devise devise) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String symbol = getCurrencySymbol(devise);
        return df.format(amount) + " " + symbol;
    }

    private String getCurrencySymbol(sn.fr.samagp.repository.model.Devise devise) {
        return switch (devise) {
            case EUR -> "€";
            case USD -> "$";
            case XOF -> "CFA";
            default -> devise.toString();
        };
    }
}