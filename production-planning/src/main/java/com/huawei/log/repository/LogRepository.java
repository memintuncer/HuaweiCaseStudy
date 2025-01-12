package com.huawei.log.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huawei.log.entity.Log;
import com.huawei.log.entity.LogActions;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
	List<Log> findByAction(LogActions action);
}
