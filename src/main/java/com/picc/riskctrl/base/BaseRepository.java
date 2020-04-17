package com.picc.riskctrl.base;

import com.picc.riskctrl.common.utils.QueryRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

/**
 * base repository
 *
 * @author wangwenjie
 * @date 2020-01-08
 */
@NoRepositoryBean
public interface BaseRepository<DOMAIN, ID> extends JpaRepository<DOMAIN, ID>, JpaSpecificationExecutor<DOMAIN> {

    /**
     * 通过QueryRule条件查询所有
     * @param queryRule
     * @return
     */
    default List<DOMAIN> findAll(QueryRule<DOMAIN> queryRule) {
        return findAll(queryRule.getSpecification());
    }

    /**
     * 分页查询
     * @param queryRule
     * @param pageable
     * @return
     */
    default Page<DOMAIN> findAll(QueryRule<DOMAIN> queryRule, Pageable pageable) {
        return findAll(queryRule.getSpecification(), pageable);
    }

    /**
     * 分页查询
     * @param queryRule
     * @param pageNo 查询页码 从 1 开始
     * @param pageSize 当前页size
     * @return
     */
    default Page<DOMAIN> findAll(QueryRule<DOMAIN> queryRule, int pageNo, int pageSize) {
        if (queryRule.getSort() != null) {
            return findAll(queryRule, PageRequest.of(pageNo - 1, pageSize, queryRule.getSort()));
        }
        return findAll(queryRule, PageRequest.of(pageNo - 1, pageSize));
    }

    /**
     * 查询唯一对象
     * @param queryRule
     * @return
     */
    default Optional<DOMAIN> findOne(QueryRule<DOMAIN> queryRule) {
        return findOne(queryRule.getSpecification());
    }

    /**
     * 查询最大id + 1
     *
     * @author wangwenjie
     * @return int
     */
    @Query("select max(id) + 1 from #{#entityName}")
    int queryMaxId();
}
