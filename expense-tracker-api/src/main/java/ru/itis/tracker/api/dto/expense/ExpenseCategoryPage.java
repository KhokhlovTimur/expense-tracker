package ru.itis.tracker.api.dto.expense;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ExpenseCategoryPage {

    private List<ExpenseCategoryDto> expenseCategories;
    private int pagesCount;
    private int elementsTotalCount;

}
