import java.lang.ArithmeticException

fun main(args: Array<String>) {
    val nums = arrayOf(1, 2, 3, 4, 5)
    val key = 0
    var lambda = { a: Int, b: Int -> divide(a, b) }
    process(nums, key, lambda)
}
fun process(nums: Array<Int>, key: Int, lmbd: (Int, Int) -> String) {
    for (num in nums) {
        wrapperLambd { lmbd(num, key) }
    }
}
fun divide(key1: Int, key2: Int): String {
    var res = key1 / key2
    return "" + res
}
fun wrapperLambd(lmbd: () -> String) {
    try {
        lmbd()
    } catch (e: ArithmeticException) {
        println("hi")
    }
}
