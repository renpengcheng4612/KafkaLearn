package cn.pc.sz.service;

public interface MessageService {
    <T> void sendMessage(String topic, T data);
}
