package com.example.demo.repository;

import com.example.demo.model.TestData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestDataRepository extends JpaRepository<TestData, Long> {
    List<TestData> findByType(String type);
    List<TestData> findByName(String name);
}
