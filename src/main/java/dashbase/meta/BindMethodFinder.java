package dashbase.meta;

import dashbase.ast.AstQueryProgram;
import dashbase.ast.array.AstArrayProperty;
import dashbase.ast.object.AstObjectProperty;
import dashbase.ast.primary.AstPrimaryProperty;
import dashbase.exception.BindException;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BindMethodFinder {

    private static final int BRIDGE = 0x40;
    private static final int SYNTHETIC = 0x1000;
    private static final int MODIFIERS_IGNORE = Modifier.ABSTRACT | Modifier.STATIC | BRIDGE | SYNTHETIC;

    private static final Map<Class<?>, List<BindMethod>> METHOD_CACHE = new HashMap<>();

    static class BindStates {
        final List<BindMethod> subscriberMethods = new ArrayList<>();
        final Class<?> bindClass;

        BindStates(Class<?> bindClass) {
            this.bindClass = bindClass;
        }

        boolean checkAdd(Class<?> nodeType, GrammarMode mode) {
            if (mode == GrammarMode.OBJECT) {
                return nodeType.isAssignableFrom(AstObjectProperty.class);
            } else if (mode == GrammarMode.ARRAY) {
                return nodeType.isAssignableFrom(AstArrayProperty.class);
            } else if (mode == GrammarMode.PRIMARY) {
                return nodeType.isAssignableFrom(AstPrimaryProperty.class);
            } else if (mode == GrammarMode.WRAPPER) {
                return nodeType.isAssignableFrom(AstQueryProgram.class);
            }

            return false;
        }
    }

    public List<BindMethod> findBindMethods(Class<?> subscriberClass) {
        List<BindMethod> bindMethods = METHOD_CACHE.get(subscriberClass);

        if (bindMethods != null) {
            return bindMethods;
        }

        BindStates state = new BindStates(subscriberClass);
        findUsingReflectionInSingleClass(state);

        bindMethods = state.subscriberMethods;

        if (bindMethods.isEmpty()) {
            throw new BindException("Bind " + subscriberClass
                    + " and its super classes have no public methods with the @Bind annotation");
        } else {
            METHOD_CACHE.put(subscriberClass, bindMethods);
            return bindMethods;
        }

    }

    private void findUsingReflectionInSingleClass(BindStates findState) {
        Method[] methods;
        try {
            // This is faster than getMethods
            methods = findState.bindClass.getDeclaredMethods();
        } catch (Throwable th) {
            methods = findState.bindClass.getMethods();
        }

        for (Method method : methods) {
            int modifiers = method.getModifiers();
            if ((modifiers & Modifier.PUBLIC) != 0 && (modifiers & MODIFIERS_IGNORE) == 0) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 2) {
                    Bind bindAnnotation = method.getAnnotation(Bind.class);
                    if (bindAnnotation != null) {
                        Class<?> nodeType = parameterTypes[0];
                        GrammarMode mode = bindAnnotation.mode();

                        if (findState.checkAdd(nodeType, mode)) {
                            findState.subscriberMethods.add(new BindMethod(
                                    bindAnnotation.name(),
                                    bindAnnotation.prefix(),
                                    mode,
                                    method
                            ));
                        }
                    }
                } else if (method.isAnnotationPresent(Bind.class)) {
                    String methodName = method.getDeclaringClass().getName() + "." + method.getName();
                    throw new BindException("@Bind method " + methodName +
                            "must have exactly 2 parameter but has " + parameterTypes.length);
                }
            } else if (method.isAnnotationPresent(Bind.class)) {
                String methodName = method.getDeclaringClass().getName() + "." + method.getName();
                throw new BindException(methodName +
                        " is a illegal @Bind method: must be public, non-static, and non-abstract");
            }
        }


    }

}
