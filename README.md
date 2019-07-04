# session-redis
如何替换掉Servlet容器创建和管理HttpSession的实现呢？
（1）设计一个Filter，利用HttpServletRequestWrapper，实现自己的 getSession()方法，接管创建和管理Session数据的工作。spring-session就是通过这样的思路实现的。 
（2）利用Servlet容器提供的插件功能，自定义HttpSession的创建和管理策略，并通过配置的方式替换掉默认的策略。不过这种方式有个缺点，就是需要耦合Tomcat/Jetty等Servlet容器的代码。这方面其实早就有开源项目了，例如memcached-session-manager，以及tomcat-redis-session-manager。暂时都只支持Tomcat6/Tomcat7。 


spring-session-1.2.2.RELEASE 不兼容 spring-data-redis-1.7.4.RELEASE
目前 spring-session 最新版本是 1.2.2.RELEASE,内置依赖 spring-data-redis 版本是 1.7.1.RELEASE


用户认证的信息是通过session进行保存的，当然默认的就是使用servlet容器(比如tomcat,jetty)生成的session；如果使用了spring session，可以通过Filter将session进行包装，进而将用户认证的信息保存到Redis去，而不依赖servlet容器。


springSession的简易使用步骤
生成
step 1：后台业务模块使用Spring-Session生成一个session
step 2：后台业务模块往session里设置信息
step 3：将session存到redis缓存中（支持持久化）
step 4：将session id 返回给浏览器
step 5：浏览器根据cookie方式保存session id
使用
step 6：浏览器取出session id通过HTTP报文带给后台
step 7：后台根据session id从redis里取出缓存的session
step 8：从session中读取出信息，进行业务处理。

Spring Session对HTTP的支持是通过标准的servlet filter来实现的，这个filter必须要配置为拦截所有的web应用请求，并且它应该是filter链中的第一个filter。
Spring Session filter会确保随后调用javax.servlet.http.HttpServletRequest的getSession()方法时，都会返回Spring Session的HttpSession实例，而不是应用服务器默认的HttpSession。

SessionRepositoryFilter的依赖关系：SessionRepositoryFilter --> SessionRepository --> RedisTemplate --> RedisConnectionFactory

Http Session数据在Redis中是以Hash结构存储的。
（1）Http Session数据在Redis中是以Hash结构存储的。 
（2）可以看到，还有一个key="spring:session:expirations:1431577740000"的数据，是以Set结构保存的。这个值记录了所有session数据应该被删除的时间（即最新的一个session数据过期的时间）。 


2018年10月18日记录：
（1）2018年10月18日街道GitHub的漏洞提醒通知，升级spring版本；
（2）spring的版本更新到4.3.17；
（3）同时更新spring-session-data-redis的版本，更新到1.3.3
（4）更新fastjson版本，更新到1.2.47；
（5）同时设置JDK的版本，设置成1.8版本；

2019年5月20日记录：
（1）更新fastjson版本，设置最低版本为1.2.31；
