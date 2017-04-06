package br.mp.mpt.prt8.severino.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.mp.mpt.prt8.severino.entity.Empresa;
import br.mp.mpt.prt8.severino.mediator.EmpresaMediator;
import br.mp.mpt.prt8.severino.utils.NegocioException;

/**
 * Teste da classe empresa.
 * 
 * @author sergio.eoliveira
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class EmpresaControllerTest {
	@Mock
	private EmpresaController empresaControllerMock;

	@Mock
	private EmpresaMediator empresaMediatorMock;

	@Mock
	RedirectAttributes redirectAttributesMock;

	@Mock
	BindingResult bindingResultMock;

	@SuppressWarnings("unchecked")
	@Test
	public void testSalvarNegocioException() throws Exception {
		Mockito.when(empresaControllerMock.getMediatorBean()).thenReturn(empresaMediatorMock);
		Mockito.when(empresaControllerMock.getModelName()).thenReturn("empresa");
		Empresa entity = new Empresa();
		Mockito.when(empresaMediatorMock.save(entity)).thenThrow(NegocioException.class);

		Mockito.when(empresaControllerMock.salvar(entity, bindingResultMock, redirectAttributesMock)).thenCallRealMethod();

		empresaControllerMock.salvar(entity, bindingResultMock, redirectAttributesMock);

	}

	@Test
	public void testSalvar() throws Exception {

		Mockito.when(empresaControllerMock.getMediatorBean()).thenReturn(empresaMediatorMock);
		Mockito.when(empresaControllerMock.getModelName()).thenReturn("empresa");
		Empresa entity = new Empresa();

		Mockito.when(empresaControllerMock.salvar(entity, bindingResultMock, redirectAttributesMock)).thenCallRealMethod();
		Mockito.when(empresaControllerMock.redirectAposGravar(entity)).thenCallRealMethod();

		empresaControllerMock.salvar(entity, bindingResultMock, redirectAttributesMock);
		Mockito.verify(empresaMediatorMock, Mockito.only()).save(entity);
	}

	@Test
	public void testGetMediatorBean() throws Exception {
		Mockito.when(empresaControllerMock.getMediatorBean()).thenCallRealMethod();
		assertNull(empresaControllerMock.getMediatorBean());
	}

	@Test
	public void testListarPorNome() throws Exception {
		String string = "qualquer";
		Mockito.when(empresaControllerMock.listarPorNome(string)).thenCallRealMethod();
		Mockito.when(empresaControllerMock.getMediatorBean()).thenReturn(empresaMediatorMock);
		Mockito.when(empresaMediatorMock.findByParteNome(string)).thenReturn(Collections.emptyList());
		empresaControllerMock.listarPorNome(string);
		Mockito.verify(empresaMediatorMock, Mockito.only()).findByParteNome(string);
	}

	@Test
	public void testSalvarValidation() throws Exception {
		Mockito.when(empresaControllerMock.getMediatorBean()).thenReturn(empresaMediatorMock);
		Mockito.when(empresaControllerMock.getModelName()).thenReturn("empresa");

		Empresa entity = new Empresa();
		Mockito.when(empresaControllerMock.salvar(entity, bindingResultMock, redirectAttributesMock)).thenCallRealMethod();
		Mockito.when(bindingResultMock.hasErrors()).thenReturn(true);

		empresaControllerMock.salvar(entity, bindingResultMock, redirectAttributesMock);

	}

	@Test
	public void testDelete() {
		Mockito.when(empresaControllerMock.getMediatorBean()).thenReturn(empresaMediatorMock);
		Mockito.when(empresaControllerMock.getModelName()).thenReturn("empresa");
		Mockito.when(empresaControllerMock.delete(Mockito.anyInt(), Mockito.any(RedirectAttributes.class))).thenCallRealMethod();

		String keyMensagem = "Registro " + 1 + " removido com sucesso.";
		Mockito.when(redirectAttributesMock.addFlashAttribute(AbstractFullCrudController.KEY_MSG, keyMensagem)).thenReturn(redirectAttributesMock);
		empresaControllerMock.delete(1, redirectAttributesMock);
		Mockito.verify(redirectAttributesMock, Mockito.only()).addFlashAttribute(Mockito.eq(AbstractFullCrudController.KEY_MSG), Mockito.eq(keyMensagem));
		Mockito.verify(empresaMediatorMock, Mockito.only()).apagar(Mockito.eq(1));
	}

	@Test
	public void testDeleteNegocioException() {
		Mockito.when(empresaControllerMock.getMediatorBean()).thenReturn(empresaMediatorMock);
		Mockito.when(empresaControllerMock.getModelName()).thenReturn("empresa");
		Mockito.when(empresaControllerMock.delete(Mockito.anyInt(), Mockito.any(RedirectAttributes.class))).thenCallRealMethod();
		Mockito.doThrow(NegocioException.class).when(empresaMediatorMock).apagar(Mockito.anyInt());

		Mockito.when(redirectAttributesMock.addFlashAttribute(AbstractFullCrudController.KEY_MSG, null)).thenReturn(redirectAttributesMock);
		empresaControllerMock.delete(1, redirectAttributesMock);
		Mockito.verify(redirectAttributesMock, Mockito.only()).addFlashAttribute(Mockito.eq(AbstractFullCrudController.KEY_ERROR), Mockito.anyString());
	}

	@Test
	public void testInicioEdit() throws Exception {
		Optional<Integer> param = Optional.of(1);
		Mockito.when(empresaControllerMock.inicio(param)).thenCallRealMethod();
		Mockito.when(empresaControllerMock.getModelName()).thenReturn("empresa");
		Mockito.when(empresaControllerMock.getMediatorBean()).thenReturn(empresaMediatorMock);
		Empresa value = new Empresa();
		Mockito.when(empresaMediatorMock.findOne(param)).thenReturn(value);
		empresaControllerMock.inicio(param);
	}

	@Test
	public void testInicioNew() throws Exception {
		Optional<Integer> param = Optional.of(1);
		Mockito.when(empresaControllerMock.inicio(param)).thenCallRealMethod();
		Mockito.when(empresaControllerMock.getModelName()).thenReturn("empresa");
		Mockito.when(empresaControllerMock.getMediatorBean()).thenReturn(empresaMediatorMock);
		Mockito.when(empresaMediatorMock.findOne(param)).thenReturn(null);
		empresaControllerMock.inicio(param);
	}

	@Test
	public void testListarPage() throws Exception {
		Mockito.when(empresaControllerMock.listarPage()).thenCallRealMethod();
		Mockito.when(empresaControllerMock.getModelName()).thenReturn("empresa");
		assertEquals("empresa-listar", empresaControllerMock.listarPage());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testListar() throws Exception {
		DataTablesInput dataTablesInput = new DataTablesInput();
		dataTablesInput.setStart(0);
		dataTablesInput.setLength(4);
		Mockito.when(empresaControllerMock.listar(dataTablesInput)).thenCallRealMethod();
		Mockito.when(empresaControllerMock.getMediatorBean()).thenReturn(empresaMediatorMock);
		Page<Empresa> pageMock = Mockito.mock(Page.class);
		Mockito.when(empresaMediatorMock.find(dataTablesInput)).thenReturn(pageMock);
		DataTablesOutput<Empresa> result = empresaControllerMock.listar(dataTablesInput);
		assertNotNull(result);
	}

	@Test
	public void testGetNewEntity() throws Exception {
		EmpresaController empresaController = new EmpresaController();
		assertTrue(empresaController.getNewEntity() instanceof Empresa);
	}

	@Test
	public void testGetModelName() throws Exception {
		EmpresaController empresaController = new EmpresaController();
		assertEquals("empresa", empresaController.getModelName());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testAddCollections() throws Exception {
		EmpresaController empresaController = new EmpresaController();
		ModelAndView mav = Mockito.mock(ModelAndView.class);
		empresaController.addCollections(mav, new Empresa());
		Mockito.verify(mav, Mockito.never()).addObject(Mockito.anyString());
		Mockito.verify(mav, Mockito.never()).addAllObjects(Mockito.anyMap());
		Mockito.verify(mav, Mockito.never()).addObject(Mockito.anyString(), Mockito.any());
	}
}
