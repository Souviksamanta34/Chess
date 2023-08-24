package com.example.chessgame

import org.junit.Test
import org.junit.Assert.*

class CanKnightMoveUnitTest {

    @Test
    fun canKnightMove_singlePiece(){
        ChessGame2.ChessGame.clear()
        ChessGame2.ChessGame.addPiece(ChessPiece(3,3, ChessPlayer.WHITE, Chessman.KNIGHT, -1))
        println(ChessGame2.ChessGame)
        assertTrue(ChessGame2.ChessGame.canKnightMove(Square(3,3), Square(5,4)))
        assertTrue(ChessGame2.ChessGame.canKnightMove(Square(3,3),Square(4,5)))

    }
}