package com.chanwoo7.filescopier

import com.intellij.icons.AllIcons
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.vfs.VirtualFile
import java.awt.datatransfer.StringSelection
import java.nio.charset.StandardCharsets


/**
 * An action to copy the content of selected files to the clipboard.
 */
class CopyFilesContentAction : AnAction(
    "Copy Files Content to Clipboard"
) {
    override fun update(e: AnActionEvent) {
        e.presentation.icon = AllIcons.Actions.Copy
    }

    override fun actionPerformed(e: AnActionEvent) {
        val files = e.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY)
        val projectRoot = e.project?.basePath ?: ""
        val builder = StringBuilder()
        val visited = mutableSetOf<String>()
        var totalFiles = 0

        if (files != null) {
            for (file in files) {
                totalFiles += appendFileOrDirectory(builder, file, projectRoot, visited)
            }
        }

        CopyPasteManager.getInstance()
            .setContents(StringSelection(builder.toString()))

        val message = if (totalFiles > 0) {
            "$totalFiles file${if (totalFiles > 1) "s" else ""} copied to clipboard!"
        } else {
            "No files found to copy."
        }

        NotificationGroupManager.getInstance()
            .getNotificationGroup("FilesCopierNotificationGroup")
            .createNotification(message, NotificationType.INFORMATION)
            .notify(e.project)
    }

    /**
     * Add content while iterating through files or directories.
     * @return number of files processed
     */
    private fun appendFileOrDirectory(
        builder: StringBuilder,
        root: VirtualFile,
        projectRoot: String,
        visited: MutableSet<String>
    ): Int {
        val queue = ArrayDeque<VirtualFile>()
        queue.add(root)
        var count = 0

        while (queue.isNotEmpty()) {
            val file = queue.removeFirst()
            if (file.isDirectory) {
                queue.addAll(file.children)
            } else {
                if (visited.add(file.path) && isValidFile(file)) {
                    appendFileContent(builder, file, projectRoot)
                    count++
                }
            }
        }
        return count
    }

    /**
     * Add the path + content + 3 line breaks of a single file.
     */
    private fun appendFileContent(builder: StringBuilder, file: VirtualFile, projectRoot: String) {
        val relativePath = file.path.removePrefix("$projectRoot/")
        val extension = file.extension ?: ""
        val languageHint = when (extension.lowercase()) {
            "kt" -> "kotlin"
            "java" -> "java"
            "js" -> "javascript"
            "ts" -> "typescript"
            "py" -> "python"
            "rb" -> "ruby"
            "go" -> "go"
            "cpp", "cc", "cxx" -> "cpp"
            "c" -> "c"
            "cs" -> "csharp"
            "php" -> "php"
            "html", "htm" -> "html"
            "css" -> "css"
            "json" -> "json"
            "xml" -> "xml"
            "md" -> "markdown"
            "sh" -> "bash"
            "yml", "yaml" -> "yaml"
            else -> ""
        }

        builder.append("### $relativePath\n")
        if (languageHint.isNotEmpty()) {
            builder.append("```$languageHint\n")
        } else {
            builder.append("```\n")
        }

        builder.append(
            try {
                String(file.contentsToByteArray(), StandardCharsets.UTF_8)
            } catch (e: Exception) {
                "// [Failed to read: ${e.message}]\n"
            }
        )
        builder.append("\n```\n\n")
    }

    /**
     * Check if the file is valid for copying.
     */
    private fun isValidFile(file: VirtualFile): Boolean {
        val name = file.name
        return !name.equals(".DS_Store", ignoreCase = true) &&
                !name.endsWith("~") &&
                !name.startsWith(".")
    }
}
