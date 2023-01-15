package com.pointware

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.jvm.tasks.Jar

private const val GENERATE_TASK_NAME = "gen"
private const val EXTENSION_CONFIG_NAME = "genConfig"
internal const val PLUGIN_TASK_GROUP = "my tasks"
internal const val BUILD_OUTPUT_DIR = "build/generated/sources/gen/java/main"

private const val BUILD_LIBS = "build/libs"

private const val MESSAGE_JAR = "gen-message.jar"

class MessagePlugin: Plugin<Project> {

    override fun apply(project: Project) {

        project.plugins.apply(MavenPublishPlugin::class.java)

        val extensionConfig = project.extensions.create(EXTENSION_CONFIG_NAME, MessagePluginExtension::class.java)
        val generateTask = project.tasks.create(GENERATE_TASK_NAME, MessageGenerationTask::class.java) {
            it.messagePluginExtension = extensionConfig
        }

        val buildJar = project.tasks.register("buildMessageJar", Jar::class.java) {
            it.group = PLUGIN_TASK_GROUP
            it.from(BUILD_OUTPUT_DIR)
            it.into(BUILD_LIBS)
            it.archiveFileName.set(MESSAGE_JAR)
            it.setDependsOn(listOf(generateTask))
        }

        project.tasks.register("allOneToMavenLocal") { task ->
            task.group = PLUGIN_TASK_GROUP
            task.setDependsOn(listOf("publishNewMessagePublicationToMavenLocal"))
        }

        project.afterEvaluate { proj ->
            proj.extensions.configure(PublishingExtension::class.java) { extension ->
                extension.publications.register("newMessage", MavenPublication::class.java) { publication ->
                    publication.artifact("${BUILD_LIBS}/${MESSAGE_JAR}")
                    publication.groupId = extensionConfig.group
                    publication.artifactId = extensionConfig.artifactId
                    publication.version = extensionConfig.version
                }
            }
        }
    }
}