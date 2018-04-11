package com.tianque.fastnetloader.gradle

import com.tianque.fastnetloader.gradle.task.CollectPreloadResTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class AppPlugin implements Plugin<Project> {
    protected Project project
    protected AppExtension extension

    @Override
    void apply(Project project) {
        this.project = project

        createExtension()
        createTask(project)
    }


    protected void createExtension() {
        // Add the 'small' extension object
        extension = project.extensions.create('fastnetloader', AppExtension, project)
    }

    protected void updateExtension() {
        extension = project.extensions.findByName('fastnetloader')
    }

    protected void createTask(Project project) {
        project.afterEvaluate {
            if (!project.android) {
                throw new IllegalStateException('Must apply \'com.android.application\' or \'com.android.library\' first!')
            }

            updateExtension()

            CollectPreloadResTask task = project.tasks.create("collectPreLoadResources", CollectPreloadResTask) {
                appExtension = getExtension()
            }
            task.group = "fastNetLoader"
//            project.tasks.find{
//                it.name.contains("merge")&&it.name.contains("Assets")
//            }.each {
//                it.dependsOn(task)
//            }
        }

    }

    protected AppExtension getExtension() {
        return this.extension
    }


}