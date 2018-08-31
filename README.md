# Random Java Benchmarks

### Redis Benchmarks

First run using regular tcp socket connection.

```
Benchmark                 Mode  Cnt     Score    Error  Units
RedisBenchmarks.set_1k    avgt    9    34.773 ±  1.385  us/op
RedisBenchmarks.set_4k    avgt    9    36.745 ±  0.599  us/op
RedisBenchmarks.set_100k  avgt    9   136.228 ±  3.176  us/op
RedisBenchmarks.set_1m    avgt    9  1546.738 ± 27.813  us/op
```

Second run using unix domain socket connection.
```
Benchmark                 Mode  Cnt     Score    Error  Units
RedisBenchmarks.set_1k    avgt    9    27.999 ±  1.295  us/op
RedisBenchmarks.set_4k    avgt    9    30.489 ±  1.083  us/op
RedisBenchmarks.set_100k  avgt    9   123.587 ±  1.150  us/op
RedisBenchmarks.set_1m    avgt    9  1451.147 ± 37.196  us/op
```
