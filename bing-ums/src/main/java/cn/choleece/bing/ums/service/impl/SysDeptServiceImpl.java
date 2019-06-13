package cn.choleece.bing.ums.service.impl;

import cn.choleece.bing.ums.entity.SysDept;
import cn.choleece.bing.ums.mapper.SysDeptMapper;
import cn.choleece.bing.ums.service.ISysDeptService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author choleece
 * @date 2019/5/27
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {
    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public IPage<SysDept> listDepts(IPage<SysDept> page, String keyword) {
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", keyword);
        return sysDeptMapper.selectPage(page, queryWrapper);
    }
}
