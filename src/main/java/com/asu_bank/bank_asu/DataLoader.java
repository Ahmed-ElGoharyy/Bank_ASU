package com.asu_bank.bank_asu;

import com.asu_bank.bank_asu.Model.*;


import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataLoader {



    public void loadData(Bank bank) {

        Map<String, Map<Long, Object>> datamap = new HashMap<String, Map<Long, Object>>();

        Map<Long, Object> empMap = new HashMap<Long, Object>();
        Map<Long, List<Account>> accountMap = new HashMap<Long, List<Account>>();
        Map<Long, Client> clientMap = new HashMap<Long, Client>();

        List<Employee> emps = readFile("Employee.csv", Employee.class);

        List<Client> Clients = readFile("Clients.csv", Client.class);

        List<Transaction> atmTansactions = readFile("ATMTransactions.csv", Transaction.class);

        List<Moneytrans> TransTansactions = readFile("TransferTransactions.csv", Moneytrans.class);

        List<CurrentAccount> CurrentAccs = readFile("CuurentAccs.csv", CurrentAccount.class);

        List<SavingAccount> SavingAccs = readFile("SavingAcc.csv", SavingAccount.class);


        bank.BankEmployees = emps;
        bank.BankClients = Clients;
        bank.BankATMTrans = atmTansactions;
        bank.BankMoneyTransfers = TransTansactions;
        bank.BankCurrentAccounts = CurrentAccs;
        bank.BankSavingAccounts = SavingAccs;





        //set account client relation
        for(Client client : Clients) {
              clientMap.put(client.getClient_id(), client);
              System.out.println("Client ID :"+client.getClient_id());
        }
        for(SavingAccount acc : SavingAccs) {
           Long clientId=   acc.getClient_id();
            System.out.println(" Client id 444: "+ clientId);

            Client client = clientMap.get(clientId);
           ArrayList<SavingAccount> savings = client.getSaving();
           if(savings == null){
               savings = new ArrayList<SavingAccount>();
               client.setSaving(savings);
           }
           savings.add(acc);
        }

        for(CurrentAccount acc : CurrentAccs) {
            Long clientId=   acc.getClient_id();

            Client client = clientMap.get(clientId);
            ArrayList<CurrentAccount> currents = client.getCurrent();
            if(currents == null){
                currents = new ArrayList<CurrentAccount>();
                client.setCurrent(currents);
            }
            currents.add(acc);
        }

        Client c = clientMap.get(Long.valueOf(2003));
        System.out.println("Client Test ID :"+c);

        System.out.println(" Client id: "+ c.getCurrent().get(0).getBalance());
         // + "   "+c.getCurrent().get(1).getBalance());
    }



    public <T> List<T> readFile(String filename,Class<T> clazz) {
        String filepath = "";
        List<T> objects = new ArrayList<T>();
        try {

            filepath = this.getClass().getResource(filename).toURI().getPath();
            System.out.println(filepath);


        } catch (Exception e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {

            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            List objAttributes = null;

            if (line != null) {

                objAttributes = getTokens(line, clazz,null, objAttributes);
                System.out.println("objAttributes:"+objAttributes);
            }
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
                if(line !=null) {
                    T obj = clazz.newInstance();
                    getTokens(line, clazz, obj, objAttributes);
                    objects.add(obj);
                    System.out.println("Line :" + line);
                }
            }
            String everything = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objects;
    }

    public List<Object> getTokens(String line,Class clazz,Object obj,List<String> fieldNames){
        List<Object> tokens =new ArrayList<>();

        try {

            StringTokenizer st = new StringTokenizer(line, ",");

            int index = 0;
            String fieldName = null;
            Field field = null;


            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                if(fieldNames != null) {
                    fieldName = fieldNames.get(index);
                    if(fieldName != null ) {
                        setFieldValue(obj,fieldName, token);
                    }
                }

                System.out.println("token :" + token);
                tokens.add(token);
                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokens;
    }



    private static Field getField(Class mClass, String fieldName) throws NoSuchFieldException {
        try {
            System.out.println("fieldName::"+fieldName);
            return mClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class superClass = mClass.getSuperclass();
            if (superClass == null) {
                throw e;
            } else {
                return getField(superClass, fieldName);
            }
        }
    }

    public static Field setFieldValue(Object object, String fieldName, String valueString) throws NoSuchFieldException, IllegalAccessException {
        Field field = getField(object.getClass(), fieldName);
        Object objVal = null;
        String fieldClassName = field.getType().getCanonicalName();

        System.out.println("fieldClassName:"+fieldClassName);
        if(fieldClassName.equals(Long.class.getCanonicalName())) {
            objVal = Long.valueOf(valueString);
        } else  if(fieldClassName.equals(Integer.class.getCanonicalName())) {
            objVal = Integer.valueOf(valueString);
        } else  if(fieldClassName.equals(Double.class.getCanonicalName())) {
            objVal = Double.valueOf(valueString);
        } else  if(fieldClassName.equals(String.class.getCanonicalName())) {
            objVal = valueString;
        } else  if(fieldClassName.equals(Character.class.getCanonicalName())) {
            objVal = Character.valueOf(valueString.charAt(0));
        }else  if(fieldClassName.equals(Date.class.getCanonicalName())) {
            try {
                objVal = new SimpleDateFormat("yyyy-MM-dd").parse(valueString);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }


        field.setAccessible(true);
        System.out.println("set field name: "+fieldName + "   val:"+objVal);
        field.set(object, objVal);
        return field;
    }

    public static void main(String[] args) {
        Bank bank = Bank.getInstance();

        DataLoader dl = new DataLoader();
        dl.loadData(bank);
    }
}
