package com.monk_commerce.SDE_Assignment.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedCartItemWrapper {
    private List<UpdatedCartItem> items;
}
