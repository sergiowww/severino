package br.mp.mpt.prt8.severino.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.FileSystemResource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.mp.mpt.prt8.severino.entity.Endereco;
import br.mp.mpt.prt8.severino.entity.Estado;
import br.mp.mpt.prt8.severino.entity.Visitante;
import br.mp.mpt.prt8.severino.mediator.VisitanteMediator;

/**
 * Teste para o controlador dos visitantes.
 * 
 * @author sergio.eoliveira
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VisitanteControllerTest {
	@Mock
	private VisitanteMediator visitanteMediatorMock;

	@Mock
	private SmartValidator smartValidator;

	@Mock
	private RedirectAttributes redirectAttributesMock;

	@Mock
	private BindingResult bindingResultMock;

	@InjectMocks
	private VisitanteController visitanteController;

	@Test
	public void testAddCollectionsModelAndViewVisitante() {
		ModelAndView mav = Mockito.mock(ModelAndView.class);
		Visitante entity = new Visitante();
		visitanteController.addCollections(mav, entity);
		Mockito.verify(mav, Mockito.only()).addObject(Mockito.eq("ufs"), Mockito.anyObject());
	}

	@Test
	public void testGetMediatorBean() {
		assertSame(visitanteController.getMediatorBean(), visitanteMediatorMock);
	}

	@Test
	public void testGetNewEntity() {
		Visitante visitante = visitanteController.getNewEntity();
		assertEquals(Estado.PA, visitante.getUf());
		assertNotNull(visitante.getTokenFoto());
	}

	@Test
	public void testGetByDocumento() {
		String documento = "123456";
		Visitante visitante = new Visitante();
		Mockito.when(visitanteMediatorMock.findByDocumento(documento)).thenReturn(visitante);
		assertSame(visitante, visitanteController.getByDocumento(documento));
	}

	@Test
	public void testSalvarVisitanteBindingResultRedirectAttributes() {
		Visitante visitante = new Visitante();
		visitanteController.salvar(visitante, bindingResultMock, redirectAttributesMock);
		Mockito.verify(smartValidator, Mockito.only()).validate(visitante, bindingResultMock);
		Mockito.verify(visitanteMediatorMock, Mockito.only()).save(visitante);
	}

	@Test
	public void testSalvarComEnderecoNull() throws Exception {
		Visitante visitante = new Visitante();
		visitante.setEndereco(new Endereco());
		visitanteController.salvar(visitante, bindingResultMock, redirectAttributesMock);
		Mockito.verify(smartValidator, Mockito.only()).validate(visitante, bindingResultMock);
		Mockito.verify(visitanteMediatorMock, Mockito.only()).save(visitante);
	}

	@Test
	public void testSalvarComEnderecoNotNull() throws Exception {
		Visitante visitante = new Visitante();
		Endereco endereco = new Endereco();
		endereco.setBairro("bairro");
		visitante.setEndereco(endereco);
		visitanteController.salvar(visitante, bindingResultMock, redirectAttributesMock);
		Mockito.verify(smartValidator, Mockito.only()).validate(visitante, bindingResultMock);
		Mockito.verify(visitanteMediatorMock, Mockito.only()).save(visitante);
	}

	@Test
	public void testGravarImagem() throws IOException {
		InputStream imagem = getClass().getResourceAsStream("/foto954.png");
		String tokenFoto = "token";
		assertNotNull(visitanteController.gravarImagem(tokenFoto, imagem));
		Mockito.verify(visitanteMediatorMock, Mockito.only()).gravarImagemTemporaria(tokenFoto, imagem);
	}

	@Test
	public void testRemoverFotoTemporaria() throws IOException {
		String tokenFoto = "token";
		assertNotNull(visitanteController.removerFotoTemporaria(tokenFoto));
		Mockito.verify(visitanteMediatorMock, Mockito.only()).removerFotoTemporaria(tokenFoto);
	}

	@Test
	public void testDownloadFoto() throws IOException {
		String documento = "123456";
		String tokenFoto = "token";
		Path pathHomeDir = Paths.get(System.getProperty("user.home"));
		Mockito.when(visitanteMediatorMock.getFotoByDocumento(documento, tokenFoto)).thenReturn(pathHomeDir);
		FileSystemResource file = visitanteController.downloadFoto(documento, tokenFoto);
		assertTrue(file.exists());
	}

}
