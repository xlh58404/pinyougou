package com.itheima.listener;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.utils.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SmsListener {

    @Autowired
    private SmsUtils smsUtils;


    @JmsListener(destination = "pinyougou_sms")
    public void sendSms(Map<String, String> mapMessage){
        //String mobile, String template_code, String sign_name, String param
        try {
            //1. 接收消息
            String mobile = mapMessage.get("mobile");   //手机号
            String template_code = mapMessage.get("template_code"); //  模板号
            String sign_name = mapMessage.get("sign_name");     //签名
            String param = mapMessage.get("param");     //参数

            //2. 发送短信
            SendSmsResponse response = smsUtils.sendSms(mobile, template_code, sign_name, param);

            //3. 输出返回信息到控制台
            System.out.println("code : " + response.getCode());
            System.out.println("message : " + response.getMessage());
            System.out.println("requestId : " + response.getRequestId());
            System.out.println("bizId : " + response.getBizId());

        } catch (ClientException e) {
            e.printStackTrace();
        }

    }
}
