package com.example.reposicaodeaulaestatistica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.text.trimmedLength
import androidx.core.view.isEmpty
import com.example.reposicaodeaulaestatistica.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Listas de controle
    private val listaDeNumerosString: MutableList<String> = mutableListOf()
    private val listaDeNumerosInt: MutableList<Int> = mutableListOf()

    //Variáveis de controle
    private var contador = 0
    private var maximo = 0
    private var minimo = 0
    private var numeroDeControle = 1
    private var amplitude = 0
    private var somatoria = 0
    private var numeroDeVezes = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnAdd.setOnClickListener {
            adicionarNumerosNaLista()
            mostrarLista(listaDeNumerosString)
            dadosMaximo(listaDeNumerosInt)
            dadosMinimo(listaDeNumerosInt)
            amplitude()
            media(listaDeNumerosInt)
        }

        binding.btnReset.setOnClickListener { reset() }
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
            listaDeNumerosInt.add(numero)

            contador += 1
        }
    }


    private fun mostrarLista(lista: MutableList<String>) {
        //Transforma a Lista em uma String
        val myStg = lista.joinToString()

        //Mostrando a lista no TextView
        binding.tvNumerosEscolhidos.text = myStg
    }


    private fun dadosMaximo(lista: MutableList<Int>) {
        for (item in lista) {

            if (item >= numeroDeControle) {
                maximo = item
                numeroDeControle = item
            }
        }
        binding.itCardMaximo.text = maximo.toString()
    }


    private fun dadosMinimo(lista: MutableList<Int>) {
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


    private fun media(lista: MutableList<Int>) {

        for (item in lista) {
            somatoria += item
            numeroDeVezes += 1
        }
        val media = somatoria
        binding.itCardMedia.text = media.toString()

        TODO("ARRUMAR SOMATORIA")
    }

    private fun reset() {
        contador = 0
        maximo = 0
        minimo = 0
        numeroDeControle = 1
        amplitude = 0

        listaDeNumerosString.clear()
        listaDeNumerosInt.clear()

        binding.itCardMinimo.text = "0"
        binding.itCardMaximo.text = "0"
        binding.tvNumerosEscolhidos.text = "0"
    }
}