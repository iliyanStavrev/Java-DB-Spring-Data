package softuni.exam.util;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidationUtilImpl implements ValidationUtil {

    @Override
    public <E> boolean isValid(E entity) {
        Validator validator = Validation.buildDefaultValidatorFactory()
                .getValidator();
        return validator.validate(entity).isEmpty();
    }


}
