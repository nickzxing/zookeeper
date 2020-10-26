clone项目后使用ant eclipse命令进行构建

服务端单节点启动：
* 运行：QuorumPeerMain.java
* VM options：-Dlog4j.configuration=file:$ProjectFileDir$/conf/log4j.properties
* Program arguments：$ProjectFileDir$\conf\zoo.cfg

服务端集群启动：
* 分别使用zoo1.cfg、zoo2.cfg、zoo3.cfg运行三次：QuorumPeerMain.java
* VM options：-Dlog4j.configuration=file:$ProjectFileDir$/conf/log4j.properties
* Program arguments：$ProjectFileDir$\conf\zoo1.cfg
* Program arguments：$ProjectFileDir$\conf\zoo2.cfg
* Program arguments：$ProjectFileDir$\conf\zoo3.cfg

客户端启动：
* 运行：ZooKeeperMain.java
* VM options：-Dlog4j.configuration=file:$ProjectFileDir$/conf/log4j.properties -Djline.terminal=jline.UnsupportedTerminal

服务端编译显示找不到version：
* 运行：VerGen.java
* Program arguments：3.4.13 1 2020-07-26

其他案例main方法启动：
* -Dlog4j.configuration=file:$ProjectFileDir$/conf/log4j_simple.properties