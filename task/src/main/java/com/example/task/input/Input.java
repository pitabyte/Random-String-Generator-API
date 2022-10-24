package com.example.task.input;
import javax.persistence.*;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table
public class Input {
    @Id
    @SequenceGenerator(
            name = "task_sequence",
            sequenceName = "task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_sequence"
    )
    private Long id;
    private String str;
    private ArrayList<Character> chars;
    private int minLength;
    private int maxLength;
    private int numberOfStrings;
    private String state;

    public Input() {
        this.state = "running";
    }

    public Input(String str, int minLength, int maxLength, int numberOfStrings) {
        this.str = str;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.numberOfStrings = numberOfStrings;
        this.state = "running";
    }

    public Input(Long id, String str, int minLength, int maxLength, int numberOfStrings) {
        this.id = id;
        this.str = str;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.numberOfStrings = numberOfStrings;
        this.state = "running";
    }

    public void setChars() {
        Set<Character> set = new HashSet<>();
        ArrayList<Character> chars = new ArrayList<>();
        for (char c : this.str.toCharArray()) {
            set.add(c);
        }
        chars.addAll(set);
        this.chars = chars;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public ArrayList<Character> getChars(String str) {
        Set<Character> set = new HashSet<>();
        ArrayList<Character> chars = new ArrayList<>();
        for (char c : this.str.toCharArray()) {
            set.add(c);
        }
        chars.addAll(set);
        return chars;
    }


    public int getNumberOfStrings() {
        return numberOfStrings;
    }

    public void setNumberOfStrings(int numberOfStrings) {
        this.numberOfStrings = numberOfStrings;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void completed() {
        this.state = "completed";
    }

    public Long maxNumber() {
        Long sum = 0L;
        int stringLength = this.maxLength;
        while (stringLength >= this.minLength) {
            sum = sum + (long) Math.pow(this.chars.size(), stringLength);
            stringLength--;
        }
        return sum;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int generateStringLength() {
        int stringLength = ThreadLocalRandom.current().nextInt(this.minLength, this.maxLength + 1);
        return stringLength;
    }

    public boolean isPossible() {
        this.chars = this.getChars(this.str);
        if (this.numberOfStrings > this.maxNumber() || this.numberOfStrings <= 0) {
            return false;
        }
        if (this.maxLength < minLength || this.maxLength <= 0 || this.minLength <= 0) {
            return false;
        }
        return true;
    }

    public ArrayList<String> randomUniqueStringsFromBottom() {
        ArrayList<String> outputStrings = new ArrayList<>();
        String singleString = "";
        int i = 0;
        while (i < numberOfStrings) {
            singleString = randomString();
            if (isUnique(outputStrings, singleString)) {
                outputStrings.add(singleString);
                i++;
            }
        }
        return outputStrings;
    }

    public ArrayList<String> randomUniqueStringsFromTop() {
        ArrayList<String> allStrings = allStrings();
        String singleString = "";
        int i = 0;
        while (i < (maxNumber() - this.numberOfStrings)) {
            int randomInt = ThreadLocalRandom.current().nextInt(0, allStrings.size());
            allStrings.remove(randomInt);
            i++;
        }
        return allStrings;
    }

    public String randomString() {
        int stringLength = generateStringLength();
        String singleString = "";
        for (int i = 0; i < stringLength; i++) {
            int randomInt = ThreadLocalRandom.current().nextInt(0, this.chars.size());
            singleString = singleString + this.chars.get(randomInt);
        }
        return singleString;
    }

    public boolean isUnique(ArrayList<String> outputStrings, String randomString) {
        if (outputStrings.contains(randomString)) {
            return false;
        }
        return true;
    }


    public ArrayList<String> allStrings() {
        ArrayList<String> allStrings = new ArrayList<>();
        int stringLength = this.maxLength;
        while(stringLength >= this.minLength) {
            String singleString = "";
            allStringsRecursion(stringLength, allStrings, singleString);
            stringLength--;
        }
        return allStrings;

    }

    public void allStringsRecursion(int stringLength, ArrayList<String> allStrings, String singleString) {
        if (stringLength == 0) {
            allStrings.add(singleString);
            return;
        }
        for (int i = 0; i < this.chars.size(); i++) {
            String newSingleString = singleString + this.chars.get(i);
            allStringsRecursion(stringLength - 1, allStrings, newSingleString);
        }
    }

    public ArrayList<String> createStrings() {
        ArrayList<String> strings = new ArrayList<>();
        double percentage = (double) numberOfStrings * 100 / maxNumber();
        if (percentage < 50) {
            strings = randomUniqueStringsFromBottom();
            return strings;
        }
        strings = randomUniqueStringsFromTop();
        return strings;
    }


    public void stringsToFile() {
        ArrayList<String> strings = createStrings();
        try {
            String path = this.getOutputPath();
            FileWriter newFile = new FileWriter(path);
            for (int i = 0; i < this.numberOfStrings; i++) {
                newFile.append(strings.get(i) + "\n");
            }
            newFile.close();


        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public boolean isCompleted() {
        String state = this.getState();
        if (state.equals("completed")) {
            return true;
        }
        return false;
    }

    public String getOutputPath() {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        String filename = "output" + this.getId() + ".txt";
        String path = s + "/" + filename;
        return path;
    }

    public String getFileOutput() {
        String outputPath = this.getOutputPath();
        Path path = Paths.get(outputPath);
        String content = "";
        try {
            content = Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String output = "";
        output = content.replace("\n", "\n");
        return output;
    }

    @Override
    public String toString() {
        return "Id: " + this.id +
                ", Characters: " + this.chars +
                ", minLength: "  + this.minLength +
                ", maxLength: " + this.maxLength +
                ", No. of strings: " + this.numberOfStrings +
                ", State: " + this.state;
    }
}
