### 项目记录

#### 1.表中密码两次MD5加密

```sql
password VARCHAR(255) DEFAULT NULL COMMENT 'MD5(MD5(pass明文+固定salt)+salt)'
```
1.前端到后端一次MD5加密
2.后端到数据库一次MD5加密


#### 2.逆向工程

可以创建一个单独的逆向工程 generator
参考 mybatis-plus 官网配置
(https://baomidou.com/guide/generator.html#使用教程)
生成pojo、mapper、service及mapper.xml

#### 3.实现登陆功能

前后端的参数校验


#### 4.参数校验

对参数校验进行抽象化
```xml
<dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

```
@Valid 注解

@IsMobile
自定义注解 + 自定义规则



#### 5.异常处理

Spring处理异常的方式
ControllerAdvice + ExceptionHandler 实现

@ExceptionHandler(Exception.class)
@RestControllerAdvice

#### 6.用户登录状态

cookie + session

redis存储用户



#### 7.分布式session

- 1.session复制(占用内存)
- 2.前端存储(存在安全风险)
- 3.session粘滞(重新hash)
- 4.后端集中存储(安全,容易水平拓展;增加复杂度、重写代码)


使用redis进行分布式session

```
redis 5.0.5

make
//make install
make PERFIX=/usr/local/redis install
cd /usr/local/redis/bin
./redis-server  // 默认是后台启动

cp redis.conf /usr/local/redis/bin

修改/usr/local/redis/bin目录下的conf

修改bind、后台启动

[root@iZuf627r2t0po6jun6m806Z bin]# ./redis-server redis.conf
30370:C 23 May 2021 18:51:43.031 # oO0OoO0OoO0Oo Redis is starting oO0OoO0OoO0Oo
30370:C 23 May 2021 18:51:43.031 # Redis version=5.0.5, bits=64, commit=00000000, modified=0, pid=30370, just started
30370:C 23 May 2021 18:51:43.031 # Configuration loaded

ps -ef | grep redis


// 连接远程
1.阿里云服务器开启6379端口访问权限
2.redis.conf -> bind 127.0.0.1 注释掉; 


redis-cli -h 47.100.130.231 -p 6379

```

redis高性能的内存数据库,广泛用于缓存方向、也可以作为分布式锁




#### 8.解除SpringSession依赖

使用redis代替




#### 9.秒杀实现


#### 10.压力测试jMeter

jMeter中的衡量指标

QPS（TPS）：每秒钟request/事务 数量

- QPS(query per second)
每秒查询率QPS是对一个特定的查询服务器在规定时间内所处理流量多少的衡量标准，
在因特网上，作为域名系统服务器的机器的性能经常用每秒查询率来衡量

- TPS(Transactions Per Second)
服务器每秒处理的事务数。
TPS包括一条消息入和一条消息出，加上一次用户数据库访问。（业务TPS = CAPS × 每个呼叫平均TPS）


- 并发数量

系统同时处理的request/事务数


**linux压测**

配置远程mysql:

启动mysql的守护线程
systemctl start mysqld

grep 'password' /var/log/mysqld.log

//alter user 'root'@'localhost' identified by 'root';

set password for 'root'@'localhost'=password('root');
FLUSH PRIVILEGES;

开始远程访问

设置访问权限
create user 'dengs'@'%' identified by 'dengs';
grant all on *.* to 'dengs'@'%';


**服务器无界面测试**
./jmeter.sh -n -t FirstJmeter.jmx -l result.jtl

#### 11.项目打包


###  项目问题记录

##### 1.端口占用
```text
Web server failed to start. Port 8080 was already in use.
```
查看端口占用
```shell script
netstat -n 
lsof -i tcp:8080
```
- 修改端口 yml中 servlet.port = 8082
- 关闭占用端口的进程


##### 2.session

确认访问用户的身份:

- Basic认证(基本认证)
- Digest认证(摘要认证)
- SSL客户端认证
- FormBase认证(基于表单认证)

使用的认证方式:

Web 应用程序各自实现基于表单的认证方式

**使用Cookie来管理Session**



#### 3.config相关问题

##### 3.1 RedisConfig

关于@configuration注解
```text
@Configuration用于定义配置类，可替换xml配置文件，
被注解的类内部包含有一个或多个被@Bean注解的方法，
这些方法将会被AnnotationConfigApplicationContext或
AnnotationConfigWebApplicationContext类进行扫描，并用于构建bean定义，初始化Spring容器
```


redisTemplate

##### 3.2 WebConfig
WebMvcConfigurer


#### 4.Spring事务管理



#### 5.Redis相关学习

##### 5.1 redis数据库中的键值对

##### 5.2 NIO && Reactor模式

Redis是基于Reactor模式开发实现自己的网络事件处理器

reactor == notifier + dispatcher


[Reactor及NIO](https://dengshuo7412.com/2021/05/26/Reactor模式及NIO/)



#### 6.@Controller && @RestController

@RestController 会默认返回响应体

页面跳转不能用 @RestController,应该直接使用@Controller 


#### 7.数据访问异常层次体系

返回异常，建立一个全局通用的异常处理类进行实现
GlobalException


#### 8. o.s.web.servlet.PageNotFound             

 No mapping for GET /bootstrap/css/bootstrap.min.css.map

#### 9.端口占用

Web server failed to start port 8084 was already use
```
lsof - i:8084
kill pid
```



### 项目提升

#### 1.添加注册界面

#### 1.实现支付功能

#### 1.长链接转换为短链接