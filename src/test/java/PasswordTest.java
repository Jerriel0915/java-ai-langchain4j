import cn.cdut.ai.Utils.BCryptUtil;
import org.junit.jupiter.api.Test;

public class PasswordTest {
    @Test
    public void testBCrypt() {
        String rawPassword = "123";
        String encodedPassword = BCryptUtil.encode(rawPassword);
        System.out.println(encodedPassword);
    }
}
