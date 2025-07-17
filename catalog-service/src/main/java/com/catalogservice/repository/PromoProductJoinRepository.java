package com.catalogservice.repository;

import com.catalogservice.model.PromoProductJoin;
import com.catalogservice.model.PromoProductJoinId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromoProductJoinRepository extends JpaRepository<PromoProductJoin, PromoProductJoinId> {
    List<PromoProductJoin> findByProductIdIn(List<Integer> productIds);

}
