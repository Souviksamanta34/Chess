package com.example.chessgame

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import java.io.PrintWriter
import java.net.ConnectException
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException
import java.util.*
import java.util.concurrent.Executors


class MainActivity2 : AppCompatActivity() , ChessDelegate{
    lateinit var url: Uri
    var gso: GoogleSignInOptions? = null
    //var rl = findViewById<LinearLayout>(R.id.lineatlayout)
    var gsc: GoogleSignInClient? = null
    lateinit var imageView1: ImageView
    lateinit var imageView2: ImageView
    //var imageView2: ImageView? = null
    //var textView1: TextView? = null
    lateinit var textView1: TextView
    var textView2: TextView? = null
    private val socketHost = "127.0.0.1"
    private val socketPORT: Int = 50000
    private val socketGuestPORT: Int = 50001
    private lateinit var chessView: ChessView
    private lateinit var resetButton: Button
    private lateinit var listenButton: Button
    private lateinit var connectButton: Button
    private lateinit var logout: Button
    private var printWriter: PrintWriter? = null
    private var serverSocket: ServerSocket? = null
    private val isEmulator = Build.FINGERPRINT.contains("generic")
    var accessToken = AccessToken.getCurrentAccessToken()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this, gso!!)
        println("VALUE OF BHI$p $d")
        chessView = findViewById<ChessView>(R.id.chess_view)
        resetButton = findViewById<Button>(R.id.reset_button)
        listenButton = findViewById<Button>(R.id.listen_button)
        connectButton = findViewById<Button>(R.id.connect_button)
        logout = findViewById<Button>(R.id.button4)
        imageView2 = findViewById<ImageView>(R.id.imageView2)
        imageView1 = findViewById<ImageView>(R.id.imageView1)
        textView1 = findViewById<TextView>(R.id.textView2)
        textView2 = findViewById<TextView>(R.id.textView)



        chessView.chessDelegate = this

        val uri = "@drawable/avatar1"
        val uri2 = "@drawable/avatar3"
        val imageResource = resources.getIdentifier(uri, null, packageName)
        val imageResource2 = resources.getIdentifier(uri2, null, packageName)
        val res = resources.getDrawable(imageResource)
        val res2 = resources.getDrawable(imageResource2)
        imageView1.setImageDrawable(res)
        imageView2.setImageDrawable(res2)
        textView1.text = "Username1"

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(resources.getString(R.string.google_app_id))
            .requestProfile()
            .requestEmail()
            .build()
        gsc = GoogleSignIn.getClient(this, gso!!)

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            val personName = acct.displayName
            url = acct.photoUrl!!
            textView1.text = personName
            Picasso.get().load(url)
                .into(imageView2);
            //imageView1.setImageURI(url)
            //email.setText(personEmail);
        }

        val accessToken = AccessToken.getCurrentAccessToken()

        if (accessToken != null) {
        val request = GraphRequest.newMeRequest(
            accessToken
        ) { `object`, response ->
            try {
                val fullname = `object`!!.getString("name")
                val url = `object`!!.getJSONObject("picture").getJSONObject("data")
                    .getString("url")
                textView1.text = fullname
                Picasso.get().load(url).into(imageView2)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            // Application code
        }
            val parameters = Bundle()
            parameters.putString("fields", "id,name,link,picture.type(large)")
        request.parameters = parameters
        request.executeAsync()
        }

        resetButton.setOnClickListener {
            chessView.invalidate()
            ChessGame2.ChessGame.reset()
            ids = 1
            p = 1
            d = 1
            serverSocket?.close()
            listenButton.isEnabled = true
        }

        listenButton.setOnClickListener {
            listenButton.isEnabled = false
            val port = if (isEmulator) socketGuestPORT else socketPORT
            Toast.makeText(this,"socket server listening to $port", Toast.LENGTH_SHORT).show()
                Executors.newSingleThreadExecutor().execute {
                    ServerSocket(port).let { srvSkt ->
                        serverSocket = srvSkt
                        try {
                            val socket = srvSkt.accept()
                            receiveMove(socket)
                        } catch (_: SocketException){

                        }
                    }
                }
        }

        connectButton.setOnClickListener {
            Executors.newSingleThreadExecutor().execute {
                try {
                    val socket = Socket(socketHost, socketPORT)
                    receiveMove(socket)
                } catch (e: ConnectException){
                    runOnUiThread {
                        Toast.makeText(this, "Connection failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        logout.setOnClickListener {
            signOut()
        }
    }

    private fun receiveMove(socket: Socket){
        val scanner = Scanner(socket.getInputStream())
        printWriter = PrintWriter(socket.getOutputStream(), true)
        while (scanner.hasNextLine()){
            val move: List<Int> = scanner.nextLine().split(",").map{it.toInt() }
            runOnUiThread {
                ChessGame2.ChessGame.movePiece(Square(move[0], move[1]), Square(move[2], move[3]))
                chessView.invalidate()
            }
        }
    }

    override fun pieceAt(square: Square): ChessPiece? = ChessGame2.ChessGame.pieceAt(square)

    override fun movePiece(from: Square, to: Square) {
        ChessGame2.ChessGame.movePiece(from, to)
        chessView.invalidate()

        printWriter?.let {
            val moveSte = "${from.col},${from.row},${to.col},${to.row}"
            Executors.newSingleThreadExecutor().execute {
                it.println(moveSte)
            }
        }

   }

    fun signOut() {
        //textView1 = findViewById<TextView>(R.id.textView2)
        //textView1.text = "Username1"
        gsc?.signOut()?.addOnCompleteListener(OnCompleteListener { task: Task<Void?>? ->
            LoginManager.getInstance().logOut()
            startActivity(Intent(this@MainActivity2, Login::class.java))
            finish()
            accessToken = null
        })
    }
}
