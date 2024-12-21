package com.asu_bank.bank_asu;

import com.asu_bank.bank_asu.Model.*;


import com.asu_bank.bank_asu.Model.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;



import java.io.FileReader;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataLoader {
    private static final String BASE_PATH = "com/asu_bank/bank_asu/";
    private static final String CLIENTS_FILE_PATH = BASE_PATH + "clients.json";
    private static final String EMPLOYEES_FILE_PATH = BASE_PATH + "employees.json";
    public static List<Client> clientsList = new ArrayList<>();
    public static List<Employee> employeesList = new ArrayList<>();

    /**
     * Reads client and employee data from JSON files at program startup
     * Creates directory structure and files if they don't exist
     * @return boolean indicating if the read operation was successful
     */

    public static boolean loadDataFromFiles(Bank bank) {
        try {
            // Create directory structure if it doesn't exist
            File directory = new File(BASE_PATH);
            if (!directory.exists()) {
                if (directory.mkdirs()) {
                    System.out.println("Created directory structure: " + BASE_PATH);
                } else {
                    throw new IOException("Failed to create directory structure");
                }
            }

            // Configure Gson with settings matching our save method
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .serializeNulls()
                    .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                    .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                    .create();

            File clientFile = new File(CLIENTS_FILE_PATH);
            File employeeFile = new File(EMPLOYEES_FILE_PATH);

            // Create files if they don't exist
            if (!clientFile.exists()) {
                System.out.println("Clients file not found. Creating new file: " + CLIENTS_FILE_PATH);
                try (Writer writer = new FileWriter(clientFile)) {
                    JsonObject rootObject = new JsonObject();
                    rootObject.add("clients", new JsonArray());
                    gson.toJson(rootObject, writer);
                }
            }

            if (!employeeFile.exists()) {
                System.out.println("Employees file not found. Creating new file: " + EMPLOYEES_FILE_PATH);
                try (Writer writer = new FileWriter(employeeFile)) {
                    JsonObject rootObject = new JsonObject();
                    rootObject.add("employees", new JsonArray());
                    gson.toJson(rootObject, writer);
                }
            }

            // Read clients data
            try (Reader clientReader = new FileReader(CLIENTS_FILE_PATH)) {
                JsonObject rootObject = JsonParser.parseReader(clientReader).getAsJsonObject();
                JsonArray clientsArray = rootObject.getAsJsonArray("clients");
                clientsList = new ArrayList<>();

                for (JsonElement element : clientsArray) {
                    Client client = gson.fromJson(element, Client.class);
                    clientsList.add(client);
                }
                System.out.println("Successfully loaded " + clientsList.size() + " clients.");
            }

            // Read employees data
            try (Reader employeeReader = new FileReader(EMPLOYEES_FILE_PATH)) {
                JsonObject rootObject = JsonParser.parseReader(employeeReader).getAsJsonObject();
                JsonArray employeesArray = rootObject.getAsJsonArray("employees");
                employeesList = new ArrayList<>();

                for (JsonElement element : employeesArray) {
                    Employee employee = gson.fromJson(element, Employee.class);
                    employeesList.add(employee);
                }
                System.out.println("Successfully loaded " + employeesList.size() + " employees.");
            }


            return true;

        } catch (IOException e) {
            System.err.println("Error: Failed to read or create data files. " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error: Failed to parse JSON data. " + e.getMessage());
            e.printStackTrace(); // This will help debug any JSON parsing issues
            return false;
        }
    }

    /**
     * Writes client and employee data to JSON files when program exits
     * @return boolean indicating if the write operation was successful
     */
    public static boolean saveDataToFiles(Bank bank) {
        try {

            for(Client c :bank.BankClients ){
                for(CurrentAccount curr : c.getCurrent()){
                    for(CurrentAccount maincurr: bank.BankCurrentAccounts){
                        if(maincurr.getClient_id().equals(curr.getClient_id())){
                            curr=maincurr;
                        }
                    }
                }
                for(SavingAccount sav : c.getSaving()){
                    for(SavingAccount mainsav: bank.BankSavingAccounts){
                        if(mainsav.getClient_id().equals(sav.getClient_id())){
                            sav=mainsav;
                        }
                    }
                }
            }


            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .serializeNulls()
                    .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                    .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                    .create();

            //Write Clientsss
            try (Writer clientWriter = new FileWriter(CLIENTS_FILE_PATH)) {
                JsonObject rootObject = new JsonObject();
                JsonArray clientsArray = new JsonArray();

                for (Client client : clientsList) {
                    JsonObject clientObject = new JsonObject(); // Create empty object to control order

                    List<Class<?>> classHierarchy = new ArrayList<>();
                    Class<?> currentClass = client.getClass();
                    while (currentClass != null) {
                        classHierarchy.add(0, currentClass); // Add to start of list
                        currentClass = currentClass.getSuperclass();
                    }

                    // Process fields from each class in order (parent to child)
                    for (Class<?> clazz : classHierarchy) {
                        Field[] fields = clazz.getDeclaredFields();
                        for (Field field : fields) {
                            field.setAccessible(true);
                            if (!Modifier.isTransient(field.getModifiers()) &&
                                    !Modifier.isStatic(field.getModifiers())) {
                                try {
                                    Object value = field.get(client);
                                    clientObject.add(field.getName(), gson.toJsonTree(value));
                                } catch (IllegalAccessException e) {
                                    System.err.println("Warning: Could not access field " + field.getName());
                                }
                            }
                        }
                    }
                    clientsArray.add(clientObject);
                }
                rootObject.add("clients", clientsArray);
                gson.toJson(rootObject, clientWriter);
                System.out.println("Successfully saved " + clientsList.size() + " clients with ordered attributes.");
            }

            // Write Employeees
            try (Writer employeeWriter = new FileWriter(EMPLOYEES_FILE_PATH)) {
                JsonObject rootObject = new JsonObject();
                JsonArray employeesArray = new JsonArray();

                for (Employee employee : employeesList) {
                    JsonObject employeeObject = new JsonObject(); // Create empty object to control order

                    //Beyrateb El Hierarchy beta3t el classes
                    List<Class<?>> classHierarchy = new ArrayList<>();
                    Class<?> currentClass = employee.getClass();
                    while (currentClass != null) {
                        classHierarchy.add(0, currentClass); // Add to start of list
                        currentClass = currentClass.getSuperclass();
                    }

                    //Beycheck el fields el gowa el class
                    for (Class<?> clazz : classHierarchy) {
                        Field[] fields = clazz.getDeclaredFields();
                        for (Field field : fields) {
                            //bey5ale el private accessible
                            field.setAccessible(true);
                            //beysheel el static wel transient fields
                            if (!Modifier.isTransient(field.getModifiers()) &&
                                    !Modifier.isStatic(field.getModifiers())) {
                                try {
                                    //bey5ale el object ad el employee
                                    Object value = field.get(employee);
                                    employeeObject.add(field.getName(), gson.toJsonTree(value));
                                } catch (IllegalAccessException e) {
                                    System.err.println("Warning: Could not access field " + field.getName());
                                }
                            }
                        }
                    }
                    employeesArray.add(employeeObject);
                }
                rootObject.add("employees", employeesArray);
                gson.toJson(rootObject, employeeWriter);
                System.out.println("Successfully saved " + employeesList.size() + " employees with ordered attributes.");
            }

            return true;
        } catch (IOException e) {
            System.err.println("Error: Failed to write data files. " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error: Failed to convert data to JSON. " + e.getMessage());
            return false;
        }
    }
    public static void loadData(Bank bank){
        DataLoader.loadDataFromFiles(bank);
        bank.BankClients=clientsList;
        bank.BankEmployees=employeesList;
        bank.BankCurrentAccounts=new ArrayList<>();
        bank.BankSavingAccounts=new ArrayList<>();
        for(Client c : bank.BankClients) {
            for (CurrentAccount curr : c.getCurrent()) {
                bank.BankCurrentAccounts.add(curr);
                for(Moneytrans trans:curr.moneytransfer){
                    bank.BankMoneyTransfers.add(trans);
                }
                for(Transaction trans:curr.trasactions){
                    bank.BankATMTrans.add(trans);
                }
            }
            for (SavingAccount sav : c.getSaving()) {
                bank.BankSavingAccounts.add(sav);
                for(Moneytrans trans:sav.moneytransfer){
                    bank.BankMoneyTransfers.add(trans);
                }
                for(Transaction trans:sav.trasactions){
                    bank.BankATMTrans.add(trans);
                }
            }
        }

    }
}
