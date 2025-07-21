package com.vv.voj.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vv.voj.common.ErrorCode;
import com.vv.voj.constant.CommonConstant;
import com.vv.voj.exception.BusinessException;
import com.vv.voj.model.dto.question.QuestionQueryRequest;
import com.vv.voj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.vv.voj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.vv.voj.model.entity.Question;
import com.vv.voj.model.entity.QuestionSubmit;
import com.vv.voj.model.entity.User;
import com.vv.voj.model.enums.JudgeInfoMessageEnum;
import com.vv.voj.model.enums.QuestionSubmitLanguageEnum;
import com.vv.voj.model.enums.QuestionSubmitStateEnum;
import com.vv.voj.model.vo.QuestionSubmitVO;
import com.vv.voj.model.vo.QuestionVO;
import com.vv.voj.model.vo.UserVO;
import com.vv.voj.service.QuestionService;
import com.vv.voj.service.QuestionSubmitService;
import com.vv.voj.mapper.QuestionSubmitMapper;
import com.vv.voj.service.UserService;
import com.vv.voj.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ccw
 * @description 针对表【question_submit(题目提交表)】的数据库操作Service实现
 * @createDate 2025-06-13 22:13:10
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit> implements QuestionSubmitService {

    @Resource
    private QuestionService questionService;

    @Resource
    private UserService userService;

    /**
     * @Title: 题目提交信息
     * @Author: vv
     * @Date: 2025/7/19 22:47
     */

    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {

        String language = questionSubmitAddRequest.getLanguage();
        //判断语言是否合法
        QuestionSubmitLanguageEnum value = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (value == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的编程语言");
        }

        long questionId = questionSubmitAddRequest.getQuestionId();
        //判断实体是否存在
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        long userId = loginUser.getId();
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(language);
        questionSubmit.setQuestionId(questionId);
        //设置初始状态
        questionSubmit.setStatus(QuestionSubmitStateEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return questionSubmit.getId();

    }

    /**
     * 获取查询包装类（前端用户根据哪些字段来查询，根据前端传来的请求对象）
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }

        Long questionId = questionSubmitQueryRequest.getQuestionId();
        String language = questionSubmitQueryRequest.getLanguage();
        Long userId = questionSubmitQueryRequest.getUserId();
        String status = questionSubmitQueryRequest.getStatus();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();
        String sortField = questionSubmitQueryRequest.getSortField();


        // 拼接查询条件
        queryWrapper.eq(StringUtils.isNotBlank(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq(StringUtils.isNotBlank(status), "status", status);
        queryWrapper.eq("isDelete", false);
        //根据传入的字段名 sortField 和排序方向 sortOrder，决定是否进行排序、按哪个字段排序、升序还是降序
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);

        return queryWrapper;
    }


    /**
     * @Title: 获取题目封装（一条数据）
     * @Author: vv
     * @Date: 2025/6/14 1:42
     */

    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit,User loginUser) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        //脱敏（仅管理员和本人能查看自己的代码）
        if (loginUser.getId() != questionSubmit.getUserId() && !userService.isAdmin(loginUser)) {
            questionSubmitVO.setCode(null);
        }
        return questionSubmitVO;
    }

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollUtil.isEmpty(questionSubmitList)) {
            return questionSubmitVOPage;
        }
        // 1. 关联查询用户信息
        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList.stream()
                .map(questionSubmit -> getQuestionSubmitVO(questionSubmit, loginUser))
                .collect(Collectors.toList());
        questionSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitVOPage;
    }


}




