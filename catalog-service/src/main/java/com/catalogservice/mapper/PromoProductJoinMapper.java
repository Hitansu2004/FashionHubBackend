package com.catalogservice.mapper;

import com.catalogservice.dto.PromoProductJoinRequestDto;
import com.catalogservice.dto.PromoProductJoinResponseDto;
import com.catalogservice.model.PromoProductJoin;
import org.springframework.stereotype.Component;

@Component
public class PromoProductJoinMapper {

    public PromoProductJoin toEntity(PromoProductJoinRequestDto dto) {
        PromoProductJoin entity = new PromoProductJoin();
        entity.setProductId(dto.getProductId());
        entity.setPromoCode(dto.getPromoCode());

        return entity;
    }

    public PromoProductJoinResponseDto toDto(PromoProductJoin entity) {
        PromoProductJoinResponseDto dto = new PromoProductJoinResponseDto();
        dto.setId(entity.getId());
        dto.setProductId(entity.getProductId());
        dto.setPromoCode(entity.getPromoCode());

        return dto;
    }
}
