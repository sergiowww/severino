package br.mp.mpt.prt8.severino.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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
	@InjectMocks
	private EmpresaController empresaController;

	@Mock
	private EmpresaMediator empresaMediatorMock;

	@Mock
	private RedirectAttributes redirectAttributesMock;

	@Mock
	private BindingResult bindingResultMock;

	@Test
	public void testSalvarNegocioException() throws Exception {
		Empresa empresa = new Empresa();
		String mensagem = "negócio exceptioin";
		Mockito.when(empresaMediatorMock.save(empresa)).thenThrow(new NegocioException(mensagem));
		ModelAndView mav = empresaController.salvar(empresa, bindingResultMock, redirectAttributesMock);
		assertEquals(1, mav.getModelMap().values().stream().filter(p -> mensagem.equals(p)).count());
	}

	@Test
	public void testSalvar() throws Exception {

		Empresa entity = new Empresa();

		empresaController.salvar(entity, bindingResultMock, redirectAttributesMock);
		Mockito.verify(empresaMediatorMock, Mockito.only()).save(entity);
	}

	@Test
	public void testGetMediatorBean() throws Exception {
		assertSame(empresaMediatorMock, empresaController.getMediatorBean());
	}

	@Test
	public void testListarPorNome() throws Exception {
		String string = "qualquer";
		Mockito.when(empresaMediatorMock.findByParteNome(string)).thenReturn(Collections.emptyList());
		empresaController.listarPorNome(string);
		Mockito.verify(empresaMediatorMock, Mockito.only()).findByParteNome(string);
	}

	@Test
	public void testSalvarValidation() throws Exception {

		Empresa entity = new Empresa();
		Mockito.when(bindingResultMock.hasErrors()).thenReturn(true);

		empresaController.salvar(entity, bindingResultMock, redirectAttributesMock);

	}

	@Test
	public void testDelete() {

		String keyMensagem = "Registro " + 1 + " removido com sucesso.";
		Mockito.when(redirectAttributesMock.addFlashAttribute(AbstractFullCrudController.KEY_MSG, keyMensagem)).thenReturn(redirectAttributesMock);
		empresaController.delete(1, redirectAttributesMock);
		Mockito.verify(redirectAttributesMock, Mockito.only()).addFlashAttribute(Mockito.eq(AbstractFullCrudController.KEY_MSG), Mockito.eq(keyMensagem));
		Mockito.verify(empresaMediatorMock, Mockito.only()).apagar(Mockito.eq(1));
	}

	@Test
	public void testDeleteNegocioException() {
		Mockito.doThrow(NegocioException.class).when(empresaMediatorMock).apagar(Mockito.anyInt());

		Mockito.when(redirectAttributesMock.addFlashAttribute(AbstractFullCrudController.KEY_MSG, null)).thenReturn(redirectAttributesMock);
		empresaController.delete(1, redirectAttributesMock);
		Mockito.verify(redirectAttributesMock, Mockito.only()).addFlashAttribute(Mockito.eq(AbstractFullCrudController.KEY_ERROR), Mockito.anyString());
	}

	@Test
	public void testInicioEdit() throws Exception {
		Optional<Integer> param = Optional.of(1);
		Empresa value = new Empresa();
		Mockito.when(empresaMediatorMock.findOne(param)).thenReturn(value);
		empresaController.inicio(param);
	}

	@Test
	public void testInicioNew() throws Exception {
		Optional<Integer> param = Optional.of(1);
		Mockito.when(empresaMediatorMock.findOne(param)).thenReturn(null);
		empresaController.inicio(param);
	}

	@Test
	public void testListarPage() throws Exception {
		assertEquals("empresa-listar", empresaController.listarPage());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testListar() throws Exception {
		DataTablesInput dataTablesInput = new DataTablesInput();
		dataTablesInput.setStart(0);
		dataTablesInput.setLength(4);
		Page<Empresa> pageMock = Mockito.mock(Page.class);
		Mockito.when(empresaMediatorMock.find(dataTablesInput)).thenReturn(pageMock);
		DataTablesOutput<Empresa> result = empresaController.listar(dataTablesInput);
		assertNotNull(result);
	}

	@Test
	public void testGetNewEntity() throws Exception {
		assertTrue(empresaController.getNewEntity() instanceof Empresa);
	}

	@Test
	public void testGetModelName() throws Exception {
		assertEquals("empresa", empresaController.getModelName());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testAddCollections() throws Exception {
		ModelAndView mav = Mockito.mock(ModelAndView.class);
		empresaController.addCollections(mav, new Empresa());
		Mockito.verify(mav, Mockito.never()).addObject(Mockito.anyString());
		Mockito.verify(mav, Mockito.never()).addAllObjects(Mockito.anyMap());
		Mockito.verify(mav, Mockito.never()).addObject(Mockito.anyString(), Mockito.any());
	}
}
