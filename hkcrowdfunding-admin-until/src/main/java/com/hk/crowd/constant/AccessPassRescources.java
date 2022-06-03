package com.hk.crowd.constant;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Dragon
 * @version 1.0.0
 */
public class AccessPassRescources {
    public final static Set<String> PASS_RES_ST=new HashSet<String>();
    public final static Set<String> STATIC_RECOURCES=new HashSet<String>();
    static {
        PASS_RES_ST.add("/");
        PASS_RES_ST.add("/member/to/regist.html");
        PASS_RES_ST.add("/member/to/login");
        PASS_RES_ST.add("/member/do/login");
        PASS_RES_ST.add("/member/send/shortMessage.json");
        PASS_RES_ST.add("/member/to/finish/regist.html");
        PASS_RES_ST.add("/member/to/logot");
        STATIC_RECOURCES.add("bootstrap");
        STATIC_RECOURCES.add("css");
        STATIC_RECOURCES.add("fonts");
        STATIC_RECOURCES.add("img");
        STATIC_RECOURCES.add("jquery");
        STATIC_RECOURCES.add("layer");
        STATIC_RECOURCES.add("script");
        STATIC_RECOURCES.add("ztree");
    }
    public static boolean judgeIsStaticRescources(String path){
        if(Objects.isNull(path)||path.length()==0){
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        String[] split = path.split("/");
        String firstLevelPath=split[1];
        return STATIC_RECOURCES.contains(firstLevelPath);
    }
}
