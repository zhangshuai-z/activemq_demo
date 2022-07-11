import com.zhangs.boot.activemq.MainApp_produce;
import com.zhangs.boot.activemq.prodece.Queue_Produce;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

// 加载主类
@SpringBootTest(classes = MainApp_produce.class)
// 加载spring 的 junit
@RunWith(SpringRunner.class)
// 加载web
@WebAppConfiguration
public class TestActiveMQ {

    @Resource
    private Queue_Produce queue_produce;

    @Test
    public void testSend() {
        queue_produce.produceMessage();
    }
}
