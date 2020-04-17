package com.picc.riskctrl.common;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pdfc.framework.common.ResultPage;

import java.io.Serializable;
import java.util.List;

/**
 * 提供基于Crud的Service接口，共CrudApi类使用.<br>
 * 
 * @author zhouxianli
 *
 * @param <V> VO类
 * @param <I> ID类
 * @param <K> 主键类
 */

public interface CrudService<V extends Serializable, I extends Serializable, K extends Serializable> {
	/**
	 * 插入一条记录
	 * 
	 * @param vo 传入VO
	 * @return 返回影响的记录数
	 */
	public I create(V vo);

	/**
	 * 修改记录信息
	 * 
	 * @param vo 传入VO
	 * @return 返回成功更新主对象记录条数
	 */
	public int update(V vo);

	/**
	 * 获取记录信息
	 * 
	 * @param id 传入id
	 * @return 返回结果对象
	 */

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public V selectByPrimaryKey(K id);

	/**
	 * 删除多条记录
	 * 
	 * @param ids 传入ids
	 * @return 返回成功删除主对象记录条数
	 */
	public int delete(List<K> ids);

	/**
	 * 前端页面查询
	 * 
	 * @param organizationVo 传入VO
	 * @return 返回结果对象
	 */

	@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED, readOnly = true)
	public ResultPage<V> search(V t);
}
