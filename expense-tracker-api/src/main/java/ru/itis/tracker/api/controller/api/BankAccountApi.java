package ru.itis.tracker.api.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.itis.tracker.api.dto.ExceptionDto;
import ru.itis.tracker.api.dto.bank.AddBankAccountDto;
import ru.itis.tracker.api.dto.bank.BankAccountDto;
import ru.itis.tracker.api.dto.bank.BankDto;

@Tag(name = "Bank account")
public interface BankAccountApi {

    @PostMapping("/bank/account")
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

}
