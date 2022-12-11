package day07

sealed interface Command

object MoveUpOneDirectory : Command
data class ChangeDirectory(val targetDirectory: String) : Command
data class ListContents(val outputs: List<String> = emptyList()) : Command

fun parseCommand(command: String): Command = when {
    command == "cd .." -> MoveUpOneDirectory
    command.startsWith("cd") -> {
        val targetDirectory = command.removePrefix("cd ")
        ChangeDirectory(targetDirectory)
    }

    command == "ls" -> ListContents()
    else -> throw IllegalArgumentException("Unknown command \"$command\"")
}
