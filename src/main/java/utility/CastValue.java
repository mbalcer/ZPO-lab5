package utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CastValue {

    public Object cast(Class<?> parameterType, String value) {
        Object valueFieldAfterParse = null;

        if (parameterType.equals(int.class))
            valueFieldAfterParse = Integer.parseInt(value);
        else if (parameterType.equals(double.class))
            valueFieldAfterParse = Double.parseDouble(value);
        else if (parameterType.equals(String.class))
            valueFieldAfterParse = value;
        else if (parameterType.equals(LocalDate.class))
            valueFieldAfterParse = LocalDate.parse(value, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        else if (parameterType.isEnum())
            valueFieldAfterParse =  Enum.valueOf((Class<Enum>) parameterType, value);
        else if (parameterType.equals(boolean.class))
            valueFieldAfterParse = Boolean.parseBoolean(value);

        return valueFieldAfterParse;
    }
}
