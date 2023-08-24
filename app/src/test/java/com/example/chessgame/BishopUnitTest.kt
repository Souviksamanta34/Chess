package com.example.chessgame

import org.junit.Test
import org.junit.Assert.*

class BishopUnitTest {

    @Test
    fun canBishopMove_singlePiece(){
        ChessGame2.ChessGame.clear()
        ChessGame2.ChessGame.addPiece(ChessPiece(3,3, ChessPlayer.WHITE, Chessman.BISHOP, -1 ))
        println(ChessGame2.ChessGame)
        assertTrue(ChessGame2.ChessGame.canMove(Square(3,3),Square(7,7)))
        assertFalse(ChessGame2.ChessGame.canMove(Square(3,3),Square(5,4)))
    }

    @Test
    fun canBishopMove_Blocked(){
        println(ChessGame2.ChessGame)
        assertFalse(ChessGame2.ChessGame.canMove(Square(2,0),Square(5,3)))
        assertFalse(ChessGame2.ChessGame.canMove(Square(2,0),Square(0,2)))
        assertFalse(ChessGame2.ChessGame.canMove(Square(2,7),Square(0,5)))
        assertFalse(ChessGame2.ChessGame.canMove(Square(2,0),Square(7,2)))



    }
}