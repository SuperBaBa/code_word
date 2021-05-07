package org.jarvis.consumer.hanlder;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * author:tennyson  date:2020/6/26
 */
@ControllerAdvice
public class CustomizedExceptionHandler {
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ModelAndView uploadException(HttpServletRequest request, MaxUploadSizeExceededException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", "上传超出限制");
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ModelAttribute(value = "userInfo")
    public Map<String, String> userInfo() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", "徐锦江");
        map.put("出演作品", "海王");
        return map;
    }
    @ModelAttribute(value = "globalInfo")
    public Map<String, String> globalInfo() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "全局信息");
        map.put("value", "这是一个全局信息目前只会在");
        return map;
    }
}
