/**
 * Controlador da câmera.
 */
function CameraControllerClass() {
	/**
	 * camera inicializada.
	 */
	this.camera = null;

	/**
	 * foto corrente.
	 */
	this.snapshot = null;

	/**
	 * @type HTMLDivElement
	 */
	this.divContainer = null;

	this.inicializarCamera = function() {
		this.divContainer = $("#camera");

		JpegCamera.DefaultOptions.shutter_ogg_url = "resources/jpeg_camera/shutter.ogg";
		JpegCamera.DefaultOptions.swf_url = "resources/jpeg_camera/jpeg_camera.swf";
		JpegCamera.DefaultOptions.shutter_mp3_url = "resources/jpeg_camera/shutter.mp3";
		JpegCamera.DefaultOptions.mirror = false;
		this.carregarFoto();
	};

	this.carregarFoto = function() {
		this.divContainer.empty();
		var fotoCadastrada = eval($("[id$=fotoCadastrada]").val());
		if (fotoCadastrada) {
			var documento = $("[id$=documento]").val();
			var contextPath = Utils.getContextPath();
			var tokenFoto = this.getTokenFoto();
			this.divContainer.append("<img src=\"" + contextPath + "visitante/foto?documento=" + documento + "&tokenFoto=" + tokenFoto + "\" />");
			this.camera = null;
		} else {
			this.startCamera();
		}
	};

	/**
	 * Tirar uma foto e gravar no servidor de arquivos no temp.
	 */
	this.tirarFoto = function() {
		if (this.camera == null) {
			this.startCamera();
			this.camera.ready(this.tirarFotoOnReady.bind(this));
		} else {
			this.tirarFotoOnReady();
		}

	};

	/**
	 * Tirar foto quando a camera estive pronta.
	 */
	this.tirarFotoOnReady = function() {
		var divCamera = this.divContainer;
		divCamera.effect("fade", divCamera.show.bind(divCamera));

		var snapshot = this.camera.capture();
		snapshot.show();
		if (this.snapshot != null) {
			this.snapshot.discard();
		}
		this.snapshot = snapshot;
		var tokenFoto = this.getTokenFoto();
		var dialog = bootbox.dialog({
			message: '<p class="text-center">Gravando imagem no servidor...</p>',
			closeButton: false
		});
		snapshot.upload({
			api_url: Utils.getContextPath() + "visitante/gravarImagem?tokenFoto=" + tokenFoto
		}).done(function(response) {
			dialog.modal('hide');
			if (response !== "upload_complete") {
				alert("O upload da imagem falhou... ")
			}
		}).fail(function(status_code, error_message, response) {
			dialog.modal('hide');
			alert("Upload da foto falhou... " + status_code);
		});
	};

	this.startCamera = function() {
		this.camera = new JpegCamera(this.divContainer.selector);
	};

	/**
	 * Descartar a foto tirada e não gravada.
	 */
	this.removerFoto = function() {
		var divCamera = this.divContainer;
		if (this.camera == null) {
			divCamera.empty();
			this.startCamera();
		}
		var tokenFoto = this.getTokenFoto();
		$.get(Utils.getContextPath() + "visitante/removerFotoTemporaria?tokenFoto=" + tokenFoto, this.descartarFoto.bind(this));
		divCamera.effect("fade", divCamera.show.bind(divCamera));
	};

	this.getTokenFoto = function() {
		return $("[id$=tokenFoto]").val();
	};

	this.descartarFoto = function() {
		if (this.snapshot != null) {
			this.snapshot.discard();
		}
		this.snapshot = null;
	};

	$(document).ready(this.inicializarCamera.bind(this));
};

var CameraController = new CameraControllerClass();