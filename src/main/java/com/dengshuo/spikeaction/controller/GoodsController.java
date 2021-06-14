package com.dengshuo.spikeaction.controller;

/**
 * @Author deng shuo
 * @Date 5/29/21 19:30
 * @Version 1.0
 */

import com.dengshuo.spikeaction.pojo.User;
import com.dengshuo.spikeaction.service.IGoodsService;
import com.dengshuo.spikeaction.service.IUserService;
import com.dengshuo.spikeaction.vo.DetailVo;
import com.dengshuo.spikeaction.vo.GoodsVo;
import com.dengshuo.spikeaction.vo.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 商品
 *
 * @Author deng shuo
 * @Date 5/23/21 17:50
 * @Version 1.0
 */
@Controller
@RequestMapping("/goods")
@Slf4j
public class GoodsController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IGoodsService iGoodsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 跳转商品列表页
     * @param model
     * @param user  传入用户,已经实现参数解析 WebConfig
     * @return 具体跳转页面
     */
    @RequestMapping(value="/toList",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String productToList(Model model,User user,
                                HttpServletRequest request,
                                HttpServletResponse response){

        // 页面缓存
        ValueOperations valueOps = redisTemplate.opsForValue();
        String html = (String) valueOps.get("goodsList");

        if(!StringUtils.isEmpty(html)){
            return html;
        }

        model.addAttribute("user",user);
        log.info("{}", iGoodsService.findGoodsVo());
        model.addAttribute("goodsList",iGoodsService.findGoodsVo());

        // Redis缓冲中相应界面为空
        // 手动渲染
        WebContext webContext = new WebContext(request,response,
                request.getServletContext(),request.getLocale(),
                model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", webContext);
        if(!StringUtils.isEmpty(html)){
            valueOps.set("goodsList",html,60, TimeUnit.SECONDS);
        }

        // 页面缓存,不再进行界面跳转
        //return "goodsList";

        return html;
    }

    /**
     * 跳转商品详情页
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/toDetail/{goodsId}",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String toDetail(Model model, User user, @PathVariable long goodsId ,
                           HttpServletRequest request,
                           HttpServletResponse response){

        ValueOperations valueOperations = redisTemplate.opsForValue();

        String html = (String) valueOperations.get("goodsDetail:" + goodsId);

        if(!StringUtils.isEmpty(html)){
            return html;
        }

        model.addAttribute("user",user);

        GoodsVo goodsVo = iGoodsService.findGoodsVoById(goodsId);

        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date currentDate = new Date();

        /* 秒杀状态 */
        int spikeStatus = 0;
        /* 秒杀倒计时 */
        int remainSeconds = 0;

        if(currentDate.before(startDate)){
            /* 秒杀未开始 */
            remainSeconds = (int)((startDate.getTime() - currentDate.getTime())/1000);

        }else if(currentDate.after(endDate)){
            /* 秒杀结束 */
            spikeStatus = 2;
            remainSeconds = -1;
        }else{
            /* 秒杀进行中 */
            spikeStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("remainSeconds",remainSeconds);
        model.addAttribute("spikeStatus",spikeStatus);
        model.addAttribute("goods",goodsVo);

        // redis缓存中没有查询到
        // 创建并放入缓存中
        WebContext iContext = new WebContext(request,response,
                request.getServletContext(),request.getLocale(),
                model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail",iContext);
        if(!StringUtils.isEmpty(html)){
            // 设置key过期时间
            valueOperations.set("goodsDetail:"+goodsId,html,60,TimeUnit.SECONDS);
        }
        //return "goodsDetail";
        return html;

    }


    /**
     * 商品详情页的静态化
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/detail/{goodsId}")
    @ResponseBody
    public ResponseBean toDetail(User user, @PathVariable Long goodsId) {

        log.info("{进入请求 detail/goodId}",goodsId);
        GoodsVo goodsVo = iGoodsService.findGoodsVoById(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        //秒杀状态
        int spikeStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;
        //秒杀还未开始
        if (nowDate.before(startDate)) {
            remainSeconds = ((int) ((startDate.getTime() - nowDate.getTime()) / 1000));
        } else if (nowDate.after(endDate)) {
            //	秒杀已结束
            spikeStatus = 2;
            remainSeconds = -1;
        } else {
            //秒杀中
            spikeStatus = 1;
            remainSeconds = 0;
        }
        DetailVo detailVo = new DetailVo();
        detailVo.setUser(user);
        detailVo.setGoodsVo(goodsVo);
        detailVo.setSpikeStatus(spikeStatus);
        detailVo.setRemainSeconds(remainSeconds);
        return ResponseBean.success(detailVo);
    }


    /**
     * 跳转商品列表页
     * //@param session  会话参数
     * @param request  请求参数
     * @param response 响应参数
     * @param model  模型
     * @param ticket cookie值
     * @return

     @RequestMapping("/toList")
     public String productList(HttpServletRequest request,
     HttpServletResponse response,
     Model model,
     @CookieValue("userTicket") String ticket){
     // 跳转登录
     if(StringUtils.isEmpty(ticket)){
     return "login";
     }
     // Session 被Redis存储替代
     // User user = (User)session.getAttribute(ticket);

     User user = iUserService.getUserByCookie(ticket,request,response);

     if(null == user){
     return "login";
     }

     // 添加
     model.addAttribute("user",user);

     return "goodsList";

     }
     */
}
