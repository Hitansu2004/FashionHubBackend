package com.catalogservice.mapper;

import com.catalogservice.dto.PromoProductJoinRequestDto;
import com.catalogservice.dto.PromoProductJoinResponseDto;
import com.catalogservice.model.PromoProductJoin;
import org.springframework.stereotype.Component;

@Component
public class PromoProductJoinMapper {

    public PromoProductJoin toEntity(PromoProductJoinRequestDto dto) {
        PromoProductJoin entity = new PromoProductJoin();
<<<<<<< HEAD
        entity.setProductId(dto.getProductId());
        entity.setPromoCode(dto.getPromoCode());
=======
        entity.setPromoCode(dto.getPromoCode());
        entity.setProductId(dto.getProductId());
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
        return entity;
    }

    public PromoProductJoinResponseDto toDto(PromoProductJoin entity) {
        PromoProductJoinResponseDto dto = new PromoProductJoinResponseDto();
<<<<<<< HEAD
        dto.setId(entity.getId());
        dto.setProductId(entity.getProductId());
        dto.setPromoCode(entity.getPromoCode());
=======
        dto.setPromoCode(entity.getPromoCode());
        dto.setProductId(entity.getProductId());
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
        return dto;
    }
}
