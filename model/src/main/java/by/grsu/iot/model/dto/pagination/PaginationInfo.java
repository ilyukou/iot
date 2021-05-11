package by.grsu.iot.model.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Pagination Information
 * {@link PaginationInfo#pages} count of pages
 * {@link PaginationInfo#size} count of element
 * {@link PaginationInfo#elementPerPage} count of element per page
 *
 * @author Ilyukou Ilya
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationInfo implements Serializable {

    private Integer pages;
    private Integer size;
    private Long elementPerPage;
}
