package br.com.cotemig.calcinteligente

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.cotemig.calcinteligente.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // private var _binding: ActivityMainBinding? = null
    //  private val binding get() = _binding!!
    private lateinit var binding: ActivityMainBinding
    var valorStr = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bt0.setOnClickListener {
            preenche("0")
        }
        binding.bt1.setOnClickListener {
            preenche("1")
        }


    }

    private fun preenche(s: String) {
        valorStr = s
        binding.tvVisor.text = valorStr
    }
}