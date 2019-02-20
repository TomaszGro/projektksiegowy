package pl.tomaszgronski.projektksiegowy.invoice.validator;

public interface Validator<T> {
    boolean validate(T objectToValidate);
}
