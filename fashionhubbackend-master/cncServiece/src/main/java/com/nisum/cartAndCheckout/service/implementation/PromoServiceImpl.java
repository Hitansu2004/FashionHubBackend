package com.nisum.cartAndCheckout.service.implementation;

import com.nisum.cartAndCheckout.constants.AppConstants;
import com.nisum.cartAndCheckout.dto.PromoDTO;
import com.nisum.cartAndCheckout.dto.response.PromoResponse;
import com.nisum.cartAndCheckout.dto.response.PromoResponseDto;
import com.nisum.cartAndCheckout.entity.ShoppingCart;
import com.nisum.cartAndCheckout.exception.PromoServiceUnavailableException;
import com.nisum.cartAndCheckout.mapper.PromoMapper;
import com.nisum.cartAndCheckout.repository.ShoppingCartRepository;
import com.nisum.cartAndCheckout.service.interfaces.PromoService;
import com.nisum.cartAndCheckout.validation.PromoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.nisum.cartAndCheckout.constants.AppConstants.PROMO_VALIDATION_URL;

@Service
public class PromoServiceImpl implements PromoService {

    private final RestTemplate restTemplate;
    private final ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public PromoServiceImpl(RestTemplate restTemplate,ShoppingCartRepository shoppingCartRepository) {
        this.restTemplate = restTemplate;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public List<PromoDTO> getPromosForProducts(List<Integer> productIds) {
        try {
            PromoValidator.validateProductIds(productIds);

            String url = AppConstants.PROMO_SERVICE_URL;
            HttpEntity<List<Integer>> requestEntity = new HttpEntity<>(productIds);

            ResponseEntity<List<PromoResponse>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<List<PromoResponse>>() {}
            );

            List<PromoResponse> promoResponseList = Objects.requireNonNull(responseEntity.getBody());
            return PromoMapper.toPromoDTOList(promoResponseList);

        } catch (IllegalArgumentException e) {
            // Wrap validation exception into PromoServiceUnavailableException
            throw new PromoServiceUnavailableException("Promo fetch failed.", e);
        } catch (Exception e) {
            throw new PromoServiceUnavailableException("Promo fetch failed.", e);
        }
    }


    public PromoResponseDto validatePromoCode(String promoCode, int userId) {
        Optional<ShoppingCart> cartOpt = shoppingCartRepository.findByUserId(userId);
        if (cartOpt.isEmpty()) {
            return PromoResponseDto.builder()
                    .valid(false)
                    .message("No cart found for user")
                    .build();
        }

        BigDecimal cartTotal = cartOpt.get().getCartTotal();
        ResponseEntity<Map> response = restTemplate.getForEntity(PROMO_VALIDATION_URL, Map.class,promoCode);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && response.getBody().containsKey("amount")) {
            Integer amount = (Integer) response.getBody().get("amount");
            BigDecimal promoAmount = BigDecimal.valueOf(amount);

            if (promoAmount.compareTo(cartTotal) > 0) {
                return PromoResponseDto.builder()
                        .valid(false)
                        .message("Promo not applicable for this order")
                        .build();
            }

            return PromoResponseDto.builder()
                    .valid(true)
                    .amount(amount)
                    .build();
        } else {
            return PromoResponseDto.builder()
                    .valid(false)
                    .message("Promocode not found")
                    .build();
        }

    }


}