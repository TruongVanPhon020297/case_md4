package com.cg.service.CartItem;

import com.cg.model.CartItem;
import com.cg.model.dto.CartItemDTO;
import com.cg.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService{

    @Autowired
    CartItemRepository cartItemRepository;

    @Override
    public List<CartItem> findAll() {
        return null;
    }

    @Override
    public Optional<CartItem> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public CartItem getById(Long id) {
        return null;
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Optional<CartItemDTO> findCartItemDTOByCartIdAndProductId(long cartId, long productId) {
        return cartItemRepository.findCartItemDTOByCartIdAndProductId(cartId,productId);
    }

    @Override
    public List<CartItemDTO> findAllCartItemByCartId(Long cartId) {
        return cartItemRepository.findAllCartItemByCartId(cartId);
    }
}
