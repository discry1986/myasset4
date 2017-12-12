package com.discry.myasset.sys;

import com.discry.myasset.model.sys.ProjectType;
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
public class TestProjectType {
    private static Logger logger = LoggerFactory.getLogger(TestProjectType.class);

    @Autowired
    ProjectTypeRepository projectTypeRepository;

    @Before
    public void initData(){
//        ProjectType projectType1 = new ProjectType();
//        projectType1.setNumber("A.01");
//        projectType1.setName("工资");
//        projectType1.setCreatedate(new Date());
//        projectTypeRepository.save(projectType1);
    }

    @Test
    public void findProjectType(){
        ProjectType projectType = projectTypeRepository.findOne(1L);
        logger.info("====project classes==== projectClasses number:{} name:{}",projectType.getNumber(),projectType.getName());

    }
}
