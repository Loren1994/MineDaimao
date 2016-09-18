package com.example.groovyplugindemo

import org.gradle.api.Plugin
import org.gradle.api.Project

//插件的入口类
class TestPlugin implements Plugin<Project> {

    //扩展别名
    public static final String EXTENSION_NAME="printMessage"

    @Override
    void apply(Project project) {
        //创建扩展
        TestExtension extension= project.extensions.create(EXTENSION_NAME,TestExtension)
        project.task printMsg << {
            println(extension.message)
        }
    }
}