package com.example.chessgame

import org.junit.Assert
import org.junit.Test

class PawnUnitTest {

    @Test
    fun canPawnMove_firstMove(){
        //ChessGame.clear()
        //ChessGame.addPiece(ChessPiece(3,3, ChessPlayer.WHITE, Chessman.PAWN, -1 ))
        println(ChessGame2.ChessGame)
        Assert.assertTrue(ChessGame2.ChessGame.canMove(Square(3, 1), Square(3, 2)))
        Assert.assertTrue(ChessGame2.ChessGame.canMove(Square(3, 1), Square(3, 3)))
        Assert.assertFalse(ChessGame2.ChessGame.canMove(Square(3, 1), Square(4, 3)))

    }
}