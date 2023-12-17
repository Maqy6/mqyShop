package com.shop.controller;

import com.shop.pojo.ProductInfo;
import com.shop.pojo.vo.ProductInfoVo;
import com.shop.service.ProductInfoService;
import com.shop.utils.FileNameUtil;
import com.github.pagehelper.PageInfo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductInfoAction {

    public static final int PAGE_SIZE=5;    //每一页显示的条数

    String saveFileName="";       //添加商品时图片的名称
    @Autowired
    ProductInfoService productInfoService;

    //展示所有的商品
    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
        List<ProductInfo> list=productInfoService.getAll();
        request.setAttribute("list",list);  //将信息传递回去
        return "product";
    }


    @RequestMapping("/split")
    public String split(HttpServletRequest request){
        PageInfo info=null;
        Object vo=request.getSession().getAttribute("prodVo");
        if(vo!=null){//当前存在查询条件
            info=productInfoService.splitPageVo((ProductInfoVo) vo,PAGE_SIZE);
            //取出以后要清空prodVo，免得在其他位置进行读取或判断时造成什么影响
            request.getSession().removeAttribute("prodVo");
        }
        else{//如果查询调节为空，那么就默认显示第一页的内容
            info=productInfoService.splitPage(1,PAGE_SIZE);
        }
        request.setAttribute("info",info);  //将信息传递回去
        return "product";
    }

    @RequestMapping("/user-split")
    public String usersplit(HttpServletRequest request){
        PageInfo info=null;
        Object vo=request.getSession().getAttribute("prodVo");
        if(vo!=null){//当前存在查询条件
            info=productInfoService.splitPageVo((ProductInfoVo) vo,PAGE_SIZE);
            //取出以后记得清空prodVo，免得在其他地方造成什么影响
            request.getSession().removeAttribute("prodVo");
        }
        else{//如果查询调节为空，那么就默认显示第一页的内容
            info=productInfoService.splitPage(1,PAGE_SIZE);
        }
        request.setAttribute("info",info);
        return "userProduct";
    }

    //实现ajax分页
    @ResponseBody
    @RequestMapping("/ajaxsplit")
    public void ajaxsplit(ProductInfoVo vo, HttpSession session){
        PageInfo info=productInfoService.splitPageVo(vo,PAGE_SIZE);
        session.setAttribute("info",info);
    }

    //多条件查询
    @ResponseBody
    @RequestMapping("/condition")
    public void condition(ProductInfoVo vo,HttpSession session){
        List<ProductInfo> list=productInfoService.selectCondition(vo);
        session.setAttribute("list",list);
    }

    //ajax文件上传，用于在添加商品时，将选中的商品图片上传到服务端，显示到页面
    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage,HttpServletRequest request){    //参数名称保持与前端页面一致

        //用FileNameUtil生成文件名称：UUID+文件后缀
        saveFileName= FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());

        //获取项目中图片的存储路径
        String path=request.getServletContext().getRealPath("/image_big");

        //把图片存储到项目下
        try {
            pimage.transferTo(new File(path+ File.separator+saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //完成存储后，返回封装了图片路径的JSON对象给页面，让页面能够实现即时显示上传的图片
        JSONObject object=new JSONObject();
        object.put("imgurl",saveFileName);

        return object.toString();
    }

    @RequestMapping("/save")
    public String save(ProductInfo info,HttpServletRequest request){
        //设置商品相关信息
        info.setpImage(saveFileName);
        info.setpDate(new Date());

        int n=-1;

        try{
            n=productInfoService.save(info);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(n>0){
            request.setAttribute("msg","添加成功！");
        }
        else{
            request.setAttribute("msg","添加失败！");
        }

        //添加完商品之后要手动清空saveFileName，否则下次新增商品时图片就会显示上一次的
        saveFileName="";

        return "forward:/prod/split.action";
    }

    @RequestMapping("/one")
    public String one(int pid,ProductInfoVo vo, Model model,HttpSession session){
        ProductInfo info=productInfoService.getByID(pid);
        model.addAttribute("prod",info);
        //把查询的条件和页码都放在session里，在页面更新（新增、修改、删除）后读取查询条件和页码，对容器进行处理，使得更新后显示的仍然是按条件查询的内容
        session.setAttribute("prodVo",vo);
        return "update";
    }

    //更新商品信息
    @RequestMapping("/update")
    public String update(ProductInfo info,HttpServletRequest request){
        if(!saveFileName.equals("")){//点击了浏览并上传了图片
            info.setpImage(saveFileName);
        }
        int n=-1;
        try{
            n= productInfoService.update(info);
        }   catch (Exception e){
            e.printStackTrace();
        }
        if(n>0){//更新成功
            request.setAttribute("msg","更新成功!");
        }
        else{//更新失败
            request.setAttribute("msg","更新失败！");
        }

        //更新完后要清空saveFileName，否则会影响下一次更新的判断
        saveFileName="";
        //重新跳转到分页页面上
        return "forward:/prod/split.action";
    }


    //删除单个商品
    @RequestMapping("/delete")
    public String delete(int pid,ProductInfoVo vo,HttpServletRequest request){
        int n=-1;
        try {
            n=productInfoService.delete(pid);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(n>0){
            request.setAttribute("msg","删除成功！");
            request.getSession().setAttribute("deleteProdVo",vo);
        }
        else{
            request.setAttribute("msg","删除失败！");
        }
        //删除后返回
        return "forward:/prod/deleteAjaxSplit.action";
    }


    //之前的方法ajaxsplit的返回类型是void，无法作为delete函数的返回值，所以这里重新写一个deleteAjaxSplit作为返回值
    @ResponseBody
    @RequestMapping(value="/deleteAjaxSplit",produces = "text/html;charset=UTF-8")//添加参数设置字符集，使得相应流中的中文能够正确显示
    public Object deleteAjaxSplit(HttpServletRequest request){
        PageInfo info=null;
        Object vo=request.getSession().getAttribute("deleteProdVo");
        if(vo!=null){//存在查询条件
            info=productInfoService.splitPageVo((ProductInfoVo) vo,PAGE_SIZE);
        }
        else {//否则直接回到第一页
            info = productInfoService.splitPage(1, PAGE_SIZE);
        }
        request.getSession().setAttribute("info",info);
        return request.getAttribute("msg");
    }

    //批量删除商品
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String pids,HttpServletRequest request){
        //上传过来的参数是一个字符串数组，格式为(1,2,3,4)，这里首先需要对字符串进行处理
        String ps[]=pids.split(",");
        int n=-1;
        try {
            n=productInfoService.deleteBatch(ps);
            if(n>0){
                request.setAttribute("msg","批量删除成功！");
            }
            else{
                request.setAttribute("msg","批量删除失败！");
            }
        }catch (Exception e){
            request.setAttribute("msg","商品无法删除");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }
}