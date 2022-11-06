
####  生产者重要参数：

参数数名称  描述
bootstrap.servers  生产者连接集群所需的 broker 地址清单。例如 "172.20.10.8:9092,172.20.10.9:9092,172.20.10.10:9092",hadoop104:9092，可以 设置 1 个或者多个，中间用逗号隔开。注意这里并非需要所有的 broker 地址，因为生产者从给定的 broker 里查找到其他 broker信息。
key.serializer和 value.serializer  指定发送消息的 key 和 value 的序列化类型。一定要写全类名。
buffer.memory  RecordAccumulator 缓冲区总大小，默认 32m。
batch.size  缓冲区一批数据最大值，默认 16k。适当增加该值，可以提高吞吐量，但是如果该值设置太大，会导致数据传输延迟增加。
linger.ms  如果数据迟迟未达到 batch.size，sender 等待 linger.time 之后就会发送数据。单位 ms，默认值是 0ms，表示没有延迟。生产环境建议该值大小为 5-100ms之间。
acks  0：生产者发送过来的数据，不需要等数据落盘应答。 1：生产者发送过来的数据，Leader收到数据后应答。 -1（all）：生产者发送过来的数据，Leader+和 isr 队列 里面的所有节点收齐数据后应答。默认值是-1，-1 和 all是等价的。
max.in.flight.requests.per.connection  允许最多没有返回 ack 的次数，默认为 5，开启幂等性，要保证该值是 1-5 的数字。
retries  当消息发送出现错误的时候，系统会重发消息。retries 表示重试次数。默认是 int最大值，2147483647。 如果设置了重试，还想保证消息的有序性，需要设置 MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION=1 否则在重试此失败消息的时候，其他的消息可能发送成功了。
retry.backoff.ms  两次重试之间的时间间隔，默认是 100ms。
enable.idempotence  是否开启幂等性，默认 true，开启幂等性。
compression.type  生产者发送的所有数据的压缩方式。默认是 none，也就是不压缩。 支持压缩类型：none、gzip、snappy、lz4 和 zstd。



#### 消费者重要参数：

参数名称  描述
bootstrap.servers  向 Kafka集群建立初始连接用到的 host/port列表。
key.deserializer 和
value.deserializer
指定接收消息的 key 和 value 的反序列化类型。一定要写全
类名。
group.id  标记消费者所属的消费者组。
enable.auto.commit  默认值为 true，消费者会自动周期性地向服务器提交偏移
量。
auto.commit.interval.ms  如果设置了 enable.auto.commit 的值为 true， 则该值定义了
消费者偏移量向 Kafka提交的频率，默认 5s。
auto.offset.reset  当 Kafka 中没有初始偏移量或当前偏移量在服务器中不存在
（如，数据被删除了），该如何处理？ earliest：自动重置偏
移量到最早的偏移量。 latest：默认，自动重置偏移量为最
新的偏移量。 none：如果消费组原来的（previous）偏移量
不存在，则向消费者抛异常。 anything：向消费者抛异常。
offsets.topic.num.partitions __consumer_offsets 的分区数，默认是 50 个分区。
heartbeat.interval.ms  Kafka 消费者和 coordinator 之间的心跳时间，默认 3s。
该条目的值必须小于 session.timeout.ms ，也不应该高于
session.timeout.ms 的 1/3。
session.timeout.ms  Kafka 消费者和 coordinator 之间连接超时时间，默认 45s。
超过该值，该消费者被移除，消费者组执行再平衡。

max.poll.interval.ms  消费者处理消息的最大时长，默认是 5 分钟。超过该值，该
消费者被移除，消费者组执行再平衡。
fetch.min.bytes  默认 1 个字节。消费者获取服务器端一批消息最小的字节
数。
fetch.max.wait.ms  默认 500ms。如果没有从服务器端获取到一批数据的最小字
节数。该时间到，仍然会返回数据。
fetch.max.bytes  默认 Default: 52428800（50 m）。消费者获取服务器端一批
消息最大的字节数。如果服务器端一批次的数据大于该值
（50m）仍然可以拉取回来这批数据，因此，这不是一个绝
对最大值。一批次的大小受 message.max.bytes （broker
config）or max.message.bytes （topic config）影响。
max.poll.records  一次 poll拉取数据返回消息的最大条数，默认是 500 条。


生产经验——分区的分配以及再平衡:

数名称  描述
heartbeat.interval.ms  Kafka 消费者和 coordinator 之间的心跳时间，默认 3s。
该条目的值必须小于 session.timeout.ms，也不应该高于
session.timeout.ms 的 1/3。
session.timeout.ms  Kafka 消费者和 coordinator 之间连接超时时间，默认 45s。超
过该值，该消费者被移除，消费者组执行再平衡。
max.poll.interval.ms  消费者处理消息的最大时长，默认是 5 分钟。超过该值，该
消费者被移除，消费者组执行再平衡。
partition.assignment.strategy 消 费 者 分 区 分 配 策 略 ， 默 认 策 略 是 Range +
CooperativeSticky。Kafka 可以同时使用多个分区分配策略。
可 以 选 择 的 策 略 包 括 ： Range 、 RoundRobin 、 Sticky 、
CooperativeSticky


自动提交offset 

参数名称  描述
enable.auto.commit  默认值为 true，消费者会自动周期性地向服务器提交偏移量。
auto.commit.interval.ms 如果设置了 enable.auto.commit 的值为 true， 则该值定义了消
费者偏移量向 Kafka 提交的频率，默认 5s。