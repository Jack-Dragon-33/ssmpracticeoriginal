package com.hk.crowd.handler;

import com.hk.crowd.api.MySQLRemoteService;
import com.hk.crowd.api.RedisRemoteService;
import com.hk.crowd.config.ShortMessageProperties;
import com.hk.crowd.constant.CrowdConstant;
import com.hk.crowd.entity.po.MemberPO;
import com.hk.crowd.entity.vo.MemberVO;
import com.hk.crowd.util.CrowdUtil;
import com.hk.crowd.util.ResultEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Dragon
 * @version 1.0.0
 */
@Controller
public class MemberHandler {
    @Autowired
    private RedisRemoteService redisRemoteService;
    @Autowired
    private MySQLRemoteService mySQLRemoteService;
    @Autowired
    private ShortMessageProperties shortMessageProperties;
    @RequestMapping("/member/to/logot")
    public String logot(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
    @RequestMapping("/member/do/login")
    public String login(@RequestParam("loginacct") String loginacct,
                        @RequestParam("userpswd") String userpswd,
                        ModelMap modelMap,
                        HttpSession session) {

        ResultEntity<MemberPO> loginAcctRemote = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginacct);
        if ("SUCCESS".equals(loginAcctRemote.getOperationResult())) {
            MemberPO queryData = loginAcctRemote.getQueryData();
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            boolean matches = bCryptPasswordEncoder.matches(userpswd, queryData.getUserpswd());
            if (matches) {
                session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER, queryData);
                return "redirect:http://localhost/member/to/vip";
            } else {
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, "账号或者密码不正确，请重新输入！");
                return "member-login";
            }
        }
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, loginAcctRemote.getOperationMessage());
        return "member-login";
    }

    @ResponseBody
    @RequestMapping(value = "/member/send/shortMessage.json")
    public ResultEntity<String> sendShowMessage(@RequestParam("phoneNum") String phoneNum) {
        String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
        ResultEntity<String> resultEntity = CrowdUtil.sendCodeByShortMessage(shortMessageProperties.getHost(),
                shortMessageProperties.getPath(),
                shortMessageProperties.getMethod(),
                phoneNum,
                shortMessageProperties.getAppCode(),
                shortMessageProperties.getTemplateId()
        );
        if ("SUCCESS".equals(resultEntity.getOperationResult())) {
            String code = resultEntity.getQueryData();
            redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, code, 15, TimeUnit.MINUTES);
            return ResultEntity.successWithoutData();
        }
        return resultEntity;
    }

    @RequestMapping("/member/to/finish/regist.html")
    public String registMember(MemberVO memberVO, ModelMap modelMap) {
        //首先拿出手机号
        String phoneNum = memberVO.getPhoneNum();
        //取出验证码
        String checkCode = memberVO.getCheckCode();
        String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
        //然后从redis中取出验证码
        ResultEntity<String> redisStringValueByKeyRemote = redisRemoteService.getRedisStringValueByKeyRemote(key);
        if ("SUCCESS".equals(redisStringValueByKeyRemote.getOperationResult())) {
            String redisCode = redisStringValueByKeyRemote.getQueryData();
            //将验证码进行比较
            if (Objects.equals(redisCode, checkCode)) {
                //如果验证码相同就把redis中的删除
                redisRemoteService.removeRedisKeyRemote(key);
                MemberPO memberPO = new MemberPO();
                BeanUtils.copyProperties(memberVO, memberPO);
                //调用远程的数据库将其保存
                ResultEntity<String> save = mySQLRemoteService.save(memberPO);
                if ("SUCCESS".equals(save.getOperationResult())) {
                    return "member-login";
                } else {
                    modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, save.getOperationMessage());
                }
            } else {
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, "验证码错误，请重新输入");
                return "member-regist";
            }
        } else {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, redisStringValueByKeyRemote.getOperationMessage());
            return "member-regist";
        }
        return "redirect:http://localhost/member/to/login";
    }
}
