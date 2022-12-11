package day07

sealed interface FileSystemNode {
    fun size(): Int
}

class FileNode(val name: String, val size: Int, val parent: DirectoryNode) : FileSystemNode {
    override fun size() = size

    override fun toString() = "FileNode(name=$name, size=$size)"
}

sealed class DirectoryNode : FileSystemNode {

    abstract val children: List<FileSystemNode>

    abstract fun addAll(nodes: List<FileSystemNode>)

    override fun size(): Int = children.sumOf { childNode ->
        when (childNode) {
            is DirectoryNode -> childNode.size()
            is FileNode -> childNode.size
        }
    }

    fun getChildDirectories() = children.filterIsInstance<Directory>()
    fun getAllChildDirectories(): List<DirectoryNode> =
        listOf(this) + getChildDirectories().flatMap { it.getAllChildDirectories() }
}

data class Root(override val children: MutableList<FileSystemNode> = mutableListOf()) : DirectoryNode() {

    override fun addAll(nodes: List<FileSystemNode>) {
        children.addAll(nodes)
    }
}

class Directory(
    val name: String, val parent: DirectoryNode, override val children: MutableList<FileSystemNode> = mutableListOf()
) : DirectoryNode() {

    override fun addAll(nodes: List<FileSystemNode>) {
        children.addAll(nodes)
    }

    override fun toString(): String = "Directory(name=$name, children=$children)"
}
