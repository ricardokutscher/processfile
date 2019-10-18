package com.kutscher.fileprocess.service;

import com.kutscher.fileprocess.dto.Customer;
import com.kutscher.fileprocess.dto.Sale;
import com.kutscher.fileprocess.dto.Salesman;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
public class OutputFileService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private FileService fileService;

    public OutputFileService(FileService fileService) {
        this.fileService = fileService;
    }

    public void process(List<Object> listLines, String fileName) throws IOException {
        logger.info("Processing line: " + listLines.toString());
        List<String> lines = new ArrayList<String>();
        Map<Class, ?> map = listLines.stream().collect(Collectors.groupingBy(Object::getClass));
        List<Sale> sales = (List<Sale>) map.get(Sale.class);
        List<Salesman> salesmens = (List<Salesman>) map.get(Salesman.class);
        List<Customer> customers = (List<Customer>) map.get(Customer.class);

        logger.info("Processing line: " + customers.toString());
        lines.add("Total clients: " + getTotalSalesman(salesmens));
        lines.add("Total customer: " + getTotalCustomer(customers));
        lines.add("Most expensive sale: " + getExpensiveSale(sales));
        lines.add("Worst salesman: " + getWorstSalesman(sales));
        fileService.generateOutputFile(lines, fileName);
    }

    private Integer getTotalSalesman(List<Salesman> list){
        return list.stream().distinct().collect(Collectors.toList()).size();
    }

    private Integer getTotalCustomer(List<Customer> list){
        return list.stream().distinct().collect(Collectors.toList()).size();
    }

    private String getExpensiveSale(List<Sale> list){
        return list.stream()
                .sorted(comparing(Sale::getTotalOrder).reversed()).findFirst().get().getSaleId();
    }

    private String getWorstSalesman(List<Sale> list){
        return list.stream()
                .collect(Collectors.groupingBy(Sale::getSalesName,
                        Collectors.reducing(BigDecimal.ZERO, Sale::getTotalOrder, BigDecimal::add)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue()).findFirst().get().getKey();
    }


}
