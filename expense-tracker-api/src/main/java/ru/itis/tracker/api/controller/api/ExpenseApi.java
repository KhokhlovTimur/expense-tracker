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
import ru.itis.tracker.api.dto.expense.*;

import java.util.UUID;

public interface ExpenseApi {

    @PostMapping("/expense")
    @Tag(name = "Expense")
    @Operation(summary = "Добавление расхода")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Добавленный расход",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExpenseDto.class))
                    }),
            @ApiResponse(responseCode = "409", description = "Информация об ошибке",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    })
    })
    ResponseEntity<ExpenseDto> add(@RequestBody @Valid CreateExpenseRequestDto expenseDto);

    @GetMapping("/expense/{id}")
    @Tag(name = "Expense")
    @Operation(summary = "Получение информации о расходе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о расходе",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExpenseDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Информация об ошибке",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    })
    })
    ResponseEntity<ExpenseDto> findById(@PathVariable("id") UUID id);

    @PatchMapping("/expense/{id}")
    @Tag(name = "Expense")
    @Operation(summary = "Обновление информации о расходе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Обновленная информация о расходе",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExpenseDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Информация об ошибке",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    })

    })
    ResponseEntity<ExpenseDto> update(@PathVariable("id") UUID id, @RequestBody @Valid UpdateExpenseRequestDto expenseDto);

    @GetMapping("/user/{id}/expenses")
    @Tag(name = "User")
    @Operation(summary = "Получение расходов пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о расходах",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExpensePage.class))
                    })
    })
    ResponseEntity<ExpensePage> findAllByUserId(@PathVariable("id") UUID id, @RequestParam("page") int pageNumber);
}
