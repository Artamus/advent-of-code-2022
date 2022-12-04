package day02

sealed class GameMove(val score: Int)
object Rock : GameMove(score = 1)
object Paper : GameMove(score = 2)
object Scissors : GameMove(score = 3)