package vn.dsc.fingerheart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object {
        // Used to load the 'fingerheart' library on application startup.
        init {
            System.loadLibrary("fingerheart")
        }
    }
}
