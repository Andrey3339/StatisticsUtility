import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    // префикс к итоговым файлам
    static String prefFile = "";
    // префикс к итоговым путям
    static String prefPath = "";
    // добавление в существующие файлы
    static boolean parA = false;
    // краткая статистика
    static boolean parS = false;
    // полная статистика
    static boolean parF = false;
    // имена исходных файлов
    static List<String> sourceFiles = new ArrayList<>();
    // список целых чисел из файлов
    static List<String> listIntegers = new ArrayList<>();
    // список вещественных чисел из файлов
    static List<String> listFloats = new ArrayList<>();
    // список строк из файлов
    static List<String> listStrings = new ArrayList<>();
    // начальная каталог
    static String initialPath = "C:\\Projects\\Java\\shift\\src\\";
    static int countIntegers, minIntegers, maxIntegers, sumIntegers = 0;
    static float avrIntegers = 0;
    static int countFloats = 0;
    static float minFloats, maxFloats, sumFloats, avrFloats = 0;
    static int countStrings, minLength, maxLength = 0;

    public static void main(String[] args) {

        parseArgs(args);
        readLinesFromFiles();
        writeLinesToFiles();
        statisticsPrint();
    }

    private static void statisticsPrint() {
        if (parF) {

            System.out.println("Статистика по числам типа Integer: ");
            System.out.println("Количество чисел: " + countIntegers);
            System.out.println("Минимальное число: " + minIntegers);
            System.out.println("Максимальное число: " + maxIntegers);
            System.out.println("Сумма чисел: " + sumIntegers);
            System.out.println("Среднее значение: " + avrIntegers + "\n");

            System.out.println("Статистика по числам типа Float: ");
            System.out.println("Количество чисел: " + countFloats);
            System.out.println("Минимальное число: " + minFloats);
            System.out.println("Максимальное число: " + maxFloats);
            System.out.println("Сумма чисел: " + sumFloats);
            System.out.println("Среднее значение: " + avrFloats + "\n");

            System.out.println("Статистика по типу String: ");
            System.out.println("Количество строк: " + countStrings);
            System.out.println("Минимальная длина строки: " + minLength);
            System.out.println("Максимальная длина строки: " + maxLength + "\n");

        }
        else if (parS ) {

            System.out.println("Статистика по числам типа Integer: ");
            System.out.println("Количество чисел: " + countIntegers);

            System.out.println("Статистика по числам типа Float: ");
            System.out.println("Количество чисел: " + countFloats);

            System.out.println("Статистика по типу String: ");
            System.out.println("Количество строк: " + countStrings);
        }
    }

    private static void createDirectories() {
        File dir = new File(initialPath + prefPath);
        if (!dir.exists()) {
            try {
                dir.mkdirs();
            } catch (SecurityException e) {
                System.err.println("Недостаточно прав для создания каталога: " + e.getMessage());
            }
        }
    }

    private static void writeLinesToFiles() {
        if (listIntegers.size() != 0) {
            String filePathIntegers = initialPath + prefPath + prefFile + "integers.txt";
            createDirectories();
            try(FileWriter writer = new FileWriter(filePathIntegers, parA)) {
                for (String s : listIntegers) {
                    writer.write(s + "\n");
                }
            } catch(IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (listFloats.size() != 0) {
            String filePathFloats = initialPath + prefPath + prefFile + "floats.txt";
            createDirectories();
            try(FileWriter writer = new FileWriter(filePathFloats, parA)) {
                for (String s: listFloats) {
                    writer.write(s + "\n");
                }
            } catch(IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (listStrings.size() != 0) {
            String filePathStrings = initialPath + prefPath + prefFile + "strings.txt";
            createDirectories();
            try(FileWriter writer = new FileWriter(filePathStrings, parA)) {
                for (String s: listStrings) {
                    writer.write(s + "\n");
                }
            } catch(IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static void readLinesFromFiles() {
        for (String sf : sourceFiles) {
            String fileName = initialPath + sf;

            try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
                String str;
                boolean flagString = false;
                while ((str = in.readLine()) != null) {
                    if (isInteger(str)) {
                        listIntegers.add(str);
                        countIntegers++;
                        int temp = Integer.parseInt(str);
                        sumIntegers += temp;
                        avrIntegers = sumIntegers / (float)countIntegers;
                        if (temp < minIntegers)
                            minIntegers = temp;
                        if (temp > maxIntegers)
                            maxIntegers = temp;
                    }
                    else if (isFloat(str)) {
                        listFloats.add(str);
                        countFloats++;
                        float temp1 = Float.parseFloat(str);
                        sumFloats += temp1;
                        avrFloats = sumFloats / countFloats;
                        if (temp1 < minFloats)
                            minFloats = temp1;
                        if (temp1 > maxFloats)
                            maxFloats = temp1;
                    }
                    else {
                        listStrings.add(str);
                        countStrings++;
                        int temp2 = str.length();
                        if (!flagString) {
                            minLength = temp2;
                            flagString = true;
                        }
                        if (temp2 < minLength)
                            minLength = temp2;
                        if (temp2 > maxLength)
                            maxLength = temp2;
                    }
                }
            } catch (IOException e) {
                System.err.println("Ошибка чтения файла: " + e.getMessage());
            }
        }
    }

    private static void parseArgs(String[] args) {
        for (int i = 0; i < args.length; i ++) {
            switch (args[i]) {
                case ("-p") :
                    prefFile = args[i + 1];
                    i++;
                    continue;
                case ("-o") :
                    String[] temp = args[i + 1].split("/");
                    for (String str : temp) {
                        if (!str.equals(""))
                            prefPath += str + "\\";
                    }
                    i++;
                    continue;
                case ("-a") :
                    parA = true;
                    continue;
                case ("-s") :
                    parS = true;
                    continue;
                case ("-f") :
                    parF = true;
                    continue;
                default:
                    sourceFiles.add(args[i]);
            }
        }
    }

    private static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}