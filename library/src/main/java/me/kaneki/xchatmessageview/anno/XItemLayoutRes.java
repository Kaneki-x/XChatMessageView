package me.kaneki.xchatmessageview.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置消息列表Item布局资源
 *
 * @author kaneki
 * @version 16/11/24
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface XItemLayoutRes {

    /**
     * @return 待添加的Item布局资源类型
     */
    int[] value();

}
