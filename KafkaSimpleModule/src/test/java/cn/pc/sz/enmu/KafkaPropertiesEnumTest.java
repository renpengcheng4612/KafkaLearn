package cn.pc.sz.enmu;

import org.junit.Test;

public class KafkaPropertiesEnumTest {


    @Test
    public void test1() {
        System.out.println(KafkaPropertiesEnum.BOOTSTRAP_SERVERS_CONFIG_VALUE_1);
        System.out.println(KafkaPropertiesEnum.BOOTSTRAP_SERVERS_CONFIG_VALUE_1.getValue());
        System.out.println(KafkaPropertiesEnum.BOOTSTRAP_SERVERS_CONFIG_VALUE_2.getValue());
    }
}
