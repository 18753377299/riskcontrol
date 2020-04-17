package com.picc.riskctrl.common.jpa.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;


@NoRepositoryBean
public interface JpaBaseRepository<T,ID extends Serializable> extends 
			PagingAndSortingRepository<T,ID>,JpaRepository<T, ID>,JpaSpecificationExecutor<T>,Repository<T, ID> {

    @Override
    Page<T> findAll(Specification<T> specification, Pageable pageable);
    
    @Override
    List<T> findAll(Specification<T> specification);
    
    @Override
    List<T> findAll(Specification<T> specification, Sort sort);
}
