import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class App {
    private static List<File> tmplist = new ArrayList<>();

    private static List<File> FilterFile(File root) {
        if (root.isFile()) {
            if (checkFile(root)) {
                tmplist.add(root);
            }
        } else {
            File[] files = root.listFiles();

            for (File file : files) {
                FilterFile(file);
            }
        }

        return tmplist;
    }

    private static boolean checkFile(File file) {
        return FileReaderText.read(file).contains("String");
    }

    public static void main(final String[] args) {
        File path = new File("c://test");

        List<File> list = FilterFile(path);

        File[] files = new File[list.size()];

        for (int i = 0; i < list.size(); i++) {
            files[i] = list.get(i);
        }


        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                if (file1.getName().length() > file2.getName().length()) {
                    return 1;
                } else if (file1.getName().length() < file2.getName().length()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        int sum = 0;

        for (File file : list) {
            sum += file.length();
        }

        System.out.println("Общий размер отфильтрованных файлов: " + sum);

        System.out.println("Отсортированный по длине имени файлов список: ");

        for (File file : files) {
            System.out.println(file.getName() + ", Размер: " + file.length());
        }

        System.out.println("");

        for (File file : list) {
            try (InputStream in = new FileInputStream(file.getAbsoluteFile())) {
                int value;

                System.out.print(file.getName() + ": ");

                while ((value = in.read()) != -1) {
                    System.out.print(value + " ");
                }

                System.out.println("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class FileReaderText {
    public static String read(File file) {
        StringBuilder sb = new StringBuilder();

        try {
            try (BufferedReader in = new BufferedReader(new FileReader(file))) {
                String s;

                while((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}