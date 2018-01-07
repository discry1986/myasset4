package com.discry.myasset.services.sys;

import com.discry.myasset.model.sys.Project;
import com.discry.myasset.model.sys.StatusEnum;
import com.discry.myasset.repository.sys.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@EnableTransactionManagement
@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    /**
     * 分页查询所有项目
     *
     * @return Page<Project>
     */
    public Page<Project> getPagedProjects(int pageNumber, int pageSize) {
        PageRequest request = new PageRequest(pageNumber - 1, pageSize, null);
        Page<Project> pagedProjects = this.projectRepository.findAll(request);
        return pagedProjects;
    }

    /**
     * 删除、禁用、启用项目明细
     */
    @Transactional
    public Integer updateProject(StatusEnum status, Long id) {
       return projectRepository.updateProject(status, id);
    }

    /**
     * 保存更新项目明细
     */
    public Project saveProject(Project project) {
        project.setCreatedate(new Date());
        projectRepository.save(project);
        return project;
    }
}
