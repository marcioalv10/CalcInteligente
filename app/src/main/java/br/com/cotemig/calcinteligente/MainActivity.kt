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
        binding.bt2.setOnClickListener {
            preenche("2")
        }
        binding.bt3.setOnClickListener {
            preenche("3")
        }
        binding.bt4.setOnClickListener {
            preenche("4")
        }
        binding.bt5.setOnClickListener {
            preenche("5")
        }
        binding.bt6.setOnClickListener {
            preenche("6")
        }
        binding.bt7.setOnClickListener {
            preenche("7")
        }
        binding.bt8.setOnClickListener {
            preenche("8")
        }
        binding.bt9.setOnClickListener {
            preenche("9")
        }


        binding.btAC.setOnClickListener {
            binding.tvVisor.text = ""
            valorStr = ""
        }



    }

    private fun preenche(s: String) {

        if(!valorStr.equals("0")){

            valorStr += s
        }else{
            valorStr = s
        }

        binding.tvVisor.text = valorStr
    }
}