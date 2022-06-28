package cl.ionix.ms.prueba.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
@AllArgsConstructor
public class MessageDTO implements Serializable {

    private static final long serialVersionUID = -6533078367549236908L;

    @NonNull
    private String status;

    @NonNull
    private String message;

}