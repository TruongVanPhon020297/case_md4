package com.cg.service.order;

import com.cg.model.*;
import com.cg.model.dto.CartInfoDTO;
import com.cg.model.dto.OrderDTO;
import com.cg.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{

    @Autowired
    private LocationRegionDeliveryRepository locationRegionDeliveryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Order getById(Long id) {
        return null;
    }

    @Override
    public Order save(Order order) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Order doCreateOrder(OrderDTO orderDTO, CartInfoDTO cartInfoDTO) {
        LocationRegionDelivery locationRegionDelivery = orderDTO.getLocationRegion().toLocationRegionDelivery();
        locationRegionDelivery.setId(0L);
        LocationRegionDelivery locationRegionDeliveryNew = locationRegionDeliveryRepository.save(locationRegionDelivery);
        Order order = new Order();
        order.setId(0L);
        order.setGrandTotal(new BigDecimal(cartInfoDTO.getGrandTotal()));
        order.setDeliveryDate(orderDTO.getDeliveryDate());
        order.setLocationRegionDelivery(locationRegionDelivery);
        order.setUser(cartInfoDTO.getUser().toUser());
        Order orderNew = orderRepository.save(order);
        OrderItem orderItem = new OrderItem();
        List<CartItem> cartItemList = cartItemRepository.findAllCartItemByCart(Long.parseLong(cartInfoDTO.getId()));
        for(CartItem cartItem : cartItemList) {
            orderItem.setId(0L);
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTitle(cartItem.getTitle());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            orderItem.setOrder(orderNew);
            orderItemRepository.save(orderItem);
            cartItemRepository.deleteById(cartItem.getId());
        }
        cartRepository.deleteById(Long.parseLong(cartInfoDTO.getId()));
        return orderNew;
    }
}
