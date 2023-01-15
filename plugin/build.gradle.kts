plugins {
    kotlin("jvm") version "1.7.21"
    `java-gradle-plugin`
    `maven-publish`
}


dependencies {
}

gradlePlugin {
    plugins {
        create("message") {
            id = "com.pointware.mesg-gen"
            implementationClass = "com.pointware.GeneratorPlugin"
        }
    }
}

//task("buildJar", org.gradle.jvm.tasks.Jar::class) {
//    from("build/generated/sources/generate")
//    into("build/libs")
//}

//publications {
//    maven(MavenPublication) {
//        artifact sourceJar // Publish the output of the sourceJar task
//                artifact 'my-file-name.jar' // Publish a file created outside of the build
//        artifact source: sourceJar, classifier: 'src', extension: 'zip'
//    }
//}

//publishing {
//    publications {
//        create<MavenPublication>("message") {
//            artifact(tasks["buildJar"])
//            groupId = "com.pointware.mesg"
//            artifactId = "messages"
//            version = "2.2"
//        }
//    }
//}
