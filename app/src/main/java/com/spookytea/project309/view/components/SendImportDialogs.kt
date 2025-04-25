package com.spookytea.project309.view.components

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spookytea.project309.viewmodel.SMSViewModel
import kotlinx.serialization.SerializationException


//SMS dialogs
@Composable
fun SendDialog(dismiss: () -> Unit){


    val vm = viewModel<SMSViewModel>()
    val context = LocalContext.current
    val creatureCount by vm.creatureCount.collectAsState(0)
    val pagerState = rememberPagerState { creatureCount }
    var number by rememberSaveable {  mutableStateOf("") }

    Dialog(dismiss) {
        Card(Modifier.size(500.dp)) {
            Column{
                AnimalDisplay(pagerState, Modifier.weight(0.9f), vm)
                OutlinedTextField(
                    number,
                    {number=it},
                    Modifier.padding(horizontal = 20.dp)
                            .padding(bottom = 5.dp)
                            .fillMaxWidth(),
                    label = { Text("Phone Number") }
                )
                TextButton(
                    onClick = {
                        try {
                            vm.send(number)
                            dismiss()
                        } catch(_: IllegalArgumentException){//Shows error when invalid phone number
                            Toast.makeText(context, "Invalid Number", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                                       .padding(horizontal = 20.dp)
                ) { Text("Send SMS") }
            }



        }


    }

}

@Composable
fun ImportDialog(dismiss: () -> Unit){
    val context = LocalContext.current
    val vm = viewModel<SMSViewModel>()
    var import by rememberSaveable { mutableStateOf("")  }
    Dialog(dismiss) {
        Card(Modifier.size(500.dp)){
            Column {
                OutlinedTextField(import, {import=it}, Modifier.weight(0.9f).padding(20.dp))
                TextButton(
                    onClick = {
                        try {
                            vm.import(import)
                            dismiss()
                        } catch (_: SerializationException){ //If cannot be deserialized, sends error
                            Toast.makeText(context, "Invalid Exported Code", Toast.LENGTH_SHORT)
                                 .show()

                        }

                    },
                    modifier = Modifier.padding(horizontal = 20.dp)
                                       .padding(bottom = 5.dp)
                                       .align(Alignment.End)
                ) {
                    Text("Import")
                }
            }
        }

    }





}