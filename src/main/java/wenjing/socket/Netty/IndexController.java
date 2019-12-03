package wenjing.socket.Netty;

import cn.hutool.core.util.RandomUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Random;

/**
 * @Author: Salexal.fww
 * @Date: 2019/12/3 14:11
 * @Version 1.0
 * @Type
 */
@RestController
public class IndexController {

    @GetMapping("/index")
    public ModelAndView index(){
        ModelAndView mav=new ModelAndView("socket");
        mav.addObject("uid",  RandomUtil.randomNumbers(6));
        return mav;
    }

}

