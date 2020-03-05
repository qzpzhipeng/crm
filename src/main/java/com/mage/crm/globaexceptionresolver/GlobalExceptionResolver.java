package com.mage.crm.globaexceptionresolver;

import com.alibaba.fastjson.JSON;
import com.mage.crm.exceptions.NoLoginException;
import com.mage.crm.exceptions.ParamsException;
import com.mage.crm.model.ResultInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @param
 * @author qzp
 * @create 2020-03-05 14:48
 */
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handle, Exception ex) {
        /*
        * 首先判断异常类型
        *   如果异常类型是未登录异常，执行视图转发
        * */
        ModelAndView modelAndView = new ModelAndView();
        if(ex instanceof NoLoginException){
            NoLoginException noLoginException = (NoLoginException) ex;
            modelAndView.setViewName("no_login");
            modelAndView.addObject("msg",noLoginException.getMsg());
            modelAndView.addObject("ctx",request.getContextPath());
            return modelAndView;
        }
        /**方法返回值类型判断:
         *    如果方法级别存在@ResponseBody 方法响应内容为json  否则视图
         *    handler 参数类型为HandlerMethod
         * 返回值
         *    视图:默认错误页面
         *
         *
         *
         *    json:错误的json信息
         */
        modelAndView.setViewName("error");
        modelAndView.addObject("code",400);
        modelAndView.addObject("msg","系统异常，请稍后重试...");

        if(handle instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handle;
            ResponseBody responseBodyAnnotation = handlerMethod.getMethod().getDeclaredAnnotation(ResponseBody.class);
            System.out.println(responseBodyAnnotation);
            //@org.springframework.web.bind.annotation.ResponseBody()
            if (responseBodyAnnotation == null) {
                /*
                * 方法返回视图
                * */
                if(ex instanceof ParamsException){
                    ParamsException paramsException = (ParamsException) ex;
                    modelAndView.addObject("msg",paramsException.getMsg());
                    modelAndView.addObject("code",paramsException.getCode());
                }
                return modelAndView;
            }else {
                /*
                * 方法返回json
                * */
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(300);
                resultInfo.setMsg("系统错误，请稍后重试!");
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json;charset=utf-8");
                PrintWriter printWriter=null;
                try {
                    printWriter = response.getWriter();
                    printWriter.write(JSON.toJSONString(resultInfo));
                    printWriter.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (printWriter != null) {
                        printWriter.close();
                    }
                }
                return null;
            }
        }else {
            return modelAndView;
        }
    }
}
