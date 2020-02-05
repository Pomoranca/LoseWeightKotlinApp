package com.pomoranca.myapplication.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.activities.TipActivity
import com.pomoranca.myapplication.data.Meal


class MealRecyclerViewAdapter : RecyclerView.Adapter<MealRecyclerViewAdapter.MealHolder>() {

    private lateinit var listener: OnItemClickListener


    var mealList = mutableListOf<Meal>()

    inner class MealHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(mealList[position])
                }
            }
        }

        val mealTitle: TextView = itemView.findViewById(R.id.meal_title)
        val mealBackground: ImageView = itemView.findViewById(R.id.meal_background)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_meal, parent, false)
        return MealHolder(itemView)

    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: MealHolder, position: Int) {
        val currentMeal = mealList[position]
        holder.mealTitle.text = currentMeal.title
        holder.mealBackground.setColorFilter(
            Color.parseColor("#E8D8D8D8"),
            PorterDuff.Mode.MULTIPLY
        )

//        Glide
//            .with(holder.mealBackground.context)
//            .load(currentMeal.backgroundPath)
//            .centerCrop()
//            .into(holder.mealBackground)

        holder.mealBackground.setImageResource(currentMeal.backgroundPath)


        holder.mealBackground.setOnClickListener {
            val intent = Intent(it.context, TipActivity::class.java)
            intent.putExtra("NAME", currentMeal.title)
            intent.putExtra("BACKGROUND", currentMeal.backgroundPath)
            intent.putExtra("DESCRIPTION", currentMeal.description)
            startActivity(it.context, intent, null)
        }

    }

    fun populateMealList() {
        mealList.add(
            Meal(
                "Drink more water",
                "By: E.C. LaMeaux\n" +
                        "\n" +
                        "You’ve probably heard it more than once: drinking more water will help you lose more weight. But does water really help weight loss? The short answer is yes. Drinking water helps boost your metabolism, cleanse your body of waste, and acts as an appetite suppressant. Also, drinking more water helps your body stop retaining water, leading you to drop those extra pounds of water weight. What can you do to make sure you’re drinking the recommended eight to ten eight-ounce glasses per day to keep yourself hydrated and encourage weight loss?\n" +
                        "\n" +
                        "STEP 1: DRINK BEFORE YOU EAT\n" +
                        "Because water is an appetite suppressant, drinking it before meals can make you feel fuller, therefore reducing the amount of food you eat. Health resource website WebMD states that drinking water before meals results in an average reduction in intake of 75 calories per meal. Drinking water before just one meal per day would cause you to ingest 27,000 fewer calories per year. Do the math: You'd lose about eight pounds per year just from drinking water! Now imagine if you drank it before each meal. Our Gaiam Stainless Steel Water Bottle is a great way to make sure you are getting the right amount of water before a meal.\n" +
                        "\n" +
                        "STEP 2: REPLACE CALORIE-FILLED DRINKS WITH WATER\n" +
                        "Ditch the sodas and juice and replace them with water to help you lose weight. If you think water tastes boring, add a slice of lemon. A glass of water with lemon is a recipe for successful weight loss because the pectin in lemons helps reduce food cravings. Think water doesn’t really help with weight loss? Give up those sugary drinks for just a few weeks and see the difference.\n" +
                        "\n" +
                        "STEP 3: DRINK IT ICE COLD\n" +
                        "According to the editorial staff at WebMD, drinking ice cold water helps boost your metabolism because your body has to work harder to warm the water up, therefore burning more calories and helping you to lose weight. Plus, ice cold water is just so much more refreshing than water that’s room temperature. Our new 32 oz. Stainless Steel Wide Mouth Water Bottles merge style with functionality and can ultimately give you the tools you need to start losing weight and boosting your metabolism.\n" +
                        "\n" +
                        "merge style with functionality and can ultimately give you the tools you need to start losing weight and boosting your metabolism.\n" +
                        "\n" +
                        "STEP 4: HIT THE GYM\n" +
                        "Because drinking water helps prevent muscle cramps and keeps your joints lubricated, you can work out longer and harder. Just another way that proper hydration helps you lose weight. Whether you prefer Rodney Yee’s calm guidance or Jillian Michaels’ intense push, we have plenty of options to make your weight loss strategy fit your busy lifestyle.\n" +
                        "\n" +
                        "STEP 5: MAKE SURE YOU DRINK ENOUGH WATER\n" +
                        "If you really want the water you drink to help you lose weight, you should follow the “8x8” rule recommended by most nutritionists: Drink eight eight-ounce glasses of water per day for weight loss and to maintain an ideal weight. You might need to drink more water if you exercise a lot or sweat heavily, or less water if you drink other beverages like herbal tea (make sure they are decaffeinated).\n" +
                        "\n" +
                        "Trent Nessler, PT, DPT, MPT, managing director of Baptist Sports Medicine in Nashville, says the amount of water you need depends on your size, weight, and activity level. He adds that you should try to drink between half an ounce and an ounce of water for each pound you weigh, every day.\n" +
                        "\n" +
                        "How do you know if you’re getting enough water? A general rule is to check the toilet after you’ve gone to the bathroom. You’ll know you’re well-hydrated if your urine is clear or very light yellow in color. The darker your urine, the more water you need to drink, especially if weight loss is your goal. Try this Water Intake Calculator to see if you're staying hydrated enough for your weight loss goals!",
                R.drawable.tips_water
            )
        )
        mealList.add(
            Meal(
                "Develop healthy habits",
                "Just doing one of the items on this list won't have a big effect on its own.\n" +
                        "\n" +
                        "If you want good results, you need to combine different methods that have been shown to be effective.\n" +
                        "\n" +
                        "Interestingly, many of these are things generally associated with healthy eating and an overall healthy lifestyle.\n" +
                        "\n" +
                        "Therefore, changing your lifestyle for the long term is the key to losing your belly fat and keeping it off.\n" +
                        "\n" +
                        "When you have healthy habits and eat real food, fat loss tends to follow as a natural side effect",
                R.drawable.tips_change
            )
        )
        mealList.add(
            Meal(
                "Get enough sleep",
                "Sleep is important for many aspects of your health, including weight. Studies show that people who don't get enough sleep tend to gain more weight, which may include belly fat (49Trusted Source, 50Trusted Source).\n" +
                        "\n" +
                        "A 16-year study in more than 68,000 women found that those who slept less than five hours per  were significantly more likely to gain weight than those who slept seven hours or more per  (51Trusted Source).\n" +
                        "\n" +
                        "The condition known as sleep apnea, where breathing stops intermittently during the , has also been linked to excess visceral fat (52Trusted Source).\n" +
                        "\n" +
                        "In addition to sleeping at least seven hours per , make sure you're getting sufficient quality sleep.\n" +
                        "\n" +
                        "If you suspect you may have sleep apnea or another sleep disorder, speak to a doctor and get treated.",
                R.drawable.tips_sleep
            )
        )
        mealList.add(
            Meal(
                "Reduce stress",
                "Stress can make you gain belly fat by triggering the adrenal glands to produce cortisol, also known as the stress hormone.\n" +
                        "\n" +
                        "Research shows that high cortisol levels increase appetite and drive abdominal fat storage (19Trusted Source, 20Trusted Source).\n" +
                        "\n" +
                        "What's more, women who already have a large waist tend to produce more cortisol in response to stress. Increased cortisol further adds to fat gain around the middle (21Trusted Source).\n" +
                        "\n" +
                        "To help reduce belly fat, engage in pleasurable activities that relieve stress. Practicing yoga or meditation can be effective methods.",
                R.drawable.tips_stress
            )
        )
        mealList.add(
            Meal(
                "Get a routine",
                "Drinking has it's benefits",
                R.drawable.tips_training_routine
            )
        )
        mealList.add(
            Meal(
                "Track your progress",
                "Many things can help you lose weight and belly fat, but consuming fewer calories than your body needs for weight maintenance is key (53Trusted Source).\n" +
                        "\n" +
                        "Keeping a food diary or using an online food tracker or app can help you monitor your calorie intake. This strategy has been shown to be beneficial for weight loss (54Trusted Source, 55Trusted Source).\n" +
                        "\n" +
                        "In addition, food-tracking tools help you see your intake of protein, carbs, fiber and micronutrients. Many also allow you to record your exercise and physical activity.",
                R.drawable.tips_track
            )
        )
        mealList.add(
            Meal(
                "Eat fibers",
                "Soluble fiber absorbs water and forms a gel that helps slow down food as it passes through your digestive system.\n" +
                        "\n" +
                        "Studies show that this type of fiber promotes weight loss by helping you feel full, so you naturally eat less. It may also decrease the number of calories your body absorbs from food (3Trusted Source, 4Trusted Source, 5Trusted Source).\n" +
                        "\n" +
                        "What's more, soluble fiber may help fight belly fat.\n" +
                        "\n" +
                        "An observational study in over 1,100 adults found that for every 10-gram increase in soluble fiber intake, belly fat gain decreased by 3.7% over a 5-year period (6Trusted Source).\n" +
                        "\n" +
                        "Make an effort to consume high-fiber foods every day. Excellent sources of soluble fiber include flaxseed, shirataki noodles, Brussels sprouts, avocados, legumes and blackberries.",
                R.drawable.tips_fiber
            )
        )
        mealList.add(
            Meal(
                "Reduce fat intake",
                "Reducing your carb intake can be very beneficial for losing fat, including abdominal fat.\n" +
                        "\n" +
                        "Diets with under 50 grams of carbs per day cause belly fat loss in overweight people, those at risk of type 2 diabetes and women with polycystic ovary syndrome (PCOS) (31Trusted Source, 32Trusted Source, 33Trusted Source).\n" +
                        "\n" +
                        "You don't have to follow a strict low-carb diet. Some research suggests that simply replacing refined carbs with unprocessed starchy carbs may improve metabolic health and reduce belly fat (34Trusted Source, 35Trusted Source).\n" +
                        "\n" +
                        "In the famous Framingham Heart Study, people with the highest consumption of whole grains were 17% less likely to have excess abdominal fat than those who consumed diets high in refined grains (36Trusted Source).",
                R.drawable.tips_fats
            )
        )
        mealList.add(
            Meal(
                "Enjoy the process",
                "Aerobic exercise (cardio) is an effective way to improve your health and burn calories.\n" +
                        "\n" +
                        "Studies also show that it’s one of the most effective forms of exercise for reducing belly fat. However, results are mixed as to whether moderate-intensity or high-intensity exercise is more beneficial (27Trusted Source, 28Trusted Source, 29Trusted Source).\n" +
                        "\n" +
                        "In any case, the frequency and duration of your exercise program are more important than its intensity.\n" +
                        "\n" +
                        "One study found that postmenopausal women lost more fat from all areas when they did aerobic exercise for 300 minutes per week, compared to those who exercised 150 minutes per week (30Trusted Source).",
                R.drawable.tips_cheat
            )
        )
        mealList.add(
            Meal(
                "Avoid alcohol",
                        "Research suggests that too much alcohol can also make you gain belly fat.\n" +
                        "\n" +
                        "Observational studies link heavy alcohol consumption to a significantly increased risk of central obesity — that is, excess fat storage around the waist (11Trusted Source, 12Trusted Source).\n" +
                        "\n" +
                        "Cutting back on alcohol may help reduce your waist size. You don't need to give it up altogether but limiting the amount you drink in a single day can help.\n" +
                        "\n" +
                        "In a study in more than 2,000 people, those who drank alcohol daily but averaged less than one drink per day had less belly fat than those who drank less frequently but consumed more alcohol on the days they drank (12Trusted Source).",
                R.drawable.tips_alcohol
            )
        )
        mealList.add(
            Meal(
                "Eat proteins",
                "Protein is an extremely important nutrient for weight control.\n" +
                        "\n" +
                        "High protein intake increases the release of the fullness hormone PYY, which decreases appetite and promotes fullness. Protein also raises your metabolic rate and helps you retain muscle mass during weight loss (13Trusted Source, 14Trusted Source, 15Trusted Source).\n" +
                        "\n" +
                        "Many observational studies show that people who eat more protein tend to have less abdominal fat than those who eat a lower-protein diet (16Trusted Source, 17Trusted Source, 18Trusted Source).\n" +
                        "\n" +
                        "Be sure to include a good protein source at every meal, such as meat, fish, eggs, dairy, whey protein or beans.",
                R.drawable.tips_protein
            )
        )
        mealList.add(
            Meal(
                "Stop drinking liquid sugar",
                "Although fruit juice provides vitamins and minerals, it's just as high in sugar as soda and other sweetened beverages.\n" +
                        "\n" +
                        "Drinking large amounts may carry the same risk of abdominal fat gain (61Trusted Source).\n" +
                        "\n" +
                        "An 8-ounce (240-ml) serving of unsweetened apple juice contains 24 grams of sugar, half of which is fructose (62).\n" +
                        "\n" +
                        "To help reduce excess belly fat, replace fruit juice with water, unsweetened iced tea or sparkling water with a wedge of lemon or lime.",
                R.drawable.tips_replace_juice
            )
        )
        mealList.add(
            Meal(
                "Avoid fast food",
                "Sugar contains fructose, which has been linked to several chronic diseases when consumed in excess.\n" +
                        "\n" +
                        "These include heart disease, type 2 diabetes, obesity and fatty liver disease (22Trusted Source, 23Trusted Source, 24Trusted Source).\n" +
                        "\n" +
                        "Observational studies show a relationship between high sugar intake and increased abdominal fat (25Trusted Source, 26Trusted Source).\n" +
                        "\n" +
                        "It's important to realize that more than just refined sugar can lead to belly fat gain. Even healthier sugars, such as real honey, should be used sparingly.",
                R.drawable.tips_sugary
            )
        )

    }

    interface OnItemClickListener {
        fun onItemClick(meal: Meal)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

}

