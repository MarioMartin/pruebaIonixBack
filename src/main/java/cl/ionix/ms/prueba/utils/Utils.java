package cl.ionix.ms.prueba.utils;

import cl.ionix.ms.prueba.dtos.MessageDTO;

public class Utils {

	public static MessageDTO generarOkMessageDTO() {
		return new MessageDTO(Constantes.OK, Constantes.M200);
	}

	public static MessageDTO generarOkUpdateMessageDTO() {
		return new MessageDTO(Constantes.OK, Constantes.M200);
	}

	public static MessageDTO generarAvatarErrorMessageDTO() {
		return new MessageDTO(Constantes.OK, Constantes.M505);
	}

}
