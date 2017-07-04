package data.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import data.drools.PointDomain;
import data.drools.PointRuleEngine;

/**
 * @author onlyone
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    private final Logger    logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private PointRuleEngine pointRuleEngine;

    /**
     * <pre>
     * 任务初始化
     * url:http://localhost:8091/task/init
     * </pre>
     */
    @RequestMapping(value = "/init")
    public String initTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
        pointRuleEngine.initEngine();
        return "任务配置项初次加载成功";
    }

    /**
     * <pre>
     * 任务执行
     * url:http://localhost:8091/task/do
     * </pre>
     */
    @RequestMapping(value = "/do")
    public String doTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PointDomain pointDomain = new PointDomain();
        pointDomain.setUserName("Tom哥");
        pointDomain.setBackMondy(100d);
        pointDomain.setBuyMoney(500d);
        pointDomain.setBackNums(1);
        pointDomain.setBuyNums(5);
        pointDomain.setBillThisMonth(5);
        pointDomain.setBirthDay(true);
        pointDomain.setPoint(0l);

        System.out.println("ִ执行前 point=" + pointDomain.getPoint());

        pointRuleEngine.executeRuleEngine(pointDomain);

        System.out.println("BillThisMonth=" + pointDomain.getBillThisMonth());
        System.out.println("BuyMoney=" + pointDomain.getBuyMoney());
        System.out.println("BuyNums=" + pointDomain.getBuyNums());
        System.out.println("ִ执行后 point=" + pointDomain.getPoint());

        return JSONObject.toJSONString(pointDomain);
    }

    /**
     * <pre>
     * 任务初始化
     * url:http://localhost:8091/task/reload
     * </pre>
     */
    @RequestMapping(value = "/reload")
    public String reloadTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
        pointRuleEngine.refreshEnginRule();
        return "任务配置项重新加载成功";
    }

}
