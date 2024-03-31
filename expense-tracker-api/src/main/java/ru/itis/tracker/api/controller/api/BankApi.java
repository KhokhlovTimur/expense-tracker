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
import ru.itis.tracker.api.dto.bank.BankDto;
import ru.itis.tracker.api.dto.bank.BanksPage;
import ru.itis.tracker.api.dto.bank.CreateBankRequestDto;
import ru.itis.tracker.api.dto.bank.UpdateRequestBankDto;

import java.util.UUID;

@Tag(name = "Bank")
public interface BankApi {

    @PostMapping("/bank")
    @Operation(summary = "Добавление банка")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Добавленный банк",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BankDto.class))
                    }),
            @ApiResponse(responseCode = "409", description = "Информация об ошибке",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    })
    })
    ResponseEntity<BankDto> save(@RequestBody @Valid CreateBankRequestDto bankDto);

    @PatchMapping("/bank/{id}")
    @Operation(summary = "Обновление информации о банке")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Обновленная информация",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BankDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Информация об ошибке",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    })

    })
    ResponseEntity<BankDto> update(@PathVariable("id") UUID id, @RequestBody @Valid UpdateRequestBankDto bankDto);

    @GetMapping("/bank/{id}")
    @Operation(summary = "Получение информации о банке")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о банке",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BankDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Информация об ошибке",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    })
    })
    ResponseEntity<BankDto> findById(@PathVariable("id") UUID id);

    @GetMapping("/banks")
    @Operation(summary = "Получение информации о всех банках")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о банках",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BanksPage.class))
                    })
    })
    ResponseEntity<BanksPage> findAll(@RequestParam("page") int pageNumber);
}
