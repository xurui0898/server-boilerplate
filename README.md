# server-boilerplate
基于SpringBoot的后端server脚手架，集成环境配置、数据库操作、权限控制、日志处理、标准化发布等通用模块。

## 环境配置 —— Profile

在根目录的`pom.xml`中配置dev、beta、prod环境变量，然后在SpringBoot的配置文件中引用maven的profile属性。

[参考文档](http://blog.csdn.net/lihe2008125/article/details/50443491) 

## 数据库操作 —— MyBatis

`server-dao`模块集成了`mybatis-generator`，使用它来自动生成MyBatis的dao层，除此之外也添加了两个generator插件：

  + **MySQL分页**：此脚手架未采用基于MyBatis拦截器的第三方分页插件，而是直接在生成的dao层添加`setLimit*`，使用它们即可在生成的sql中直接添加`limit ?, ?`，这样做即简洁又易于理解。  
  + **MySQL注释**：将数据库元数据中的comment插入自动生成的Model类中，便于代码的阅读。
  
由于`mybatis-generator`插件依赖了另外一个模块`server-common`，因此在执行generator前需要安装这些依赖到本地仓库：


```
    mvn install
```

否则`mybatis-generator`会因为找不到`server-common`依赖而执行失败。

## 权限控制 —— shiro

`server-api`模块集成了shiro进行权限控制，参见`com.boilerplate.server.init.ShiroConfig`类。
 
对于纯粹的后端server而言，可能还需要自己实现一个生成错误json的filter来替换默认filter，因为默认filter会在权限验证失败时重定向至错误页面。

## 日志处理 —— logback

无它，唯一需要配置的就是日志输出位置，此脚手架中支持在SpringBoot中配置日志输出位置，这种配置对开发测试阶段更加友好，避免了日志文件满天飞的情况。

## 部署与发布

使用docker构建Java8环境，将server-api打包jar文件映射到容器内/app.jar即可。

参考文档以beta环境打包为例，因为beta环境配置的MySQL链接是宿主机的IP。

参考文档：[Dockerfile构建java8环境](https://www.yuque.com/docs/share/6b1dedf2-615b-42cf-91b3-47427b5db71b) 
