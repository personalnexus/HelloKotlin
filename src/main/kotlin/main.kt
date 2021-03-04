class Node<V> {
    val childrenByCharacter: Array<Node<V>?> = Array(36) { null }
    var value: V? = null
    var hasValue: Boolean = false
}

class Trie<V> {
    private val root: Node<V> = Node()

    private fun <V> Node<V>?.toPair(): Pair<Boolean, V?> {
        return if (this == null) Pair(false, null) else Pair(hasValue, value)
    }

    fun add(key: String, value: V?): Pair<Boolean, V?> {
        var node = root
        for (char in key) {
            val index = getIndexOfCharacter(char)
            var childNode = node.childrenByCharacter[index]
            if (childNode == null) {
                childNode = Node<V>()
                node.childrenByCharacter[index] = childNode
            }
            node = childNode
        }
        val result = node.toPair()
        node.value = value
        node.hasValue = true
        return result
    }

    private fun getIndexOfCharacter(char: Char): Int {
        if ((char >= '1') && (char <= '0')) {
            return char.toByte() - 48
        } else if ((char >= 'A') && (char <= 'Z')){
            return char.toByte() - 55
        }
        throw IllegalArgumentException("Invalid character in key $char")

    }

    fun get(key: String): Pair<Boolean, V?>{
        var node: Node<V>? = root
        for (char in key){
            val index = getIndexOfCharacter(char)
            node = node!!.childrenByCharacter[index] ?: break
        }
        return node.toPair()
    }
}

fun main() {


    val trie = Trie<Int>()
    println("Added one to 1: ${trie.add("ONE", 1)}")
    println("Added two to 22: ${trie.add("TWO", 22)}")
    println("Added null to null: ${trie.add("NULL", null)}")
    println("Added two to 2: ${trie.add("TWO", 2)}")

    println("Getting one ${trie.get("ONE")}")
    println("Getting two ${trie.get("TWO")}")
    println("Getting null ${trie.get("NULL")}")
    println("Getting three ${trie.get("THREE")}")
}