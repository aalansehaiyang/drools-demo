package data.drools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.spi.Activation;

/**
 * 规则接口实现类
 */
public class PointRuleEngineImpl implements PointRuleEngine {

    private RuleBase ruleBase;

    public void initEngine() {
        // 设置时间格式
        System.setProperty("drools.dateformat", "yyyy-MM-dd HH:mm:ss");
        ruleBase = RuleBaseFacatory.getRuleBase();
        try {
            PackageBuilder backageBuilder = getPackageBuilderFromDrlFile();
            ruleBase.addPackages(backageBuilder.getPackages());
        } catch (DroolsParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshEnginRule() {
        ruleBase = RuleBaseFacatory.getRuleBase();
        org.drools.rule.Package[] packages = ruleBase.getPackages();
        for (org.drools.rule.Package pg : packages) {
            ruleBase.removePackage(pg.getName());
        }

        initEngine();
    }

    public void executeRuleEngine(PointDomain pointDomain) {
        if (null == ruleBase.getPackages() || 0 == ruleBase.getPackages().length) {
            return;
        }

        StatefulSession statefulSession = ruleBase.newStatefulSession();
        statefulSession.insert(pointDomain);

        // fire
        statefulSession.fireAllRules(new org.drools.spi.AgendaFilter() {

            public boolean accept(Activation activation) {
                return !activation.getRule().getName().contains("allFitPoint");
            }
        });

        statefulSession.dispose();
    }

    /**
     * 从Drl规则文件中读取规则
     * 
     * @return
     * @throws Exception
     */
    private PackageBuilder getPackageBuilderFromDrlFile() throws Exception {
        // 获取测试脚本文件
        List<String> drlFilePath = getDrlFile();
        // 装载测试脚本文件
        List<Reader> readers = readRuleFromDrlFile(drlFilePath);

        PackageBuilder backageBuilder = new PackageBuilder();
        for (Reader r : readers) {
            backageBuilder.addPackageFromDrl(r);
        }

        // 检查脚本是否有问题
        if (backageBuilder.hasErrors()) {
            throw new Exception(backageBuilder.getErrors().toString());
        }

        return backageBuilder;
    }

    /**
     * @param drlFilePath 脚本文件路径
     * @return
     * @throws FileNotFoundException
     */
    private List<Reader> readRuleFromDrlFile(List<String> drlFilePath) throws FileNotFoundException {
        if (null == drlFilePath || 0 == drlFilePath.size()) {
            return null;
        }

        List<Reader> readers = new ArrayList<Reader>();

        for (String ruleFilePath : drlFilePath) {
            // readers.add(new FileReader(new File(ruleFilePath)));
            readers.add(new InputStreamReader(
                                              PointRuleEngineImpl.class.getClassLoader().getResourceAsStream(ruleFilePath)));
        }

        return readers;
    }

    /**
     * 获取测试规则文件
     * 
     * @return
     */
    private List<String> getDrlFile() {
        List<String> drlFilePath = new ArrayList<String>();
        drlFilePath.add("data/rules/addpoint.drl");
        drlFilePath.add("data/rules/subpoint.drl");

        return drlFilePath;
    }
}
