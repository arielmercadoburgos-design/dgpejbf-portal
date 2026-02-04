package dgpejbf.portal.service.dto;

import dgpejbf.portal.domain.User;
import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
/**
 * A DTO representing a user, with only the public attributes.
 */
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDate fechaExpiracion;

    private Long id;

    private String login;

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.telefono = user.getTelefono();
        this.fechaExpiracion = user.getFechaExpiracion();
        this.login = user.getLogin();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    //agregue el campo telefono para usuario

    private String telefono;

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaExpiracion() {
    return fechaExpiracion;
}

public void setFechaExpiracion(LocalDate fechaExpiracion) {
    this.fechaExpiracion = fechaExpiracion;
}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserDTO userDTO = (UserDTO) o;
        if (userDTO.getId() == null || getId() == null) {
            return false;
        }

        return Objects.equals(getId(), userDTO.getId()) && Objects.equals(getLogin(), userDTO.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogin());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDTO{" +
            "id='" + id + '\'' +
            ", login='" + login + '\'' +
            "}";
    }
}
