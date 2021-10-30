package com.phonecompany.billing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Main{
    public static void main(String[] args) throws ParseException {

        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("path of csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\n");
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> numbers = new ArrayList<>();

        for(List list : records){
            String rec = list.get(0).toString();
            String[] split = rec.split(",");
            numbers.add(split[0]);
        }

        Map<String, Long> occurrences =
                numbers.stream().collect(Collectors.groupingBy(w -> w, Collectors.counting()));

        Long max = Collections.max(occurrences.values());

        List<String> keys = new ArrayList<>();
        for (Map.Entry<String, Long> entry : occurrences.entrySet()) {
            if (entry.getValue()==max) {
                keys.add(entry.getKey());
            }
        }

        String maxNumber = keys.get(0);
        Long tempNumber = Long.valueOf(keys.get(0));

        if(keys.size() > 1){
            for(String i : keys){
                Long j = Long.valueOf(i.trim());
                if(tempNumber > j){
                    tempNumber = Long.valueOf(i);
                    maxNumber = i;
                }
            }
        }

        Calculator calculator = new Calculator();

        for(List list : records){
            String[] number = list.get(0).toString().split(",");

            if(number[0].equals(maxNumber)){
                continue;
            }
            System.out.println(calculator.calculate(list.get(0).toString()));
        }

    }
}
