package myjob.common.util;

/**
 * 反射工具
 * @author ljnan
 *2022-01-10
 */
public class ReflectUtil<T> {
	
	/**
	 * 类的全路径！！
	 * @param className
	 * @return
	 * @throws ClassNotFoundException 
	 */
	public static Class<?> getClassByUrl(String className) {		
		Class<?> c = null;
		try {
			c = (Class<?>) Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public static Object getBean(Class<?> c) throws InstantiationException, IllegalAccessException {
		return c.newInstance();
	}
}
