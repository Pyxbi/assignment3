package org.example.frontend;

import org.example.frontend.components.form.fields.FormField;
import java.util.List;

public class MissingRequiredFieldException extends Exception{
    public MissingRequiredFieldException(List<FormField> missingFields) {
        super(getErrorMsg(missingFields));
    }

    private static String getErrorMsg(List<FormField> fields) {
        StringBuilder s = new StringBuilder("Missing required field(s): ");

        for (int i = 0; i < fields.size() - 1; i++) {
            s.append(fields.get(i).getLabel());
            s.append(", ");
        }

        int lastInd = (fields.size() - 1);
        s.append(fields.get(lastInd).getLabel());
        s.append(".");

        return s.toString();
    }
}