package cn.cdut.ai.Utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BCryptUtil {
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    /**
     * 生成哈希
     * @param rawString 原始字符串
     * @return BCrypt加密后的字符串
     */
    public static String encode(String rawString) {
        return ENCODER.encode(rawString);
    }

    /**
     * 哈希加密匹配
     * @param rawString 原始字符串
     * @param encodedString 加密字符串
     * @return 匹配是否成功
     */
    public static boolean matches(String rawString, String encodedString) {
        return ENCODER.matches(rawString, encodedString);
    }
}
