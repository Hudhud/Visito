package dtu.app.visito;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

    public static <T> T clone(T t, TypeReference<T> type)
    {
        ObjectMapper mapper = new ObjectMapper();

        T clone;
        try
        {
            String json = mapper.writeValueAsString(t);
            clone = mapper.readValue(json, type);
        } catch (final Exception ex)
        {
            return null;
        }

        return clone;
    }

}
