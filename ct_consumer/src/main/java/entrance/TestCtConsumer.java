package entrance;

import kafka.HBaseConsumer;
import analyze.other.utils.PropertiesUtil;

public class TestCtConsumer {
    public static void main(String[] args) {
        System.out.println(PropertiesUtil.getProperty("kafka.topics"));
        HBaseConsumer hBaseConsumer = new HBaseConsumer();
        hBaseConsumer.getDataFromKafka();
    }
}
