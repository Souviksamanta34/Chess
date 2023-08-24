package com.example.chessgame
import org.junit.Test
import org.junit.Assert.*


class CanRookMove {

    @Test
    fun canRookMove_singlePiece(){
        ChessGame2.ChessGame.clear()
        ChessGame2.ChessGame.addPiece(ChessPiece(3,3, ChessPlayer.WHITE, Chessman.ROOK, -1 ))
        println(ChessGame2.ChessGame)

        assertTrue(ChessGame2.ChessGame.canMove(Square(3,3),Square(3,0)))
        assertFalse(ChessGame2.ChessGame.canMove(Square(3,3), Square(4,4)))
        assertTrue(ChessGame2.ChessGame.canMove(Square(3,3),Square(7,3)))

    }

    @Test
    fun canRookMove_blockedHorizontally(){
        ChessGame2.ChessGame.clear()
        ChessGame2.ChessGame.addPiece(ChessPiece(3,3, ChessPlayer.WHITE, Chessman.ROOK, -1 ))
        ChessGame2.ChessGame.addPiece(ChessPiece(5,3, ChessPlayer.WHITE, Chessman.KNIGHT, -1 ))
        ChessGame2.ChessGame.addPiece(ChessPiece(2,3, ChessPlayer.WHITE, Chessman.KNIGHT, -1 ))

        println(ChessGame2.ChessGame)
        assertFalse(ChessGame2.ChessGame.canMove(Square(3,3), Square(7,3)))
        assertFalse(ChessGame2.ChessGame.canMove(Square(3,3),Square(6,3)))
        assertTrue(ChessGame2.ChessGame.canMove(Square(3,3),Square(4,3)))
        assertFalse(ChessGame2.ChessGame.canMove(Square(3,3),Square(0,3)))
        assertFalse(ChessGame2.ChessGame.canMove(Square(3,3),Square(1,3)))
    }

    @Test
    fun canRookMove_blockedVertically(){
        ChessGame2.ChessGame.clear()
        ChessGame2.ChessGame.addPiece(ChessPiece(3,3, ChessPlayer.WHITE, Chessman.ROOK, -1 ))
        ChessGame2.ChessGame.addPiece(ChessPiece(3,5, ChessPlayer.WHITE, Chessman.KNIGHT, -1 ))
        ChessGame2.ChessGame.addPiece(ChessPiece(3,2, ChessPlayer.WHITE, Chessman.KNIGHT, -1 ))

        println(ChessGame2.ChessGame)
        assertFalse(ChessGame2.ChessGame.canMove(Square(3,3), Square(3,7)))
        assertFalse(ChessGame2.ChessGame.canMove(Square(3,3),Square(3,6)))
        assertTrue(ChessGame2.ChessGame.canMove(Square(3,3),Square(3,4)))
        assertFalse(ChessGame2.ChessGame.canMove(Square(3,3),Square(3,0)))
        assertFalse(ChessGame2.ChessGame.canMove(Square(3,3),Square(3,1)))
    }
}