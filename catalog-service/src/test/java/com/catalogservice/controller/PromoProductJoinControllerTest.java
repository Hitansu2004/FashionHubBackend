package com.catalogservice.controller;

import com.catalogservice.dto.PromoProductJoinRequestDto;
import com.catalogservice.dto.PromoProductJoinResponseDto;
<<<<<<< HEAD
import com.catalogservice.model.PromoProductJoinId;
=======
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
import com.catalogservice.service.PromoProductJoinService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PromoProductJoinControllerTest {
    @Mock
    private PromoProductJoinService promoProductJoinService;

    @InjectMocks
    private PromoProductJoinController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        PromoProductJoinRequestDto request = new PromoProductJoinRequestDto();
        PromoProductJoinResponseDto response = new PromoProductJoinResponseDto();
        when(promoProductJoinService.save(request)).thenReturn(response);
        ResponseEntity<PromoProductJoinResponseDto> result = controller.save(request);
        assertEquals(response, result.getBody());
        verify(promoProductJoinService).save(request);
    }

    @Test
    void testGetAll() {
        List<PromoProductJoinResponseDto> list = Arrays.asList(new PromoProductJoinResponseDto());
        when(promoProductJoinService.getAll()).thenReturn(list);
        ResponseEntity<List<PromoProductJoinResponseDto>> result = controller.getAll();
        assertEquals(list, result.getBody());
        verify(promoProductJoinService).getAll();
    }

    @Test
    void testDelete() {
<<<<<<< HEAD
        Integer productId = 1;
        doNothing().when(promoProductJoinService).deleteByProductId(productId);
        ResponseEntity<Void> result = controller.delete(productId);
        assertEquals(204, result.getStatusCodeValue());
        verify(promoProductJoinService).deleteByProductId(productId);
=======
        Long id = 1L;
        doNothing().when(promoProductJoinService).delete(id);
        ResponseEntity<Void> result = controller.delete(id);
        assertEquals(204, result.getStatusCodeValue());
        verify(promoProductJoinService).delete(id);
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
    }
}
