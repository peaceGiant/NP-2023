package Zadachi.Zad59;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.stream.Collectors;

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

class FrontPage {

    private List<NewsItem> newsItemList;
    private Category[] categories;

    public FrontPage(Category[] categories) {
        newsItemList = new ArrayList<>();
        this.categories = categories;
    }

    public void addNewsItem(NewsItem newsItem) {
        newsItemList.add(newsItem);
    }

    public List<NewsItem> listByCategory(Category category) {
        return newsItemList.stream()
                .filter(newsItem -> newsItem.category.equals(category))
                .collect(Collectors.toList());
    }

    public List<NewsItem> listByCategoryName(String category) throws CategoryNotFoundException {
        if (Arrays.stream(categories).noneMatch(i -> i.equals(new Category(category))))
            throw new CategoryNotFoundException(String.format("Category %s was not found", category));
        return listByCategory(new Category(category));
    }

    @Override
    public String toString() {
        return newsItemList.stream().map(NewsItem::getTeaser).collect(Collectors.joining("\n")) + '\n';
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

    public abstract String getTeaser();
}

class TextNewsItem extends NewsItem {

    private String text;

    public TextNewsItem(String title, Date date, Category category, String text) {
        super(title, date, category);
        this.text = text;
    }

    @Override
    public String getTeaser() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(title).append('\n')
                .append(date.toString()).append('\n')
                .append(text, 0, Math.min(80, text.length())).toString();
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

    @Override
    public String getTeaser() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(title).append('\n')
                .append(date.toString()).append('\n')
                .append(url).append('\n')
                .append(views).toString();
    }

}

class Category {

    private String category;

    public Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category1 = (Category) o;

        return category.equals(category1.category);
    }

    @Override
    public int hashCode() {
        return category != null ? category.hashCode() : 0;
    }

}

class CategoryNotFoundException extends Exception {

    public CategoryNotFoundException(String message) {
        super(message);
    }

}
