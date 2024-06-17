package ru.itis.tracker.api.dto.bank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class BankAccountPage {

    private List<BankAccountDto> bankAccounts;
    private int pagesCount;
    private int elementsTotalCount;

}
