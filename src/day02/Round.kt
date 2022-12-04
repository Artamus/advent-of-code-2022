package day02

data class Round(private val elfMove: GameMove, private val playerMove: GameMove, private val outcome: Outcome) {
    val score = outcome.score + playerMove.score

    companion object {
        private val beatingMoves = mapOf(
            Rock to Paper,
            Paper to Scissors,
            Scissors to Rock,
        )

        private val losingMoves = beatingMoves.entries.associateBy({ it.value }) { it.key }

        fun fromMoves(elfMove: GameMove, playerMove: GameMove): Round {
            val outcome = calculateOutcome(elfMove, playerMove)
            return Round(elfMove, playerMove, outcome)
        }

        fun fromMoveAndOutcome(elfMove: GameMove, outcome: Outcome): Round {
            val requiredPlayerMove = calculatePlayerMove(elfMove, outcome)
            return Round(elfMove, requiredPlayerMove, outcome)
        }

        private fun calculateOutcome(elfMove: GameMove, playerMove: GameMove): Outcome {
            if (elfMove == playerMove) return Draw

            return when (elfMove) {
                Rock -> if (playerMove == Paper) Win else Loss
                Paper -> if (playerMove == Scissors) Win else Loss
                Scissors -> if (playerMove == Rock) Win else Loss
            }
        }

        private fun calculatePlayerMove(elfMove: GameMove, outcome: Outcome): GameMove = when (outcome) {
            Draw -> elfMove
            Loss -> losingMoves[elfMove]!!
            Win -> beatingMoves[elfMove]!!
        }
    }
}
