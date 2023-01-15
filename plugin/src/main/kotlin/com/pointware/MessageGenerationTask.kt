package com.pointware

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class MessageGenerationTask : DefaultTask() {
    init {
        group = PLUGIN_TASK_GROUP
    }


    @get:Input
    internal lateinit var messagePluginExtension: MessagePluginExtension

    @TaskAction
    fun generate() {
        messagePluginExtension.also {
            println(it.documentId)
            println(it.version)
        }

        val file = project.file(BUILD_OUTPUT_DIR)

        if (!file.exists()) {
            file.mkdirs()
            println("created directory: ${file.path}")
        }

//        Generator.main(arrayOf(BUILD_OUTPUT_DIR))
    }
}