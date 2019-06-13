package cn.choleece.bing.ums.service;

import cn.choleece.bing.ums.entity.SysDept;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 * @author choleece
 * @date 2019/5/27
 */
public interface ISysDeptService extends IService<SysDept> {

    IPage<SysDept> listDepts(IPage<SysDept> page, String keyword);

}
