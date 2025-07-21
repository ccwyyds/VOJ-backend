package com.vv.voj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vv.voj.annotation.AuthCheck;
import com.vv.voj.common.BaseResponse;
import com.vv.voj.common.ErrorCode;
import com.vv.voj.common.ResultUtils;
import com.vv.voj.constant.UserConstant;
import com.vv.voj.exception.BusinessException;
import com.vv.voj.model.dto.question.QuestionQueryRequest;
import com.vv.voj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.vv.voj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.vv.voj.model.entity.Question;
import com.vv.voj.model.entity.QuestionSubmit;
import com.vv.voj.model.entity.User;
import com.vv.voj.model.vo.QuestionSubmitVO;
import com.vv.voj.model.vo.QuestionVO;
import com.vv.voj.service.QuestionSubmitService;
import com.vv.voj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 *
 * @Author: vv
 * @Date: 2025/6/10 1:33
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * @Title: 提交题目
     * @Author: vv
     * @Date: 2025/7/15 14:15
     * @Return: 返回提交记录的id
     */
    @PostMapping("/")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest, HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        final User loginUser = userService.getLoginUser(request);
        long questionSubmitId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(questionSubmitId);
    }


    /**
     * 分页获取题目提交列表
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest, HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        //获取题目提交列表
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));

        //进行脱敏(仅管理员和本人能查看自己的代码（通过userId和当前登录的用户id来进行比对）)
        final User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser));
    }


}
