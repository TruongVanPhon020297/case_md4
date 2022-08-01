package com.cg.service.cart;

import com.cg.model.Cart;
import com.cg.model.dto.CartInfoDTO;
import com.cg.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService{
    @Autowired
    CartRepository cartRepository;
    @Override
    public List<Cart> findAll() {
        return null;
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Cart getById(Long id) {
        return null;
    }

    @Override
    public Cart save(Cart cart) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Optional<CartInfoDTO> findUserDTOByUserId(String id) {
        return cartRepository.findUserDTOByUserId(id);
    }

    @Override
    public void addCart() {

    }
}
