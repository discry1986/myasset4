package com.discry.myasset.fin;

import com.discry.myasset.model.fin.Income;
import com.discry.myasset.model.sys.Project;
import com.discry.myasset.repository.fin.IncomeRepository;
import com.discry.myasset.repository.sys.ProjectRepository;
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
public class TestIncome {
    private static Logger logger = LoggerFactory.getLogger(TestIncome.class);

    @Autowired
    IncomeRepository incomeRepository;
    @Autowired
    ProjectRepository projectRepository;

    @Before
    public void initData() {
//        Project project = projectRepository.getOne(1L);
//        Income income = new Income();
//        income.setCreateDate(new Date());
//        income.setFinYM("201710");
//        income.setDepositAmt(0.00);
//        income.setIncomeAmt(10000.00);
//        income.setProject(project);
//        income.setRemark("先尼科工资");
//        incomeRepository.save(income);
    }

    @Test
    public void findIncome() {
        Income income = incomeRepository.findOne(1L);
        logger.info("====Income==== Income finYM:{} name:{} depositAmt:{} incomeAmt:{} project:{} remark:{}",
                income.getFinYM(), income.getDepositAmt(), income.getIncomeAmt(), income.getProject().getName(), income.getRemark());
    }
}
