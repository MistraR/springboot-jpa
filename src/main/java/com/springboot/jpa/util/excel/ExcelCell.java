package com.springboot.jpa.util.excel;

import java.lang.annotation.*;

/**
 * Author : WangRui
 * Date : 2018/5/20
 * Describe :
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelCell {
    int index() default 0;//列标记
    ExcelCellType value();//Cell类型
}
