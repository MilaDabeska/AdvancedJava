//package collections.aud;
//
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.stream.Collectors;
//
///**
// * Partial exam II 2016/2017
// */
//
//class File implements Comparable<File> {
//    protected char folder;
//    protected String name;
//    protected int size;
//    protected LocalDateTime createdAt;
//
//    public File(char folder, String name, int size, LocalDateTime createdAt) {
//        this.folder = folder;
//        this.name = name;
//        this.size = size;
//        this.createdAt = createdAt;
//    }
//
//    public char getFolder() {
//        return folder;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public int getSize() {
//        return size;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%-10s %5dB %s", name, size, createdAt);
//    }
//
//    @Override
//    public int compareTo(File o) {
////        int result1 = createdAt.compareTo(o.createdAt);
////        if (result1 == 0) {
////            int result2 = name.compareTo(o.name);
////            if (result2 == 0) {
////                return Integer.compare(size, o.size);
////            } else {
////                return result2;
////            }
////        }
////        return result1;
//
//        Comparator<File> comparator = Comparator.comparing(File::getCreatedAt)
//                .thenComparing(File::getName)
//                .thenComparing(File::getSize);
//
//        return comparator.compare(this, o);
//    }
//}
//
//class FileSystem {
//    Map<Character, Set<File>> filesByDirectories;
//    List<File> allFiles;
//
//    public FileSystem() {
//        filesByDirectories = new HashMap<>();
//        allFiles = new ArrayList<>();
//    }
//
//    public void addFile(char folder, String name, int size, LocalDateTime createdAt) {
//        File newFile = new File(folder, name, size, createdAt);
//        allFiles.add(newFile);
//
//        filesByDirectories.putIfAbsent(folder, new TreeSet<>());
//        filesByDirectories.get(folder).add(newFile);
//    }
//
//    public List<File> findAllHiddenFilesWithSizeLessThen(int size){
//        allFiles.stream()
//    }
//}
//
//public class FileSystemTest {
//    public static void main(String[] args) {
//        FileSystem fileSystem = new FileSystem();
//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        scanner.nextLine();
//        for (int i = 0; i < n; i++) {
//            String line = scanner.nextLine();
//            String[] parts = line.split(":");
//            fileSystem.addFile(parts[0].charAt(0), parts[1],
//                    Integer.parseInt(parts[2]),
//                    LocalDateTime.of(2016, 12, 29, 0, 0, 0).minusDays(Integer.parseInt(parts[3]))
//            );
//        }
//        int action = scanner.nextInt();
//        if (action == 0) {
//            scanner.nextLine();
//            int size = scanner.nextInt();
//            System.out.println("== Find all hidden files with size less then " + size);
//            List<File> files = fileSystem.findAllHiddenFilesWithSizeLessThen(size);
//            files.forEach(System.out::println);
//        } else if (action == 1) {
//            scanner.nextLine();
//            String[] parts = scanner.nextLine().split(":");
//            System.out.println("== Total size of files from folders: " + Arrays.toString(parts));
//            int totalSize = fileSystem.totalSizeOfFilesFromFolders(Arrays.stream(parts)
//                    .map(s -> s.charAt(0))
//                    .collect(Collectors.toList()));
//            System.out.println(totalSize);
//        } else if (action == 2) {
//            System.out.println("== Files by year");
//            Map<Integer, Set<File>> byYear = fileSystem.byYear();
//            byYear.keySet().stream().sorted()
//                    .forEach(key -> {
//                        System.out.printf("Year: %d\n", key);
//                        Set<File> files = byYear.get(key);
//                        files.stream()
//                                .sorted()
//                                .forEach(System.out::println);
//                    });
//        } else if (action == 3) {
//            System.out.println("== Size by month and day");
//            Map<String, Long> byMonthAndDay = fileSystem.sizeByMonthAndDay();
//            byMonthAndDay.keySet().stream().sorted()
//                    .forEach(key -> System.out.printf("%s -> %d\n", key, byMonthAndDay.get(key)));
//        }
//        scanner.close();
//    }
//}
//
//// Your code here
//
//
