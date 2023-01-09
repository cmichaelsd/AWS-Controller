import stack.*

fun main(args: Array<String>) {
    println("Application starts")

    val operations = listOf(
        Operation.UPDATE
    )

    Driver.run(operations)

    println("Application ends")
}