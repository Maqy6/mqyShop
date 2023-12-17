package com.shop.service;

import com.shop.pojo.ProductInfo;
import com.shop.pojo.vo.ProductInfoVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductInfoService {
    List<ProductInfo> getAll();

    //分页
    PageInfo splitPage(int pageNum,int pageSize);

    //用于增加商品
    int save(ProductInfo info);

    //按id查询商品
    ProductInfo getByID(int pid);

    //商品更新
    int update(ProductInfo info);

    //商品删除
    int delete(int pid);

    int deleteBatch(String ids[]);

    //多条件查询
    List<ProductInfo> selectCondition(ProductInfoVo vo);

    //多条件分页查询
    public PageInfo splitPageVo(ProductInfoVo vo,int pageSize);

    public int addToCart(int pid);
}
