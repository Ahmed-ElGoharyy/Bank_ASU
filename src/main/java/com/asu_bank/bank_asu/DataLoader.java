package com.asu_bank.bank_asu;

import com.asu_bank.bank_asu.Model.Bank;
import com.asu_bank.bank_asu.Model.Employee;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataLoader {

    public static final String EMP_CLASS = "Employee";

   // Map<Long,Object> datamap = new HashMap<Long,Object>();




    public   void   loadData( Bank bank){

        Map<String,Map<Long,Object>> datamap = new HashMap<String,Map<Long,Object>>();

        Map<Long,Object> empMap = new HashMap<Long,Object>();
        datamap.put(EMP_CLASS,empMap);
        try {
            String currentPath = new java.io.File(".").getCanonicalPath();
            System.out.println("Current dir:"+currentPath);


            Path path = Paths.get("Employee.csv");

            List<Employee> emps = new ArrayList<Employee>();
            emps = readCsvToBeanList(path, Employee.class, emps);
            for (Employee emp : emps) {
                System.out.println(emp.getId() + " - " + emp.getFirstName() );
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static <T> List<T> readCsvToBeanList(Path path, Class clazz, List<T> list) throws Exception {
        HeaderColumnNameMappingStrategy ms = new HeaderColumnNameMappingStrategy();
        ms.setType(clazz);

        try (Reader reader = Files.newBufferedReader(path)) {
            CsvToBean cb = new CsvToBeanBuilder(reader)
                    .withType(clazz)
                    .withMappingStrategy(ms)
                    .build();

            list = cb.parse();
        }
        return list;
    }


    public static void main(String[] args) {
        Bank bank = Bank.getInstance();
       DataLoader dl=new DataLoader();
        dl.loadData(bank);
    }
}
