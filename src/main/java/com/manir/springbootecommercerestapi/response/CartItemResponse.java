package com.manir.springbootecommercerestapi.response;


import com.manir.springbootecommercerestapi.dto.CartItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    private List<CartItemDto> content;
    private double totalCost;
}
