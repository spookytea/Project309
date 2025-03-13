package com.example.project309

//data class AnimalDataModel(
//    var energyLevel: Int = 100,
//    var hungerLevel: Int = 100,
//    var funLevel: Int = 100,
//    var kind: String = "Cat"
//)

//class AnimalDataViewModel(app: Application) : AndroidViewModel(app){
//
//
//    init {
//        val f = File(app.applicationContext.filesDir, "animal.txt")
//        if (f.createNewFile()) {
//            this.Save()
//        }
//
//        for(i in f.readLines()){
//            val arr = i.split("=")
//            when(arr[0]){
//                "Energy" -> this.energyLevel = parseInt(arr[1])
//                "Hunger" -> this.hungerLevel = parseInt(arr[1])
//                "Fun"    -> this.funLevel    = parseInt(arr[1])
//                "Kind"   -> this.kind        = arr[1]
//
//            }
//        }
//    }
//}

//
//    @Composable fun Save(){
//        val f = File(LocalContext.current.filesDir, "animal.txt")
//        f.writeText("""
//                Energy=$energyLevel
//                Hunger=$hungerLevel
//                Fun=$funLevel
//                Kind=$kind
//            """.trimIndent()
//        )
//    }


