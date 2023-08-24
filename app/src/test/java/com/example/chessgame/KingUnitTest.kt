package com.example.chessgame
import org.junit.Assert
import org.junit.Test

class KingUnitTest {

    @Test
    fun basicKingMove_singlePiece(){
        ChessGame2.ChessGame.clear()
        ChessGame2.ChessGame.addPiece(ChessPiece(3,3, ChessPlayer.WHITE, Chessman.KING, -1 ))
        println(ChessGame2.ChessGame)
        Assert.assertTrue(ChessGame2.ChessGame.canMove(Square(3, 3), Square(2, 2)))
        Assert.assertTrue(ChessGame2.ChessGame.canMove(Square(3, 3), Square(2, 3)))
        Assert.assertTrue(ChessGame2.ChessGame.canMove(Square(3, 3), Square(2, 4)))

        Assert.assertTrue(ChessGame2.ChessGame.canMove(Square(3, 3), Square(4, 2)))
        Assert.assertTrue(ChessGame2.ChessGame.canMove(Square(3, 3), Square(4, 3)))
        Assert.assertTrue(ChessGame2.ChessGame.canMove(Square(3, 3), Square(4, 4)))

        Assert.assertTrue(ChessGame2.ChessGame.canMove(Square(3, 3), Square(3, 2)))
        Assert.assertTrue(ChessGame2.ChessGame.canMove(Square(3, 3), Square(3, 4)))

        Assert.assertFalse(ChessGame2.ChessGame.canMove(Square(3, 3), Square(5, 3)))


    }
}