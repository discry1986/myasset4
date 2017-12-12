package com.discry.myasset.model.fin;

import com.discry.myasset.model.sys.Project;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 收入明细类
 */
@Entity
@Table(name = "fin_income_data")
public class Income implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;

    private String finYM;

    private Double depositAmt;

    private Double incomeAmt;

    private String remark;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getFinYM() {
        return finYM;
    }

    public void setFinYM(String finYM) {
        this.finYM = finYM;
    }

    public Double getDepositAmt() {
        return depositAmt;
    }

    public void setDepositAmt(Double depositAmt) {
        this.depositAmt = depositAmt;
    }

    public Double getIncomeAmt() {
        return incomeAmt;
    }

    public void setIncomeAmt(Double incomeAmt) {
        this.incomeAmt = incomeAmt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Income{" +
                "Id=" + Id +
                ", project=" + project +
                ", finYM='" + finYM + '\'' +
                ", depositAmt=" + depositAmt +
                ", incomeAmt=" + incomeAmt +
                ", createDate=" + createDate +
                '}';
    }
}
