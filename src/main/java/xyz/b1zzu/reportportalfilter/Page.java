package xyz.b1zzu.reportportalfilter;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {
    public List<T> content = new ArrayList<>();
    public PageMeta page = new PageMeta();

    public static class PageMeta {
        public int number;
        public int size;
        public int totalElements;
        public int totalPages;
    }
}
