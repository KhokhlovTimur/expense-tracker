package ru.itis.tracker.api.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.tracker.api.dto.ExceptionDto;
import ru.itis.tracker.api.dto.bank.AddBankAccountDto;
import ru.itis.tracker.api.dto.bank.BankAccountDto;
import ru.itis.tracker.api.dto.bank.BankAccountPage;
import ru.itis.tracker.api.service.bank.dto.BankStatement;

import java.util.UUID;


public interface BankAccountApi {

    @PostMapping("/bank/account")
    @Tag(name = "Bank account")
    @Operation(summary = "Добавление банковского счета")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Добавленный счет",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BankAccountDto.class))
                    }),
            @ApiResponse(responseCode = "409", description = "Информация об ошибке",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    })
    })
    ResponseEntity<BankAccountDto> save(@RequestBody @Valid AddBankAccountDto accountDto);

    @GetMapping("/bank/account/{number}")
    @Tag(name = "Bank account")
    @Operation(summary = "Получение информации о счете")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о счете",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BankAccountDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Информация об ошибке",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    })
    })
    ResponseEntity<BankAccountDto> findByAccountNumber(@PathVariable("number") String number);

    @GetMapping("/user/{id}/accounts")
    @Tag(name = "User")
    @Operation(summary = "Получение счетов пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о счетах",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BankAccountPage.class))
                    })
    })
    ResponseEntity<BankAccountPage> findAllByUserId(@PathVariable("id") UUID id, @RequestParam("page") int pageNumber);

    @GetMapping("/bank/{bank_id}/accounts/{account_number}")
    @Tag(name = "Bank account")
    @Operation(summary = "Получение выписки из банка")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о счетах",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BankStatement.class))
                    })
    })
    ResponseEntity<BankStatement> getStatement(@PathVariable("account_number") String number, @PathVariable("bank_id") UUID id);

    @DeleteMapping("/user/{id}/accounts")
    @Tag(name = "User")
    @Operation(summary = "Удаление счета")
    ResponseEntity<Void> delete(@PathVariable("id") UUID id, @RequestParam("number") String number);

}
