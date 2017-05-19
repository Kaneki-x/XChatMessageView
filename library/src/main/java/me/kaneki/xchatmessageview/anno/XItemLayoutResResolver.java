package me.kaneki.xchatmessageview.anno;

import me.kaneki.xchatmessageview.base.XMessageAdapter;

/**
 * {@link XItemLayoutRes}注解解析器<br/>
 * 用来解析并创建Item布局实例<br/>
 *
 * @author Kaneki
 * @version 16/11/24
 */
public class XItemLayoutResResolver {

    /**
     * 解析并创建布局资源实例
     *
     * @param xMessageAdapter      待解析的实例对象
     * @return 布局Res实例集合
     */
    public static int[] resolve(XMessageAdapter xMessageAdapter) {
        Class<?> cls = xMessageAdapter.getClass();
        int[] resIds;

        XItemLayoutRes xItemLayoutRes = cls.getAnnotation(XItemLayoutRes.class);
        // 根据注解配置信息生成底部菜单待填充项实例
        resIds = xItemLayoutRes.value();

        return resIds;
    }



}
