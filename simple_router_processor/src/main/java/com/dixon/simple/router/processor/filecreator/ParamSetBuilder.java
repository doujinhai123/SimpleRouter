package com.dixon.simple.router.processor.filecreator;

import com.dixon.simple.router.api.SimpleParam;
import com.dixon.simple.router.base.core.Constant;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Param不能像Router一样，有统一的包名。
 * 因为Router是一个module统一注册所有的Activity，所以Activity的类名是否相同无所谓。
 * 而Param是每个Activity类生成一个辅助Set类，所以只根据类名来生成，可能会有重复的情况。
 */
public class ParamSetBuilder implements IJavaFileBuilder {

    // Module内所有被SimpleParams标注的集合 以根类分组 最终生成根类数量个_ParamSet类
    Map<TypeElement, Set<VariableElement>> mParamClassMap;
    // 用于将Java文件输出到Build目录下
    private Filer mFiler;

    private Elements mElements;

    public ParamSetBuilder(Map<TypeElement, Set<VariableElement>> mParamClassMap, Filer mFiler, Elements elements) {
        this.mParamClassMap = mParamClassMap;
        this.mFiler = mFiler;
        this.mElements = elements;
    }

    @Override
    public void buildJavaFile() throws Exception {
        for (TypeElement typeElement : mParamClassMap.keySet()) {
            // 生成的帮助类的类名：xxx.xxx.xxActivity_ParamSet
            generateSingleClass(typeElement);
        }
    }

    // 生成单个 xxx_ParamsSet 类
    private void generateSingleClass(TypeElement typeElement) throws IOException, ClassNotFoundException {
        String className = typeElement.getSimpleName() + Constant.PARAM_SET_SUFFIX;
        String packageName = mElements.getPackageOf(typeElement).getQualifiedName().toString();

        ClassName activityClass = ClassName.get("android.app", "Activity");
        ClassName intentClass = ClassName.get("android.content", "Intent");
        ClassName bundleClass = ClassName.get("android.os", "Bundle");
        ClassName typeClass = ClassName.get(packageName, typeElement.getSimpleName().toString());

        MethodSpec initParams = null;

        MethodSpec.Builder initParamsBuilder = MethodSpec.methodBuilder("initParams")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(activityClass, "originActivity")
                .addStatement("$T activity = (" + typeElement.getSimpleName() + ")originActivity", typeClass)
                .addStatement("$T intent = activity.getIntent()", intentClass)
                .addStatement("$T bundle = intent.getBundleExtra($S)", bundleClass, Constant.ROUTER_BUNDLE_NAME)
                .addCode("if (bundle != null) {\n");

        Set<VariableElement> paramsInSingleClass = mParamClassMap.get(typeElement);
        for (VariableElement variableElement : paramsInSingleClass) {
            SimpleParam simpleParam = variableElement.getAnnotation(SimpleParam.class);
            String value = simpleParam.value();
            initParamsBuilder.addStatement("activity." + variableElement.getSimpleName() + " = (" + variableElement.asType().toString() + ")bundle.get($S)", value);
        }
        initParamsBuilder.addCode("}\n");

        initParams = initParamsBuilder.build();
        TypeSpec paramsSet = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(initParams)
                .build();

        JavaFile javaFile = JavaFile.builder(packageName, paramsSet)
                .build();
        // 输出为Java文件
        // 输出的文件在build->source->apt->目录下
        // 也可以通过javaFile自定义目录
        javaFile.writeTo(mFiler);
    }
}
