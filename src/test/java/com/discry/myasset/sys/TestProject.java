package com.discry.myasset.sys;

import com.discry.myasset.model.sys.Project;
import com.discry.myasset.model.sys.ProjectType;
import com.discry.myasset.model.sys.StatusEnum;
import com.discry.myasset.repository.sys.ProjectRepository;
import com.discry.myasset.repository.sys.ProjectTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestProject {
    private static Logger logger = LoggerFactory.getLogger(TestProject.class);

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectTypeRepository projectTypeRepository;

    @Before
    public void initData() {
//        ProjectType projectType1 = projectTypeRepository.findOne(1L);
//
//        Project project1 = new Project();
//        project1.setNumber("A001");
//        project1.setName("工资A");
//        project1.setFstaus(StatusEnum.NORMAL);
//        project1.setCreatedate(new Date());
//        project1.setProjectType(projectType1);
//        projectRepository.save(project1);

    }

    @Test
    public void findProject() {
        Project project = projectRepository.findOne(1L);
        logger.info("====Project==== Project number:{} name:{} projectType:{} fstatus:{}",
                project.getNumber(), project.getName(), project.getProjectType(), project.getFstaus());
    }
}
