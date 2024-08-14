

package com.brodygaudel.ebank.util.pdf;

import com.brodygaudel.ebank.entities.AccountOperation;
import com.brodygaudel.ebank.exceptions.AccountOperationNotFoundException;
import com.brodygaudel.ebank.repositories.AccountOperationRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import org.springframework.core.env.Environment;


@Component
@Slf4j
public class InvoiceGenerator {

    private final AccountOperationRepository repository;
    private final Environment environment;

    public InvoiceGenerator(AccountOperationRepository repository, Environment environment) {
        this.repository = repository;
        this.environment = environment;
    }

    public String generateInvoice(Long id) throws AccountOperationNotFoundException {
        log.info("In generate Invoice()");
        AccountOperation operation = repository.findById(id)
                .orElseThrow( () -> new AccountOperationNotFoundException("Operation Not Found"));
        String path = environment.getProperty("invoice") + "/operation" + id + ".pdf";
        writeInvoice(operation, path);
        log.info("Invoice generated");
        return path;
    }


    private void writeInvoice(@NotNull AccountOperation operation, String path) {
        try{
            PDPage newPage = new PDPage();
            PDDocument pdDocument = getDocument();
            if (pdDocument != null){
                pdDocument.addPage(newPage);
                PDPage myPage = pdDocument.getPage(0);
                PDPageContentStream cs = getPageContentStream(pdDocument, myPage);
                if (cs != null){
                    cs.beginText();
                    cs.setFont(PDType1Font.TIMES_ROMAN, 20);
                    cs.newLineAtOffset(140, 750);
                    cs.showText("SOFT E-BANK CORPORATION");
                    cs.endText();

                    cs.beginText();
                    cs.setFont(PDType1Font.TIMES_ROMAN, 18);
                    cs.newLineAtOffset(270, 690);
                    cs.showText("INVOICE");
                    cs.endText();

                    cs.beginText();
                    cs.setFont(PDType1Font.TIMES_ROMAN, 14);
                    cs.setLeading(20f);
                    cs.newLineAtOffset(60, 610);
                    cs.showText("FIRSTNAME: ");
                    cs.newLine();
                    cs.showText("NAME: ");
                    cs.newLine();
                    cs.showText("ACCOUNT ID: ");
                    cs.newLine();
                    cs.showText("DATE: ");
                    cs.newLine();
                    cs.showText("TYPE: ");
                    cs.newLine();
                    cs.showText("AMOUNT: ");
                    cs.newLine();
                    cs.showText("DESCRIPTION: ");
                    cs.newLine();
                    cs.showText("REFERENCE: ");
                    cs.endText();

                    cs.beginText();
                    cs.setFont(PDType1Font.TIMES_ROMAN, 14);
                    cs.setLeading(20f);
                    cs.newLineAtOffset(170, 610);
                    cs.showText(operation.getBankAccount().getCustomer().getFirstname());
                    cs.newLine();
                    cs.showText(operation.getBankAccount().getCustomer().getName());
                    cs.newLine();

                    cs.showText(operation.getBankAccount().getId());
                    cs.newLine();

                    cs.showText(String.valueOf(operation.getOperationDate()));
                    cs.newLine();

                    cs.showText(String.valueOf(operation.getType()));
                    cs.newLine();

                    cs.showText(String.valueOf(operation.getAmount()));
                    cs.newLine();

                    cs.showText(operation.getDescription());
                    cs.newLine();

                    cs.showText(String.valueOf(operation.getId()));
                    cs.endText();

                    cs.close();
                    pdDocument.save(path);
                }else {
                    log.warn("PageContentStream is null, pdf will not be generated");
                }
                pdDocument.close();
            }else{
                log.warn("PDDocument is null, pdf will not be generated");
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }

    }


    private @Nullable PDDocument getDocument(){
        try {
            return new PDDocument();
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    private @Nullable PDPageContentStream getPageContentStream(PDDocument pdDocument,PDPage myPage){
        try {
            return new PDPageContentStream(pdDocument, myPage);
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
}
