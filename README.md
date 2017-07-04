

#### 一、简介

Drools是一个基于java的规则引擎，开源的，可以将复杂多变的规则从硬编码中解放出来，以规则脚本的形式存放在文件中，使得规则的变更不需要修正代码重启机器就可以立即在线上环境生效。

#### 二、工程说明

工程采用spring boot 架构。

* 启动入口，main 函数启动（data.Main）

* 任务配置项首次加载

```
http://localhost:8091/task/init
```

* 任务执行

```
http://localhost:8091/task/do
```

* 配置项修改后，重新加载生效

```
http://localhost:8091/task/reload
```

#### 三、测试结果


```
第一次执行:

执行前 point=0
增加对Tom哥的类型为birthdayPoint的积分操作记录.
增加对Tom哥的类型为buyMoneyPoint的积分操作记录.
增加对Tom哥的类型为buyNumsPoint的积分操作记录.
增加对Tom哥的类型为allFitPoint的积分操作记录.
增加对Tom哥的类型为subBackNumsPoint的积分操作记录.
增加对Tom哥的类型为subBackMondyPoint的积分操作记录.
BillThisMonth=10
BuyMoney=1000.0
BuyNums=10
执行后 point=290
```

```
修改配置后，第二次执行:

执行前 point=0
增加对Tom哥的类型为birthdayPoint的积分操作记录.
增加对Tom哥的类型为buyMoneyPoint的积分操作记录.
增加对Tom哥的类型为buyNumsPoint的积分操作记录.
增加对Tom哥的类型为allFitPoint的积分操作记录.
增加对Tom哥的类型为subBackNumsPoint的积分操作记录.
增加对Tom哥的类型为subBackMondyPoint的积分操作记录.
BillThisMonth=10
BuyMoney=1000.0
BuyNums=10
执行后 point=300
```


