package com.example.recipesapplication.data.local

import com.example.recipesapplication.data.model.Ingredient
import com.example.recipesapplication.data.model.Recipe

object SampleData {
    val sampleRecipes = listOf(
        Recipe(
            id = "52772",
            name = "Chocolate Lava Cake",
            category = "Dessert",
            area = "French",
            imageUrl = "https://www.themealdb.com/images/media/meals/1549542917.jpg",
            instructions = "Preheat oven to 425°F (220°C). Butter and cocoa powder four 6-ounce ramekins.\nMelt dark chocolate and butter together in a heatproof bowl over simmering water, stirring until smooth.\nIn a separate bowl, whisk together eggs, egg yolks, and sugar until pale and thickened.\nFold chocolate mixture into egg mixture, then gently fold in flour until just combined.\nDivide batter evenly among prepared ramekins. Bake for 12-14 minutes until edges are firm but center is soft.\nLet cool for 1 minute, then run a knife around edges and invert onto dessert plates. Serve immediately with vanilla ice cream or fresh berries.",
            ingredients = listOf(
                Ingredient("Dark Chocolate", "200g"),
                Ingredient("Butter", "100g"),
                Ingredient("Eggs", "2 large"),
                Ingredient("Egg Yolks", "2 large"),
                Ingredient("Caster Sugar", "100g"),
                Ingredient("All-purpose Flour", "50g"),
                Ingredient("Cocoa Powder", "1 tbsp for dusting")
            )
        ),
        Recipe(
            id = "52906",
            name = "Tiramisu",
            category = "Dessert",
            area = "Italian",
            imageUrl = "https://www.themealdb.com/images/media/meals/1550441882.jpg",
            instructions = "Separate egg whites and yolks. Beat yolks with sugar until creamy and pale. Add mascarpone cheese and whisk until smooth.\nIn another bowl, whip egg whites with a pinch of salt until stiff peaks form. Gently fold whites into the mascarpone mixture.\nCombine cooled strong espresso with coffee liqueur in a shallow dish.\nQuickly dip ladyfingers into espresso mixture and layer in the bottom of a dish.\nSpread half of mascarpone cream over ladyfingers. Repeat with another layer of dipped ladyfingers and top with remaining mascarpone cream.\nCover and refrigerate for at least 4-6 hours (preferably overnight). Dust generously with cocoa powder before serving.",
            ingredients = listOf(
                Ingredient("Ladyfingers", "24 biscuits"),
                Ingredient("Mascarpone Cheese", "500g"),
                Ingredient("Eggs", "4 fresh"),
                Ingredient("Sugar", "100g"),
                Ingredient("Strong Espresso Coffee", "300ml"),
                Ingredient("Coffee Liqueur / Kahlua", "2 tbsp"),
                Ingredient("Unsweetened Cocoa Powder", "2 tbsp")
            )
        ),
        Recipe(
            id = "52857",
            name = "Strawberry Cheesecake",
            category = "Dessert",
            area = "American",
            imageUrl = "https://www.themealdb.com/images/media/meals/1549542877.jpg",
            instructions = "Crush graham crackers into fine crumbs and mix with melted butter and sugar. Press firmly into the bottom of a springform pan. Chill while making filling.\nBeat softened cream cheese and sugar until smooth. Add vanilla extract and eggs one at a time, beating on low speed until just blended.\nPour cream cheese mixture over crust and bake at 325°F (165°C) for 50-55 minutes until center is almost set.\nCool completely at room temperature, then chill in refrigerator for at least 4 hours.\nFor strawberry topping, cook fresh strawberries, sugar, and lemon juice in a saucepan until thickened into a glossy syrup. Cool and spread over cheesecake before serving.",
            ingredients = listOf(
                Ingredient("Graham Crackers", "200g"),
                Ingredient("Unsalted Butter", "80g melted"),
                Ingredient("Cream Cheese", "600g softened"),
                Ingredient("Sugar", "150g"),
                Ingredient("Eggs", "3 large"),
                Ingredient("Vanilla Extract", "1 tsp"),
                Ingredient("Fresh Strawberries", "300g"),
                Ingredient("Lemon Juice", "1 tbsp")
            )
        ),
        Recipe(
            id = "52893",
            name = "Apple Pie",
            category = "Dessert",
            area = "American",
            imageUrl = "https://www.themealdb.com/images/media/meals/1549542805.jpg",
            instructions = "Peel, core, and slice apples thinly. Toss with sugar, cinnamon, nutmeg, lemon juice, and flour.\nRoll out half of pie dough and fit into a 9-inch pie dish. Heap apple filling inside.\nRoll out remaining dough and place on top, or cut into strips for a lattice top. Crimp edges to seal and cut slits for steam.\nBrush top crust with egg wash and sprinkle with coarse sugar.\nBake at 400°F (200°C) for 20 minutes, then reduce heat to 375°F (190°C) and bake another 35-40 minutes until crust is golden brown and juices bubble. Cool before slicing.",
            ingredients = listOf(
                Ingredient("Pie Crust Dough", "2 sheets"),
                Ingredient("Apples (Granny Smith)", "6 medium"),
                Ingredient("Granulated Sugar", "150g"),
                Ingredient("Ground Cinnamon", "1 tsp"),
                Ingredient("Ground Nutmeg", "1/4 tsp"),
                Ingredient("All-purpose Flour", "2 tbsp"),
                Ingredient("Lemon Juice", "1 tbsp"),
                Ingredient("Egg Wash", "1 egg beaten with 1 tbsp water")
            )
        ),
        Recipe(
            id = "52900",
            name = "French Macarons",
            category = "Dessert",
            area = "French",
            imageUrl = "https://www.themealdb.com/images/media/meals/1550442382.jpg",
            instructions = "Sift almond flour and powdered sugar together twice to ensure fine texture.\nWhip egg whites until foamy, then gradually add granulated sugar while whipping until stiff, glossy peaks form.\nFold dry ingredients into meringue using the macaronage technique until batter flows like molten lava.\nPipe 1.5-inch rounds onto parchment-lined baking sheets. Tap sheets firmly on counter to release air bubbles.\nLet piped shells rest at room temperature for 30-45 minutes until a dull skin forms on top.\nBake at 300°F (150°C) for 14-16 minutes. Let cool completely before filling with chocolate ganache or buttercream.",
            ingredients = listOf(
                Ingredient("Almond Flour", "100g"),
                Ingredient("Powdered Sugar", "100g"),
                Ingredient("Egg Whites", "2 large room temp"),
                Ingredient("Granulated Sugar", "50g"),
                Ingredient("Food Coloring Gel", "a few drops"),
                Ingredient("Chocolate Ganache / Buttercream", "150g for filling")
            )
        ),
        Recipe(
            id = "52928",
            name = "Churros",
            category = "Dessert",
            area = "Spanish",
            imageUrl = "https://www.themealdb.com/images/media/meals/1550441982.jpg",
            instructions = "In a saucepan, combine water, butter, sugar, and salt. Bring to a boil over medium heat.\nRemove from heat, add flour all at once, and stir vigorously with a wooden spoon until a smooth ball forms.\nLet cool for 5 minutes, then beat in eggs one at a time until dough is smooth and glossy.\nTransfer dough to a piping bag fitted with a large star tip.\nHeat oil in a deep pot to 350°F (175°C). Pipe 4-inch strips of dough into hot oil and snip ends with scissors.\nFry for 2-3 minutes per side until golden brown and crispy. Drain on paper towels, then roll immediately in cinnamon sugar. Serve hot with warm chocolate dipping sauce.",
            ingredients = listOf(
                Ingredient("Water", "250ml"),
                Ingredient("Butter", "100g"),
                Ingredient("All-purpose Flour", "150g"),
                Ingredient("Eggs", "3 medium"),
                Ingredient("Granulated Sugar", "2 tbsp + 100g for coating"),
                Ingredient("Ground Cinnamon", "1 tbsp"),
                Ingredient("Vegetable Oil", "for deep frying"),
                Ingredient("Dark Chocolate", "100g for sauce")
            )
        )
    )
}
