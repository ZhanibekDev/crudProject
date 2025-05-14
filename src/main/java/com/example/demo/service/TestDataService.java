package com.example.demo.service;


import com.example.demo.model.TestData;
import com.example.demo.repository.TestDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TestDataService {
    private final TestDataRepository repository;

    // Получить список данных
    public List<TestData> getAllTestData() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    // Создать данны
    public TestData createTestData(TestData testData) {
        return repository.save(testData);
    }

    // Обновить данные по параметру id
    public TestData updateTestData(Long id, TestData updatedData) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(updatedData.getName());
                    existing.setType(updatedData.getType());
                    existing.setValue(updatedData.getValue());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("TestData not found with id " + id));
    }

    // Частично обновить данные по параметру id
    public TestData patchTestData(Long id, Map<String, Object> updates) {
        TestData existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TestData с id " + id + " не найден"));
        updates.forEach((field, value) -> {
            switch (field) {
                case "name" -> existing.setName((String) value);
                case "type" -> existing.setType((String) value);
                case "value" -> existing.setValue((String) value);
                default -> throw new IllegalArgumentException("Поле " + field + " не поддерживается для обновления");
            }
        });

        return repository.save(existing);
    }

    // Удаление данных по id
    public void deleteTestData(Long id) {

        if(!repository.existsById(id)) {
            throw new RuntimeException("TestDatac id " + id + "не найден!");
        }
        repository.deleteById(id);
    }

    // Получить данные по type
    public List<TestData> getByType(String type) {
        return repository.findByType(type);
    }

    // Получить данные по name
    public List<TestData> getByName(String name) {
        return repository.findByName(name);
    }
}
