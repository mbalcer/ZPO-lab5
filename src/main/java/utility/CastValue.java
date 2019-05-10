package utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CastValue {

    private InfoDialog info;

    public CastValue() {
        this.info = new InfoDialog();
    }

    public Object cast(Class<?> parameterType, String value) {
        Object valueFieldAfterParse = null;

        try {
            if (parameterType.equals(int.class))
                valueFieldAfterParse = Integer.parseInt(value);
            else if (parameterType.equals(double.class))
                valueFieldAfterParse = Double.parseDouble(value);
            else if (parameterType.equals(String.class))
                valueFieldAfterParse = value;
            else if (parameterType.equals(LocalDate.class))
                valueFieldAfterParse = LocalDate.parse(value, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            else if (parameterType.isEnum())
                valueFieldAfterParse = Enum.valueOf((Class<Enum>) parameterType, value);
            else if (parameterType.equals(boolean.class))
                valueFieldAfterParse = Boolean.parseBoolean(value);
        } catch (DateTimeParseException e ){
            info.showAlert("Error", "Error when parsing the date. Date must be format dd-mm-yyyy");
        } catch (NumberFormatException e) {
            info.showAlert("Error", "Error when parsing the number.");
        } catch (IllegalArgumentException e) {
            info.showAlert("Error", "There isn't such value Enum");
        }
        return valueFieldAfterParse;
    }
}
