package com.oracle.casb;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.ValidationException;
import sun.misc.Unsafe;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        System.out.println( "Hello World!" );
        App app = new App();
        //app.testMaxLength();
        app.readNumberDigits();
        //app.testUnsafe();
        //app.checkZipFile("/Users/abhijsri/tools/tomcat9/webapps/SecloreWebApp/WEB-INF/lib");
         //app.checkZipFile("/Users/abhijsri/odcs/SecloreWebApp/src/main/webapp/WEB-INF/lib");
       // app.checkZipFile("/Users/abhijsri/odcs/SecloreWebApp/target/SecloreWebApp/WEB-INF/lib");

        //app.checkValidZip("fs-ws-client.jar");
        /*app.getCount();
        try {
            String retVal = app.encodeHtml("<<SCRIPT>alert(\\\"XSS\\\");//<</SCRIPT>");
            System.out.println(retVal  );
        } catch (ValidationException e) {
            e.printStackTrace();
        }*/
    }

    private void readNumberDigits() {
        String number = "854559745684320697549060368131279814466643179689928095831053239604130293492672614469791533133321";
        int sum = 0;
        for (char ch : number.toCharArray()) {
            sum += Character.getNumericValue(ch);
        }
        System.out.printf("Length %d, Sum %d%c", number.length(), sum, '\n');
    }

    private void testMaxLength() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/abhijsri/github/casb-test/src/main/resources/ESAPI.properties"))))) {
            String longestLine
                    = reader.lines()
                    .min(Comparator.comparingInt(s -> s.length()))
                    .get();
            System.out.println(longestLine);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  void testUnsafe() {
        Field f = null;
        try {
            f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            Unsafe unsafe = (Unsafe) f.get(null);
            //unsafe.arrayIndexScale()
            System.out.println(unsafe.allocateInstance(FieldType.class));
            System.out.println(unsafe.pageSize());
        } catch (NoSuchFieldException |IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private boolean checkValidZip(String fileName) {
        Path zipfile = Paths.get(fileName);
        boolean isValid = true;
        try {
            FileSystem fs = FileSystems.newFileSystem(zipfile, App.class.getClassLoader());
        } catch (IOException e) {
            isValid = false;
            e.printStackTrace();
        } catch (Error error) {
            System.out.println(fileName + " is not valid");
            error.printStackTrace();
            isValid = false;
        }
        System.out.println(isValid ? fileName + " is Valid" : fileName + " is inValid");
        return isValid;
    }

    private void checkZipFile(String directoryName) {
        File directory = new File(directoryName);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList){
           // System.out.println(file.getName());
            if (checkValidZip(directoryName, file)) {
                System.out.println(file.getName() +" is valid zip file");
            } else {
                System.out.println(file.getName() +" is not a valid zip file");
            }
        }

    }

    private boolean checkValidZip(String directoryName, File filename) {
        boolean isValid = true;
        String file = directoryName + '/' + filename.getName();
        System.out.println("File : " + file);
        Path zipfile = Paths.get(file);
        try {
            FileSystem fs = FileSystems.newFileSystem(zipfile, App.class.getClassLoader());
        } catch (IOException e) {
            isValid = false;
            e.printStackTrace();
        } catch (Error error) {
            System.out.println(file + " is not valid");
            error.printStackTrace();
            isValid = false;
        }
        return isValid;
    }

    private String encodeHtml(String value) throws ValidationException {
        /*String safeValue = ESAPI.validator().getValidInput("An input parameter", value,
                "SafeStringRelaxed", 5000, true, false);*/
        return ESAPI.encoder().encodeForHTML(value);
    }

    private void getCount() {

        String line = "10";
        int n = Integer.parseInt(line);
        line = "203 204 205 206 207 208 203 204 205 206";

        Map<Integer, Integer> mapN = createFrequencyMap(line);
        line = "203 204 204 205 206 207 205 208 203 206 205 206 204";
        Map<Integer, Integer> mapM = createFrequencyMap(line);
        List<Integer> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : mapM.entrySet()) {
            if (mapN.get(entry.getKey()) == null || mapN.get(entry.getKey()) < mapM.get(entry.getKey())) {
                list.add(entry.getKey());
            }
        }
        String result = list.stream().map(e -> e.toString()).collect(Collectors.joining(" "));
        System.out.println(result);
    }

    private Map<Integer, Integer> createFrequencyMap(String line) {
        String[] nArr = line.split("\\s");
        Map<Integer, Integer> mapN = new TreeMap<>();
        for (String numStr : nArr) {
            int num = Integer.parseInt(numStr);
            if (mapN.containsKey(num)) {
                mapN.put(num, mapN.get(num) + 1);
            } else {
                mapN.put(num, 1);
            }
        }
        return mapN;
    }

    public int topVotedCandidate(int[] persons, int[] times, int q) {
        TreeMap<Integer, Integer> timeVote = new TreeMap<>();
        Map<Integer, Integer> personVote = new HashMap<>();
        int[] topVoted = new int[]{0, 0};
        for (int i = 0; i < persons.length ; i++) {
            if (persons[i] > 0) {
                int votes = personVote.containsKey(i) ? personVote.get(i) : 0;
                votes += 1;
                personVote.put(i, votes);
                if (votes >= topVoted[1]) {
                    topVoted[0] = i;
                    topVoted[1] = votes;
                }
            }
            timeVote.put(times[i], topVoted[0]);
        }

        return timeVote.floorEntry(q).getValue();

    }
}
