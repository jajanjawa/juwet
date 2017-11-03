package com.github.jajanjawa.juwet.wire;

import com.github.jajanjawa.juwet.util.JsonUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Parameters extends ArrayList<Object> {

    public void convertValues(Method method) {
        convertValues(method.getGenericParameterTypes());
    }

    public void convertValues(Type[] types) {
        int size = size();
        if (size != types.length) {
            throw new IllegalArgumentException("argument ada yang salah, kurang atau lebih");
        }

        ArrayList<Object> list = new ArrayList<Object>();
        for (int i = 0; i < size; i++) {
            String json = (String) get(i);
            Object o = JsonUtil.gson.fromJson(json, types[i]);
            System.out.println(o.getClass().getName());
            list.add(o);
        }
        clear();
        addAll(list);
    }
}
