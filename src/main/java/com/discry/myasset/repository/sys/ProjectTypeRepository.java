package com.discry.myasset.repository.sys;

import com.discry.myasset.model.sys.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProjectTypeRepository extends JpaRepository<ProjectType,Long>{
    @Modifying
    @Query("update ProjectType t set t.number = ?1,t.name=?2 where t.id = ?3")
    int updateProjectType(String number, String name, Long id);
}
