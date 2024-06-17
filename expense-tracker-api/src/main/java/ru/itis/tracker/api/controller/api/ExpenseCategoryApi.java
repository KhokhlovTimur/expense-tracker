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

public interface ExpenseCategoryApi {

    @PostMapping("/expense/category")
    @Tag(name = "Expense category")
    @Operation(summary = "Добавление категории расхода")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Добавленная категория",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExpenseCategoryDto.class))
                    }),
            @ApiResponse(responseCode = "409", description = "Информация об ошибке",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    })
    })
    ResponseEntity<ExpenseCategoryDto> add(@RequestBody @Valid CreateExpenseCategoryDto categoryDto);

    @GetMapping("/expense/category/{id}")
    @Tag(name = "Expense category")
    @Operation(summary = "Получение информации о категории расходов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о категории расходов",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExpenseCategoryDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Информация об ошибке",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    })
    })
    ResponseEntity<ExpenseCategoryDto> findById(@PathVariable("id") UUID id);

    @PatchMapping("/expense/category/{id}")
    @Tag(name = "Expense category")
    @Operation(summary = "Обновление информации о категории расхода")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Обновленная информация категории расхода",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExpenseCategoryDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Информация об ошибке",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    })

    })
    ResponseEntity<ExpenseCategoryDto> update(@PathVariable("id") UUID id, @RequestBody @Valid UpdateExpenseCategoryRequestDto categoryDto);

    @DeleteMapping("/expense/category/{id}")
    @Tag(name = "Expense category")
    @Operation(summary = "Удаление категории расхода")
    ResponseEntity<Void> delete(@PathVariable("id") UUID id);

    @GetMapping("/user/{id}/expense/categories")
    @Tag(name = "User")
    @Operation(summary = "Получение категорий расходов пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о категориях",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExpenseCategoryPage.class))
                    })
    })
    ResponseEntity<ExpenseCategoryPage> findAllByUserId(@PathVariable("id") UUID id, @RequestParam("page") int pageNumber);

}
