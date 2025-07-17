package com.catalogservice.service.impl;

import com.catalogservice.dto.PromoProductJoinRequestDto;
import com.catalogservice.dto.PromoProductJoinResponseDto;
import com.catalogservice.mapper.PromoProductJoinMapper;
import com.catalogservice.model.PromoProductJoin;
import com.catalogservice.model.PromoProductJoinId;

import com.catalogservice.repository.PromoProductJoinRepository;
import com.catalogservice.service.PromoProductJoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromoProductJoinServiceImpl implements PromoProductJoinService {
    private final PromoProductJoinRepository repo;
    private final PromoProductJoinMapper mapper;

    @Autowired
    public PromoProductJoinServiceImpl(PromoProductJoinRepository repo, PromoProductJoinMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public PromoProductJoinResponseDto save(PromoProductJoinRequestDto dto) {
        return mapper.toDto(repo.save(mapper.toEntity(dto)));
    }

    @Override
    public List<PromoProductJoinResponseDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public void delete(PromoProductJoinId id) {
        repo.deleteById(id);
    }

    @Override
    public void storeProductIds(List<Integer> ids) {
        for (Integer productId : ids) {
            PromoProductJoin join = new PromoProductJoin();
            join.setProductId(productId);
            join.setPromoCode("DEFAULT"); // Use a real promoCode if needed
            repo.save(join);
        }
    }

    @Override
    public void deleteByProductId(Integer productId) {
        List<PromoProductJoin> entities = repo.findByProductIdIn(List.of(productId));
        for (PromoProductJoin entity : entities) {
            repo.delete(entity);
        }
    }

    @Override
    public List<Integer> getAllProductIds() {
        // Dummy implementation, replace with actual logic if needed
        return repo.findAll().stream()
                .map(PromoProductJoin::getProductId)
                .toList();
    }

}
