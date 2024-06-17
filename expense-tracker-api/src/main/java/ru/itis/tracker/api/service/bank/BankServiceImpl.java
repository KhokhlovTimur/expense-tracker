package ru.itis.tracker.api.service.bank;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.itis.tracker.api.dto.bank.BankDto;
import ru.itis.tracker.api.dto.bank.BanksPage;
import ru.itis.tracker.api.dto.bank.CreateBankRequestDto;
import ru.itis.tracker.api.dto.bank.UpdateBankRequestDto;
import ru.itis.tracker.api.exception.AlreadyExistsException;
import ru.itis.tracker.api.exception.NotFoundException;
import ru.itis.tracker.api.mapper.BankMapper;
import ru.itis.tracker.api.model.Bank;
import ru.itis.tracker.api.repository.BankRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    private final BankMapper bankMapper;

    @Value(value = "${default.page-size}")
    private int pageSize;

    @Override
    public BanksPage findAll(int pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Bank> page = bankRepository.findAll(pageRequest);

        return BanksPage.builder()
                .banks(bankMapper.toDtoList(page.getContent()))
                .pagesCount(page.getTotalPages())
                .elementsTotalCount(page.getTotalElements())
                .build();
    }

    @Override
    public BankDto findById(UUID id) {
        return bankMapper.toDto(
                getOrThrow(id)
        );
    }

    @Override
    public Bank findModelById(UUID id) {
        return getOrThrow(id);
    }

    @Override
    public Bank findModelByName(String name) {
        return bankRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(String.format("Банка с названием [%s] не существует", name)));
    }

    @Override
    public BankDto save(CreateBankRequestDto bankDto) {
        new UniqueCheckBuilder(bankDto)
                .bic()
                .ogrn()
                .inn()
                .correspondentAccount();

        return bankMapper.toDto(
                bankRepository.save(
                        bankMapper.toModel(bankDto))
        );
    }

    @Override
    public BankDto update(UUID id, UpdateBankRequestDto bankDto) {
        Bank bank = getOrThrow(id);
        UniqueCheckBuilder checkBuilder = new UniqueCheckBuilder(bankDto);

        if (bankDto.getName() != null) {
            bank.setName(bankDto.getName());
        }

        if (bankDto.getKpp() != null) {
            bank.setKpp(bankDto.getKpp());
        }

        if (bankDto.getBic() != null) {
            checkBuilder.bic();
            bank.setBic(bankDto.getBic());
        }

        if (bankDto.getOgrn() != null) {
            checkBuilder.ogrn();
            bank.setOgrn(bankDto.getOgrn());
        }

        if (bankDto.getInn() != null) {
            checkBuilder.inn();
            bank.setInn(bankDto.getInn());
        }

        if (bankDto.getCorrespondentAccount() != null) {
            checkBuilder.correspondentAccount();
            bank.setCorrespondentAccount(bankDto.getCorrespondentAccount());
        }

        return bankMapper.toDto(
                bankRepository.save(bank)
        );
    }

    private Bank getOrThrow(UUID id) {
        return bankRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Банка с id [%s] не существует", id)));
    }

    @AllArgsConstructor
    private class UniqueCheckBuilder {

        private CreateBankRequestDto bankDto;

        public UniqueCheckBuilder bic() {
            if (bankRepository.existsByBic(bankDto.getBic())) {
                throw new AlreadyExistsException(String.format("Bank with bic [%s] already exists", bankDto.getBic()));
            }

            return this;
        }

        public UniqueCheckBuilder inn() {
            if (bankRepository.existsByInn(bankDto.getInn())) {
                throw new AlreadyExistsException(String.format("Bank with inn [%s] already exists", bankDto.getInn()));
            }

            return this;
        }

        public UniqueCheckBuilder ogrn() {
            if (bankRepository.existsByOgrn(bankDto.getOgrn())) {
                throw new AlreadyExistsException(String.format("Bank with ogrn [%s] already exists", bankDto.getOgrn()));
            }

            return this;
        }

        public UniqueCheckBuilder correspondentAccount() {
            if (bankRepository.existsByCorrespondentAccount(bankDto.getCorrespondentAccount())) {
                throw new AlreadyExistsException(String.format("Bank with correspondent account [%s] already exists", bankDto.getCorrespondentAccount()));
            }

            return this;
        }

    }
}
