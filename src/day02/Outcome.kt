package day02

sealed class Outcome(val score: Int)
object Loss : Outcome(score = 0)
object Draw : Outcome(score = 3)
object Win : Outcome(score = 6)
