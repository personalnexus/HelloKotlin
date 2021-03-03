class Node<V> {
    val childrenByCharacter: MutableMap<Char, Node<V>> = HashMap()
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
            node = node.childrenByCharacter.getOrElse(char, {
                val newNode = Node<V>()
                node.childrenByCharacter[char] = newNode
                newNode})
        }
        val oldHasValue = node.hasValue
        val oldValue = if (node.hasValue) node.value else null
        node.value = value
        node.hasValue = true
        return Pair(oldHasValue, oldValue)
    }

    fun get(key: String): Pair<Boolean, V?>{
        var node: Node<V>? = root
        for (char in key){
            node = node!!.childrenByCharacter[char] ?: break
        }
        return node.toPair()
    }
}

fun main() {
    val trie = Trie<Int>()
    println("Added one to 1: ${trie.add("one", 1)}")
    println("Added two to 22: ${trie.add("two", 22)}")
    println("Added null to null: ${trie.add("null", null)}")
    println("Added two to 2: ${trie.add("two", 2)}")

    println("Getting one ${trie.get("one")}")
    println("Getting two ${trie.get("two")}")
    println("Getting null ${trie.get("null")}")
    println("Getting three ${trie.get("three")}")
}