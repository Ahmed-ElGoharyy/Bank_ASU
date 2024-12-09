package com.asu_bank.bank_asu;

import com.asu_bank.bank_asu.Model.Account;
import com.asu_bank.bank_asu.Model.Bank;
import com.asu_bank.bank_asu.Model.Client;
import com.asu_bank.bank_asu.Model.Employee;


import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.*;

public class DataLoader {



    public void loadData(Bank bank) {

        Map<String, Map<Long, Object>> datamap = new HashMap<String, Map<Long, Object>>();

        Map<Long, Object> empMap = new HashMap<Long, Object>();
        Map<Long, List<Account>> accountMap = new HashMap<Long, List<Account>>();
        Map<Long, Client> clientMap = new HashMap<Long, Client>();

        List<Employee> emps = readFile("Employee.csv", Employee.class);

        bank.BankEmployees = emps;

        System.out.println("Id:"+ emps.get(0).getId() + "  firstname:"+ emps.get(0).getFirstName());



        //List<Client> clients = readFile("Client.csv", Client.class);
        // List<Account> accounts = readFile("Account.csv", Account.class);

        //set account client relation
        /*for(Client client : clients) {
              clientMap.put(client.getId(), client);
        }
        for(Account acc : accounts) {
           Long clientId= 10L;// acc.getClientId();
           Client client = clientMap.get(clientId);
           //client.get
        }*/
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
        } else  if(fieldClassName.equals(String.class.getCanonicalName())) {
            objVal = valueString;
        } else  if(fieldClassName.equals(Character.class.getCanonicalName())) {
            objVal = Character.valueOf(valueString.charAt(0));
        }
        field.setAccessible(true);
        System.out.println("set field name: "+fieldName + "   val:"+objVal);
        field.set(object, objVal);
        return field;
    }

    public static void main(String[] args) {
        Bank bank = Bank.getInstance();


    }
}
