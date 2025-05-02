package br.com.register.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PaginationTest {

    @Test
    public void shouldReturnAscSortDirectionWhenValueIsAsc() {
        Sort.Direction direction = Pagination.getSort("ASC");
        assertThat(direction).isEqualTo(Sort.Direction.ASC);
    }

    @Test
    public void shouldReturnDescSortDirectionWhenValueIsNotAsc() {
        Sort.Direction direction = Pagination.getSort("DESC");
        assertThat(direction).isEqualTo(Sort.Direction.DESC);

        direction = Pagination.getSort("anything_else");
        assertThat(direction).isEqualTo(Sort.Direction.DESC);
    }

    @Test
    public void shouldReturnPageRequestWithCorrectValues() {
        int page = 2;
        int size = 10;
        String sort = "ASC";
        String[] props = {"id"};

        PageRequest request = Pagination.getPageRequest(size, page, sort, props);

        assertThat(request.getPageNumber()).isEqualTo(1);
        assertThat(request.getPageSize()).isEqualTo(10);
        assertThat(request.getSort().getOrderFor("id").getDirection()).isEqualTo(Sort.Direction.ASC);
    }

    @Test
    public void shouldReturnCorrectOffsetForPage1() {
        int offset = Pagination.getOffset(1, 10);
        assertThat(offset).isEqualTo(0);
    }

    @Test
    public void shouldReturnCorrectOffsetForPageGreaterThan1() {
        int offset = Pagination.getOffset(3, 10);
        assertThat(offset).isEqualTo(20);
    }

}