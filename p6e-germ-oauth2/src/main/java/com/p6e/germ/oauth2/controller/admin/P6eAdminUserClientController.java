package com.p6e.germ.oauth2.controller.admin;

import com.p6e.germ.oauth2.auth.P6eAdminAuth;
import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.model.vo.P6eAdminUserClientParamVo;
import com.p6e.germ.oauth2.model.vo.P6eAdminUserManageParamVo;
import org.springframework.web.bind.annotation.*;

/**
 * 获取客户端列表
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/admin/user/client")
public class P6eAdminUserClientController extends P6eBaseController {

    @GetMapping
    @P6eAdminAuth(role = 0)
    public P6eResultModel list() {
        return null;
    }

    @PutMapping
    @P6eAdminAuth(role = 0)
    public P6eResultModel create(@RequestBody P6eAdminUserClientParamVo param) {
        return null;
    }

    @PutMapping("/{id}")
    @P6eAdminAuth(role = 0)
    public P6eResultModel update(@PathVariable Integer id) {
        return null;
    }

    @DeleteMapping("/{id}")
    @P6eAdminAuth(role = 0)
    public P6eResultModel delete(@PathVariable Integer id) {
        return null;
    }

    @GetMapping("/examine")
    public P6eResultModel examineList() {
        return null;
    }

    @PutMapping("/examine/{id}")
    public P6eResultModel examine(@PathVariable Integer id, @RequestBody String name) {
        return null;
    }

}
