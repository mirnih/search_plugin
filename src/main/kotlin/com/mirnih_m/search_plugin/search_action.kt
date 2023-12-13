package com.mirnih_m.search_plugin
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.SelectionModel
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.ui.Messages
import java.awt.datatransfer.StringSelection
import java.awt.datatransfer.Transferable

@Suppress("DEPRECATION")
class search_action : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project
        val editor = getSelectedTextEditor(project)

        editor?.let {
            val selectedText = getSelectedText(editor)

            if (selectedText.isNotBlank()) {
                val searchString = "https://www.yandex.ru/search/?text=$selectedText"
                copyToClipboard(searchString)

                // Вызываем браузер с помощью командной строки
                Runtime.getRuntime().exec("cmd.exe /c start $searchString")

                Messages.showInfoMessage("Code copied to Yandex search pole: $selectedText", "Successful!")
            } else {
                Messages.showWarningDialog("No code selected.", "Warning")
            }
        }
    }

    private fun getSelectedTextEditor(project: com.intellij.openapi.project.Project?): Editor? {
        if (project == null) return null

        val editor = com.intellij.openapi.editor.EditorFactory.getInstance().allEditors.find { it.project == project }
        return editor
    }

    private fun getSelectedText(editor: Editor): String {
        val selectionModel: SelectionModel = editor.selectionModel
        return selectionModel.selectedText ?: ""
    }

    private fun copyToClipboard(content: String) {
        val clipboard = CopyPasteManager.getInstance()
        val transferable: Transferable = StringSelection(content)
        clipboard.setContents(transferable)
    }
}
