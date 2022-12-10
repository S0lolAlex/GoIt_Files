import org.json.*;

import java.io.*;
import java.util.*;

public class Person {
    public void showNumber(String fileName) throws Exception{
        try(FileInputStream file = new FileInputStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(file))){
            String s;
            while((s = reader.readLine()) != null){
                if(s.charAt(3) == '-' || s.charAt(0) == '('){
                    System.out.println(s);
                }
            }
        }catch(FileNotFoundException e){
            System.out.println("Wrong fileName");
        }catch (IOException e){
            System.out.println("Wrong input data");
        }
    }

    public void showWordsCount(String fileName) throws Exception{
        try(FileInputStream file = new FileInputStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(file))){
            String str;
            ArrayList<String> list = new ArrayList<>();
            while((str = reader.readLine())!= null){
                String[] s = str.split(" ");
                for(String tmp : s){
                    list.add(tmp.trim());
                }
            }
            HashMap<String, Integer> wordsCount = new HashMap<>();
            for(String words : list){
                if(wordsCount.containsKey(words)){
                    wordsCount.put(words, wordsCount.get(words) + 1);
                }else {
                    wordsCount.put(words,1);
                }
            }
            wordsCount.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .forEach(System.out::println);
        }catch(FileNotFoundException e){
            System.out.println("Wrong fileName");
        }catch (IOException e){
            System.out.println("Wrong input data");
        }
    }

    public void showPersons(String fileName) throws Exception{
        try(FileInputStream file = new FileInputStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(file))){
            List<String> person = new ArrayList<>();
            StringBuilder line = new StringBuilder();
            String s;
            while((s = reader.readLine()) != null){
                line.append(s.replace(" ",",") + "\n");
            }
            s = line.toString().trim();
            JSONArray result = CDL.toJSONArray(s);
            for(Object r : result){
                System.out.println(r);
            }

        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }catch (IOException e){
            System.out.println("Wrong input data");
        }

    }
}
