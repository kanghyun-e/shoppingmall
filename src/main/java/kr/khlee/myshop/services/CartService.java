package kr.khlee.myshop.services;

import kr.khlee.myshop.models.Cart;

import java.util.List;

public interface CartService {
    void add(Cart input) throws Exception;
    void update(Cart input) throws Exception;
    void delete(Cart input) throws Exception;
    List<Cart> getList(Cart input) throws Exception;
}

