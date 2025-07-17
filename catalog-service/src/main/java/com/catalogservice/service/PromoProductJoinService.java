package com.catalogservice.service;

import com.catalogservice.dto.PromoProductJoinRequestDto;
import com.catalogservice.dto.PromoProductJoinResponseDto;
<<<<<<< HEAD
import com.catalogservice.model.PromoProductJoinId;
=======
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b

import java.util.List;

public interface PromoProductJoinService {
    PromoProductJoinResponseDto save(PromoProductJoinRequestDto dto);
    List<PromoProductJoinResponseDto> getAll();
<<<<<<< HEAD
    void delete(PromoProductJoinId id);
    void storeProductIds(List<Integer> ids);
    void deleteByProductId(Integer productId);
    List<Integer> getAllProductIds();
=======
    void delete(Long id);
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
}
