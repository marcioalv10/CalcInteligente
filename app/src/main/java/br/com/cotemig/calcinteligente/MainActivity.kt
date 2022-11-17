package br.com.cotemig.calcinteligente

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import br.com.cotemig.calcinteligente.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    // private var _binding: ActivityMainBinding? = null
    //  private val binding get() = _binding!!
    private lateinit var binding: ActivityMainBinding


    var valorStr = ""
    var desl = 0
    var sinal = ""
    var memStr = ""
    var resultado = 0.0
    var design = 0

    var numMem = 0.0
    var numMem2 = 0.0
    var modeParenteses = 0

    var sinalp = ""
    var resultadop = 0.0
    var mode = 1


    var dec = DecimalFormat("#,###.#######")


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var pDownX = 0
        var pDownY = 0
        var pUpX = 0
        var pUpY = 0
        // var cont = 0


        with(binding.tvVisor) {
            setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                    return true
                }

            })
        }


        binding.tvVisor.setOnTouchListener { v, event ->
            val action = event.action


            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    pDownX = event.x.toInt()
                    pDownY = event.y.toInt()
                }
                MotionEvent.ACTION_MOVE -> {

                    if (pDownX > pUpX + 30) {
                        //   Toast.makeText(this, "Moveu Esquerda "+ cont.toString(), Toast.LENGTH_SHORT).show()

                        //  Toast.makeText(this, "moveu pDonwx ="+pDownX.toString(), Toast.LENGTH_SHORT).show()
                        // Toast.makeText(this, "moveu pupx = "+pUpX.toString(), Toast.LENGTH_SHORT).show()
                        if (sinal != "#" && !binding.tvVisor.text.contains(".")) {

                            binding.tvVisor.text = binding.tvVisor.text.dropLast(1)

                            valorStr = binding.tvVisor.text.toString()
                        }

                        pDownX = pUpX

                    } else if (pDownX < pUpX - 30) {
                        // Toast.makeText(this, "Moveu Direita"+cont.toString(), Toast.LENGTH_SHORT).show()

                        // Toast.makeText(this, "moveu pDonwx ="+pDownX.toString(), Toast.LENGTH_SHORT).show()
                        // Toast.makeText(this, "moveu pupx = "+pUpX.toString(), Toast.LENGTH_SHORT).show()

                        if (sinal != "#" && !binding.tvVisor.text.contains(".")) {

                            binding.tvVisor.text = binding.tvVisor.text.drop(1)
                            valorStr = binding.tvVisor.text.toString()
                        }

                        pDownX = pUpX
                    } else {

                    }

                    //Detecta movimentos para cima /baixo
                    if (pDownY > pUpY) {
                        // Toast.makeText(this, "Moveu cima ${cont} ${pDownX} ${pDownY}", Toast.LENGTH_SHORT).show()
                    } else {
                        //Toast.makeText(this, "Moveu baixo ${cont} ${pDownX} ${pDownY}", Toast.LENGTH_SHORT).show()

                    }

                    //  cont ++
                }
                MotionEvent.ACTION_UP -> {

                    pUpX = event.x.toInt()
                    pUpY = event.y.toInt()
                }
                MotionEvent.ACTION_CANCEL -> {
                    // Toast.makeText(this, "Action cancel", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // Toast.makeText(this, "Default", Toast.LENGTH_SHORT).show()
                }
            }


            true
        }


        var numDesign = getInstaceSharedPreference().getInt("designSalvo", 0)
        design = numDesign

        escolheCor()

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

        binding.btVrigula.setOnClickListener {
            separaDecimal()
        }

        binding.tvMem.setOnLongClickListener {
            //binding.tvMem.visibility = View.INVISIBLE
            // binding.tvMem.visibility = if(binding.tvMem.isVisible) View.INVISIBLE else View.VISIBLE
            binding.tvMem.visibility = if (binding.tvMem.isVisible) {

                View.GONE

            } else {
                View.VISIBLE

            }

            binding.tvMem2.visibility = View.VISIBLE


//            binding.tvMem.visibility = if(binding.tvMem.isInvisible){
//
//                View.VISIBLE
//
//            } else{
//                View.INVISIBLE
//            }

            // Toast.makeText(this, "teste", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }


        binding.tvMem2.setOnLongClickListener {

            binding.tvMem.visibility = if (binding.tvMem.isVisible) {

                View.GONE


            } else {
                View.VISIBLE

            }

            binding.tvMem2.visibility = View.GONE
            //   Toast.makeText(this, "teste clicou memoria 2", Toast.LENGTH_SHORT).show()

            return@setOnLongClickListener true
        }



        binding.btMais.setOnClickListener {
            if (!valorStr.isEmpty() && !binding.tvVisor.text.equals("-")) {
                calcular()
            }
            sinal = "+"
            gravarsinal()
            valorStr = ""

            liberaParenteses()
        }

        binding.btMenos.setOnClickListener {
            Log.i("entrou", "clicou btn menos")

            if (!valorStr.isEmpty() && !binding.tvVisor.text.equals("-")) {
                Log.i("entrou", "1 if")
                calcular()
            } else {
                Log.i("entrou", "else")
                //  binding.tvMem.text ="-"
            }

            sinal = "-"
            gravarsinal()
            valorStr = ""

            liberaParenteses()

        }

        binding.btMultiplicacao.setOnClickListener {
            if (!valorStr.isEmpty() && !binding.tvVisor.text.equals("-")) {
                calcular()
            }
            sinal = "x"
            gravarsinal()
            valorStr = ""

            liberaParenteses()
        }

        binding.btDivisao.setOnClickListener {
            if (!valorStr.isEmpty() && !binding.tvVisor.text.equals("-")) {
                calcular()
            }

            sinal = "÷"
            gravarsinal()
            valorStr = ""

            liberaParenteses()
        }

        binding.btDivisao.setOnLongClickListener {

            design++
            escolheCor()
            salvardesign()
            return@setOnLongClickListener true
        }


        binding.btCalcular.setOnClickListener {
            calcular()
            sinal = "#"
        }

        binding.btAC.setOnClickListener {
            resetar()
        }

        binding.btAC.setOnLongClickListener {

            when (mode) {
                1 -> {
                    Toast.makeText(this, "Modo Calculadora financeira ativado", Toast.LENGTH_SHORT)
                        .show()
                    dec = DecimalFormat("#,##0.00")
                    mode = 2
                }
                2 -> {
                    Toast.makeText(this, "Modo Calculadora padrão ativado", Toast.LENGTH_SHORT)
                        .show()
                    dec = DecimalFormat("#,###.#######")
                    mode = 1
                }

            }


            return@setOnLongClickListener true
        }

        binding.btPorcentagem.setOnClickListener {
            funcaoPorcentagem()

        }

        binding.btMaisMenos.setOnClickListener {

            funcaoMaisMenos()

        }


        binding.btMaisMenos.setOnLongClickListener {

            abreParenteses()
            return@setOnLongClickListener true
        }

        binding.btPorcentagem.setOnLongClickListener {

            fechaParenteses()
            return@setOnLongClickListener true
        }

    }

    private fun salvardesign() {
        getInstaceSharedPreference().edit {
            putInt("designSalvo", design)
        }
    }

    private fun getInstaceSharedPreference(): SharedPreferences {
        return getSharedPreferences("br.com.cotemig.calcinteligente", Context.MODE_PRIVATE)
    }


    private fun escolheCor() {
        when (design) {
            1 -> {
                setColorsDesign(R.drawable.button_design_green)
            }
            2 -> {
                setColorsDesign(R.drawable.button_design_pink)
            }
            3 -> {
                setColorsDesign(R.drawable.button_design_orange)
            }
            4 -> {
                setColorsDesign(R.drawable.button_design_red)
            }
            5 -> {
                setColorsDesign(R.drawable.button_design_purple)
            }
            6 -> {
                setColorsDesign(R.drawable.button_function)
            }
            7 -> {
                setColorsDesign(R.drawable.button_tecla)

                binding.btDivisao.setTextColor(getColor(R.color.black))
                binding.btMultiplicacao.setTextColor(getColor(R.color.black))
                binding.btMenos.setTextColor(getColor(R.color.black))
                binding.btMais.setTextColor(getColor(R.color.black))
                binding.btCalcular.setTextColor(getColor(R.color.black))
            }
            else -> {
                setColorsDesign(R.drawable.button_calculator)

                binding.btDivisao.setTextColor(getColor(R.color.white))
                binding.btMultiplicacao.setTextColor(getColor(R.color.white))
                binding.btMenos.setTextColor(getColor(R.color.white))
                binding.btMais.setTextColor(getColor(R.color.white))
                binding.btCalcular.setTextColor(getColor(R.color.white))
                design = 0
            }
        }


    }

    private fun setColorsDesign(cor: Int) {
        binding.btDivisao.setBackgroundResource(cor)
        binding.btMultiplicacao.setBackgroundResource(cor)
        binding.btMenos.setBackgroundResource(cor)
        binding.btMais.setBackgroundResource(cor)
        binding.btCalcular.setBackgroundResource(cor)
    }

    private fun funcaoPorcentagem() {
        if (sinal == "+" || sinal == "-") {
            if (!valorStr.isEmpty()) {
                valorStr =
                    ((valorStr.replace(",", ".").toDouble() / 100) * resultado).toString()
                binding.tvVisor.text = dec.format(valorStr.toDouble())
            }
        } else {
            if (!valorStr.isEmpty() && !binding.tvVisor.text.equals("-") && !binding.tvVisor.text.equals(
                    ","
                )
            ) {
                valorStr = ((valorStr.replace(",", ".").toDouble() / 100)).toString()
                binding.tvVisor.text = dec.format(valorStr.toDouble())
            }
        }
    }

    private fun liberaParenteses() {
        if (modeParenteses == 0) {

            binding.btPorcentagem.setText("(")
        }

        funcaoParenteses()

    }

    private fun funcaoParenteses() {
        binding.btPorcentagem.setOnClickListener {
            if (binding.btPorcentagem.text.equals("(")) {
                abreParenteses()
                binding.btPorcentagem.setText(")")

            } else if (binding.btPorcentagem.text.equals(")")) {
                fechaParenteses()
                binding.btPorcentagem.setText("%")
            } else {


                binding.btPorcentagem.setText("%")
            }
        }


    }

    private fun funcaoMaisMenos() {
        if (!valorStr.equals("") && !valorStr.equals("0") && !binding.tvVisor.text.equals("") && !binding.tvVisor.text.equals(
                "-"
            ) && !binding.tvVisor.text.equals(",")
        ) {
            valorStr = (valorStr.replace(",", ".").toDouble() * -1).toString()

            binding.tvVisor.text = dec.format(valorStr.toDouble())
            valorStr = binding.tvVisor.text.toString()
        } else {
            valorStr = "-"
            binding.tvVisor.text = valorStr
        }

    }

    private fun abreParenteses() {

        memStr += "("
        binding.tvMem.text = memStr

        valorStr = ""
        sinalp = sinal
        sinal = ""
        resultadop = resultado
        resultado = 0.0
        binding.tvVisor.text = ""
        modeParenteses = 1

    }

    private fun fechaParenteses() {

        if (sinal != "") {
            calcular()
            //teste paren
            // sinal ="#"
        }

        memStr += ")"
        binding.tvMem.text = memStr

        sinal = sinalp
        sinalp = ""
        valorStr = resultado.toString()
        resultado = resultadop
        calculap()

        modeParenteses = 0

    }

    private fun calculap() {

        if (!valorStr.isEmpty() && !binding.tvVisor.text.equals("-") &&
            !binding.tvVisor.text.equals(",")
        )
            when (sinal) {
                "+" -> resultado += valorStr.replace(",", ".").toDouble()
                "-" -> resultado -= valorStr.replace(",", ".").toDouble()
                "x" -> resultado *= valorStr.replace(",", ".").toDouble()
                "÷" -> resultado /= valorStr.replace(",", ".").toDouble()

                else -> resultado = valorStr.replace(",", ".").toDouble()
            }

        binding.tvVisor.text = dec.format(resultado)

        //binding.tvMem.textcolor
        gravaMem()

        valorStr = ""
        sinal = ""

        // binding.btPorcentagem.setText(")")
        // funcaoParenteses()

    }

    private fun sairParenteses() {
        binding.btPorcentagem.setText(")")
        funcaoParenteses()
    }

    private fun preenchep(s: String) {
        if (sinal == "#") {
            memStr = ""
            binding.tvMem.text = ""
        }

        if (!valorStr.equals("0")) {

            valorStr += s
        } else {
            valorStr = s
        }

        binding.tvVisor.text = valorStr
        desl = 0
    }


    private fun resetar() {
        binding.tvVisor.text = "0"
        valorStr = ""
        resultado = 0.0
        sinal = "AC"
        numMem = 0.0
        numMem2 = 0.0
        desl++
        modeParenteses = 0
        binding.btPorcentagem.setText("%")


        if (desl >= 2) {
            binding.tvVisor.text = ""
            desl = 0
        }
        memStr = ""
        binding.tvMem.text = ""
    }

    private fun gravaMem() {


        if (valorStr != "-" && valorStr != "") {

            memStr += dec.format(valorStr.replace(",", ".").toDouble())

            binding.tvMem.text = memStr
        }

    }

    private fun gravarsinal() {
        Log.i("entrou", "fun gravar sinal")

        if (memStr != "") {
            Log.i("entrou", "if fun gravar sinal")
            if (memStr.last().isDigit()) {
                Log.i("entrou", "if memstr diferente vazio fun gravar sinal")
                // memStr.dropLast(1)
                memStr += sinal
                binding.tvMem.text = memStr

            } else {
                Log.i("entrou", "else memstr diferente vazio fun gravar sinal")

                if (!memStr.last().equals('(')) {

                    Log.i("entrou", "if chave")

                    memStr = memStr.dropLast(1)
                    memStr += sinal
                    binding.tvMem.text = memStr

                } else {
                    memStr += sinal
                    binding.tvMem.text = memStr
                }

            }

        } else {
            Log.i("entrou", "ultimo else  fun gravar sinal")
            memStr += sinal
            binding.tvMem.text = memStr
        }


    }

    private fun calcular() {


        if (!valorStr.isEmpty() && !binding.tvVisor.text.equals("-") &&
            !binding.tvVisor.text.equals(",")
        )
            when (sinal) {
                "+" -> resultado += valorStr.replace(",", ".").toDouble()
                "-" -> resultado -= valorStr.replace(",", ".").toDouble()
                "x" -> resultado *= valorStr.replace(",", ".").toDouble()
                "÷" -> resultado /= valorStr.replace(",", ".").toDouble()

                else -> resultado = valorStr.replace(",", ".").toDouble()
            }

        binding.tvVisor.text = dec.format(resultado)
        gravaMem()

        valorStr = ""
        sinal = ""

        if (modeParenteses == 1) {
            sairParenteses()

        }


    }

    private fun separaDecimal() {

        if (sinal == "#") {
            memStr = ""
            binding.tvMem.text = ""
        }

        if (valorStr.isEmpty()) {
            valorStr = "0"
            binding.tvVisor.text = valorStr
        }
        if (!valorStr.contains(",") && !valorStr.contains(".")) {
            if (!binding.tvVisor.text.equals("-")) {
                valorStr += ","
                binding.tvVisor.text = valorStr
            }
        }
    }

    private fun preenche(s: String) {

        if (sinal == "#") {
            memStr = ""
            binding.tvMem.text = ""
        }

        if (!valorStr.equals("0")) {

            valorStr += s
            // valorStr = dec.format(valorStr.replace(",", ".").toDouble())

        } else {
            valorStr = s
        }

        binding.tvVisor.text = valorStr
        desl = 0

        liberaPorcentagem()
    }

    private fun liberaPorcentagem() {
        binding.btPorcentagem.setText("%")
        binding.btPorcentagem.setOnClickListener {
            funcaoPorcentagem()

        }
    }
}