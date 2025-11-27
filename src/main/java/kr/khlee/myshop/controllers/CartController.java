package kr.khlee.myshop.controllers;

import jakarta.servlet.http.HttpSession;
import kr.khlee.myshop.models.Cart;
import kr.khlee.myshop.models.Member;
import kr.khlee.myshop.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/cart")
    public String cartView(
            Model model,
            @SessionAttribute("memberInfo") Member member
    ) throws Exception {

        Cart input = new Cart();
        input.setMemberId(member.getId());

        List<Cart> carts = cartService.getList(input);

        int totalPrice = carts.stream()
                .mapToInt(Cart::getTotalPrice)
                .sum();

        model.addAttribute("carts", carts);
        model.addAttribute("totalPrice", totalPrice);

        return "cart/index";
    }
}

