package com.dixon.simple.router.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public abstract class SimpleAbstractProcessor extends AbstractProcessor {

    protected Filer mFiler;       // 文件管理类，生成Java文件需要
    protected Types mTypesUtils;  // 类型处理类
    protected Elements mElements; // Element处理类，可以获取包名

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        // 固定写法
        mFiler = processingEnvironment.getFiler();
        mTypesUtils = processingEnvironment.getTypeUtils();
        mElements = processingEnvironment.getElementUtils();
    }

    // APT支持的版本号 必须重写 固定写法
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();// 表示支持最新的Java版本
    }
}
