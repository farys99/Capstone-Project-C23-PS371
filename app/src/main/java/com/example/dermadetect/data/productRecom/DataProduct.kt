package com.example.dermadetect.data.productRecom

object DataProduct {
    private val productData = arrayOf(
        arrayOf( "Caladine Lotion", "Contains calamine, zinc oxide, and diphenhydramine HCl", "https://www.farmaku.com/artikel/wp-content/uploads/2022/09/Caladine_Lotion_60_ml-300x300.jpeg"),
        arrayOf( "Hydrocortisone Cream", "Treat skin conditions, such as allergies, dermatitis, and skin rashes", "https://www.farmaku.com/artikel/wp-content/uploads/2022/09/Hydrocortisone_1-300x300.jpg"),
        arrayOf( "Kenacort-A 0,1% Cream", "Reduce inflammation and treat itching", "https://www.farmaku.com/artikel/wp-content/uploads/2022/09/Kenacort_A_Crem_10_gr-300x300.jpg"),
        arrayOf( "Alleron", "Inhibit the triggers of allergic reactions", "https://www.farmaku.com/artikel/wp-content/uploads/2022/09/Alleron_4_mg_Kaplet-300x300.jpg"),
        arrayOf( "Cetirizine", "Reduce the effects of allergic reactions", "https://www.farmaku.com/artikel/wp-content/uploads/2022/09/Cetirizine_10_mg_Tab_Hexp-300x300.jpeg"),
        arrayOf( "Caladine Cream", "Helps relieve itching from insect bites", "https://www.farmaku.com/artikel/wp-content/uploads/2022/09/Caladine_Cream_15_g-300x300.jpeg%E2%80%9D"),
        arrayOf( "Orphen", "Can relieve allergy symptoms such as itching", "https://www.farmaku.com/artikel/wp-content/uploads/2022/09/gambar-orphen.jpg")
    )

    val listProduct : ArrayList<Product>
        get(){
            val list = ArrayList<Product>()
            for(i in productData){
                val product = Product()
                product.namaProduct = i[0] as String
                product.descriptionProduct = i[1] as String
                product.image = i[2] as String

                list.add(product)
            }
            return list
        }
}