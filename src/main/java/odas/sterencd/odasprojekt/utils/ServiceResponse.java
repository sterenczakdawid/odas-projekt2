package odas.sterencd.odasprojekt.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder
public class ServiceResponse<T> {
    public T data;
    public Boolean isSuccess;
    public String message;
}