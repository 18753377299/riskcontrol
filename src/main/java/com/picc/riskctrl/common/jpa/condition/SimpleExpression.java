package com.picc.riskctrl.common.jpa.condition;

import com.picc.riskctrl.common.jpa.vo.Criterion;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;



public class SimpleExpression implements Criterion{  
    
    private String fieldName;       //属性名  
    private Object value;           //对应值  
    private Operator operator;      //计算符
    private Integer from;  			// 从什么位置开始
    private Integer len;			// 截取长度
  
    public SimpleExpression(String fieldName, Operator operator) {
		super();
		this.fieldName = fieldName;
		this.operator = operator;
	}
	protected SimpleExpression(String fieldName, Object value, Operator operator) {  
        this.fieldName = fieldName;  
        this.value = value;  
        this.operator = operator;  
    }  
    public SimpleExpression(String fieldName, Object value, Operator operator, Integer from, Integer len) {
		super();
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
		this.from = from;
		this.len = len;
	}
    
	public Integer getFrom() {
		return from;
	}


	public Integer getLen() {
		return len;
	}


	public String getFieldName() {  
        return fieldName;  
    }  
    public Object getValue() {  
        return value;  
    }  
    public Operator getOperator() {  
        return operator;  
    }  
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query,  
            CriteriaBuilder builder) {  
        Path expression = null;  
        if(fieldName.contains(".")){  
            String[] names = StringUtils.split(fieldName, ".");
            expression = root.get(names[0]);  
            for (int i = 1; i < names.length; i++) {  
                expression = expression.get(names[i]);  
            }  
        }else{  
            expression = root.get(fieldName);  
        }  
        switch (operator) {  
        case EQ:  
            return builder.equal(expression, value);  
        case NE:  
            return builder.notEqual(expression, value);  
        case LIKE: 
        	// 全模糊
//            return builder.like((Expression<String>) expression, "%" + value + "%");
        	// 可以进行自定义模糊
        	return builder.like((Expression<String>) expression, value.toString());
        case LT:  
            return builder.lessThan(expression, (Comparable) value);  
        case GT:  
            return builder.greaterThan(expression, (Comparable) value);  
        case LTE:  
            return builder.lessThanOrEqualTo(expression, (Comparable) value);  
        case GTE:  
            return builder.greaterThanOrEqualTo(expression, (Comparable) value);
            // 进行字段截取,等于的时候
        case INTERCEPT: 
        	return builder.equal(builder.substring(expression, from, len), value);
        	 // 进行字段截取,不等于的时候
        case NINTERCEPT: 
        	return builder.notEqual(builder.substring(expression, from, len), value);
        case ISNOTNULL: 
        	return builder.isNotNull(expression);
        case ISNULL: 
        	return builder.isNull(expression);
        default:  
            return null;  
        }  
    }

      
}
