package com.tj.ythu.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

public class UserInterceptor implements HandlerInterceptor {

	/**
	 * afterCompletion：在action返回视图后执行。
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception exception)
			throws Exception {

	}

	/**
	 * postHandle：在执行action里面的逻辑后返回视图之前执行。
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2, ModelAndView view)
			throws Exception {

	}

	/**
	 * preHandle：在执行action里面的处理逻辑之前执行，它返回的是boolean，
	 * 这里如果我们返回true在接着执行postHandle和afterCompletion，如果我们返回false则中断执行。
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		System.out.println(request.getSession().getId() + "----"
				+ request.getLocalAddr() + "--" + request.getServerPort());
		// java.lang.annotation.Annotation接口：这个接口是所有Annotation类型的父接口
		// java.lang.reflect.AnnotatedElement接口：该接口代表程序中可以被注解的程序元素

		// handler.getClass().getAnnotations();
		// 返回该程序元素上存在的所有注解。

		// handler.getClass().getAnnotation(LoginAnnotation.class);
		// 返回该程序元素上存在的指定类型的注解。

		// handler.getClass().isAnnotation()
		// 判断该元素是否是注解。

		// handler.getClass().getDeclaredAnnotations();
		// 返回直接存在于此元素上的所有注解。

		// handler.getClass().isAnnotationPresent(LoginAnnotation.class)
		// 判断该程序元素上是否存在指定类型的注解，如果存在则返回true，否则返回false。
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			LoginAnnotation la = ((HandlerMethod) handler)
					.getMethodAnnotation(LoginAnnotation.class);
			if (la != null) {
				if (la.isNeed()) {
					HttpSession session = request.getSession();
					Object userInfo = session.getAttribute("user_info");
					if (userInfo == null) {
						FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
				        FlashMapManager flashMapManager = RequestContextUtils.getFlashMapManager(request);
				        flashMap.put("errorMsg", "请重新登录");
				        flashMapManager.saveOutputFlashMap(flashMap, request, response);
				        response.sendRedirect("index");
						return false;
					}
				}
			}
		}

		return true;
	}
}
