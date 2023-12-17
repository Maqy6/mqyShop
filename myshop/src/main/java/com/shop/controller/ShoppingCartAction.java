package com.shop.controller;
import com.shop.pojo.ProductInfo;
import com.shop.pojo.ShoppingCart;
import com.shop.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartAction {

    @Autowired
    ProductInfoService productInfoService;
    public ShoppingCart shoppingCart = new ShoppingCart();

    @GetMapping("/cart")
    public List<ProductInfo>userCart(){
        return shoppingCart.cartItems;
    }

    @RequestMapping("/add-to-cart")
    public String addToCart(@RequestParam("pid") Integer pid, HttpSession session) {
        // 根据 productId 获取 ProductInfo 对象
        ProductInfo prod=productInfoService.getByID(pid);

        // 将商品添加到购物车
        shoppingCart.addToCart(prod);
        // 设置成功消息
        session.setAttribute("msg", "商品已成功添加到购物车！");
        return "userProduct";
    }

        //把商品移除购物车
        @RequestMapping("/remove-from-cart")
        public String removeFromCart(@RequestParam("index") Integer index, HttpSession session) {
            shoppingCart.getCartItems().remove(index.intValue());
            session.setAttribute("msg", "商品已成功从购物车移除！");
            return "redirect:/shopping-cart/cart";
        }

    private ProductInfo createProductInfo(Integer productId) {
        ProductInfo product = new ProductInfo();
        // 设置产品信息，例如从数据库中获取相应的信息
        return product;
    }
}
