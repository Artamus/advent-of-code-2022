package day07

import readInput

fun main() {
    val inputData = readInput("input-data-7.txt")

    val commands = parseCommands(inputData)

    val fileSystemRoot = assembleFileSystem(commands)

    val sumOfSmallDirectories =
        fileSystemRoot.getAllChildDirectories().filter { it.size() <= 100000 }.sumOf { it.size() }
    println("Sum of all directory sizes under size threshold is $sumOfSmallDirectories")

    val availableSpace = 70000000 - fileSystemRoot.size()
    val additionalFreeSpaceRequired = 30000000 - availableSpace
    val directoryToDeleteSize =
        fileSystemRoot.getAllChildDirectories().sortedBy { it.size() }.first { it.size() > additionalFreeSpaceRequired }
            .size()
    println("Size of smallest directory that will free up enough space is $directoryToDeleteSize")
}

fun parseCommands(inputRows: List<String>) = inputRows.fold(listOf<Command>()) { commands, line ->
    if (line.startsWith("$")) {
        val fullCommand = line.removePrefix("$ ")
        val command = parseCommand(fullCommand)

        commands + listOf(command)
    } else {
        val lastCommand = commands.last()
        if (lastCommand !is ListContents) throw IllegalStateException("Non-command lines should only follow list command.")
        val revisedLastCommand = lastCommand.copy(outputs = lastCommand.outputs + listOf(line))

        commands.dropLast(1) + listOf(revisedLastCommand)
    }
}

fun assembleFileSystem(commands: List<Command>): Root {
    val rootNode = Root()
    var selectedNode: DirectoryNode = rootNode
    for (command in commands.drop(1)) {
        when (command) {
            is MoveUpOneDirectory -> {
                when (selectedNode) {
                    is Root -> throw IllegalArgumentException("Cannot move past root directory.")
                    is Directory -> selectedNode = selectedNode.parent
                }
            }

            is ChangeDirectory -> {
                val childDirectory = selectedNode.getChildDirectories().find { it.name == command.targetDirectory }
                    ?: throw IllegalArgumentException("Cannot navigate to directory \"${command.targetDirectory}\", not a child of node: \"$selectedNode\".")
                selectedNode = childDirectory
            }

            is ListContents -> {
                val outputNodes = command.outputs.map {
                    val parts = it.split(" ")
                    if (parts[0] == "dir") {
                        Directory(parts[1], selectedNode)
                    } else {
                        FileNode(parts[1], parts[0].toInt(), selectedNode)
                    }
                }

                selectedNode.addAll(outputNodes)
            }
        }
    }

    return rootNode
}
