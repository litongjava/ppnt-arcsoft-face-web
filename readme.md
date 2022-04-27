## ppnt-arcsoft-face-web
依赖ppnt-arcsoft-face-utils和jfinal开发的web接入服务,提供http api

### 1.快速入门
#### 1.1.整合jfinal
添加依赖
```
<dependency>
  <groupId>top.ppnt</groupId>
  <artifactId>ppnt-arcsoft-face-web</artifactId>
  <version>1.0</version>
</dependency>
```
启动类 addHotSwapClassPrefix(PpntArcSoftFaceConstants.PACKAGE_NAME)
```
package top.ppnt.modules.arcsoft.face;

import com.jfinal.server.undertow.UndertowConfig;
import com.jfinal.server.undertow.UndertowServer;
import com.litongjava.utils.ip.IpUtils;

import top.ppnt.jfinal.commons.web.constants.PpntJfinalWebConstants;
import top.ppnt.jfinal.swaager.api.constants.PpntSwaggerConstants;
import top.ppnt.modules.arcsoft.face.config.FaceServerAppConfig;
import top.ppnt.modules.asrsoft.face.constancts.PpntArcSoftFaceConstants;

public class FaceServerApplication {

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    UndertowServer undertowServer = UndertowServer.create(FaceServerAppConfig.class);
    //添加SwapClassPrefix,开发环境扫描 jar 包中的路由 
    undertowServer.addHotSwapClassPrefix(PpntArcSoftFaceConstants.PACKAGE_NAME);
    //启动服务
    undertowServer.start();
    UndertowConfig undertowConfig = undertowServer.getUndertowConfig();
    int port = undertowConfig.getPort();
    String contextPath = undertowConfig.getContextPath();
    long end = System.currentTimeMillis();
    IpUtils.getThisUrl(port, contextPath);
    System.out.println("启动完成,共使用了" + (end - start) + "ms");
  }
}
```
配置类me.scan(PpntArcSoftFaceConstants.PACKAGE_NAME);
```
package top.ppnt.modules.arcsoft.face.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.template.Engine;

import top.ppnt.jfinal.commons.web.constants.PpntJfinalWebConstants;
import top.ppnt.jfinal.swaager.api.config.SwaggerRoutes;
import top.ppnt.modules.asrsoft.face.constancts.PpntArcSoftFaceConstants;

public class FaceServerAppConfig extends JFinalConfig {

  public void configConstant(Constants me) {
    me.setDevMode(true);
    me.setInjectDependency(true);
    me.setInjectSuperClass(true);
  }

  public void configRoute(Routes me) {
    me.scan(PpntArcSoftFaceConstants.PACKAGE_NAME);
  }

  @Override
  public void configEngine(Engine me) {
  }

  @Override
  public void configPlugin(Plugins me) {
  }

  @Override
  public void configInterceptor(Interceptors me) {
  }

  @Override
  public void configHandler(Handlers me) {
  }
}
```
#### 1.2.访问接口
检查服务是否有效  
http://127.0.0.1:8080/face-server/api/arcsoft/face/check  
其他接口参考接口文档  
[https://www.apifox.cn/apidoc/project-898965](https://www.apifox.cn/apidoc/project-898965/)