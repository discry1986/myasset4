package com.discry.myasset.controllers.sys;

import com.discry.myasset.model.sys.Project;
import com.discry.myasset.model.sys.ProjectType;
import com.discry.myasset.model.sys.StatusEnum;
import com.discry.myasset.services.sys.ProjectService;
import com.discry.myasset.services.sys.ProjectTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sys/")
public class SystemController {

    @Autowired
    private ProjectTypeService projectTypeService;
    @Autowired
    private ProjectService projectService;

    /**
     * 查询所有项目类型
     *
     * @return prdType的Json列表
     */
    @RequestMapping(value = "ptlistJson")
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
     * 新增或更新项目类型信息
     *
     * @param params
     * @return ProjectType
     */
    @PostMapping(value = "addPt")
    public ProjectType addPt(@RequestBody Map<String, Object> params) {
        ProjectType pt = new ProjectType();
        pt.setNumber(params.get("number").toString());
        pt.setName(params.get("name").toString());
        pt.setCreatedate(new Date());
        if (params.get("id") != null && !"".equals(params.get("id").toString()))
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

    /**
     * 分页查询所项目名称
     *
     * @return Page<Project>
     */
    @RequestMapping(value = "projectListJson")
    public Page<Project> projectsListJson(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                          @RequestParam(value = "pageSize", defaultValue = "0") int pageSize) {
        return projectService.getPagedProjects(pageNumber, pageSize);
    }

    /**
     * 更改项目状态
     *
     * @param params
     */
    @PostMapping(value = "updateProStatus")
    public Integer updateProStatus(@RequestBody Map<String, Object> params) {
        int status = Integer.parseInt(params.get("status").toString());
        Long id = Long.parseLong(params.get("id").toString());
        Integer res = 0;
        if (status == 1)
            res = projectService.updateProject(StatusEnum.NORMAL, id);
        if (status == 2)
            res = projectService.updateProject(StatusEnum.DISABLED, id);
        return res;
    }

    /**
     * 新增或更新项目明细记录
     */
    @PostMapping(value = "addPro")
    public Project addPro(@RequestBody Project project) {
        if (project.getFstaus() == null)
            project.setFstaus(StatusEnum.NORMAL);
        return projectService.saveProject(project);
    }
}
