package br.mp.mpt.prt8.severino.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utilitários e constantes para criação e manipulação dos arquivos gerados pela
 * aplicação.
 * 
 * @author sergio.eoliveira
 *
 */
public abstract class FileUtilsApp {
	public static final String EXTENSAO_PADRAO = ".jpg";
	private static final String DIRETORIO_FOTOS = "fotos_severino";
	private static String homeDir = System.getProperty("user.home");
	private static String tempDir = System.getProperty("java.io.tmpdir");

	public static String getHomeDir() {
		return homeDir;
	}

	public static void setHomeDir(String homeDir) {
		FileUtilsApp.homeDir = homeDir;
	}

	public static String getTempDir() {
		return tempDir;
	}

	public static void setTempDir(String tempDir) {
		FileUtilsApp.tempDir = tempDir;
	}

	public static Path getArquivoTemp(String tokenFoto) {
		try {
			Files.createDirectories(Paths.get(getTempDir(), DIRETORIO_FOTOS));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return Paths.get(getTempDir(), DIRETORIO_FOTOS, tokenFoto + FileUtilsApp.EXTENSAO_PADRAO);
	}

	/**
	 * Referência ao diretório de imagens.
	 * 
	 * @return
	 */
	public static Path getDiretorioFotos() {
		Path diretorioFotos = Paths.get(getHomeDir(), FileUtilsApp.DIRETORIO_FOTOS);
		try {
			if (Files.notExists(diretorioFotos)) {
				Files.createDirectory(diretorioFotos);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return diretorioFotos;
	}

}
