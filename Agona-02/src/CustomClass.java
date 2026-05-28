import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomClass {
    public int attribute1;
    public int attribute2;
    private int attribute3;
    private int attribute4;

    public CustomClass() {
        this.attribute1 = 1;
        this.attribute2 = 2;
        this.attribute3 = 3;
        this.attribute4 = 4;
    }

    public CustomClass(int attribute1, int attribute2, int attribute3, int attribute4) {
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        this.attribute3 = attribute3;
        this.attribute4 = attribute4;
    }

    private Object createInstance(String classPath)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (classPath == null || classPath.isEmpty()) {
            throw new IllegalArgumentException("Class path cannot be null or empty");
        }
        Class<?> actualClass = Class.forName(classPath);
        Random random = new Random();
        return actualClass.getConstructor(int.class, int.class, int.class, int.class)
                .newInstance(random.nextInt(100), random.nextInt(100), random.nextInt(100), random.nextInt(100));
    }

    private String[] getVariableNames(Object targetObject) {
        if (targetObject == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        Field[] declaredFields = targetObject.getClass().getDeclaredFields();
        String[] variableNames = new String[declaredFields.length];
        for (int i = 0; i < declaredFields.length; i++) {
            variableNames[i] = declaredFields[i].getName();
        }
        return variableNames;
    }

    private void setVariableValue(Object targetObject, String variableName, Object newValue)
            throws NoSuchFieldException, IllegalAccessException {
        if (targetObject == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        if (variableName == null || variableName.isEmpty()) {
            throw new IllegalArgumentException("Variable name cannot be null or empty");
        }
        Field declaredFields = targetObject.getClass().getDeclaredField(variableName);
        declaredFields.setAccessible(true);
        declaredFields.set(targetObject, declaredFields.getType().cast(newValue));
    }

    private List<String> getMethodNames(Object targetObject) {
        if (targetObject == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        Method[] declaredMethods = targetObject.getClass().getDeclaredMethods();
        List<String> methodNames = new ArrayList<>();
        for (Method declaredMethod : declaredMethods) {
            methodNames.add(declaredMethod.getName());
        }
        return methodNames;
    }

    private Object invokeMethod(Object targetObject, String methodName, Object... parameters)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if (targetObject == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        if (methodName == null || methodName.isEmpty()) {
            throw new IllegalArgumentException("Method name cannot be null or empty");
        }
        Method[] declaredMethods = targetObject.getClass().getDeclaredMethods();
        Method methodToInvoke = null;
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().equals(methodName) && declaredMethod.getParameterCount() == parameters.length) {
                Class<?>[] methodParameterTypes = declaredMethod.getParameterTypes();
                boolean isMatch = true;
                for (int i = 0; i < methodParameterTypes.length; i++) {
                    if (parameters[i] == null) {
                        if (methodParameterTypes[i].isPrimitive()) {
                            isMatch = false;
                            break;
                        }
                    } else if (!methodParameterTypes[i].isAssignableFrom(parameters[i].getClass())) {
                        isMatch = false;
                        break;
                    }
                }
                if (isMatch) {
                    if (methodToInvoke != null) {
                         throw new NoSuchMethodException("Ambiguous method");
                    }
                    methodToInvoke = declaredMethod;
                }
            }
        }
        if (methodToInvoke == null) {
            throw new NoSuchMethodException("No suitable method");
        }
        methodToInvoke.setAccessible(true);
        return methodToInvoke.invoke(targetObject, parameters);
    }


    private List<String> getInterfacesAndAbstractClasses(Class<?> actualClass) {
        if (actualClass== null) {
            throw new IllegalArgumentException("Class cannot be null");
        }
        List<String> interfacesAndAbstractClasses = new ArrayList<>();
        Class<?>[] implementedInterfaces = actualClass.getInterfaces();
        for (Class<?> interfaceClass : implementedInterfaces) {
            interfacesAndAbstractClasses.add(interfaceClass.getName());
        }
        while (actualClass != null) {
            if (Modifier.isAbstract(actualClass.getModifiers())) {
                interfacesAndAbstractClasses.add(actualClass.getName());
            }
            actualClass = actualClass.getSuperclass();
        }
        return interfacesAndAbstractClasses;
    }

}
