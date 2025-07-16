package com.nisum.cartAndCheckout.service.implementation;

import com.nisum.cartAndCheckout.dto.AddressDto;
import com.nisum.cartAndCheckout.exception.AddressNotFoundException;
import com.nisum.cartAndCheckout.exception.UserNotFoundException;
import com.nisum.cartAndCheckout.entity.UserAddress;
import com.nisum.cartAndCheckout.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<UserAddress> getAllAddresses() {
        return addressRepository.findAll();
    }

    public List<UserAddress> getAllAddressesByUserId(Integer userId) {
        return addressRepository.findByUserId(userId);
    }

    public UserAddress addAddress(AddressDto dto) {
        UserAddress address = new UserAddress();
        address.setUserId(dto.getUserId());
        address.setAddressLane1(dto.getAddressLane1());
        address.setAddressLane2(dto.getAddressLane2());
        address.setZipcode(dto.getZipcode());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());
        address.setAddressType(dto.getAddressType());
        address.setContactName(dto.getContactName());
        address.setContactPhoneNumber(dto.getContactPhoneNumber());
        return addressRepository.save(address);
    }

    public UserAddress updateAddress(Integer addressId, AddressDto dto) {
        Optional<UserAddress> existingAddress = addressRepository.findByIdAndUserId(addressId, dto.getUserId());
        if (existingAddress.isPresent()) {
            UserAddress address = existingAddress.get();
            address.setAddressLane1(dto.getAddressLane1());
            address.setAddressLane2(dto.getAddressLane2());
            address.setZipcode(dto.getZipcode());
            address.setState(dto.getState());
            address.setCountry(dto.getCountry());
            address.setAddressType(dto.getAddressType());
            address.setContactName(dto.getContactName());
            address.setContactPhoneNumber(dto.getContactPhoneNumber());
            return addressRepository.save(address);
        } else {
            throw new AddressNotFoundException("Address not found for the current user");
        }
    }

    public void deleteAddressById(Integer addressId, Integer userId) {
        Optional<UserAddress> address = addressRepository.findByIdAndUserId(addressId, userId);
        if (address.isPresent()) {
            addressRepository.delete(address.get());
        } else {
            throw new AddressNotFoundException("User address not found for the current user");
        }
    }

    public UserAddress getAddressById(Integer addressId, Integer userId) {
        Optional<UserAddress> address = addressRepository.findByIdAndUserId(addressId, userId);
        if (address.isPresent()) {
            return address.get();
        } else {
            throw new AddressNotFoundException("Address not found for the current user");
        }
    }
}
