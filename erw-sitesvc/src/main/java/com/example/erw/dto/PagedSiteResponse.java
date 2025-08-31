package com.example.erw.dto;

import com.example.erw.model.Site;
import java.util.List;

public class PagedSiteResponse {
    private int page;
    private int size;
    private long total;
    private List<Site> items;

    public PagedSiteResponse(int page, int size, long total, List<Site> items) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.items = items;
    }

    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }
    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }
    public long getTotal() { return total; }
    public void setTotal(long total) { this.total = total; }
    public List<Site> getItems() { return items; }
    public void setItems(List<Site> items) { this.items = items; }
}