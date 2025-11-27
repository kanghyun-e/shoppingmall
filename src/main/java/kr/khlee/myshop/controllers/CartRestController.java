package kr.khlee.myshop.controllers;

import jakarta.servlet.http.HttpSession;
import kr.khlee.myshop.models.Cart;
import kr.khlee.myshop.models.Member;
import kr.khlee.myshop.services.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartRestController {

    private final CartService cartService;

    @PostMapping("/add")
    public void add(
            @SessionAttribute("memberInfo") Member member,
            @RequestParam int productId,
            @RequestParam(required = false) String optionText,
            @RequestParam int quantity
    ) throws Exception {

        Cart input = new Cart();
        input.setMemberId(member.getId());
        input.setProductId(productId);
        input.setOptionText(optionText);
        input.setQuantity(quantity);

        cartService.add(input);
    }

    @PostMapping("/update")
    public void update(
            @SessionAttribute("memberInfo") Member member,
            @RequestParam int id,
            @RequestParam int quantity
    ) throws Exception {

        Cart input = new Cart();
        input.setId(id);
        input.setQuantity(quantity);

        cartService.update(input);
    }

    @PostMapping("/delete")
    public void delete(
            @RequestParam int id
    ) throws Exception {

        Cart input = new Cart();
        input.setId(id);

        cartService.delete(input);
    }
}

