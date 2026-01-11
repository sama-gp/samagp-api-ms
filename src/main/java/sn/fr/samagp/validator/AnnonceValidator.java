package sn.fr.samagp.validator;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sn.fr.samagp.exceptions.ValidationEntityException;
import sn.fr.samagp.repository.model.Annonce;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AnnonceValidator implements IEntityValidator<Annonce> {

    private final Validator validator;

    public AnnonceValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public void validate(Annonce annonce) {
        Set<ConstraintViolation<Annonce>> violations = validator.validate(annonce);

        if (!violations.isEmpty()) {
            violations.forEach(v -> {
                System.err.println("Violation sur " + v.getPropertyPath() + " : " + v.getMessage());
            });

            List<String> errorMessages = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.toList());

            throw new ValidationEntityException(errorMessages);
        }
    }

}