package data.sign;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import sun.misc.Signal;

@Configuration
public class SignalHandlerExample {

    private final Logger log = LoggerFactory.getLogger(SignalHandlerExample.class);

    public SignalHandlerExample(){
        try {
            System.out.println("SignalHandlerExample 加载完成！");
            Arrays.asList("INT", "TERM").forEach((signalName) -> {
                Signal.handle(new Signal(signalName), this::registerSignalHandler);
            });
        } catch (IllegalArgumentException e) {
            // 可能这个信号,并不支持这个平台或JVM作为目前配置
            System.out.println("SignalHandlerExample.IllegalArgumentException");
            e.printStackTrace();
        }
    }

    private void registerSignalHandler(Signal signal) {
        System.out.println("jvm 开始执行退出！ sign=" + signal.getName() + signal.getNumber());

        try {
            // 方式一
            // Thread.currentThread().sleep(20000);
            // 方式二
            synchronized (this) {
                this.wait(5000);
            }
        } catch (InterruptedException var1) {
            log.error("执行退出回调异常 {}", var1);
        }
        System.out.println("jvm wait 结束！");

        System.exit(0);

    }

}
