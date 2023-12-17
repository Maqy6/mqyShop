package com.shop.mapper;

import com.shop.pojo.ProductInfo;
import com.shop.pojo.ProductInfoExample;
import com.shop.pojo.vo.ProductInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductInfoMapper {
    int countByExample(ProductInfoExample example);

    int deleteByExample(ProductInfoExample example);

    int deleteByPrimaryKey(Integer pId);//根据商品ID删除

    int insert(ProductInfo record);//插入商品

    int insertSelective(ProductInfo record);

    List<ProductInfo> selectByExample(ProductInfoExample example);//根据实例查找商品

    ProductInfo selectByPrimaryKey(Integer pId);//根据ID查询商品

    int updateByExampleSelective(@Param("record") ProductInfo record, @Param("example") ProductInfoExample example);

    int updateByExample(@Param("record") ProductInfo record, @Param("example") ProductInfoExample example);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);//更新商品

    int deleteBatch(String ids[]);//批量删除商品

    int addToCart(Integer pId);//将商品加入购物车

    //查询商品
    List<ProductInfo> selectCondition(ProductInfoVo vo);
}