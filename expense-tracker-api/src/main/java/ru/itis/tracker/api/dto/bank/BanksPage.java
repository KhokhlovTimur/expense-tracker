package ru.itis.tracker.api.dto.bank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class BanksPage {

    private List<BankDto> banks;
    private int pagesCount;
    private Long elementsTotalCount;

}
