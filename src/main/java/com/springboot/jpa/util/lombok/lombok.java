package com.springboot.jpa.util.lombok;

import io.swagger.annotations.ApiParam;
import lombok.*;

import java.io.*;
import java.util.Objects;

/**
 * Author: WangRui
 * Date: 2018/6/5
 * Describe: Lombok 插件测试
 *
 * @ToString 注解，为使用该注解的类生成一个toString方法，默认的toString格式为：ClassName(fieldName= fieleValue ,fieldName1=fieleValue)
 * @EqualsAndHashCode 注解，为使用该注解的类自动生成equals和hashCode方法
 * @NoArgsConstructor 注解，生成无参构造器
 * @RequiredArgsConstructor 注解，生成指定参数的构造器
 * @AllArgsConstructor 注解，生成包含所有参数的构造器
 * @Data 注解,其包含的集合@ToString，@EqualsAndHashCode，所有字段的@Getter和所有非final字段的@Setter, @RequiredArgsConstructor
 */
@Data
@ToString(callSuper = true, exclude = "id")
@EqualsAndHashCode(callSuper = false, exclude = "id")
/*@NoArgsConstructor
@RequiredArgsConstructor(staticName = "brand,type")
@AllArgsConstructor(access = AccessLevel.PROTECTED)*/
public class lombok {

    //@ToString 注解相当于：
    @Override
    public String toString() {
        return "lombok{" +
                "brand='" + brand + '\'' +
                ", type='" + type + '\'' +
                '}';
    }


    //@EqualsAndHashCode 注解相当于：
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        lombok lombok = (lombok) o;
        return Objects.equals(brand, lombok.brand) &&
                Objects.equals(type, lombok.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(brand, type);
    }

    @ApiParam("ID")
    private Long id;

    @ApiParam("品牌")
    private String brand;

    //@Getter/@Setter注解可以针对类的属性字段自动生成Get/Set方法。
    @ApiParam("型号")
    @Getter
    @Setter
    private String type;

    //@NonNull注解能够为方法或构造函数的参数提供非空检查。
    public void notNullExample(@NonNull String string) {
        //方法内的代码
    }

    /*=>上面代码相当于如下：
    public void notNullExample(String string) {
        if (string != null) {
            //方法内的代码相当于如下：
        } else {
            throw new NullPointerException("null");
        }
    }*/

    //@Cleanup 自动释放资源，会自动生成finally块代码，关闭资源
    public void cleanUpExample() throws IOException {
        @Cleanup InputStream in = new FileInputStream("read");
        @Cleanup OutputStream out = new FileOutputStream("write");
        byte[] b = new byte[10000];
        while (true) {
            int r = in.read(b);
            if (r == -1) break;
            out.write(b, 0, r);
        }
    }


}
