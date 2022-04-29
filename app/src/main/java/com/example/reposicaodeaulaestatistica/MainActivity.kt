package com.example.reposicaodeaulaestatistica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.text.trimmedLength
import androidx.core.view.isEmpty
import com.example.reposicaodeaulaestatistica.databinding.ActivityMainBinding
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Listas de controle
    private val listaDeNumerosString: MutableList<String> = mutableListOf()
    private val listaDeNumerosDouble: MutableList<Double> = mutableListOf()
    private val listaDeNumerosVariancia: MutableList<Double> = mutableListOf()
    private val listaDeNumerosOrdenados: MutableList<Double> = mutableListOf()

    //Variáveis de controle
    private var contador = 0
    private var maximo = 0.0
    private var minimo = 0.0
    private var numeroDeControle = 1.0
    private var amplitude = 0.0
    private var somatoria = 0.0
    private var numeroDeVezes = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnAdd.setOnClickListener {

            adicionarNumerosNaLista()
            mostrarLista(listaDeNumerosString)
            dadosMaximo(listaDeNumerosDouble)
            dadosMinimo(listaDeNumerosDouble)
            amplitude()
            media(listaDeNumerosDouble)
            mediana(listaDeNumerosDouble)
            variancia(listaDeNumerosDouble)
            desvioPadrao(listaDeNumerosDouble)

            //Apagar campo após adicionar
            binding.etNumero.setText("")
        }

        binding.btnReset.setOnClickListener { reset() }

        binding.btnListaOrdenada.setOnClickListener {

            if (listaDeNumerosOrdenados.isNotEmpty()){
                val myList = listaDeNumerosOrdenados.joinToString()
                listaDeNumerosOrdenados.sort()
                binding.tvNumerosEscolhidos.text = myList
                listaDeNumerosOrdenados.clear()
            }
        }
    }

    private fun adicionarNumerosNaLista() {

        //Verificação se o campo está Vazio | Adiciona na lista
        if (binding.etNumero.text?.isEmpty() == true) {
            binding.etNumeroLayout.error = "Campo Vazio"
        } else {
            //Adiciona na Lista String
            listaDeNumerosString.add(contador, binding.etNumero.text.toString())

            //Transforma String em Inteiro
            val numero: Int = Integer.parseInt(binding.etNumero.text.toString())
            listaDeNumerosDouble.add(numero.toDouble())

            //Lista Ordenada
            listaDeNumerosOrdenados.add(numero.toDouble())

            contador += 1
        }
    }


    private fun mostrarLista(lista: MutableList<String>) {
        //Transforma a Lista em uma String
        val myStg = lista.joinToString()

        //Mostrando a lista no TextView
        binding.tvNumerosEscolhidos.text = myStg
    }


    private fun dadosMaximo(lista: MutableList<Double>) {

        //Verifica o Maior número do grupo
        for (item in lista) {
            if (item >= numeroDeControle) {
                maximo = item
                numeroDeControle = item
            }
        }
        binding.itCardMaximo.text = maximo.toString()
    }


    private fun dadosMinimo(lista: MutableList<Double>) {

        //Verifica o menor número do grupo
        for (item in lista) {
            if (item <= numeroDeControle) {
                minimo = item
                numeroDeControle = item
            }
        }
        binding.itCardMinimo.text = minimo.toString()
    }

    private fun amplitude() {

        amplitude = maximo - minimo
        binding.itCardAmplitude.text = amplitude.toString()

    }


    private fun media(lista: MutableList<Double>): Double {

        for (item in lista) {
            somatoria += item
        }

        val media = somatoria / lista.size
        binding.itCardMedia.text = media.toString()

        //Para não somar tudo novamente
        somatoria = 0.0
        numeroDeVezes = 0

        return media
    }

    private fun mediana(lista: MutableList<Double>) {

        lista.sort()

        for (item in lista) {
            numeroDeVezes += 1
        }

        val primeiroNumero = (lista.size / 2) - 1
        val segundoNumero = lista.size / 2

        if (lista.size > 2) {
            val resultado = (lista[primeiroNumero] + lista[segundoNumero]) / 2
            if (numeroDeVezes % 2 == 0) {
                binding.itCardMediana.text = resultado.toString()
            } else {
                binding.itCardMediana.text = lista[numeroDeVezes / 2].toString()
            }
        }
    }

    private fun variancia(lista: MutableList<Double>) : Double {

        val resultadoDaMedia = media(lista)
        var resultadoAoQuadrado: Double
        var somatoriaVariancia = 0.0

        for (item in lista) {
            resultadoAoQuadrado = (item - resultadoDaMedia) * (item - resultadoDaMedia)
            listaDeNumerosVariancia.add(resultadoAoQuadrado)
        }

        for (item in listaDeNumerosVariancia) {
            somatoriaVariancia += item
        }

        val resultadoVariante: Double = if (listaDeNumerosVariancia.size <= 1) {
            somatoriaVariancia / listaDeNumerosVariancia.size
        } else {
            somatoriaVariancia / (listaDeNumerosVariancia.size - 1)
        }

        binding.itCardVariancia.text = resultadoVariante.toString()

        listaDeNumerosVariancia.clear()

        return resultadoVariante
    }

    private fun desvioPadrao(lista: MutableList<Double>) {

        var desvio = variancia(lista)

        var resultadoDesvio = sqrt(desvio.toDouble())

        binding.itCardDesvioPadrao.text = resultadoDesvio.toString()
    }

    private fun reset() {
        contador = 0
        maximo = 0.0
        minimo = 0.0
        numeroDeControle = 1.0
        amplitude = 0.0
        somatoria = 0.0
        numeroDeVezes = 0

        listaDeNumerosString.clear()
        listaDeNumerosDouble.clear()

        binding.itCardMinimo.text = "0"
        binding.itCardMaximo.text = "0"
        binding.tvNumerosEscolhidos.text = "0"
        binding.itCardAmplitude.text = "0"
        binding.itCardMedia.text = "0"
        binding.itCardMediana.text = "0"
        binding.itCardVariancia.text = "0"
        binding.itCardDesvioPadrao.text = "0"
    }
}