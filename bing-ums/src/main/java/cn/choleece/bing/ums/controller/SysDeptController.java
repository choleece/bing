package cn.choleece.bing.ums.controller;

import cn.choleece.bing.common.base.BaseController;
import cn.choleece.bing.common.util.R;
import cn.choleece.bing.ums.entity.SysDept;
import cn.choleece.bing.ums.service.ISysDeptService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by choleece on 2019/5/27.
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController extends BaseController {
    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 分页展示系统的部门
     * @param keyword
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("")
    public String listDepts(@RequestParam("keyword") String keyword,
                            @RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize) {
        Page<SysDept> page = new Page<>(pageIndex, pageSize);
        sysDeptService.listDepts(page, keyword);
        return R.ok(page);
    }
}
