# UltraServer
A very useful and comprehensive plugin
一个十分有用的综合性插件
This plugin is written for some newbies
这个插件是为了一些新手腐竹而写的
It contains a series of features such as manual memory cleanup, manual drop cleanup, plugin hot load, hot unload, enable, disable world hot load
里面包含手动内存清理，手动掉落物清理，插件热加载，热卸载，启用，禁用 世界热加载 一系列功能
In the future, we may add automatic drop cleaning in version 1.8.X
后期我们可能会在1.8.X版本增加自动掉落物清理功能
# API
Currently, the plugin comes with an API: PlayerFallEvent
目前该插件自带API：PlayerFallEvent
This event trigger simplifies the player fall event that many common developers need, requiring a World object to be passed in
该事件触发器简化了许多普通开发者需要的玩家摔落事件，需要传入一个World对象
If you need it, please use it slowly
需要的开发者请慢慢食用体会
# Use It(调用)
At present, Our Refish Studio does not provide Maven library import, please import the packages in Release directly into your project
目前我们Refish Studio并没有提供Maven库导入，请将Release里的包直接导入您的项目
If you are developing a Bukkit plugin, write "UltraServer" in the "depend" plugin.yml
如果你开发的是Bukkit插件，请在plugin.yml中的"depend"写入"UltraServer"
THE PLUGIN SOURCE CODE IS PROTECTED BY GNU LICENSE V3.0, PLEASE INDICATE THAT SOME OF THE API COMES FROM THE PLUGIN WHEN USING THE SOURCE CODE OR CALLING RELATED LIBRARIES
该插件源码受到GNU LICENSE V3.0保护，请在使用源码或调用相关库时注明部分API来自该插件
Please do not import the API file directly into your project, this will make the internal API algorithm extremely unstable
请不要直接将API文件导入您的项目，这将让API内部算法变得极其不稳定
