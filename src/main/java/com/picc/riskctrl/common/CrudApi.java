package com.picc.riskctrl.common;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import pdfc.framework.common.ResultPage;
import pdfc.framework.web.ApiResponse;

import java.io.Serializable;
import java.util.List;
 

/**
 * 提供基于Crud的API实现.<br>
 * 需要子类实现getCrudService()方法
 * 
 * @author zhouxianli
 *
 * @param <V> VO类
 * @param <I> ID类
 * @param <K> REST形式时的主键类
 */
public interface CrudApi<V extends Serializable, I extends Serializable, K extends Serializable> {

	CrudService<V, I, K> getCrudService();

	@ApiOperation(value = "创建对象")
	@PostMapping(value = "/")
	default ApiResponse<I> create(@RequestBody V dto) {
		return ApiResponse.ok(getCrudService().create(dto));
	}

	@ApiOperation(value = "更新对象")
	@PutMapping(value = "/")
	default ApiResponse<Integer> update(@RequestBody V dto) {
		return ApiResponse.ok(getCrudService().update(dto));
	}

	@ApiOperation(value = "查询对象")
	@GetMapping(value = "/{id}")
	default ApiResponse<V> select(@PathVariable(value = "id") K id) {
		return ApiResponse.ok(getCrudService().selectByPrimaryKey(id));
	}

	@ApiOperation(value = "删除对象")
	@DeleteMapping(value = "/{ids}")
	default ApiResponse<Integer> delete(@PathVariable(value = "ids") List<K> ids) {
		return ApiResponse.ok(getCrudService().delete(ids));
	}

	@ApiOperation(value = "查找对象")
	@PostMapping(value = "/_search")
	default ApiResponse<ResultPage<V>> search(@RequestBody V dto) {
		return ApiResponse.ok(getCrudService().search(dto));
	}
}
