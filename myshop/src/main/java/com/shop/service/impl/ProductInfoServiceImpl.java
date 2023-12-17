package com.shop.service.impl;

import com.shop.mapper.ProductInfoMapper;
import com.shop.pojo.ProductInfo;
import com.shop.pojo.ProductInfoExample;
import com.shop.pojo.vo.ProductInfoVo;
import com.shop.service.ProductInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    ProductInfoMapper productInfoMapper;

    //获取所有商品
    @Override
    public List<ProductInfo> getAll() {
        return productInfoMapper.selectByExample(new ProductInfoExample());
    }

    //根据页码分页展示页面
    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize); //使用了PageHelper插件来设置每一页的信息

        ProductInfoExample  example=new ProductInfoExample();

        //手动设置商品按主键进行降序排列，便于插入
        //select * from product_info order by p_id desc
        example.setOrderByClause("p_id desc");

        //按条件查询，并将查询到的商品信息封装进PageInfo对象中作为返回值
        List<ProductInfo> list=productInfoMapper.selectByExample(example);
        PageInfo<ProductInfo>pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    //新增商品后保存
    @Override
    public int save(ProductInfo info) {
        return productInfoMapper.insert(info);
    }
    //返回按主键查询到的商品
    @Override
    public ProductInfo getByID(int pid) {
        return productInfoMapper.selectByPrimaryKey(pid);
    }
    //更新信息
    @Override
    public int update(ProductInfo info) {
        return productInfoMapper.updateByPrimaryKey(info);
    }
    //删除单个商品
    @Override
    public int delete(int pid) {
        return productInfoMapper.deleteByPrimaryKey(pid);
    }
    //批量删除商品
    @Override
    public int deleteBatch(String[] ids) {
        return productInfoMapper.deleteBatch(ids);
    }
    //用于多条件查询商品
    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo) {
        return productInfoMapper.selectCondition(vo);
    }
    //分页展示多条件查询到的商品
    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo, int pageSize) {
        PageHelper.startPage(vo.getPage(),pageSize);
        List<ProductInfo> list=productInfoMapper.selectCondition(vo);
        return new PageInfo<>(list);
    }
    //将商品加入购物车
    @Override
    public int addToCart(int pid) {
        return productInfoMapper.addToCart(pid);
    }
}
