package com.picc.riskctrl.common.request;

import com.picc.riskctrl.test.vo.UsersVo;
import lombok.Data;

import java.io.Serializable;
	/**
 * @author  作者 E-mail: 
 * @date 创建时间：2019年7月19日 下午3:57:35
 * @version 1.0 
 * @parameter 
 * @since  
 * @return  */
@Data
public class RiskRequestVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/*页码*/
    private Integer pageNo;
    /*每页条数*/
    private Integer pageSize;
    
    private  UsersVo usersVo;
    
}
