package com.example.travel_sculptor.repository;

import com.example.travel_sculptor.domain.Landmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandmarkRepository extends JpaRepository<Landmark, Long> {
    // 특정 district와 province로 landmark name 가져오기
    List<Landmark> findAllByRegionId(Long regionId);
}