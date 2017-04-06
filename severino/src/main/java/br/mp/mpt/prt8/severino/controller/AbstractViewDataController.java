package br.mp.mpt.prt8.severino.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ResolvableType;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonView;

import br.mp.mpt.prt8.severino.entity.AbstractEntity;
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
public abstract class AbstractViewDataController<T extends AbstractEntity<ID>, ID extends Serializable> {

	private final Class<T> entityClass;

	/**
	 * Construtor.
	 */
	@SuppressWarnings("unchecked")
	public AbstractViewDataController() {
		ResolvableType resolvableType = ResolvableType.forClass(getClass()).as(getClass().getSuperclass());
		Optional<ResolvableType> first = Arrays.stream(resolvableType.getGenerics()).filter(r -> AbstractEntity.class.isAssignableFrom(r.getRawClass())).findFirst();
		entityClass = (Class<T>) first.get().getRawClass();
	}

	/**
	 * Nome do model de acordo com o nome da classe.
	 * 
	 * @return
	 */
	protected String getModelName() {
		return StringUtils.uncapitalize(entityClass.getSimpleName());
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
	 * @param entity
	 */
	protected void addCollections(ModelAndView mav, T entity) {
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
	public ModelAndView inicio(@PathVariable Optional<ID> id) {
		ModelAndView modelAndView = new ModelAndView(getModelName());
		T entity = null;
		if ((entity = getMediatorBean().findOne(id)) != null) {
			modelAndView.addObject(getModelName(), entity);
		} else {
			entity = getNewEntity();
			modelAndView.addObject(getModelName(), entity);
		}

		addCollections(modelAndView, entity);
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
