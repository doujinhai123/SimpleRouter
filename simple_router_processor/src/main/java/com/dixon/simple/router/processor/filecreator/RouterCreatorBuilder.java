package com.dixon.simple.router.processor.filecreator;

import com.dixon.simple.router.api.IRouterCreator;
import com.dixon.simple.router.api.SRouterData;
import com.dixon.simple.router.api.SimpleRouter;
import com.dixon.simple.router.base.core.Constant;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * 用于生成各个module的RouterCreator_xxx文件
 */
public class RouterCreatorBuilder implements IJavaFileBuilder {

    // 本module内被SimpleRouter标注的集合 用于生成路由表
    private Set<? extends Element> mElements;
    // 用于生成独一无二的类名
    private String mModuleName;
    // 用于将java文件输出到Build目录下
    private Filer mFiler;

    public RouterCreatorBuilder(Set<? extends Element> elements, String moduleName, Filer filer) {
        mElements = elements;
        mModuleName = moduleName;
        mFiler = filer;
    }

    // 用法直接参考JavaPoet官方文档 https://github.com/square/javapoet
    // 不用专门学
    @Override
    public void buildJavaFile() throws IOException {
        String generateClassName = Constant.ROUTER_CREATOR_PREFIX + mModuleName;

        // 方法
        MethodSpec.Builder creatorBuilder = MethodSpec.methodBuilder("createRouter")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addAnnotation(Override.class)
                .addParameter(Map.class, "routers");
        for (Element element : mElements) {
            // 强转为类或接口程序元素
            TypeElement typeElement = (TypeElement) element;
            String className = typeElement.getQualifiedName().toString();
            String value = typeElement.getAnnotation(SimpleRouter.class).value();
            String interceptor = typeElement.getAnnotation(SimpleRouter.class).interceptor();
            creatorBuilder.addStatement("routers.put($S, new $T($S, $S))", value, SRouterData.class, className, interceptor);
        }
        MethodSpec createRouter = creatorBuilder.build();

        // 类
        TypeSpec routerCreator_ = TypeSpec.classBuilder(generateClassName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(createRouter)
                .addSuperinterface(ClassName.get(IRouterCreator.class))
                .build();

        // java文件
        JavaFile javaFile = JavaFile.builder(Constant.ROUTER_PACKAGE_NAME, routerCreator_)
                .build();

        // 输出为Java文件
        // 输出的文件在build->source->apt->目录下
        // 也可以通过javaFile自定义目录
        javaFile.writeTo(mFiler);
    }
}
