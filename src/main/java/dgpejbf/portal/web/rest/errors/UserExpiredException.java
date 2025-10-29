package dgpejbf.portal.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class UserExpiredException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public UserExpiredException(String login) {
        super(
            null,
            "Usuario expirado",
            Status.LOCKED, // 423 Locked
            String.format("El usuario %s ha expirado su acceso", login)
        );
    }
}
