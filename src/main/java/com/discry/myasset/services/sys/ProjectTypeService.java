package com.discry.myasset.services.sys;

import com.discry.myasset.model.sys.ProjectType;
import com.discry.myasset.repository.sys.ProjectTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@EnableTransactionManagement
@Service
public class ProjectTypeService {

    @Autowired
    private ProjectTypeRepository projectTypeRepository;

    /**
     * 查询所有项目类型
     * @return List<ProjectType>
     */
    public List<ProjectType> getProjectTypes(){
        return projectTypeRepository.findAll();
    }


    /**
     * 更新项目类型
     * @return ProjectType
     */
    @Transactional
    public ProjectType saveOrUpdateProjectTypes(ProjectType projectType){
        if (projectType.getId() == null){
            projectTypeRepository.save(projectType);
        }else{
            projectTypeRepository.updateProjectType(projectType.getNumber(),projectType.getName(),projectType.getId());
        }
        return projectType;
    }

    /**
     * 删除项目类型
     *
     */
    public void delProjectType(Long id){
        projectTypeRepository.delete(id);
    }

}
