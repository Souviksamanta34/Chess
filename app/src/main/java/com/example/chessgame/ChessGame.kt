package com.example.chessgame

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import kotlin.math.abs


var ids = 1
var p = 1
var d = 1


class ChessGame2 : AppCompatActivity() {


    object ChessGame {

        //lateinit var player: MediaPlayer
        var canmove = ChessRules()

        var dialog = ChoosePiece()


        private var piecebox = mutableSetOf<ChessPiece>()


    init {
        reset()
    }

    fun clear(){
        piecebox.clear()
    }

    fun addPiece(piece: ChessPiece){
        piecebox.add(piece)
    }

    fun canKnightMove(from: Square,to: Square): Boolean{
        return abs(from.col - to.col) == 2 && abs(from.row - to.row)== 1 ||
                abs(from.col - to.col) == 1 && abs(from.row - to.row)== 2
    }

    fun canRookMove(from: Square, to: Square):Boolean{
        if (from.col == to.col && isClearVerticallyBetween(from, to)||
            from.row == to.row && isClearHorizontallyBetween(from, to)){
            return true
        }
        return false
    }

    private fun isClearVerticallyBetween(from: Square,to: Square):Boolean{
        if(from.col != to.col) return false
        val gap = abs(from.row - to.row) -1
        if (gap == 0) return true
        for (i in 1..gap){
            val nextRow = if (to.row > from.row) from.row + i else from.row - i
            if (pieceAt(Square(from.col, nextRow))!= null){
                return false
            }
        }
        return true
    }

    private fun isClearHorizontallyBetween(from: Square,to: Square):Boolean{
        if(from.row != to.row) return false
        val gap = abs(from.col - to.col) -1
        if (gap == 0) return true
        for (i in 1..gap){
            val nextCol = if (to.col > from.col) from.col + i else from.col - i
            if (pieceAt(Square(nextCol, from.row))!= null){
                return false
            }
        }
        return true
    }

    private fun isClearDiagonally(from: Square, to: Square):Boolean{
        if (abs(from.col - to.col) != abs(from.row - to.row)){
            return false
        }
        val gap = abs(from.col - to.col) -1
        for (i in 1..gap){
            val nextCol = if (to.col > from.col) from.col + i else from.col - i
            val nextRow = if (to.row > from.row) from.row + i else from.row - i
            if (pieceAt(nextCol,nextRow)!= null){
                return false
            }
        }
        return true
    }

    private fun canBishopMove(from: Square, to: Square):Boolean{
        if (abs(from.col - to.col)== abs(from.row - to.row)){
            return isClearDiagonally(from, to)
        }
        return false
    }

    fun canQueenMove(from: Square,to: Square):Boolean{
        return canRookMove(from, to) || canBishopMove(from, to)
    }

    fun isPiecePresent(from: Square): Boolean{
        var desc = ""
        desc += pieceAt(col = from.col, row = from.row)?.let {
            val rook = it.chessman == Chessman.ROOK
            val pawn = it.chessman == Chessman.PAWN
            val king = it.chessman == Chessman.KING
            val queen = it.chessman == Chessman.QUEEN
            val bishop = it.chessman == Chessman.BISHOP
            val knight = it.chessman == Chessman.KNIGHT
            if (rook || pawn || king || queen || bishop || knight){
                return true
            }
        }

        return false
    }


    fun canMove(from: Square,to: Square): Boolean {
        var desc = ""
        desc += pieceAt(col = from.col, row = from.row)?.let {
            //if ()
            var i : Int
            var colour = false
            val white = it.player == ChessPlayer.WHITE
            val black = it.player == ChessPlayer.BLACK
            colour = white
            if (p % 2 != 0 ){
                colour = white
            }
            else if (p % 2 == 0){
                colour = black
            }
            if (colour) {
                p++
                println("VALUE OF P and D--------------------" + p + " " + d)
                if (from.col == to.col && from.row == to.row) {
                    return false
                }
                val movingPiece = pieceAt(from) ?: return false
                return when (movingPiece.chessman) {
                    Chessman.KNIGHT -> canKnightMove(from, to)
                    Chessman.ROOK -> canRookMove(from, to)
                    Chessman.BISHOP -> canBishopMove(from, to)
                    Chessman.QUEEN -> canQueenMove(from, to)
                    Chessman.KING -> canmove.canKingMove(from, to)
                    Chessman.PAWN -> canmove.canPawnMove(from, to)
                }
                return true
            }
        }
        return false
    }

