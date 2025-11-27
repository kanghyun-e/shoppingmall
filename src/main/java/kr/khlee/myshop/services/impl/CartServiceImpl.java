package kr.khlee.myshop.services.impl;

import kr.khlee.myshop.mappers.CartMapper;
import kr.khlee.myshop.models.Cart;
import kr.khlee.myshop.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartMapper cartMapper;

    @Override
    public void add(Cart input) throws Exception {
        cartMapper.insert(input);
    }

    @Override
    public void update(Cart input) throws Exception {
        cartMapper.update(input);
    }

    @Override
    public void delete(Cart input) throws Exception {
        cartMapper.delete(input);
    }

    @Override
    public List<Cart> getList(Cart input) throws Exception {
        return cartMapper.selectList(input);
    }
}
