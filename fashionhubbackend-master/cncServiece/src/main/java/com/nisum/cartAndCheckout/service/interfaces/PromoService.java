package com.nisum.cartAndCheckout.service.interfaces;

import com.nisum.cartAndCheckout.dto.PromoDTO;
import com.nisum.cartAndCheckout.dto.response.PromoResponseDto;

import java.util.List;

public interface PromoService {
    public List<PromoDTO> getPromosForProducts(List<Integer> productIds);
    public PromoResponseDto validatePromoCode(String promoCode, int userId);
}