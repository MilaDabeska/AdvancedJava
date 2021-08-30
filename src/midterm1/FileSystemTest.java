package midterm1;

import java.util.*;

class FileNameExistsException extends Exception {
    private IFile file;
    private String name;

    public FileNameExistsException(IFile file, String name) {
        super(String.format("There is already a file named %s in the folder %s", file.getFileName(), name));
    }
}

interface IFile extends Comparable<IFile> {
    String getFileName();

    long getFileSize();

    String getFileInfo(String s);

    void sortBySize();

    long findLargestFile();

    @Override
    default int compareTo(IFile o) {
        return Long.compare(getFileSize(), o.getFileSize());
    }
}

class File implements IFile {
    protected String name;
    protected long size;

    public File(String name, long size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public long getFileSize() {
        return size;
    }

    @Override
    public String getFileInfo(String s) {
        return String.format("%sFile name: %10s File size: %10s\n", s, name, size);
    }

    @Override
    public void sortBySize() {
    }

    @Override
    public long findLargestFile() {
        return size;
    }
}

class Folder implements IFile {
    protected String name;
    protected long size;
    protected List<IFile> files;

    public Folder(String name) {
        this.name = name;
        this.size = 0;
        files = new ArrayList<>();
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public long getFileSize() {
        return size;
    }

    @Override
    public String getFileInfo(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%sFolder name: %10s Folder size: %10d\n", s, name, size));
        for (IFile f : files) {
            sb.append(f.getFileInfo(s + "\t"));
        }
        return sb.toString();
    }

    @Override
    public void sortBySize() {
        Collections.sort(files);
        for (IFile f : files) {
            f.sortBySize();
        }
    }

    @Override
    public long findLargestFile() {
        return files.stream().mapToLong(file -> file.findLargestFile()).max().orElse(0);
    }

    public void addFile(IFile file) throws FileNameExistsException {
        if (files.stream().anyMatch(f -> f.getFileName().equals(file.getFileName()))) {
            throw new FileNameExistsException(file, name);
        }
        files.add(file);
        size += file.getFileSize(); //големината на еден Folder е сума од големините на сите датотеки (обични или директориуми) коишто се наоѓаат во него.
    }
}

class FileSystem {
    protected Folder rootDirectory;

    public FileSystem() {
        rootDirectory = new Folder("root");
    }

    public void addFile(IFile file) throws FileNameExistsException {
        rootDirectory.addFile(file);
    }

    public long findLargestFile() {
        return rootDirectory.findLargestFile();
    }

    public void sortBySize() {
        rootDirectory.sortBySize();
    }

    @Override
    public String toString() {
        return rootDirectory.getFileInfo("");
    }
}

public class FileSystemTest {

    public static Folder readFolder(Scanner sc) {

        Folder folder = new Folder(sc.nextLine());
        int totalFiles = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < totalFiles; i++) {
            String line = sc.nextLine();

            if (line.startsWith("0")) {
                String fileInfo = sc.nextLine();
                String[] parts = fileInfo.split("\\s+");
                try {
                    folder.addFile(new File(parts[0], Long.parseLong(parts[1])));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                try {
                    folder.addFile(readFolder(sc));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return folder;
    }

    public static void main(String[] args) {

        //file reading from input

        Scanner sc = new Scanner(System.in);

        System.out.println("===READING FILES FROM INPUT===");
        FileSystem fileSystem = new FileSystem();
        try {
            fileSystem.addFile(readFolder(sc));
        } catch (FileNameExistsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("===PRINTING FILE SYSTEM INFO===");
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING FILE SYSTEM INFO AFTER SORTING===");
        fileSystem.sortBySize();
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING THE SIZE OF THE LARGEST FILE IN THE FILE SYSTEM===");
        System.out.println(fileSystem.findLargestFile());
    }
}