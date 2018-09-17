package cn.choleece.bing.common.util;

/**
 * format getter method
 * @author choleece
 * @date 2018/1/20
 */
public class GetterUtil {

    public static String toGetter(String fieldName) {

        if (fieldName == null || fieldName.length() == 0) {
            return null;
        }

        /**
         * if the second letter is upper, make 'get' + fieldName as getter method, eg. eBlog -> geteBlog
         */
        if (fieldName.length() > 2) {
            String secondLetter = fieldName.substring(1, 2);
            if (secondLetter.equals(secondLetter.toUpperCase())) {
                return new StringBuffer("get").append(fieldName).toString();
            }
        }

        /**
         * common situation eg. name -> getName
         */
        return new StringBuffer("get").append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1)).toString();
    }
}
