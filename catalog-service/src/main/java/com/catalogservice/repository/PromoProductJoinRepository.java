package com.catalogservice.repository;

import com.catalogservice.model.PromoProductJoin;
<<<<<<< HEAD
import com.catalogservice.model.PromoProductJoinId;
=======
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
<<<<<<< HEAD
public interface PromoProductJoinRepository extends JpaRepository<PromoProductJoin, PromoProductJoinId> {
    List<PromoProductJoin> findByProductIdIn(List<Integer> productIds);
=======
public interface PromoProductJoinRepository extends JpaRepository<PromoProductJoin, Long> {
    List<PromoProductJoin> findByProductIdIn(List<Long> productIds);
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
}
