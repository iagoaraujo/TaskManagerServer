package controllers;

import java.util.Map;

import models.Task;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.*;

public class Application extends Controller {

	private static GenericDAO dao = new GenericDAOImpl();
	
	public static Result index() {
		return ok("HOW TO:\n"
				+ "GET		/task								return all tasks\n"
				+ "POST		/task								add a new task (use param: tarefa)\n"
				+ "HEAD		/task								sumarize data about all the tasks\n\n"
				+ "GET		/task/:id							return a specific task\n"
				+ "PUT		/task/:id							set a specific task (use param: tarefa)\n"
				+ "DELETE	/task/:id							delete a specefic task\n"
				+ "HEAD		/task/:id							sumarize the data about a task\n\n");
	}
	
	@Transactional
    public static Result getAllTasks() {
		return ok(Json.toJson(getDao().findAllByClassName("task")));
    }
	
	@Transactional
	public static Result createTask() {
		Map<String, String[]> parametros = Controller.request().body().asFormUrlEncoded();
		Task task = new Task(parametros.get("tarefa")[0]);
		getDao().merge(task);
		getDao().flush();
		return ok();
	}
	
	@Transactional
	public static Result getTaskById(Long id) {
		Task task = (Task) getDao().findByEntityId(Task.class, id);
	    if(task==null) {
	    	return badRequest("Id nao cadastrado no sistema");
	    }
	    return ok(Json.toJson(task));
	}
	
	@Transactional
	public static Result editTask(Long id) {
		Task task = (Task) getDao().findByEntityId(Task.class, id);
		if(task==null) {
	    	return badRequest("Id nao cadastrado no sistema");
	    }
		Map<String, String[]> parametros = Controller.request().body().asFormUrlEncoded();
		String msg = parametros.get("tarefa")[0];
		task.setTarefa(msg);
	    return ok();
	}

	@Transactional
	public static Result deleteTask(Long id) {
		Task task = (Task) getDao().findByEntityId(Task.class, id);
		if(task==null) {
	    	return badRequest("Id nao cadastrado no sistema");
	    }
		getDao().remove(task);
		getDao().flush();
		return ok();
	}
	
	@Transactional
	public static Result getHeadAllTasks() {
		return ok(Json.toJson(head(getDao().findAllByClassName("task"))));
	}
	
	@Transactional
	public static Result getHeadFromTask(Long id) {
		Task task = (Task) getDao().findByEntityId(Task.class, id);
	    if(task==null) {
	    	return badRequest("Id nao cadastrado no sistema");
	    }
	    return ok(Json.toJson(head(task)));
	}
	
	private static GenericDAO getDao() {
		return dao;
	}
	
	private static String head(Object obj) {
		return "Content-Type: application/json   Content-Length: "
				+ obj.toString().length();
	}
}
