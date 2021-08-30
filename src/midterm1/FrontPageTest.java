package midterm1;

import java.util.*;
import java.util.stream.Collectors;

class CategoryNotFoundException extends Exception {
    private String category;

    public CategoryNotFoundException(String category) {
        super(String.format("Category %s was not found", category));
    }
}

abstract class NewsItem {

    protected String title;
    protected Date date;
    protected Category category;

    public NewsItem(String title, Date date, Category category) {
        this.title = title;
        this.date = date;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public Category getCategory() {
        return category;
    }

    public abstract String getTeaser();
}

class Category {

    private String name;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return name.equals(category.name);
    }
}

class TextNewsItem extends NewsItem {

    private String text;

    public TextNewsItem(String title, Date date, Category category, String text) {
        super(title, date, category);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String getTeaser() {
        StringBuilder sb = new StringBuilder();
        sb.append(title + System.lineSeparator());
        Date now = new Date();
        long minutes = (now.getTime() - date.getTime()) / 60000;
        sb.append(minutes + System.lineSeparator());
        if (text.length() > 80) {
            sb.append(text.substring(0, 80) + System.lineSeparator());
        } else {
            sb.append(text + System.lineSeparator());
        }
        return sb.toString();
    }
}

class MediaNewsItem extends NewsItem {

    private String url;
    private int views;

    public MediaNewsItem(String title, Date date, Category category, String url, int views) {
        super(title, date, category);
        this.url = url;
        this.views = views;
    }

    public String getUrl() {
        return url;
    }

    public int getViews() {
        return views;
    }

    @Override
    public String getTeaser() {
        StringBuilder sb = new StringBuilder();
        sb.append(title + System.lineSeparator());
        Date now = new Date();
        long minutes = (now.getTime() - date.getTime()) / 60000;
        sb.append(minutes + System.lineSeparator());
        sb.append(url + System.lineSeparator());
        sb.append(views + System.lineSeparator());
        return sb.toString();
    }
}

class FrontPage {

    private List<NewsItem> news;
    private Category[] categories;

    public FrontPage(Category[] categories) {
        news = new ArrayList<>();
        this.categories = Arrays.copyOf(categories, categories.length);
    }

    public void addNewsItem(NewsItem newsItem) {
        news.add(newsItem);
    }

    public List<NewsItem> listByCategory(Category category) {
//        List<NewsItem> list = new ArrayList<>();
//        for (NewsItem i : news) {
//            if (i.getCategory().equals(category))
//                list.add(i);
//        }
//        return list;
        return news.stream()
                .filter(i -> i.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    public List<NewsItem> listByCategoryName(String category) throws CategoryNotFoundException {
        Category tmp = new Category(category);

//        Category flag = null;
//        for (Category line : categories) {
//            if (line.equals(tmp)) {
//                flag = line;
//                break;
//            }
//        }
        Category flag = Arrays.stream(categories)
                .filter(line -> line.equals(tmp))
                .findAny()
                .orElse(null);

        if (flag == null) throw new CategoryNotFoundException(category);
        else return listByCategory(tmp);
    }

    @Override
    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        for (NewsItem item : news) {
//            sb.append(item.getTeaser());
//        }
//        return sb.toString();
        return news.stream().map(NewsItem::getTeaser).collect(Collectors.joining());
    }
}

public class FrontPageTest {
    public static void main(String[] args) {
        // Reading
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.split(" ");
        Category[] categories = new Category[parts.length];
        for (int i = 0; i < categories.length; ++i) {
            categories[i] = new Category(parts[i]);
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        FrontPage frontPage = new FrontPage(categories);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            cal = Calendar.getInstance();
            int min = scanner.nextInt();
            cal.add(Calendar.MINUTE, -min);
            Date date = cal.getTime();
            scanner.nextLine();
            String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
            frontPage.addNewsItem(tni);
        }

        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -min);
            scanner.nextLine();
            Date date = cal.getTime();
            String url = scanner.nextLine();
            int views = scanner.nextInt();
            scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
            frontPage.addNewsItem(mni);
        }
        // Execution
        String category = scanner.nextLine();
        System.out.println(frontPage);
        for (Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
            System.out.println(frontPage.listByCategoryName(category).size());
        } catch (CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}


// Vasiot kod ovde
