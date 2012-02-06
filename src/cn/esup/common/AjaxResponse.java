package cn.esup.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springside.modules.utils.encode.JsonBinder;

import com.opensymphony.xwork2.ActionSupport;

/**
 * AjaxResponse
 * 
 * @author yangq(qi.yang.cn@gmail.com)
 */
public class AjaxResponse extends ActionSupport {

	private static final long serialVersionUID = 1L;

	/** Response json format data must be use " not ' */
	public static void ajaxResp(String json) throws IOException {
		json = null == json ? " " : json;
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");// IE6不兼容application/json格式定义
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
	}

	/** Response java object data */
	public static void ajaxResp(Object object) throws JsonGenerationException, JsonMappingException, IOException {
		object = null == object ? "" : object;
		JsonBinder binder = JsonBinder.buildNonNullBinder();
		ajaxResp(binder.toJson(object));
	}

	/** Response action result is success or false */
	public static void ajaxResp(boolean flag) throws IOException {
		if (flag) {
			ajaxResp("{\"result\":\"true\"}");
		} else {
			ajaxResp("{\"result\":\"false\"}");
		}
	}
}
