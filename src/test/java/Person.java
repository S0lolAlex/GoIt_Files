import com.google.gson.Gson;

import java.io.*;
import java.util.*;

public class Person {
    public void showNumber(String fileName) throws IOException{
        isTrueFormat(fileName);
        ArrayList<String> list = inputData(fileName);
        for(String s : list){
                if(s.charAt(3) == '-' || s.charAt(0) == '('){
                    System.out.println(s);
                }
        }
    }
    public void showWordsCount(String fileName)throws IOException{
        isTrueFormat(fileName);
        HashMap<String, Integer> wordsCount = countWords(createWordsList(fileName));
        wordsCount.entrySet().stream()
                  .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                  .forEach(System.out::println);
    }
    public void showPersons(String inputFile,String outFile) throws IOException{
        isTrueFormat(inputFile);
        isFileJson(outFile);
        List<String> person = inputData(inputFile);
        List<User> persList = new ArrayList<>();
        for(String temp : person){
            if(!temp.contains("age")){
            String[] tmp = temp.split(" ");
            persList.add(new User(tmp[0],Integer.parseInt(tmp[1])));
        }}
        String s = new Gson().toJson(persList);
        outDataToFile(outFile,s);
    }
    private HashMap<String,Integer> countWords(List<String> list){
        HashMap<String, Integer> wordsCount = new HashMap<>();
        for(String words : list){
            if(wordsCount.containsKey(words)){
                wordsCount.put(words, wordsCount.get(words) + 1);
            }else {
                wordsCount.put(words,1);
            }
        }
        return wordsCount;
    }
    private List<String> createWordsList(String fileName){
        List<String> list = inputData(fileName);
        List<String> allWords = new ArrayList<>();
        for(String str : list){
            String[] s = str.split(" ");
            for(String tmp : s){
                allWords.add(tmp.trim());
            }
        }
        return allWords;
    }
    private ArrayList<String> inputData(String fileName){
        ArrayList<String> list = new ArrayList<>();
        try(FileInputStream file = new FileInputStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(file))){
            String s;
            while((s = reader.readLine()) != null){
                list.add(s);
            }
        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }catch (IOException e){
            System.out.println("Wrong input data");
        }
        return list;
    }
    private void isTrueFormat(String name) throws IOException {
        if(!name.endsWith("txt"))  throw new IOException("Invalid format file");
    }
    private void outDataToFile(String fileName, String data) throws IOException{
        try(FileOutputStream file = new FileOutputStream(fileName);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(file))){
            writer.write(data);
        }catch (IOException e){
            System.out.println("wrong data");
        }
    }
    private void isFileJson(String file) throws IOException{
        if(!file.endsWith("json")) throw new IOException("Invalid format file");
    }
}
