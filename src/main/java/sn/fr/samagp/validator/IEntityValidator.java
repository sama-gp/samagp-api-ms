package sn.fr.samagp.validator;

public interface IEntityValidator<T> {
    void validate(T entity);
}