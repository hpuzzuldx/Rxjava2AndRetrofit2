package com.ldx.retrofit2andrxjava2.networkbase.anno;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.ContextWrapper;
import android.view.View;
import android.widget.PopupWindow;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wangzhiyuan
 * @since 2018/8/29
 */

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
@CallerRestrictTo({
        Activity.class,
        Fragment.class,
        android.support.v4.app.Fragment.class,
        View.class,
        Dialog.class,
        PopupWindow.class,
        ContextWrapper.class,
        Object.class
})
public @interface CallerType {
}