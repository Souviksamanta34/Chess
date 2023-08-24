package com.example.chessgame

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun clear(){
        assertNotNull(ChessGame2.ChessGame.pieceAt(Square(0,0)))
        ChessGame2.ChessGame.clear()
        assertNull(ChessGame2.ChessGame.pieceAt(Square(0,0)))
    }
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}