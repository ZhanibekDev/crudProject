package com.example.demo.controller;


import com.example.demo.exception.NotFoundException;
import com.example.demo.model.TestData;
import com.example.demo.service.TestDataService;
//import org.apache.coyote.Response;
//import org.aspectj.weaver.ast.Test;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/test-data")
public class TestDataController {
    private final TestDataService testDataService;

    public TestDataController(TestDataService testDataService) {
        this.testDataService = testDataService;
    }

    // READ ALL
    @Operation(summary = "Получить все тестовые данные", description = "Возвращает список объектов Test Data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список успешно получен"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @GetMapping
    public ResponseEntity<List<TestData>> getAllTestData() {
        return ResponseEntity.ok(testDataService.getAllTestData());
    }

    // CREATE
    @Operation(summary = "Создать тестовые данные", description = "Создание данных по полям Test Data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Тестовые данные успешно созданы"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PostMapping
    public ResponseEntity<TestData> createTestData(@RequestBody TestData testData) {
        return ResponseEntity.ok(testDataService.createTestData(testData));
    }

    // READ BY type
    @Operation(summary = "Получить тестовые данные по типу", description = "Возвращает список тестовых данных" +
            "по type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список данных по type получен"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @GetMapping("/type/{type}")
    public ResponseEntity<List<TestData>> getByType(@PathVariable String type) {
        List<TestData> data = testDataService.getByType(type);

        if(data.isEmpty()) {
            throw new NotFoundException("Данные с типом " + type + " не найдены");
        }

        return ResponseEntity.ok(data);
    }

    // READ BY name
    @Operation(summary = "Получить тестовые данные по имени", description = "Возвращает список тестовых данных по name")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Список данных по name получен"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<List<TestData>> getByName(@PathVariable String name) {
        List<TestData> data = testDataService.getByName(name);
        if (data.isEmpty()) {throw new NotFoundException("Данные с именем " + name + " не найдены"); }
        return ResponseEntity.ok(data);
    }


    // UPDATE
    @Operation(summary = "Изменить тестовые данные по айди", description = "Возвращает " +
            "измененный список данных по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Обновленный список данных успешно получен"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TestData> updateTestData(@PathVariable Long id, @RequestBody TestData updatedData) {
        return ResponseEntity.ok(testDataService.updateTestData(id, updatedData));
    }


    // PARTIAL UPDATE
    @Operation(summary = "Частично обновить данные по id", description = "Частичное обновление данных Test Data" +
            " по полю id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Частично обновленный список успешно получен"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<TestData> patchTestData(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates
    ) {
        return ResponseEntity.ok(testDataService.patchTestData(id, updates));
    }

    // DELETE
    @Operation(summary = "Удалить данные по id", description = "Удаление данных Test Data по полю id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное удаление данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestData(@PathVariable Long id) {
        testDataService.deleteTestData(id);
        return ResponseEntity.noContent().build(); // HTTP 204, запрос выполнен, но тело ответа отсутствует
    }
}
