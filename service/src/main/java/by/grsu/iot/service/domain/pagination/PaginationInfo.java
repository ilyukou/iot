package by.grsu.iot.service.domain.pagination;

import java.io.Serializable;

/**
 * Pagination Information
 * {@link PaginationInfo#pages} count of pages
 * {@link PaginationInfo#size} count of element
 * {@link PaginationInfo#elementPerPage} count of element per page
 *
 * @author Ilyukou Ilya
 */
public class PaginationInfo implements Serializable {

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
