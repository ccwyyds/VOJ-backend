package com.vv.voj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vv.voj.model.entity.Post;
import java.util.Date;
import java.util.List;

/**
 * 帖子数据库操作
 *
 * @Author: vv
 * @Date: 2025/6/10 1:33
 */
public interface PostMapper extends BaseMapper<Post> {

    /**
     * 查询帖子列表（包括已被删除的数据）
     */
    List<Post> listPostWithDelete(Date minUpdateTime);

}




