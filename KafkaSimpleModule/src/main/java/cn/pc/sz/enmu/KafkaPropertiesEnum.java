package cn.pc.sz.enmu;


public enum KafkaPropertiesEnum {

    /**
     * 连接地址
     */
    BOOTSTRAP_SERVERS_CONFIG_VALUE_1("bootstrap.servers", "172.20.10.8:9092,172.20.10.9:9092,172.20.10.10:9092"),
    BOOTSTRAP_SERVERS_CONFIG_VALUE_2("bootstrap.servers", "192.168.197.128:9092,192.168.197.128:9092,192.168.197.128:9092");

    private String name;
    private String value;

    KafkaPropertiesEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
