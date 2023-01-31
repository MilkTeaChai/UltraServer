# UltraServer
A very useful and comprehensive plugin

一个十分有用的综合性插件

This plugin is written for some newbies

这个插件是为了一些新手腐竹而写的

It contains a series of features such as manual memory cleanup, manual drop cleanup, plugin hot load, hot unload, enable, disable and world hot load

里面包含手动内存清理，手动掉落物清理，插件热加载，热卸载，启用，禁用 世界热加载 一系列功能

We add automatic drop cleaning

我们在1.7.0.5版本添加了全自动掉落物清理

# API
We delete all of API. Please wait for the api coming back in version 1.8.X

# Use It(调用)
Please add dependencies in your pom.xml

请在您的pom.xml里，添加依赖项

~~~
<repositories>
        <repository>
            <id>UltraServer</id>
            <name>A Powerful Bukkit Plugin</name>
            <url>http://refish-repo.unaux.com/UltraServer/maven-repo</url>
        </repository>
    </repositories>
    
    <dependencies>
        <dependency>
            <groupId>org.refish</groupId>
            <artifactId>ultraserver</artifactId>
            <version>1.7.0.5</version>
        </dependency>
    </dependencies>
~~~
If you are developing a Bukkit plugin, write "UltraServer" in the "depend" plugin.yml

如果你开发的是Bukkit插件，请在plugin.yml中的"depend"写入"UltraServer"
~~~
depend: [ UltraServer ]
~~~

THE PLUGIN SOURCE CODE IS PROTECTED BY GNU LICENSE V3.0, PLEASE INDICATE THAT SOME OF THE API COMES FROM THE PLUGIN WHEN USING THE SOURCE CODE OR CALLING RELATED LIBRARIES

该插件源码受到GNU LICENSE V3.0保护，请在使用源码或调用相关库时注明部分API来自该插件

Please do not import the API file directly into your project, this will make the internal API algorithm extremely unstable

请不要直接将API文件导入您的项目，这将让API内部算法变得极其不稳定
