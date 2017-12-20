package com.github.jajanjawa.juwet;

import com.github.jajanjawa.juwet.util.JsonUtil;
import com.google.gson.JsonArray;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Simpan Method dari class modul atau listener.
 */
public class JuwetExecutor {

    private Object owner;
    private Map<String, Method> methodMap;

    public JuwetExecutor(Object owner) {
        this.owner = owner;
        methodMap = new HashMap<>();

        mapMethod(owner.getClass());
    }

    private void mapMethod(Class clazz) {
        for (Method method : clazz.getMethods()) {
            methodMap.put(method.getName(), method);
        }
    }

    public Method getMethod(String name) throws NoSuchMethodException {
        Method method = methodMap.get(name);
        if (method == null) {
            throw new NoSuchMethodException("tidak menemukan method: " + method);
        }
        return method;
    }

    private Object[] createParameters(Method method, JsonArray data) {
        Type[] parameterTypes = method.getGenericParameterTypes();
        if (parameterTypes.length == 0) {
            return null; // method tidak punya parameter
        }

        int dataSize = data.size();

        if (dataSize != parameterTypes.length) {
            String msg = String.format("Method %s punya parameter %d, tapi diberi %d",
                    method.getName(), parameterTypes.length, dataSize);
            throw new IllegalArgumentException(msg);
        }

        Object[] parameters = new Object[dataSize];
        for (int i = 0; i < parameterTypes.length; i++) {
            parameters[i] = JsonUtil.gson.fromJson(data.get(i), parameterTypes[i]);
        }
        return parameters;
    }

    public Object run(String method, JsonArray parameters) throws Exception {
        Method m = getMethod(method);
        Object[] args = createParameters(m, parameters);

        return m.invoke(owner, args);
    }

}
