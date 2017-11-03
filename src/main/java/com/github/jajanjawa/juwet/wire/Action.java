package com.github.jajanjawa.juwet.wire;

import com.github.jajanjawa.juwet.util.JsonUtil;

import java.lang.reflect.Method;

/**
 * Perintah yang perlu dijalankan.
 */
public class Action {
    private String module;
    private String name;
    private Parameters parameters;

    public Action() {
    }

    /**
     * Mengisi data dengan cepat. Masih perlu setel modul
     *
     * @param method Method yang akan diambil namanya.
     * @param args   argument yang digunakan untuk panggil method diatas.
     * @see #setModule(String)
     */
    public void from(Method method, Object[] args) {
        name = method.getName();

        if (args != null) {
            setParameters(args);
        }
    }

    /**
     * Menyalin nama modul dan nama method.
     *
     * @return salinan Action
     */
    public Action copy() {
        Action action = new Action();
        action.module = module;
        action.name = name;

        return action;
    }

    public String getModule() {
        return module;
    }

    /**
     * setel {@code Action} ini tergolong dalam modul yang diberikan.
     *
     * @param module nama modul
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * @return nama method
     */
    public String getName() {
        return name;
    }

    /**
     * @param name nama method
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Tambah parameter harus gunakan method ini.
     *
     * @param o parameter yang digunakan untuk panggil method
     */
    public void addParameter(Object o) {
        if (parameters == null) {
            parameters = new Parameters();
        }
        // TODO: 02/11/17 tanpa konversi ke String
//        String json = JsonUtil.gson.toJson(o);
        parameters.add(o);
    }

    public void setParameters(Object... objects) {
        if (parameters == null) {
            parameters = new Parameters();
        } else {
            parameters.clear();
        }

        for (Object o : objects) {
            parameters.add(o);
        }
    }

    /**
     * @return parameter atau null
     */
    public Parameters getParameters() {
        return parameters;
    }

}
