package Zadachi.Zad07;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Partial exam II 2016/2017
 */
public class FileSystemTest {
    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            fileSystem.addFile(parts[0].charAt(0), parts[1],
                    Integer.parseInt(parts[2]),
                    LocalDateTime.of(2016, 12, 29, 0, 0, 0).minusDays(Integer.parseInt(parts[3]))
            );
        }
        int action = scanner.nextInt();
        if (action == 0) {
            scanner.nextLine();
            int size = scanner.nextInt();
            System.out.println("== Find all hidden files with size less then " + size);
            List<File> files = fileSystem.findAllHiddenFilesWithSizeLessThen(size);
            files.forEach(System.out::println);
        } else if (action == 1) {
            scanner.nextLine();
            String[] parts = scanner.nextLine().split(":");
            System.out.println("== Total size of files from folders: " + Arrays.toString(parts));
            int totalSize = fileSystem.totalSizeOfFilesFromFolders(Arrays.stream(parts)
                    .map(s -> s.charAt(0))
                    .collect(Collectors.toList()));
            System.out.println(totalSize);
        } else if (action == 2) {
            System.out.println("== Files by year");
            Map<Integer, Set<File>> byYear = fileSystem.byYear();
            byYear.keySet().stream().sorted()
                    .forEach(key -> {
                        System.out.printf("Year: %d\n", key);
                        Set<File> files = byYear.get(key);
                        files.stream()
                                .sorted()
                                .forEach(System.out::println);
                    });
        } else if (action == 3) {
            System.out.println("== Size by month and day");
            Map<String, Long> byMonthAndDay = fileSystem.sizeByMonthAndDay();
            byMonthAndDay.keySet().stream().sorted()
                    .forEach(key -> System.out.printf("%s -> %d\n", key, byMonthAndDay.get(key)));
        }
        scanner.close();
    }
}

// Your code here
class FileSystem {
    Map<Character, List<File>> folders;

    FileSystem() {
        folders = new HashMap<>();
    }

    public void addFile(char folder, String name, int size, LocalDateTime createdAt) {
        folders.putIfAbsent(folder, new ArrayList<>());
        folders.get(folder).add(new File(name, size, createdAt));
    }

    public List<File> findAllHiddenFilesWithSizeLessThen(int size) {
        List<File> result = new ArrayList<>();
        for (List<File> l : folders.values()) {
            for (File f : l) {
                if (f.getName().charAt(0) == '.' && f.getSize() < size) {
                    result.add(f);
                }
            }
        }
        return result;
    }

    public int totalSizeOfFilesFromFolders(List<Character> folders) {
        int size = 0;
        for (Character c : folders) {
            if (this.folders.containsKey(c)) {
                for (File f : this.folders.get(c)) {
                    size += f.getSize();
                }
            }
        }
        return size;
    }

    // !!!
    // Better ways to do this -- use streams and Collectors like so:
//    public Map<Integer, Set<File>> byYear() {
//        return files.values().stream()
//                .flatMap(Collection::stream)
//                .collect(
//                        Collectors.groupingBy(
//                                file -> file.getCreatedAt().getYear(),
//                                Collectors.toSet())
//                );
//    }
    public Map<Integer, Set<File>> byYear() {
        Map<Integer, Set<File>> result = new HashMap<>();
        for (List<File> l : folders.values()) {
            for (File f : l) {
                result.putIfAbsent(f.getCreation_date().getYear(), new HashSet<File> ());
                result.get(f.getCreation_date().getYear()).add(f);
            }
        }
        return result;
    }

    public Map<String, Long> sizeByMonthAndDay() {
        Map<String, Long> result = new HashMap<>();
        for (List<File> l : folders.values()) {
            for (File f : l) {
                String month = f.getCreation_date().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                int day = f.getCreation_date().getDayOfMonth();
                String key = String.format("%s-%d", month.toUpperCase(), day);
                result.putIfAbsent(key, 0L);
                result.put(key, result.get(key) + f.getSize().longValue());
            }
        }
        return result;
    }
}

class File implements Comparable<File> {

    String name;
    Integer size;
    LocalDateTime creation_date;

    static Comparator<File> fileComparator =
            Comparator.comparing(File::getCreation_date_string)
                    .thenComparing(File::getName)
                    .thenComparing(File::getSize);

    public File(String name, int size, LocalDateTime createdAt) {
        this.name = name;
        this.size = size;
        this.creation_date = createdAt;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public LocalDateTime getCreation_date() {
        return creation_date;
    }

    public String getCreation_date_string() {
        return creation_date.toString();
    }

    public void setCreation_date(LocalDateTime creation_date) {
        this.creation_date = creation_date;
    }

    @Override
    public String toString() {
        return String.format("%-10s %5dB %s", name, size, creation_date.toString());
    }

    @Override
    public int compareTo(File o) {
        return fileComparator.compare(this, o);
    }
}
