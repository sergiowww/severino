package br.mp.mpt.prt8.severino.controller;

import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.mp.mpt.prt8.severino.entity.Motorista;
import br.mp.mpt.prt8.severino.entity.Viagem;
import br.mp.mpt.prt8.severino.mediator.AbstractExampleMediator;
import br.mp.mpt.prt8.severino.mediator.ControleMotoristaMediator;
import br.mp.mpt.prt8.severino.mediator.VeiculoMediator;
import br.mp.mpt.prt8.severino.mediator.ViagemMediator;
import br.mp.mpt.prt8.severino.utils.NegocioException;
import br.mp.mpt.prt8.severino.utils.Roles;
import br.mp.mpt.prt8.severino.validators.CadastrarVeiculo;
import br.mp.mpt.prt8.severino.validators.CadastrarViagem;

/**
 * Controlador de operações da entidade.
 * 
 * @author sergio.eoliveira
 *
 */
@Controller
@RequestMapping("/viagem")
public class ViagemController extends AbstractFullCrudController<Viagem, Integer> {

	@Autowired
	private ViagemMediator viagemMediator;

	@Autowired
	private VeiculoMediator veiculoMediator;

	@Autowired
	private SmartValidator smartValidator;

	@Autowired
	private ControleMotoristaMediator controleMotoristaMediator;

	@Override
	protected AbstractExampleMediator<Viagem, Integer> getMediatorBean() {
		return viagemMediator;
	}

	@Override
	protected void addCollections(ModelAndView mav, Viagem entity) {
		Integer idMotorista = null;
		Motorista motorista = entity.getMotorista();
		if (motorista != null) {
			idMotorista = motorista.getId();
		}
		mav.addObject("motoristas", controleMotoristaMediator.findMotoristasDisponiveis(idMotorista));
		mav.addObject("controleMotoristas", controleMotoristaMediator.findDisponiveis());
		mav.addObject("viaturas", veiculoMediator.findViaturas());
		mav.addObject("viagensSemBaixa", viagemMediator.findViagensSemBaixa());
	}

	@Override
	protected String redirectAposGravar(Viagem entity) {
		return "redirect:/viagem/" + entity.getId();
	}

	@PostMapping("")
	@PreAuthorize(Roles.PADRAO)
	@Override
	public ModelAndView salvar(@ModelAttribute("viagem") Viagem viagem, BindingResult result, RedirectAttributes redirectAttributes) {
		if (!CollectionUtils.isEmpty(viagem.getPassageiros())) {
			viagem.getPassageiros().removeIf(p -> StringUtils.isEmpty(p.getNome()));
		}
		if (viagem.isGravarVeiculo()) {
			smartValidator.validate(viagem, result, CadastrarViagem.class, CadastrarVeiculo.class);
		} else {
			smartValidator.validate(viagem, result, CadastrarViagem.class);
		}
		return super.salvar(viagem, result, redirectAttributes);
	}

	/**
	 * Atualizar disponibilidade do motorista identificado pelo identificador.
	 * 
	 * @param idMotorista
	 * @param horario
	 * @return
	 */
	@PostMapping("/atualizarDisponibilidade")
	@PreAuthorize(Roles.PADRAO)
	public String atualizarDisponibilidade(@Valid @ModelAttribute DisponibilidadeMotorista disponibilidade, RedirectAttributes redirectAttributes, BindingResult result) {
		if (!result.hasErrors()) {
			Date horario = disponibilidade.getHorario();
			Integer idMotorista = disponibilidade.getIdMotorista();
			try {
				controleMotoristaMediator.atualizarDisponibilidade(idMotorista, horario);
			} catch (ConstraintViolationException e) {
				String messages = e.getConstraintViolations().stream().map(c -> c.getMessage()).collect(Collectors.joining(","));
				redirectAttributes.addFlashAttribute(KEY_ERROR, messages);
			} catch (NegocioException e) {
				redirectAttributes.addFlashAttribute(KEY_ERROR, e.getMessage());
			}
		}
		return "redirect:/viagem/" + Objects.toString(disponibilidade.getIdViagem(), "");
	}

	public static class DisponibilidadeMotorista {
		@NotNull
		private Integer idMotorista;
		private Integer idViagem;
		@DateTimeFormat(pattern = "HH:mm")
		private Date horario;

		/**
		 * @return the idMotorista
		 */
		public Integer getIdMotorista() {
			return idMotorista;
		}

		/**
		 * @param idMotorista
		 *            the idMotorista to set
		 */
		public void setIdMotorista(Integer idMotorista) {
			this.idMotorista = idMotorista;
		}

		/**
		 * @return the idViagem
		 */
		public Integer getIdViagem() {
			return idViagem;
		}

		/**
		 * @param idViagem
		 *            the idViagem to set
		 */
		public void setIdViagem(Integer idViagem) {
			this.idViagem = idViagem;
		}

		/**
		 * @return the horario
		 */
		public Date getHorario() {
			return horario;
		}

		/**
		 * @param horario
		 *            the horario to set
		 */
		public void setHorario(Date horario) {
			this.horario = horario;
		}
	}

}
