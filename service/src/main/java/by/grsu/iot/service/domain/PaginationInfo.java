package by.grsu.iot.service.domain;

public class PaginationInfo {
    private Integer pages;
    private Integer size;
    private Long elementPerPage;

    public PaginationInfo(Integer pages, Integer size, Long elementPerPage) {
        this.pages = pages;
        this.size = size;
        this.elementPerPage = elementPerPage;
    }

    public PaginationInfo() {
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getElementPerPage() {
        return elementPerPage;
    }

    public void setElementPerPage(Long elementPerPage) {
        this.elementPerPage = elementPerPage;
    }
}
