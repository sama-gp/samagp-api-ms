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
            // Collecter les messages d'erreur
            List<String> errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            throw new ValidationEntityException(errorMessages);
        }
    }
}