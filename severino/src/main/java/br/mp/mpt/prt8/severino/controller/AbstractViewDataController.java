package br.mp.mpt.prt8.severino.controller;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonView;

import br.mp.mpt.prt8.severino.entity.IEntity;
import br.mp.mpt.prt8.severino.entity.Usuario;
import br.mp.mpt.prt8.severino.mediator.AbstractMediator;
import br.mp.mpt.prt8.severino.utils.DataTableUtils;

/**
 * Classe base para controllers que precisam somente de visualizar registros.
 * 
 * @author sergio.eoliveira
 *
 * @param <T>
 * @param <ID>
 */
public abstract class AbstractViewDataController<T extends IEntity<ID>, ID extends Serializable> {

	private final Class<T> entityClass;

	/**
	 * Construtor.
	 */
	public AbstractViewDataController() {
		entityClass = retrieveEntityClass();
	}

	/**
	 * Recuperar a classe informada no parâmetro genérico T
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Class<T> retrieveEntityClass() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		Type[] genericTypes = genericSuperclass.getActualTypeArguments();
		for (Type type : genericTypes) {
			Class<?> clazzType = (Class<?>) type;
			String packageNameEntidades = ClassUtils.getPackageName(Usuario.class);
			if (Objects.equals(packageNameEntidades, ClassUtils.getPackageName(clazzType))) {
				return (Class<T>) clazzType;
			}
		}
		return null;
	}

	/**
	 * Nome do model de acordo com o nome da classe.
	 * 
	 * @return
	 */
	protected final String getModelName() {
		return entityClass.getSimpleName().toLowerCase();
	}

	/**
	 * Dever retornar o mediator pertinente.
	 * 
	 * @return
	 */
	protected abstract AbstractMediator<T, ID> getMediatorBean();

	/**
	 * Criar uma nova entidade.
	 * 
	 * @return
	 */
	protected T getNewEntity() {
		return BeanUtils.instantiate(entityClass);
	}

	/**
	 * Adicionar coleções na view quando necessário.
	 * 
	 * @param mav
	 */
	protected void addCollections(ModelAndView mav) {
		// implementar se necessário.
	}

	/**
	 * Redirecionar para listagem de registros.
	 * 
	 * @return
	 */
	protected final String redirectToListar() {
		return "redirect:/" + getModelName() + "/registros";
	}

	/**
	 * Apresentar página de listagem de registros.
	 * 
	 * @return
	 */
	@GetMapping("/registros")
	public String listarPage() {
		return getModelName() + "-listar";
	}

	/**
	 * Página inicial de cadastro ou alteração de registros.
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping({ "/{id:.+}", "/" })
	public ModelAndView inicio(@PathVariable Optional<ID> id, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView(getModelName());
		T entity = null;
		if ((entity = getMediatorBean().findOne(id)) != null) {
			modelAndView.addObject(getModelName(), entity);
		} else {
			entity = getNewEntity();
			modelAndView.addObject(getModelName(), entity);
		}

		addCollections(modelAndView);
		return modelAndView;
	}

	/**
	 * Listar registros.
	 * 
	 * @param dataTablesInput
	 * @return
	 */
	@JsonView(DataTablesOutput.View.class)
	@PostMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DataTablesOutput<T> listar(@Valid @RequestBody DataTablesInput dataTablesInput) {
		Page<T> result = getMediatorBean().find(dataTablesInput);
		return DataTableUtils.toOutput(result, dataTablesInput);
	}

}
