package com.dixon.simple.router.processor;

import com.dixon.simple.router.api.SimpleParam;
import com.dixon.simple.router.api.SimpleRouter;
import com.dixon.simple.router.base.core.Constant;
import com.dixon.simple.router.base.util.Log;
import com.dixon.simple.router.processor.filecreator.ParamSetBuilder;
import com.dixon.simple.router.processor.filecreator.RouterCreatorBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

public class SimpleRouterProcessor extends SimpleAbstractProcessor {

    private String mModuleName; // 后续会根据moduleName生成类名

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mModuleName = processingEnvironment.getOptions().get("moduleName");
        if (mModuleName == null || "".equals(mModuleName)) {
            throw new IllegalArgumentException("Please set moduleName on your build.gradle first");
        }
    }

    // 返回要支持的注解 必须重写 固定写法
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(SimpleRouter.class.getCanonicalName()); //获取注解类名
        supportTypes.add(SimpleParam.class.getCanonicalName()); //获取注解类名
        return supportTypes;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set != null && set.size() != 0) {
            // 获取所有被SimpleRouter注解的元素（这里是类）
            processRouter(roundEnvironment.getElementsAnnotatedWith(SimpleRouter.class));
            processParam(roundEnvironment.getElementsAnnotatedWith(SimpleParam.class));
        }
        return false;
    }

    private void generateCode(Set<? extends Element> elements) throws IOException {
        // RouterCreatorBuilder用于生成RouterCreator_xxx类
        RouterCreatorBuilder routerCreatorBuilder = new RouterCreatorBuilder(elements, mModuleName, mFiler);
        routerCreatorBuilder.buildJavaFile();
    }

    private void processRouter(Set<? extends Element> elements) {
        try {
            generateCode(elements);
            // success
        } catch (IOException e) {
            Log.o(Constant.ROUTER_LOG_TAG, "generateCodeError router: " + e.toString());
        }
    }

    private void processParam(Set<? extends Element> elements) {
        Map<TypeElement, Set<VariableElement>> paramClassMap = sortParams(elements);
        ParamSetBuilder paramSetBuilder = new ParamSetBuilder(paramClassMap, mFiler, mElements);
        try {
            paramSetBuilder.buildJavaFile();
            // success
        } catch (Exception e) {
            Log.o(Constant.ROUTER_LOG_TAG, "generateCodeError paramsSet: " + e.toString());
        }
    }

    // 将SimpleParams按照其根类分组（即按Activity分组）
    private Map<TypeElement, Set<VariableElement>> sortParams(Set<? extends Element> elements) {
        Map<TypeElement, Set<VariableElement>> paramClassMap = new HashMap<>();
        for (Element element : elements) {
            // SimpleParam 只支持变量 这里作强制类型转换
            VariableElement variableElement = (VariableElement) element;
            // 返回此元素的根元素
            TypeElement enclosingElement = (TypeElement) variableElement.getEnclosingElement();
            Set<VariableElement> paramsSet = paramClassMap.get(enclosingElement);
            if (paramsSet == null) {
                paramsSet = new HashSet<>();
                paramClassMap.put(enclosingElement, paramsSet);
            }
            paramsSet.add(variableElement);
        }
        return paramClassMap;
    }
}
