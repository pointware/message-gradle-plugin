package com.pointware

interface MessagePluginExtension {
    var version: String
    var documentId: List<String>
    var artifactId: String
    var group: String?
}