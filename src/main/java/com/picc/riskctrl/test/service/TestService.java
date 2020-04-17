package com.picc.riskctrl.test.service;

import com.picc.riskctrl.common.request.RiskRequestVo;
import com.picc.riskctrl.common.utils.ResultPageUtils;
import com.picc.riskctrl.test.dao.TestRepository;
import com.picc.riskctrl.test.dao.TestTwoRepository;
import com.picc.riskctrl.test.dao.UsersRepositorySpecification;
import com.picc.riskctrl.test.po.Test;
import com.picc.riskctrl.test.po.TestTwo;
import com.picc.riskctrl.test.po.Users;
import com.picc.riskctrl.test.vo.UsersVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pdfc.framework.common.ResultPage;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TestService {
	
	@Autowired
	TestRepository testRepository;
	@Autowired
	TestTwoRepository testTwoRepository;
	@Autowired
	private UsersRepositorySpecification usersRepositorySpecification;
	
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String findAll() {
		try {
			List<Test> testList =  testRepository.findAll();
			for (Test test: testList) {
				System.out.println("id:"+test.getId()+",name:"+test.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "findAll";
	}
//	@Transactional(propagation = Propagation.REQUIRED)
	public void insertTest() {
		Test testA = new Test();
//		testA.setId("33");
		testA.setName("wanger");
		testRepository.save(testA);

        System.out.print(1/0);
        Test testB = new Test();
//        testA.setId("34");
        testB.setName("mazi");
		testRepository.save(testB);
	}
	public void insertTwoDiff() {
		Test testA = new Test();
//		testA.setId("43");
		testA.setName("ergouzi");
		testRepository.save(testA);

//        System.out.print(1/0);
        TestTwo test2B = new TestTwo();
//        test2B.setId("44");
        test2B.setName("wangfugui");
        testTwoRepository.save(test2B);
	}
//	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteTest(Integer  id) {
		try {
			testRepository.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @功能:多条件分页查询
	 * @author liqiankun
	 * @return ResultPage
	 *  @日期：20200107
	 **/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ResultPage<Users> findByPage(RiskRequestVo riskRequestVo) {
		ResultPage<Users>  resultPage = new ResultPage<Users>();
		int pageNo = riskRequestVo.getPageNo();
		int pageSize = riskRequestVo.getPageSize();
		UsersVo usersVo = riskRequestVo.getUsersVo();
		if(null!=usersVo) {
			/**
			 * Specification<Users>:用于封装查询条件
			 */
			Specification<Users> spec = new Specification<Users>() {
				
				//Predicate:封装了 单个的查询条件
				/**
				 * Root<Users> root:查询对象的属性的封装。
				 * CriteriaQuery<?> query：封装了我们要执行的查询中的各个部分的信息，select  from order by
				 * CriteriaBuilder cb:查询条件的构造器。定义不同的查询条件
				 */
				@Override
				public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					// where name = '张三三' and age = 20
					List<Predicate> list = new ArrayList<>();
	//				list.add(cb.equal(root.get("name"),"zhangsan"));
					//模糊查询
					if(StringUtils.isNotBlank(usersVo.getName())) {
//						list.add(cb.like(root.get("name"),"%zhang%"));
						list.add(cb.like(root.get("name"),"%"+usersVo.getName()+"%"));
					}
					if(usersVo.getAge()!=null) {
//						list.add(cb.equal(root.get("age"),20));
						list.add(cb.equal(root.get("age"),usersVo.getAge()));
					}
					Predicate[] arr = new Predicate[list.size()];
					return cb.and(list.toArray(arr));
				}
			};
	//		List<Users> list = this.usersRepositorySpecification.findAll(spec);
			// 分页加排序
			Page page = this.usersRepositorySpecification.findAll(spec,
					PageRequest.of(pageNo - 1, pageSize,Sort.by(Sort.Direction.DESC, "age")));
			List<Users> list = page.getContent();
			for (Users users : list) {
				System.out.println(users);
			}
			resultPage = ResultPageUtils.returnPage(page);
		}
		return resultPage;
	}
	
}
