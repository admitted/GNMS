package me.cuijing.util;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 参数封装Map [ 封装了 request 所有参数的 map ]
 * @author CuiJing 
 * @date   2017/3/20
 */
public class PageData extends HashMap implements Map{
	
	private static final long serialVersionUID = 1L;
	
	Map map = null;

	HttpServletRequest request;

	public PageData(HttpServletRequest request){
		this.request     = request;
		// request.getParameterMap 将所有参数 封装成 map
		Map properties   = request.getParameterMap();
		// 参数整合封装为 map
		Map returnMap    = new HashMap();
		Iterator entries = properties.entrySet().iterator(); 
		Map.Entry entry; 
		String name  = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next(); 
			name = (String) entry.getKey(); 
			Object valueObj = entry.getValue(); 
			if(null == valueObj){ 
				value = "";
			}else if(valueObj instanceof String[]){ 
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){ 
					 value = values[i] + ",";
				}
				value = value.substring(0, value.length()-1); 
			}else{
				value = valueObj.toString(); 
			}
			returnMap.put(name, value); 
		}
		map = returnMap;
	}
	
	public PageData() {
		map = new HashMap();
	}

	/**
	 * 获取 request参数为 key 的 value
	 * @param key
	 * @return
	 */
	@Override
	public Object get(Object key) {
		Object obj = null;
		if(map.get(key) instanceof Object[]) {
			Object[] arr = (Object[])map.get(key);
			obj = request == null ? arr:(request.getParameter((String)key) == null ? arr:arr[0]);
		} else {
			obj = map.get(key);
		}
		return obj;
	}
	
	public String getString(Object key) {
		// Object get(Object key)
		return (String)get(key);
	}

	/**
	 * 封装map 添加键值对
	 * @param key
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object put(Object key, Object value) {
		return map.put(key, value);
	}

	/**
	 * 封装map 获取键值对,并从map中删除
	 * @param key
	 * @return
	 */
	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}

	/**
	 * 封装map 清空map
	 */
	public void clear() {
		map.clear();
	}

	/**
	 * 封装map 是否包含 key 键
	 * @param key
	 * @return
	 */
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return map.containsKey(key);
	}

	/**
	 * 封装map 是否包含 value 值
	 * @param value
	 * @return
	 */
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return map.containsValue(value);
	}

	public Set entrySet() {
		// TODO Auto-generated method stub
		return map.entrySet();
	}

	/**
	 * 封装map 是否为空
	 * @return
	 */
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return map.isEmpty();
	}

	public Set keySet() {
		// TODO Auto-generated method stub
		return map.keySet();
	}

	@SuppressWarnings("unchecked")
	public void putAll(Map t) {
		// TODO Auto-generated method stub
		map.putAll(t);
	}

	/**
	 * 封装map 有多少对值
	 * @return
	 */
	public int size() {
		// TODO Auto-generated method stub
		return map.size();
	}

	public Collection values() {
		// TODO Auto-generated method stub
		return map.values();
	}
	
}
