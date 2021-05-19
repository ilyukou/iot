package by.grsu.iot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ilyukou Ilya
 */
@Setter
@Getter
@AllArgsConstructor
public class PageWrapper<T> {

    private List<T> content = new ArrayList<>();
    private Boolean hasNext;
    private Integer pages;
    private Long elements;

    public PageWrapper(Page<T> page) {
        this.content = page.getContent();
        this.hasNext = page.hasNext();
        this.pages = page.getTotalPages();
        this.elements = page.getTotalElements();
    }

    public PageWrapper(Boolean hasNext, Integer pages, Long elements) {
        this.hasNext = hasNext;
        this.pages = pages;
        this.elements = elements;
    }

    public void add(T t) {
        this.content.add(t);
    }
}
