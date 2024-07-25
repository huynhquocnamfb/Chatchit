package vn.edu.nlu.web.chat.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;

@Slf4j
public class DataUtils {
    public static final String PHONE_NUMBER_PATTERN = "^(([03+[2-9]|05+[6|8|9]|07+[0|6|7|8|9]|08+[1-9]|09+[1-4|6-9]]){3})+[0-9]{7}$";
    public static final String EMAIL_PATTERN = "[A-Z0-9a-z._%+-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]+";
    public static final String IMAGE_EXTENSION_PATTERN = "^(gif|jpe?g|tiff?|png|webp|bmp)$";
    public static final String REGEX_BASE64 = "^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$";
    public static final String REGEX_FILE_NAME = "^.+\\.[A-Za-z]+$";
    public static final String REGEX_CRON_EXPRESSION = "^((\\*|\\?|\\d+((\\/|\\-){0,1}(\\d+))*)\\s*){6}$";

    public static final String UPPER_CASE_LETTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER_CASE_LETTER = UPPER_CASE_LETTER.toLowerCase();
    public static final String DIGITS = "0123456789";
    public static final String SPECIAL_CHARACTERS = "!@#$%^&*()";
    private static final Random random = new Random();

    public enum DataTypeClassName {
        STRING(String.class.getName()),
        LONG(Long.class.getName()),
        PRIMITIVE_LONG(long.class.getName()),
        DOUBLE(Double.class.getName()),
        PRIMITIVE_DOUBLE(double.class.getName()),
        BOOLEAN(Boolean.class.getName()),
        PRIMITIVE(boolean.class.getName()),
        DATE(Date.class.getName()),
        BIG_DECIMAL(BigDecimal.class.getName()),
        INTEGER(Integer.class.getName()),
        INT(int.class.getName());

        public final String value;
        private static final Map<String, DataTypeClassName> lookup = new HashMap<>();

        static {
            for (DataTypeClassName d : DataTypeClassName.values()) {
                lookup.put(d.value, d);
            }
        }

        public static DataTypeClassName get(String value) {
            return lookup.get(value);
        }

        DataTypeClassName(String value) {
            this.value = value;
        }
    }

    /**
     * Copies properties from the source object to a new instance of the target class,
     * excluding properties specified in the `ignoreProperties` array.
     *
     * @param source           The source object to copy properties from. (must not be null)
     * @param classTarget      The target class to create an instance of. (must not be null)
     * @param ignoreProperties An array of property names to exclude from copying. (optional)
     * @param <T>              The type of the target object.
     * @return A new instance of the target class with copied properties.
     * @throws RuntimeException If an error occurs during property copying.
     */

    public static <T> T copyProperties(Object source, Class<T> classTarget, String... ignoreProperties) {
        Assert.notNull(source, "Source object cannot be null");
        Assert.notNull(classTarget, "Target class cannot be null");

        try {
            T targetInstance = classTarget.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, targetInstance, ignoreProperties);
            return targetInstance;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            log.error("Error copying properties", e, Level.SEVERE);
            throw new RuntimeException("Error copying properties", e);
        }
    }

    /**
     * Convenience method that calls {@link #copyProperties(Object, Class, String[])} with a null `ignoreProperties` array.
     * Copies all properties from the source object to a new instance of the target class.
     *
     * @param source      The source object to copy properties from. (must not be null)
     * @param classTarget The target class to create an instance of. (must not be null)
     * @param <T>         The type of the target object.
     * @return A new instance of the target class with copied properties.
     * @throws RuntimeException If an error occurs during property copying.
     */
    public static <T> T copyProperties(Object source, Class<T> classTarget) {
        return copyProperties(source, classTarget, (String[]) null);
    }
}
