package com.example.laboratoryreportsystem.repository;

import com.example.laboratoryreportsystem.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageDataRepository extends JpaRepository<ImageData, Long> {


    Optional<ImageData> findByName(String fileName);
}
