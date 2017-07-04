

#### 一、简介

Drools是一个基于java的规则引擎，开源的，可以将复杂多变的规则从硬编码中解放出来，以规则脚本的形式存放在文件中，使得规则的变更不需要修改代码就可以立即在线上环境生效。

#### 二、工程示例

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

* 测试结果


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

#### 三、文档资料

**Drools API可以分为三类：规则编译、规则收集和规则的执行。**

1. KnowledgeBuilder规则编译：规则文件进行编译， 最终产生一批编译好的规则包(KnowledgePackage)供其它的应用程序使用
2. KnowledgeBase：提供的用来收集应用当中知识（knowledge）定义的知识库对象，在一个KnowledgeBase 当中可以包含普通的规则（rule）、规则流(rule flow)、函数定义(function)、用户自定义对象（type model）等
3. StatefulKnowledgeSession：是一种最常用的与规则引擎进行交互的方式，它可以与规则引擎建立一个持续的交互通道，在推理计算的过程当中可能会多次触发同一数据集。在用户的代码当中，最后使用完StatefulKnowledgeSession 对象之后，一定要调用其dispose()方法以释放相关内存资源。有状态的
4. StatelessKnowledgeSession：使用StatelessKnowledgeSession 对象时不需要再调用dispose()方法释放内存资源不能进行重复插入fact 的操作、也不能重复的调用fireAllRules()方法来执行所有的规则，对应这些要完成的工作在StatelessKnowledgeSession当中只有execute(…)方法，通过这个方法可以实现插入所有的fact 并且可以同时执行所有的规则或规则流，事实上也就是在执行execute(…)方法的时候就在StatelessKnowledgeSession内部执行了insert()方法、fireAllRules()方法和dispose()方法
5. Fact ：是指在Drools 规则应用当中，将一个普通的JavaBean 插入到规则的WorkingMemory当中后的对象规则可以对Fact 对象进行任意的读写操作，当一个JavaBean 插入到WorkingMemory 当中变成Fact 之后，Fact 对象不是对原来的JavaBean 对象进行Clone，而是原来JavaBean 对象的引用


**Drools规则的属性共有13 个分别是：activation-group、agenda-group、auto-focus、date-effective、date-expires、dialect、duration、enabled、lock-on-active、no-loop、ruleflow-group、salience、when**

1. salience: 属性的值是一个数字，数字越大执行优先级越高，同时它的值可以是一个负数。默认情况下，规则的salience默认值为0，所以如果我们不手动设置规则的salience属性，那么它的执行顺序是随机的。
2. no-loop: 属性的值是一个布尔型，默认情况下规则的no-loop属性的值为false，如果no-loop 属性值为true，那么就表示该规则只会被引擎检查一次，如果满足条件就执行规则的RHS 部分
3. date-effective：在规则运行时，引擎会自动拿当前操作系统的时间与date-effective设置的时间值进行比对，只有当系统时间>=date-effective设置的时间值时，规则才会触发执行，否则执行将不执行。日期格式：dd-MM-yyyy
4. date-expires该属性的作用与date-effective属性恰恰相反，如果大于系统时间，那么规则就执行，否则就不执行。日期格式：dd-MM-yyyy
5. enabled: true执行该规则，false不执行该规则
6. dialect：该属性用来定义规则当中要使用的语言类型：mvel 和java，如果没有手工设置规则的dialect，默认使用的java 语言
7. duration: 该属性对应的值为一个长整型，单位是毫秒。如果设置了该属性，那么规则将在该属性值之后时间，在另外一个线程里触发
8. lock-on-active：该属性为boolean，当在规则上使用ruleflow-group属性或agenda-group属性的时候，将lock-on-action属性的值设置为true，可能避免因某些Fact 对象被修改而使已经执行过的规则再次被激活执行
9. activation-group该属性的作用是将若干个规则划分成一个组，用一个字符串来给这个组命名，这样在执 行的时候，具有相同 activation-group 属性的规则中只要有一个会被执行，其它的规则都将 不再执行。
10. agenda-group: agenda-group规则的调用与执行是通过StatelessSession 或StatefulSession 来实现的，一般的顺序是创建一个StatelessSession 或StatefulSession，将各种经过编译的规则的package添加到session当中，接下来将规则当中可能用到的Global 对象和Fact对象插入到Session 当中，最后调用fireAllRules 方法来触发、执行规则。在没有调用最后一步fireAllRules 方法之前，所有的规则及插入的Fact对象都存放在一个名叫Agenda 表的对象当中，这个Agenda表中每一个规则及与其匹配相关业务数据叫做Activation，在调用fireAllRules方法后，这些Activation会依次执行，这些位于Agenda表中的Activation的执行顺序在没有设置相关用来控制顺序的属性时（比如salience 属性），它的执行顺序是随机的，不确定的。Agenda Group是用来在Agenda的基础之上，对现在的规则进行再次分组，具体的分组方法可以采用为规则添加agenda-group属性来实现
11. auto-focus:它的作用是用来在已设置了agenda-group的规则上设置该规则是否可以自动独取Focus，如果该属性设置为true，那么在引擎执行时，就不需要显示的为某个Agenda Group设置Focus否则需要。
12. ruleflow-group: 在使用规则流的时候要用到ruleflow-group属性，该属性的值为一个字符串，作用是用来将规则划分为一个个的组，然后在规则流当中通过使用ruleflow-group 属性的值，从而使用对应的规则

###### 参考资料：

* https://wenku.baidu.com/view/d3c61babdd3383c4bb4cd2af.html
