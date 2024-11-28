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
        Class<?> clazz = Class.forName(classPath);
        Random random = new Random();
        return clazz.getConstructor(int.class, int.class, int.class, int.class)
                .newInstance(random.nextInt(100), random.nextInt(100), random.nextInt(100), random.nextInt(100));
    }

    private String[] getVariableNames(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        Field[] fields = object.getClass().getDeclaredFields();
        String[] variableNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            variableNames[i] = fields[i].getName();
        }
        return variableNames;
    }

    private void setVariableValue(Object object, String variableName, Object newValue)
            throws NoSuchFieldException, IllegalAccessException {
        if (object == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        if (variableName == null || variableName.isEmpty()) {
            throw new IllegalArgumentException("Variable name cannot be null or empty");
        }
        Field field = object.getClass().getDeclaredField(variableName);
        field.setAccessible(true);
        Class<?> type = field.getType();
        if (newValue == null) {
            if (type.isPrimitive()) {
                throw new IllegalArgumentException("Cannot set null value to primitive type");
            } else {
                field.set(object, null);
                return;
            }
        }
        if (type.isAssignableFrom(newValue.getClass())) {
            field.set(object, newValue);
        } else if (type == int.class) {
            field.setInt(object, (Integer) newValue);
        } else if (type == double.class) {
            field.setDouble(object, (Double) newValue);
        } else if (type == boolean.class) {
            field.setBoolean(object, (Boolean) newValue);
        } else if (type == long.class) {
            field.setLong(object, (Long) newValue);
        } else if (type == float.class) {
            field.setFloat(object, (Float) newValue);
        } else if (type == short.class) {
            field.setShort(object, (Short) newValue);
        } else if (type == byte.class) {
            field.setByte(object, (Byte) newValue);
        } else if (type == char.class) {
            field.setChar(object, (Character) newValue);
        } else {
            throw new IllegalArgumentException("Incompatible types");
        }
    }

    private List<String> getMethodNames(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        Method[] methods = object.getClass().getDeclaredMethods();
        List<String> methodNames = new ArrayList<>();
        for (Method method : methods) {
            methodNames.add(method.getName());
        }
        return methodNames;
    }

    private Object invokeMethod(Object object, String methodName, Object... parameters)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if (object == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        if (methodName == null || methodName.isEmpty()) {
            throw new IllegalArgumentException("Method name cannot be null or empty");
        }
        Method[] methods = object.getClass().getDeclaredMethods();
        Method methodToInvoke = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName) && method.getParameterCount() == parameters.length) {
                Class<?>[] methodParameterTypes = method.getParameterTypes();
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
                    methodToInvoke = method;
                }
            }
        }
        if (methodToInvoke == null) {
            throw new NoSuchMethodException("No suitable method");
        }
        methodToInvoke.setAccessible(true);
        return methodToInvoke.invoke(object, parameters);
    }


    private  List<String> getInterfacesAndAbstractClasses(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class cannot be null");
        }
        List<String> interfacesAndAbstractClasses = new ArrayList<>();
        Class<?>[] interfaces = clazz.getInterfaces();
        for (Class<?> i : interfaces) {
            interfacesAndAbstractClasses.add(i.getName());
        }
        while (clazz != null) {
            if (Modifier.isAbstract(clazz.getModifiers())) {
                interfacesAndAbstractClasses.add(clazz.getName());
            }
            clazz = clazz.getSuperclass();
        }
        return interfacesAndAbstractClasses;
    }

}
