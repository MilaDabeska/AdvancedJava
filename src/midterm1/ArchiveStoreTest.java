package midterm1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class NonExistingItemException extends Exception {

    private int id;

    public NonExistingItemException(int id) {
        super(String.format("Item with id %d doesn't exist", id));
    }
}

abstract class Archive {

    protected int id;
    protected Date dateArchived;

    public Archive(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateArchived() {
        return dateArchived;
    }

    public void setDateArchived(Date dateArchived) {
        this.dateArchived = dateArchived;
    }

    public abstract String getType();
}

class LockedArchive extends Archive {

    private Date dateToOpen;

    public LockedArchive(int id, Date dateToOpen) {
        super(id);
        this.dateToOpen = dateToOpen;
    }

    public Date getDateToOpen() {
        return dateToOpen;
    }

    public void setDateToOpen(Date dateToOpen) {
        this.dateToOpen = dateToOpen;
    }

    @Override
    public String getType() {
        return "LockedArchive";
    }
}

class SpecialArchive extends Archive {

    private int maxOpen, copy;

    public SpecialArchive(int id, int maxOpen) {
        super(id);
        this.maxOpen = maxOpen;
        copy = maxOpen;
    }

    public int getCopy() {
        return copy;
    }

    public int getMaxOpen() {
        return maxOpen;
    }

    public void open() {
        maxOpen--;
    }

    @Override
    public String getType() {
        return "SpecialArchive";
    }
}

class ArchiveStore {

    protected List<Archive> archives;
    protected StringBuilder sb;

    public ArchiveStore() {
        archives = new ArrayList<>();
        sb = new StringBuilder();
    }

    public void archiveItem(Archive item, Date date) {
        //архивирање елемент item на одреден датум date
        item.setDateArchived(date);
        archives.add(item);
        //За секоја акција на архивирање во текст треба да се додаде следната порака
        sb.append(String.format("Item %d archived at %s\n", item.getId(), date));
    }

    public void openItem(int id, Date date) throws NonExistingItemException {
        //отварање елемент од архивата со зададен id и одреден датум date

//        boolean b = true;
//        for (Archive a : archives) {
//            if (a.getId() == id) {
//                b = false;
//                break;
//            }
//        }
//        if (b){
//            throw new NonExistingItemException(id);
//        }

        if (archives.stream().noneMatch(a -> a.getId() == id)) {
            throw new NonExistingItemException(id);
        }

        for (Archive archive : archives) {
            if (archive.getId() == id) {
                if (archive.getType().equals("LockedArchive")) {
                    LockedArchive lockedArchive = (LockedArchive) archive;
                    if (lockedArchive.getDateArchived().before(lockedArchive.getDateToOpen())) {
                        //При отварање ако се работи за LockedArhive и датумот на отварање е пред датумот кога може да се отвори, да се додаде порака
                        sb.append(String.format("Item %d cannot be opened before %s\n", id, lockedArchive.getDateToOpen()));
                    } else {
                        //за секоја акција на отварање архива треба да се додаде
                        sb.append(String.format("Item %d opened at %s\n", id, date));
                    }
                } else {
                    SpecialArchive specialArchive = (SpecialArchive) archive;
                    if (specialArchive.getMaxOpen() == 0) {
                        //ако се обидиеме да ја отвориме повеќе пати од дозволениот број (maxOpen) да се додаде порака
                        sb.append(String.format("Item %d cannot be opened more than %d times\n", id, specialArchive.getCopy()));
                    } else {
                        sb.append(String.format("Item %d opened at %s\n", id, date));
                        specialArchive.open();
                    }
                }
            }
        }
    }

    public String getLog() {
        return sb.toString().replaceAll("GMT", "UTC");
    }
}


public class ArchiveStoreTest {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        Date date = new Date(113, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();
            Date dateToOpen = new Date(date.getTime() + (days * 24 * 60
                    * 60 * 1000));
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while (scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch (NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }
}

// вашиот код овде


