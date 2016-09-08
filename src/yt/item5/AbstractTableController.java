package yt.item5;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import yt.item5.bean.EntityInterface;
import yt.item5.service.GeneralService;

public abstract class AbstractTableController<T extends EntityInterface, PK extends Serializable> extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public final Class<T> classType;

	protected final String INSERT_OR_EDIT_PAGE;

	protected final String LIST_PAGE;

	protected GeneralService<T, PK> generalService;

	@SuppressWarnings("unchecked")
	public AbstractTableController() {
		this.classType = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.INSERT_OR_EDIT_PAGE = "/modify" + classType.getSimpleName() + ".jsp";
		this.LIST_PAGE = "/list" + classType.getSimpleName() + ".jsp";
	}

	public abstract PK parsePkFromReq(HttpServletRequest request);

	public abstract PK parsePkFromInt(int k);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		initGeneralService();
		String forward = excuteAction(request.getParameter("action"), request);
		if (forward == null) {
			response.sendRedirect("/MavenWebExercise5/index.jsp"); // set to main page
			return;
		}
		if (forward == LIST_PAGE)
			request.getRequestDispatcher(forward).forward(request, response);
		else
			responseJson(response, forward);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		initGeneralService();
		String action = request.getParameter("action");
		boolean deleteAction = isDelete(action);

		JsonElement je = new Gson().fromJson(request.getReader(), JsonElement.class);
		if (je.isJsonArray()) {
			JsonArray jsonArray = je.getAsJsonArray();
			JsonObject jsonData = null;
			for (int i = 0; i < jsonArray.size(); i++) {
				jsonData = jsonArray.get(i).getAsJsonObject();
				System.out.println(jsonData);
				T entity = buildEntityByJson(jsonData);
				generalService.deleteById(parsePkFromInt(entity.getId()));
				
			}
		} else {
			JsonObject jsonData = je.getAsJsonObject();
			System.out.println(jsonData);
			T entity = buildEntityByJson(jsonData);

			if (isCreate(entity.getId())) {
				generalService.insert(entity);
			} else if (deleteAction) {
				generalService.deleteById(parsePkFromInt(entity.getId()));
				return;
			} else {
				generalService.update(entity);
			}
			entity.setForeignClassNull();
			responseJson(response, new Gson().toJson(entity));
		}

	}

	private boolean isDelete(String action) {
		try {
			return ActionEnum.valueOf(action.toUpperCase()) == ActionEnum.DELETE;

		} catch (NullPointerException e) {
			return false;
		}
	}

	public T buildEntityByJson(JsonObject jsonData) {
		T entity = null;
		try {
			entity = classType.newInstance();
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		Field[] fields = classType.getDeclaredFields();

		for (Field field : fields) {
			try {
				if (field.getType().equals(String.class)) {//TODO stringUtils?
					field.setAccessible(true);
					field.set(entity, jsonData.get(field.getName()).getAsString());
				} else if (field.getType().equals(int.class)) {
					field.setAccessible(true);
					field.set(entity, checkString2Int(jsonData.get(field.getName()).getAsString()));
				}

			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				System.out.println(field.getName());
			}
		}
		return entity;
	}

	public String buildJsonDataList(HttpServletRequest request) {
		List<T> objList = generalService.findAll();
		for (T obj : objList) {
			obj.setForeignClassNull();
		}
		return new Gson().toJson(objList);
	}

	protected void responseJson(HttpServletResponse response, String json) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			System.out.print("Failed to send json response");
			e.printStackTrace();
		}
	}

	protected String excuteAction(String action, HttpServletRequest request) {//return json
		try {
			switch (ActionEnum.valueOf(action.toUpperCase())) {
				case DELETE:
					generalService.deleteById(parsePkFromReq(request));
					return buildJsonDataList(request);
				case INDEX:
					return LIST_PAGE;
				default:
					break;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return buildJsonDataList(request);
	}

	private boolean isCreate(int id) {
		return id == 0;
	}

	protected int checkString2Int(String s) {
		if (s != null && s.trim().length() > 0) {
			return Integer.valueOf(s);
		}
		return 0;
	}

	public GeneralService<T, PK> getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService<T, PK> generalService) {
		this.generalService = generalService;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initGeneralService() {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		this.generalService = (GeneralService) context.getBean(classType.getSimpleName().toLowerCase() + "Service");
	}
}
