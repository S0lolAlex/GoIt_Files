import com.google.gson.Gson;

import java.io.*;
import java.util.*;

public class Person {
    public void showNumber(String fileName) throws IOException {
        isTrueFormat(fileName);
        ArrayList<String> list = inputData(fileName);
        for (String s : list) {
            if (s.matches("^(\\d{3}-\\d{3}-\\d{4})$") || s.matches("^(\\(\\d{3}\\) \\d{3}-\\d{4})$")) {
                System.out.println(s);
            }
        }
    }

    public void showWordsCount(String fileName) throws IOException {
        isTrueFormat(fileName);
        HashMap<String, Integer> wordsCount = countWords(createWordsList(fileName));
        wordsCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(System.out::println);
    }

    public void showPersons(String inputFile, String outFile) throws IOException {
        isTrueFormat(inputFile);
        isFileJson(outFile);
        List<String> persons = inputData(inputFile);
        List<User> users = new ArrayList<>();
        for (String person : persons) {
            if (!person.equals("name age")) {
                String[] usersInfo = person.split(" ");
                users.add(new User(usersInfo[0], Integer.parseInt(usersInfo[1])));
            }
        }
        String jsonUserInfo = new Gson().toJson(users);
        outDataToFile(outFile, jsonUserInfo);
    }

    private HashMap<String, Integer> countWords(List<String> list) {
        HashMap<String, Integer> wordsCount = new HashMap<>();
        for (String words : list) {
            if (wordsCount.containsKey(words)) {
                wordsCount.put(words, wordsCount.get(words) + 1);
            } else {
                wordsCount.put(words, 1);
            }
        }
        return wordsCount;
    }

    private List<String> createWordsList(String fileName) {
        List<String> list = inputData(fileName);
        List<String> allWords = new ArrayList<>();
        for (String str : list) {
            String[] s = str.split(" ");
            for (String tmp : s) {
                allWords.add(tmp.trim());
            }
        }
        return allWords;
    }

    private ArrayList<String> inputData(String fileName) {
        ArrayList<String> list = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(file))) {
            String s;
            while ((s = reader.readLine()) != null) {
                list.add(s);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Wrong input data");
        }
        return list;
    }

    private void isTrueFormat(String name) throws IOException {
        if (!name.endsWith("txt")) throw new IOException("Invalid format file");
    }

    private void outDataToFile(String fileName, String data) throws IOException {
        try (FileOutputStream file = new FileOutputStream(fileName);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(file))) {
            writer.write(data);
        } catch (IOException e) {
            System.out.println("wrong data");
        }
    }

    private void isFileJson(String file) throws IOException {
        if (!file.endsWith("json")) throw new IOException("Invalid format file");
    }
}
