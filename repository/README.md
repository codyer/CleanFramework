# 数据仓库 Repository
包名：com.cody.repository

# 主要功能：
提供网络数据和本地数据

# 其他功能
+ 数据绑定
+ 数据库封装
+ 埋点封装
+ 模拟数据请求

# 说明
* 网路层，Retrofit 是封装的很好的网路层，但是根据公司当前情况
1. 公司服务器有多种配置
2. 多后台结构
3. 公司现有开发对 Retrofit 使用不熟悉
4. 其他原因
基于各方面的考虑，最终没有直接使用 Retrofit，而是根据实际情况参考 Retrofit 思想，例如动态代理
再加上自定义的注解，实现了适合自己公司的请求封装。


# 网络数据操作（Interaction）

# 使用说明：
1、定义接口Interaction，一个Interaction对应一个服务器提供的接口，参数和格式类似于Swagger。

整个接口和方法的定义通过注解的方式实现
1、@Server
注解在类上，标注restful接口的域名，类似于base server
eg:
@Server("http://www.cody.com")

注意：为了实现多环境的配置，所有的域名根据环境对应有相应的Domain.java文件，用于定义Domain常量，
对应的路径定义在相应的UrlPath里，和Swagger保持一致

2、@RequestMapping
注解在请求数据的方法上，需要设置请求路径、请求方式、返回数据格式
请求路径指相对路径，和Swagger路径对应
请求方式为Get、Post等，和Swagger请求方式类似

返回数据格式支持如下几种：
    SIMPLE：返回如下整个json对应的对象，格式确定为先这种
            {
              "code": "200",
              "message": "获取成功",
              "dataMap": {}
            }
    ORIGINAL:返回任意json对应的对象
            {
              "code": "200",
              "message": "获取成功",
              "dataMap": {}
            }
    BEAN:返回如下特定格式的dataMap部分数据对象
            {
              "code": "200",
              "message": "获取成功",
              "dataMap": {}
            }
    LIST_BEAN:返回如下特定格式的dataMap部分数据列表
            {
              "code": "200",
              "message": "获取成功",
              "dataMap": []
            }

eg:
 @RequestMapping(
            value = JzUrlPath.byStyleList,
            method = RequestMethod.GET,
            type = ResultType.BEAN)
    void getCase(@QueryTag Object tag, @QueryMap Map<String, String> params, @QueryClass Class<?> clazz, @QueryCallBack ICallback<CaseBean> callback);

3、@QueryTag
注解在请求数据的方法查询参数上，请求TAG，用于取消请求

4、@QueryString
注解在请求数据的方法查询参数上，查询参数，一般查询参数比较少时使用，否则直接使用@QueryMap或者@QueryJson

5、@QueryMap、
注解在请求数据的方法查询参数上，查询参数，以键值对的形式传参给服务器

6、@QueryJson、
注解在请求数据的方法查询参数上，查询参数，以JsonObject的形式传参给服务器

7、@QueryHeaderListener
注解在请求数据的方法查询参数上，查询参数，用于监听http的头部返回的信息，一般用于登录

8、@QueryClass、
注解在请求数据的方法查询参数上，查询参数，指定返回结果的类类型，可以进行自动拆包。

9、@QueryCallBack
注解在请求数据的方法查询参数上，请求回调，http请求的回调接口，使用ICallback定义


# Mock
使用MockRepository进行伪造数据，将服务器返回的json保存在res/raw目录下,调用MockRepository可以直接读取对应json作为接口返回。


# License
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```