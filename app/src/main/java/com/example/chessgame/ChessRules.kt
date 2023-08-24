package com.example.chessgame

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaPlayer.create
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import kotlin.math.abs

var bhi =0;
class ChessRules : AppCompatActivity() {
    lateinit var mediaPlayer: MediaPlayer
    var dialog = ChoosePiece()

    fun canKingMove(from: Square,to: Square):Boolean{
        //var os = ChessGame2.ChessGame.dialog.show(supportFragmentManager, "")
        if (ChessGame2.ChessGame.canQueenMove(from, to)){
            val deltacol = abs(from.col - to.col)
            val deltarow = abs(from.row - to.row)
            return deltacol == 1 && deltarow == 1 || deltacol + deltarow == 1
        }

        return false
    }


    fun canPawnMove(from: Square, to: Square): Boolean{
        //create(/* context = */ this, /* resid = */ R.raw.chessmove).also { this.mediaPlayer = it }
//        if(mediaPlayer.isPlaying){
//            mediaPlayer.pause()
//            mediaPlayer.seekTo(0)
//        }
//        mediaPlayer.start()
//        player = MediaPlayer.create(this,R.raw.chessmove).also { player = it }
//        player.start()
//        dialog.show(supportFragmentManager,"")
        var desc = ""
        desc += ChessGame2.ChessGame.pieceAt(col = from.col, row = from.row)?.let {
            //FragmentManager getFragmentManager ()

            val white = it.player == ChessPlayer.WHITE
            val black = it.player == ChessPlayer.BLACK
            val piece = it.chessman == Chessman.PAWN
            var presentjustafter =
                ChessGame2.ChessGame.isPiecePresent(Square((from.col), (from.row + 1)))
            var presentjustbefore =
                ChessGame2.ChessGame.isPiecePresent(Square((from.col), (from.row - 1)))
            var presentjustafterrightdiagonal =
                ChessGame2.ChessGame.isPiecePresent(Square((from.col + 1), (from.row + 1)))
            var presentjustbeforerightdiagonal =
                ChessGame2.ChessGame.isPiecePresent(Square((from.col + 1), (from.row - 1)))
            var presentjustafterleftdiagonal =
                ChessGame2.ChessGame.isPiecePresent(Square((from.col - 1), (from.row + 1)))
            var presentjustbeforeleftdiagonal =
                ChessGame2.ChessGame.isPiecePresent(Square((from.col - 1), (from.row - 1)))
            val absent = ChessGame2.ChessGame.pieceAt(col = from.col, row = from.row + 1)
            if (from.col == to.col) {
                if (from.row == 1 && white) {
                    bhi =1;
                    Log.d(bhi.toString(), "value of bhi " + bhi)
                    //dialog.show(supportFragmentManager,"")
//                    if (bhi==1){
//                    dialog.show(supportFragmentManager,"")
//                    }
                    return to.row == 2 || to.row == 3
                } else if (from.row == 6 && black) {
                    return to.row == 5 || to.row == 4
                }
                else if (from.row == 2 && white && !presentjustafter) {
                    return to.row == from.row+1
                }
                else if (from.row == 3 && white && !presentjustafter) {
                    return to.row == from.row+1
                }
                else if (from.row == 4 && white && !presentjustafter) {
                    return to.row == 5
                }
                else if (from.row == 5 && white && !presentjustafter) {
                    return to.row == 6
                }
                else if (from.row == 6 && white && !presentjustafter) {
                    //dialog.run { show(childFragmentManager, "okay") }
                    return to.row == 7
                }
                else if (from.row == 5 && black && !presentjustbefore) {
                    return to.row == 4
                }
                else if (from.row == 4 && black && !presentjustbefore) {
                    return to.row == 3
                }
                else if (from.row == 3 && black && !presentjustbefore) {
                    return to.row == 2
                }
                else if (from.row == 2 && black && !presentjustbefore) {
                    return to.row == 1
                }
                else if (from.row == 1 && black && !presentjustbefore) {
                    return to.row == 0
                }
            }
            else if (from.col == to.col+1){
                if (from.row == 2 && white && presentjustafterleftdiagonal) {
                    return to.row == 3
                }
                else if (from.row == 3 && white && presentjustafterleftdiagonal) {
                    return to.row == 4
                }
                else if (from.row == 4 && white && presentjustafterleftdiagonal) {
                    return to.row == 5
                }
                else if (from.row == 5 && white && presentjustafterleftdiagonal) {
                    return to.row == 6
                }
                else if (from.row == 6 && white && presentjustafterleftdiagonal) {
                    return to.row == 7
                }
                else if (from.row == 7 && white && presentjustafterleftdiagonal) {
                    return to.row == 8
                }
                else if (from.row == 7 && black && presentjustbeforeleftdiagonal) {
                    return to.row == 6
                }
                else if (from.row == 6 && black && presentjustbeforeleftdiagonal) {
                    return to.row == 5
                }
                else if (from.row == 5 && black && presentjustbeforeleftdiagonal) {
                    return to.row == 4
                }
                else if (from.row == 4 && black && presentjustbeforeleftdiagonal) {
                    return to.row == 3
                }
                else if (from.row == 3 && black && presentjustbeforeleftdiagonal) {
                    return to.row == 2
                }
                else if (from.row == 2 && black && presentjustbeforeleftdiagonal) {
                    return to.row == 1
                }
            }
            else if (from.col == to.col-1){
                if (from.row == 2 && white && presentjustafterrightdiagonal) {
                    return to.row == 3
                }
                else if (from.row == 3 && white  && presentjustafterrightdiagonal) {
                    return to.row == 4
                }
                else if (from.row == 4 && white  && presentjustafterrightdiagonal) {
                    return to.row == 5
                }
                else if (from.row == 5 && white  && presentjustafterrightdiagonal) {
                    return to.row == 6
                }
                else if (from.row == 6 && white  && presentjustafterrightdiagonal) {
                    return to.row == 7
                }
                else if (from.row == 6 && black  && presentjustbeforerightdiagonal) {
                    return to.row == 5
                }
                else if (from.row == 5 && black  && presentjustbeforerightdiagonal) {
                    return to.row == 4
                }
                else if (from.row == 4 && black  && presentjustbeforerightdiagonal) {
                    return to.row == 3
                }
                else if (from.row == 3 && black  && presentjustbeforerightdiagonal) {
                    return to.row == 2
                }
                else if (from.row == 2 && black  && presentjustbeforerightdiagonal) {
                    return to.row == 1
                }

            }
        }
        return false
    }

    private fun requireContext(): Context {
            return this
    }

    override fun onDestroy() {
        if (this::mediaPlayer.isInitialized){
            mediaPlayer.stop()
            mediaPlayer.release()
        }
        super.onDestroy()
    }


}