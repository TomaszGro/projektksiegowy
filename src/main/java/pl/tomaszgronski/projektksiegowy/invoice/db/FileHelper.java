package pl.tomaszgronski.projektksiegowy.invoice.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.tomaszgronski.projektksiegowy.invoice.model.Invoice;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service

public class FileHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileHelper.class.toString());
    @Value("${fileHelper.database.path}")
    private String filePath;

    public List<Invoice> getAllDataFromFile() {
        LOGGER.info("Get all invoices");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        List<Invoice> invoices = new ArrayList<>();
        File file = new File(filePath);
        try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                Invoice invoice = objectMapper.readValue(line, Invoice.class);
                invoices.add(invoice);
            }
        } catch (Exception e) {
            LOGGER.error("Error" + invoices.toString(), e);
        }
        return invoices;
    }

    public void saveInvoice(Invoice invoice) throws IOException {
        LOGGER.info("Save invoice");
        if (invoice.getId() == null) {
            Invoice invoice1 = getAllDataFromFile().stream().max(Comparator.comparingLong(Invoice::getId)).orElse(new Invoice());
            Long maxId = invoice1.getId();
            if (maxId == null) {
                maxId = 1l;
            } else {
                maxId = maxId + 1l;
            }
            invoice.setId(maxId);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        File file = new File(filePath);
        try (FileWriter fw = new FileWriter(file, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            String object = objectMapper.writeValueAsString(invoice);
            bw.write(object);
            bw.newLine();
        } catch (Exception e) {
            LOGGER.error("Unable to save invoice" + invoice.toString(), e);
            throw e;
        }
    }

    public void updateInvoice(Invoice invoice) throws IOException {
        LOGGER.info("Update invoice");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        File file = new File(filePath);
        File filetemp = new File("temp.txt");
        try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr);
             FileWriter fw = new FileWriter(filetemp); BufferedWriter bw = new BufferedWriter(fw)) {
            String line;
            while ((line = br.readLine()) != null) {
                Invoice invoiceInLine = objectMapper.readValue(line, Invoice.class);
                if (invoiceInLine.getId().equals(invoice.getId())) {
                    String object = objectMapper.writeValueAsString(invoice);
                    bw.write(object);
                    bw.newLine();
                } else {
                    String object = objectMapper.writeValueAsString(invoiceInLine);
                    bw.write(object);
                    bw.newLine();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Unable to update invoice" + invoice.toString(), e);
            throw e;
        }
        try {
            Files.move(filetemp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            LOGGER.error("Unable to move file to another directory" + invoice, e);
            throw e;
        }
    }

    public boolean deleteInvoice(Long invoiceId) {
        boolean successDelete = false;
        LOGGER.info("Delete invoice");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        File file = new File(filePath);
        File filetemp = new File("temp.txt");
        try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr);
             FileWriter fw = new FileWriter(filetemp); BufferedWriter bw = new BufferedWriter(fw)) {
            String line;
            while ((line = br.readLine()) != null) {
                Invoice invoiceInLine = objectMapper.readValue(line, Invoice.class);
                if (invoiceInLine.getId().equals(invoiceId)) {
                    successDelete = true;
                    continue;
                } else {
                    String object = objectMapper.writeValueAsString(invoiceInLine);
                    bw.write(object);
                    bw.newLine();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Unable to delete invoice" + invoiceId, e);
            e.printStackTrace();
        }
        try {
            Files.move(filetemp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            LOGGER.error("Unable to move file to another directory" + invoiceId, e);
            e.printStackTrace();
        }
        return successDelete;
    }

}
