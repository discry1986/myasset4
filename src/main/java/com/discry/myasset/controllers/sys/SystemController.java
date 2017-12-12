package com.discry.myasset.controllers.sys;

import com.discry.myasset.model.sys.ProjectType;
import com.discry.myasset.services.sys.ProjectTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sys/")
public class SystemController {

    @Autowired
    private ProjectTypeService projectTypeService;

    /**
     * 查询所有项目类型
     *
     * @return prdType的Json列表
     */
    @PostMapping(value = "ptlistJson")
    public List<ProjectType> ptlistJson() {
        List<ProjectType> ptlist = projectTypeService.getProjectTypes();
        return ptlist;
    }

    /**
     * 更新项目类型信息
     *
     * @return ProjectType
     */
    @RequestMapping(value = "updatePt", method = {RequestMethod.POST, RequestMethod.GET})
    public ProjectType updatePt(@RequestBody ProjectType projectType) {
        return projectTypeService.saveOrUpdateProjectTypes(projectType);
    }

    /**
     * 新增项目类型信息
     * @param params
     * @return ProjectType
     */
    @PostMapping(value = "addPt")
    public ProjectType addPt(@RequestBody Map<String, Object> params) {
        ProjectType pt = new ProjectType();
        pt.setNumber(params.get("number").toString());
        pt.setName(params.get("name").toString());
        pt.setCreatedate(new Date());
        if(params.get("id") != null && !"".equals(params.get("id").toString()))
            pt.setId(Long.parseLong(params.get("id").toString()));
        return projectTypeService.saveOrUpdateProjectTypes(pt);
    }

    /**
     * 删除项目类型信息
     *
     * @param id
     */
    @PostMapping(value = "removePt/{id}")
    public void removePt(@PathVariable("id") Long id) {
        projectTypeService.delProjectType(id);
    }
}
