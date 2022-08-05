package com.cg.service.order;

import com.cg.model.CartItem;
import com.cg.model.Order;
import com.cg.model.dto.CartInfoDTO;
import com.cg.model.dto.OrderDTO;
import com.cg.service.IGeneralService;

public interface OrderService extends IGeneralService<Order> {
    Order doCreateOrder(OrderDTO orderDTO , CartInfoDTO cartInfoDTO);
}
