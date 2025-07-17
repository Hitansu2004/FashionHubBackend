package com.catalogservice.service;

import com.catalogservice.dto.PromoProductJoinRequestDto;
import com.catalogservice.dto.PromoProductJoinResponseDto;
import com.catalogservice.model.PromoProductJoinId;


import java.util.List;

public interface PromoProductJoinService {
    PromoProductJoinResponseDto save(PromoProductJoinRequestDto dto);
    List<PromoProductJoinResponseDto> getAll();
    void delete(PromoProductJoinId id);
    void storeProductIds(List<Integer> ids);
    void deleteByProductId(Integer productId);
    List<Integer> getAllProductIds();

}
