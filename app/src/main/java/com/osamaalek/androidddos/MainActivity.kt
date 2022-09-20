package com.osamaalek.androidddos

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.osamaalek.androidddos.databinding.ActivityMainBinding
import java.lang.Integer.parseInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var attacks = ArrayList<AttackUtil>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonStart.setOnClickListener {
            if (binding.TextInputEditTextIp.text.isNullOrBlank() || binding.TextInputEditTextPort.text.isNullOrBlank()) {
                Toast.makeText(this, getString(R.string.msg_fill_all_filds), Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else if (ValidationUtil.isValidIp(binding.TextInputEditTextIp.text.toString())) {
                startAttack()
            } else {
                binding.TextInputEditTextIp.error = getString(R.string.err_its_not_ip)
            }
        }

        binding.buttonStop.setOnClickListener {
            stopAttack()
        }
    }

    private fun startAttack() {
        val attack = AttackUtil(
            binding.TextInputEditTextIp.text.toString(),
            parseInt(binding.TextInputEditTextPort.text.toString()),
            10000
        )
        if (binding.radioButtonHttp.isChecked) {
            attack.startHTTPAttach()
        } else if (binding.radioButtonTcp.isChecked) {
            attack.startTCPAttach()
        } else {
            attack.startUDPAttach()
        }
        attacks.add(attack)
    }

    private fun stopAttack() {
        attacks.forEach { it.stop() }
        attacks.clear()
    }

    override fun onDestroy() {
        stopAttack()
        super.onDestroy()
    }
}