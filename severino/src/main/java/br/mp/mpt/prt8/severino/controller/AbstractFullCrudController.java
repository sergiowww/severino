package br.mp.mpt.prt8.severino.controller;

import java.io.Serializable;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.mp.mpt.prt8.severino.entity.IEntity;
import br.mp.mpt.prt8.severino.utils.NegocioException;
import br.mp.mpt.prt8.severino.utils.Roles;

/**
 * Classe base para os controllers com operações de CreateReadUpdateUpdateDelete
 * - CRUD.
 * 
 * @author sergio.eoliveira
 *
 * @param <T>
 * @param <ID>
 */
public abstract class AbstractFullCrudController<T extends IEntity<ID>, ID extends Serializable> extends AbstractViewDataController<T, ID> {
	private static final String KEY_ERROR = "error";
	private static final String KEY_MSG = "msg";

	/**
	 * Apagar um registro.
	 * 
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@GetMapping("/delete/{id}")
	@PreAuthorize(Roles.PADRAO)
	public String delete(@PathVariable ID id, RedirectAttributes redirectAttributes) {
		try {
			getMediatorBean().apagar(id);
			redirectAttributes.addFlashAttribute(KEY_MSG, "Registro " + id + " removido com sucesso.");
			return redirectToListar();
		} catch (NegocioException e) {
			redirectAttributes.addFlashAttribute(KEY_ERROR, e.getMessage());
			return "redirect:/" + getModelName() + "/" + id;
		}
	}

	/**
	 * Salvar um registro.
	 * 
	 * @param entity
	 * @param result
	 * @return
	 */
	@PostMapping("")
	@PreAuthorize(Roles.PADRAO)
	public ModelAndView salvar(@Valid T entity, BindingResult result, RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView(getModelName());
		if (!result.hasErrors()) {
			try {
				getMediatorBean().save(entity);
				redirectAttributes.addFlashAttribute(KEY_MSG, "Registro " + entity.getId() + " gravado com sucesso!");
				mav.setViewName(redirectAposGravar(entity));
				return mav;
			} catch (NegocioException e) {
				addCollections(mav);
				return mav.addObject(KEY_ERROR, e.getMessage());
			}
		}
		addCollections(mav);
		return mav;

	}

	/**
	 * Redirecionar o registro após a gravação do mesmo.
	 * 
	 * @param entity
	 * @return
	 */
	protected String redirectAposGravar(T entity) {
		return redirectToListar();
	}
}
