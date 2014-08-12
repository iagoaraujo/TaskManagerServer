package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity(name="task")
public class Task {

	@Id
	@SequenceGenerator(name = "TASK_SEQUENCE", sequenceName = "TASK_SEQUENCE", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@Column
	@NotNull
	private String tarefa;
	
	public Task() {
	}
	
	public Task(String tarefa) {
		this.tarefa = tarefa;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTarefa() {
		return tarefa;
	}
	public void setTarefa(String tarefa) {
		this.tarefa = tarefa;
	}
	
}
