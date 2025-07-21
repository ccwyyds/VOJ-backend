package com.vv.voj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vv.voj.model.dto.question.QuestionQueryRequest;
import com.vv.voj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.vv.voj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.vv.voj.model.entity.Question;
import com.vv.voj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vv.voj.model.entity.User;
import com.vv.voj.model.vo.QuestionSubmitVO;
import com.vv.voj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ccw
 * @description 针对表【question_submit(题目提交表)】的数据库操作Service
 * @createDate 2025-06-13 22:13:10
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    //题目提交
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);


    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit,User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);

}

