package com.hk.crowd.handler;

import com.hk.crowd.api.MySQLRemoteService;
import com.hk.crowd.config.OSSProperties;
import com.hk.crowd.constant.CrowdConstant;
import com.hk.crowd.entity.po.MemberPO;
import com.hk.crowd.entity.vo.*;
import com.hk.crowd.util.CrowdUtil;
import com.hk.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Dragon
 * @version 1.0.0
 */
@Controller
public class ProjectConsumerHandler {
    @Autowired
    private OSSProperties ossProperties;
    @Autowired
    private MySQLRemoteService mySQLRemoteService;
    @RequestMapping("/get/project/detail/{projectId}")
    public String getProjectDetail(@PathVariable("projectId") Integer projectId, Model model) {
        ResultEntity<DetailProjectVO> resultEntity =
                mySQLRemoteService.getDetailProjectVORemote(projectId);
        if(ResultEntity.SUCCESS.equals(resultEntity.getOperationResult())) {
            DetailProjectVO detailProjectVO = resultEntity.getQueryData();
            model.addAttribute("detailProjectVO", detailProjectVO);
        }
        return "project-show-detail";
    }
    @RequestMapping("/create/confirm")
    public String saveConfirm(MemberConfirmInfoVO confirmInfoVO,ModelMap modelMap,HttpSession session){
        ProjectVO projectVO = (ProjectVO)session.getAttribute(CrowdConstant.ATTR_TEMP_PROJECT);
        if (projectVO==null){
            throw new RuntimeException("请检查前面的信息是否有误");
        }
        projectVO.setMemberConfirmInfoVO(confirmInfoVO);
        MemberPO member = (MemberPO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        Integer id = member.getId();
        ResultEntity<String> saveData=mySQLRemoteService.saveProjectVORemote(projectVO, id);
        if (ResultEntity.FAILED.equals(saveData.getOperationResult())){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE_PROJECT,"保存失败，请重新核对信息");
            return "project-confirm";
        }
        session.removeAttribute(CrowdConstant.ATTR_TEMP_PROJECT);
        return "redirect:http://localhost/project/create/success";
    }
    @RequestMapping("/create/save/return.json")
    @ResponseBody
    public ResultEntity<String>  saveReturn(ReturnVO returnVO, HttpSession session){
        ProjectVO temp = (ProjectVO)session.getAttribute(CrowdConstant.ATTR_TEMP_PROJECT);
        List<ReturnVO> returnVOList = temp.getReturnVOList();
        if(returnVOList==null||returnVOList.size()==0){
            returnVOList=new ArrayList<>();
        }
        returnVOList.add(returnVO);
        temp.setReturnVOList(returnVOList);
        session.setAttribute(CrowdConstant.ATTR_TEMP_PROJECT,temp);
        return ResultEntity.successWithoutData();
    }
    @RequestMapping("/create/upload/return/picture.json")
    @ResponseBody
    public ResultEntity<String> upLoadPicture(@RequestParam("returnPicture") MultipartFile returnPicture) {
        //判断传过来的头图是否为空
        boolean empty = returnPicture.isEmpty();
        if (empty) {
            return ResultEntity.failed("上传的图片不能为空!");
        }
        try {
            ResultEntity<String> resultEntity = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(),
                    ossProperties.getAccessKeyId(),
                    ossProperties.getAccessKeySecret(),
                    returnPicture.getInputStream(),
                    ossProperties.getBucketName(),
                    ossProperties.getBucketDomain(),
                    Objects.requireNonNull(returnPicture.getOriginalFilename()));
            return resultEntity;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/create/project/information")
    public String saveProjectBasicInfo(ProjectVO projectVO, MultipartFile headerPicture, List<MultipartFile> detailPictureList, ModelMap modelMap, HttpSession session) {
        //判断传过来的头图是否为空
        boolean empty = headerPicture.isEmpty();
        if (empty) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE_PROJECT, CrowdConstant.FILE_UPLOAD_FAILED);
            return "project-launch";
        }
        try {
            ResultEntity<String> resultEntity = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(),
                    ossProperties.getAccessKeyId(),
                    ossProperties.getAccessKeySecret(),
                    headerPicture.getInputStream(),
                    ossProperties.getBucketName(),
                    ossProperties.getBucketDomain(),
                    Objects.requireNonNull(headerPicture.getOriginalFilename()));
            if (ResultEntity.FAILED.equals(resultEntity.getOperationResult())) {
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE_PROJECT, CrowdConstant.FILE_UPLOAD_FAILED);
                return "project-launch";
            }
            String headFilePath = resultEntity.getQueryData();
            projectVO.setHeaderPicturePath(headFilePath);
        } catch (IOException e) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE_PROJECT, CrowdConstant.FILE_UPLOAD_FAILED);
            return "project-launch";
        }
        if (detailPictureList == null || detailPictureList.size() == 0) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE_PROJECT, "上传图片不能为空");
            return "project-launch";
        }
        List<String> paths = new ArrayList<>();
        for (MultipartFile detilFile : detailPictureList) {
            try {
                ResultEntity<String> resultEntity = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(),
                        ossProperties.getAccessKeyId(),
                        ossProperties.getAccessKeySecret(),
                        detilFile.getInputStream(),
                        ossProperties.getBucketName(),
                        ossProperties.getBucketDomain(),
                        Objects.requireNonNull(detilFile.getOriginalFilename()));
                if (ResultEntity.FAILED.equals(resultEntity.getOperationResult())) {
                    modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE_PROJECT, CrowdConstant.FILE_UPLOAD_FAILED);
                    return "project-launch";
                }
                String filePath = resultEntity.getQueryData();
                paths.add(filePath);
            } catch (IOException e) {
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE_PROJECT, CrowdConstant.FILE_UPLOAD_FAILED);
                return "project-launch";
            }
        }
        projectVO.setDetailPicturePathList(paths);
        session.setAttribute(CrowdConstant.ATTR_TEMP_PROJECT, projectVO);
        return "redirect:http://localhost/project/return/info/page";
    }
}