    fun movePiece(from: Square, to : Square): Int{
        if (canMove(from, to)){
            movePiece(from.col, from.row, to.col, to.row)
            ids++
        }
        return ids
        Log.d("show"," Button $ids")
    }

    private fun movePiece(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int){
        if (fromCol == toCol && fromRow == toRow)  return
        val movingPiece = pieceAt(fromCol, fromRow) ?: return
        pieceAt(toCol, toRow)?.let {
            if (it.player == movingPiece.player){
                return
            }
            piecebox.remove(it)
        }
        piecebox.remove(movingPiece)
        addPiece(movingPiece.copy(col = toCol, row = toRow))
    }

    fun reset() {
        clear()
        for (i in 0 until 2)  {
            addPiece(ChessPiece(0 + i * 7,0,ChessPlayer.WHITE,Chessman.ROOK , R.drawable.rook_white))
            addPiece(ChessPiece(0 + i * 7,7,ChessPlayer.BLACK,Chessman.ROOK , R.drawable.rook_black))

            addPiece(ChessPiece(1 + i *5,0,ChessPlayer.WHITE,Chessman.KNIGHT , R.drawable.knight_white))
            addPiece(ChessPiece(1 + i *5,7,ChessPlayer.BLACK,Chessman.KNIGHT, R.drawable.knight_black))

            addPiece(ChessPiece(2 + i * 3,0,ChessPlayer.WHITE,Chessman.BISHOP , R.drawable.bishop_white))
            addPiece(ChessPiece(2 + i * 3,7,ChessPlayer.BLACK,Chessman.BISHOP , R.drawable.bishop_black))
        }

        for (i in 0 until 8){
            addPiece(ChessPiece(i,1,ChessPlayer.WHITE,Chessman.PAWN , R.drawable.pawn_white))
            addPiece(ChessPiece(i,6,ChessPlayer.BLACK,Chessman.PAWN ,R.drawable.pawn_black))
        }
        addPiece(ChessPiece(3,0,ChessPlayer.WHITE,Chessman.QUEEN , R.drawable.queen_white))
        addPiece(ChessPiece(3,7,ChessPlayer.BLACK,Chessman.QUEEN ,R.drawable.queen_black))
        addPiece(ChessPiece(4,0,ChessPlayer.WHITE,Chessman.KING ,R.drawable.king_white))
        addPiece(ChessPiece(4,7,ChessPlayer.BLACK,Chessman.KING ,R.drawable.king_black))
    }

    fun pieceAt(square: Square): ChessPiece? {
        return pieceAt(square.col,square.row)
    }

    fun pieceAt(col: Int, row: Int) : ChessPiece? {
        for (piece in piecebox) {
            if (col == piece.col && row == piece.row) {
                return piece
            }
        }
        return null
    }

    fun pgnBoard(): String {
        var desc = " \n"
        desc += "  a b c d e f g h\n"
        for (row in 7 downTo 0) {
            desc += "${row + 1}"
            desc += boardRow(row)
            desc += " ${row + 1}"
            desc += "\n"
        }
        desc += "  a b c d e f g h"
        return desc
    }

    override fun toString(): String {
        var desc = " \n"
        for (row in 7 downTo 0) {
            desc += "$row"
            desc += boardRow(row)
            desc += "\n"
        }
        desc += "  0 1 2 3 4 5 6 7"
        return desc
    }


    private fun boardRow(row: Int): String {
        var desc = ""
        for (col in 0 until 8) {
            desc += " "
            desc += pieceAt(col, row)?.let {
                val white = it.player == ChessPlayer.WHITE
                when (it.chessman) {
                    Chessman.KING ->  if (white) "k" else "K"
                    Chessman.QUEEN ->  if (white) "q" else "Q"
                    Chessman.BISHOP ->  if (white) "b" else "B"
                    Chessman.ROOK ->  if (white) "r" else "R"
                    Chessman.KNIGHT ->  if (white) "n" else "N"
                    Chessman.PAWN ->  if (white) "p" else "P"
                }
            } ?: "."
        }
        return desc
    }

}

}

