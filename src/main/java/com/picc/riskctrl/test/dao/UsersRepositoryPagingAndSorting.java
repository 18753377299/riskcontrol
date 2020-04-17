package com.picc.riskctrl.test.dao;

import com.picc.riskctrl.test.po.Users;
import org.springframework.data.repository.PagingAndSortingRepository;
/**
 * 
 *PagingAndSortingRepository接口
 *
 */
public interface UsersRepositoryPagingAndSorting extends PagingAndSortingRepository<Users,Integer> {

}
