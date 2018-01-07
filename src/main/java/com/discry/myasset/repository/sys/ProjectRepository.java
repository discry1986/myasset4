package com.discry.myasset.repository.sys;

import com.discry.myasset.model.sys.Project;
import com.discry.myasset.model.sys.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {

    @Modifying
    @Query("update Project p set p.fstaus=?1 where p.id = ?2")
    Integer updateProject(StatusEnum status, Long id);
}
