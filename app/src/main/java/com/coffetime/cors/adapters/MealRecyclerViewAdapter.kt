package com.coffetime.cors.adapters

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coffetime.cors.R
import com.coffetime.cors.activities.TipActivity
import com.coffetime.cors.data.Meal


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


    override fun onBindViewHolder(holder: MealHolder, position: Int) {
        val currentMeal = mealList[position]
        Glide.with(holder.itemView.context)
            .load(currentMeal.backgroundPath)
            .centerInside()
            .into(holder.mealBackground)


        holder.mealTitle.text = currentMeal.title
        holder.mealBackground.setColorFilter(
            Color.parseColor("#E8D8D8D8"),
            PorterDuff.Mode.MULTIPLY
        )


        holder.mealBackground.setOnClickListener {
            val intent = Intent(it.context, TipActivity::class.java)
            intent.putExtra("NAME", currentMeal.title)
            intent.putExtra("BACKGROUND", currentMeal.backgroundPath)
            intent.putExtra("DESCRIPTION", currentMeal.description)
            intent.putExtra("AUTHOR", currentMeal.author)
            startActivity(it.context, intent, null)
        }

    }

    fun populateMealList() {
        mealList.add(
            Meal(
                "Drink more water",
                "By: Nicola Farrell - Lifeleisure",
                "Many people don’t realise the importance of drinking enough water and the effect it can have on our health, eating habits and weight loss. Recent studies are showing the importance of hydration and even stress how drinking 2 cups of water before a meal can help dieters lose up to 5lbs more in a year and maintain that healthy weight loss. Water speeds up our metabolic rate and can helps stop over eating. Got your attention?\n" +
                        "\n" +
                        "Many of my clients complain of headaches, feeling tired, poor performance in the gym, not sleeping, bad skin, and when I dig a little deeper into their lifestyle I find out they don’t drink anywhere near enough water. And it’s funny that many people turn up to the gym without a water bottle…did you know that we are made of up to 60% water! Therefore, it’s hugely important to stay hydrated so our bodies can function.\n" +
                        "\n" +
                        "We depend on water to survive. Every cell, tissue and organ needs water to function.  Our body uses water to regulate our body temperature, aid digestion, transport nutrients, lubricate joints and remove waste.\n" +
                        "\n" +
                        "Water is needed for good health and a strong performance in the gym or when training anywhere. We lose water over the course of the day, not only from going to the bathroom but from sweating and breathing! We will lose water even faster when it’s hot or we are physically active so we need to replenish to stay hydrated.\n" +
                        "\n" +
                        "Our bodies have ways of telling us when it is dehydrated but we shouldn’t be waiting for these signs. Drink water throughout the day and increase your water intake during physical activity, when you are hot or feeling under the weather. But if you are seeing some of these signs then you may need to drink a little more….\n" +
                        "\n" +
                        "Dry mouth\n" +
                        "Headaches\n" +
                        "Poor sleep\n" +
                        "Fatigue\n" +
                        "Poor performance\n" +
                        "Lack of concentration\n" +
                        "Bad skin\n" +
                        "Urine that is darker than usual\n" +
                        "\n" +
                        "Drinking enough water can have a positive effect on our health, wellbeing, skin, sleep, concentration and performance…. still not convinced?\n" +
                        "\n" +
                        "Do you suffer from muscle cramps, dizziness, or fatigue after exercise?\n" +
                        "\n" +
                        "In one hour of exercise the body can lose more than a quarter of its water. Dehydration leads to muscle fatigue and loss of coordination. Without an adequate supply of water the body will lack energy and muscles may cramp. So, drink before, during and after a workout.\n" +
                        "\n" +
                        "Lean muscle tissue contains more than 75 percent water, so when the body is short on H2O, muscles are more easily fatigued.  Staying hydrated helps prevent the decline in performance, strength, power, aerobic and anaerobic capacity during exercise. So, when your muscles feel too tired to finish a workout, grab a drink of water before getting back to it.\n" +
                        "\n" +
                        "The importance of drinking water before, during and after exercise 2\n" +
                        "\n" +
                        "Did you know the heart must work harder to get blood to the working muscles? When there’s not enough water in blood, both blood volume and blood pressure drop, resulting in dizziness and fatigue.\n" +
                        "\n" +
                        "So, want to look, feel and train better? Drink more water…\n" +
                        "\n" +
                        "Here are a few tips that will help you drink more of that all-important vital liquid:\n" +
                        "\n" +
                        "Drink water with every meal. Make sure you place a jug of water on the table, or buy a bottle with your lunch time meal deal!\n" +
                        "Get into good habits and drink a glass of water before bed and first thing in the morning\n" +
                        "Carry a bottle of water with you during the day\n" +
                        "Do you know how much you are drinking? Get an app or just a water bottle with the amount of water you should be drinking. Set yourself goals\n" +
                        "Don’t like the taste? Try infusing the water? Added fresh fruit to add a new flavour or how about sparkling water mixed with fresh lemon…so refreshing\n" +
                        "If you are eating out, make sure you have a glass of water with every course\n" +
                        "Drink water before, during and after exercise\n" +
                        "Feeling hungry – try drinking a glass of water and waiting 15 minutes. Our bodies can confuse thirst with hunger\n" +
                        "Water is the best option for staying hydrated but we can hydrate with food and other liquids. But remember some may contain more calories than H2O\n" +
                        "Fruit and vegetables, whole or juiced will contribute to your water intake\n" +
                        "Fruit teas and decaffeinated tea or coffee can also be part of your intake (but everything in moderation)\n" +
                        "Sports drinks, BUT remember they also contain carbohydrate and electrolytes that will increase your energy. These are usually more beneficial if you are training at high intensity or for long periods at a time. Choose sports drinks wisely as they often contain added sugars or salts\n" +
                        "Sports drinks and energy drinks are different! Energy drinks will usually contain caffeine or other stimulants and can be high in added sugars, so try some other options that are healthier if you can\n" +
                        "You can’t beat H2O for rehydrating, so when the body experiences fluid loss, reach for a cool refreshing glass of water. And don’t wait for the signals, drink before your body warns you!",
                R.drawable.tips_water
            )
        )
        mealList.add(
            Meal(
                "Develop healthy habits",
                "By: Healthline",
                "The impact of good health\n" +
                        "You know that healthy habits, such as eating well, exercising, and avoiding harmful substances, make sense, but did you ever stop to think about why you practice them? A healthy habit is any behavior that benefits your physical, mental, and emotional health. These habits improve your overall well-being and make you feel good.\n" +
                        "\n" +
                        "Healthy habits are hard to develop and often require changing your mindset. But if you’re willing to make sacrifices to better your health, the impact can be far-reaching, regardless of your age, sex, or physical ability. Here are five benefits of a healthy lifestyle.\n" +
                        "\n" +
                        "Controls weight\n" +
                        "Eating right and exercising regularly can help you avoid excess weight gain and maintain a healthy weight. According to the Mayo Clinic, being physically active is essential to reaching your weight-loss goals. Even if you’re not trying to lose weight, regular exercise can improve cardiovascular health, boost your immune system, and increase your energy level.\n" +
                        "\n" +
                        "Plan for at least 150 minutes of moderate physical activity every week. If you can’t devote this amount of time to exercise, look for simple ways to increase activity throughout the day. For example, try walking instead of driving, take the stairs instead of the elevator, or pace while you’re talking on the phone.\n" +
                        "\n" +
                        "Eating a balanced, calorie-managed diet can also help control weight. When you start the day with a healthy breakfast, you avoid becoming overly hungry later, which could send you running to get fast food before lunch.\n" +
                        "\n" +
                        "Additionally, skipping breakfast can raise your blood sugar, which increases fat storage. Incorporate at least five servings of fruits and vegetables into your diet per day. These foods, which are low in calories and high in nutrients, help with weight control. Limit consumption of sugary beverages, such as sodas and fruit juices, and choose lean meats like fish and turkey.\n" +
                        "\n" +
                        "Improves mood\n" +
                        "Doing right by your body pays off for your mind as well. The Mayo Clinic notes that physical activity stimulates the production of endorphins. Endorphins are brain chemicals that leave you feeling happier and more relaxed. Eating a healthy diet as well as exercising can lead to a better physique. You’ll feel better about your appearance, which can boost your confidence and self-esteem. Short-term benefits of exercise include decreased stress and improved cognitive function.\n" +
                        "\n" +
                        "It’s not just diet and exercise that lead to improved mood. Another healthy habit that leads to better mental health is making social connections. Whether it’s volunteering, joining a club, or attending a movie, communal activities help improve mood and mental functioning by keeping the mind active and serotonin levels balanced. Don’t isolate yourself. Spend time with family or friends on a regular basis, if not every day. If there’s physical distance between you and loved ones, use technology to stay connected. Pick up the phone or start a video chat.\n" +
                        "\n" +
                        "Combats diseases\n" +
                        "Healthy habits help prevent certain health conditions, such as heart disease, stroke, and high blood pressure. If you take care of yourself, you can keep your cholesterol and blood pressure within a safe range. This keeps your blood flowing smoothly, decreasing your risk of cardiovascular diseases.\n" +
                        "\n" +
                        "Regular physical activity and proper diet can also prevent or help you manage a wide range of health problems, including:\n" +
                        "\n" +
                        "metabolic syndrome\n" +
                        "diabetes\n" +
                        "depression\n" +
                        "certain types of cancer\n" +
                        "arthritis\n" +
                        "Make sure you schedule a physical exam every year. Your doctor will check your weight, heartbeat, and blood pressure, as well as take a urine and blood sample. This appointment can reveal a lot about your health. It’s important to follow up with your doctor and listen to any recommendations to improve your health.\n" +
                        "\n" +
                        "Boosts energy\n" +
                        "We’ve all experienced a lethargic feeling after eating too much unhealthy food. When you eat a balanced diet your body receives the fuel it needs to manage your energy level. A healthy diet includes:\n" +
                        "\n" +
                        "whole grains\n" +
                        "lean meats\n" +
                        "low-fat dairy products\n" +
                        "fruit\n" +
                        "vegetables\n" +
                        "Regular physical exercise also improves muscle strength and boosts endurance, giving you more energy, says the Mayo Clinic. Exercise helps deliver oxygen and nutrients to your tissues and gets your cardiovascular system working more efficiently so that you have more energy to go about your daily activities. It also helps boost energy by promoting better sleep. This helps you fall asleep faster and get deeper sleep.\n" +
                        "\n" +
                        "Insufficient sleep can trigger a variety of problems. Aside from feeling tired and sluggish, you may also feel irritable and moody if you don’t get enough sleep. What’s more, poor sleep quality may be responsible for high blood pressure, diabetes, and heart disease, and it can also lower your life expectancy. To improve sleep quality, stick to a schedule where you wake up and go to bed at the same time every night. Reduce your caffeine intake, limit napping, and create a comfortable sleep environment. Turn off lights and the television, and maintain a cool room temperature.\n" +
                        "\n" +
                        "Improves longevity\n" +
                        "When you practice healthy habits, you boost your chances of a longer life. The American Council on Exercise reported on an eight-year study of 13,000 people. The study showed that those who walked just 30 minutes each day significantly reduced their chances of dying prematurely, compared with those who exercised infrequently. Looking forward to more time with loved ones is reason enough to keep walking. Start with short five-minute walks and gradually increase the time until you’re up to 30 minutes.\n" +
                        "\n" +
                        "The takeaway\n" +
                        "Bad habits are hard to break, but once you adopt a healthier lifestyle, you won’t regret this decision. Healthy habits reduce the risk of certain diseases, improve your physical appearance and mental health, and give your energy level a much needed boost. You won’t change your mindset and behavior overnight, so be patient and take it one day at a time.",
                R.drawable.tips_change
            )
        )
        mealList.add(
            Meal(
                "Get enough sleep",
                "By: Everydayhealth",
                "When it comes to working out, you know that what you do in the gym is important. But what you do outside the gym — what you eat, what you drink, and especially how you sleep, is just as crucial. In fact, you must sleep in order for exercise to actually work.\n" +
                        "\n" +
                        "“We exercise for a purpose: for cardiovascular health, to increase lean muscle mass, to improve endurance, and more. All of these 'goals' require sleep,” says W. Christopher Winter, MD, the president of Charlottesville Neurology and Sleep Medicine and the author of The Sleep Solution: Why Your Sleep Is Broken and How to Fix It.\n" +
                        "\n" +
                        "In other words, without sleep, exercise does not deliver those benefits, Dr. Winter explains. “If you don’t sleep, you undermine your body.”\n" +
                        "Sleep gives your body time to recover, conserve energy, and repair and build up the muscles worked during exercise. When we get enough good quality sleep, the body produces growth hormone. During childhood and adolescence, growth hormone makes us grow (as the name implies, Winter says. “And when we are older, it helps us build lean muscle and helps our body repair when we have torn ourselves up during a hard workout,” he adds. “Growth hormone is essential for athletic recovery.”\n" +
                        "\n" +
                        "The problem is, Americans have a major issue when it comes to sleep: More than 30 percent of us are sleep-deprived, which means we're not getting the recommended seven to eight hours a night required for adults, according to the Centers for Disease Control and Prevention. (1,2) And that means approximately 108 million people in the U.S. are sabotaging their own fitness goals, too.\n" +
                        "Regular Exercise Can Absolutely Help You Sleep\n" +
                        "Can exercise help you sleep? Absolutely. And if you’ve never experienced that immediate sleep-inducing exhaustion one might experience after a day of hiking or a grueling boot camp class, there’s a ton of scientific research to back up this claim, too.\n" +
                        "\n" +
                        "In one study published in the journal Sleep Medicine, individuals with a self-reported sleep time of less than 6.5 hours completed moderate-intensity workouts (think walking, riding a stationary bicycle, or running or walking on a treadmill) four times a week for six weeks. (3) At the end of the experiment, they reported getting an extra 75 minutes of sleep per night — more than any drug has helped deliver, according to the study authors.\n" +
                        "\n" +
                        "Exercise actually has a chemical effect on the brain. “Physical activity creates more adenosine in the brain, and adenosine makes us feel sleepy,” says Winter. (Fun fact: Adenosine is the chemical that caffeine blocks to make you feel more alert.) “The harder we work out, the more driven we are by this chemical to sleep.”\n" +
                        "\n" +
                        "Working out also helps you maintain your circadian rhythm (that is, your body’s internal clock), Winter says. “Exercise helps your body understand the schedule it’s on; and morning exercise primes your body to sleep better at night.”\n" +
                        "\n" +
                        "But what about late-day exercise? While it is possible that exercising at night will keep you awake longer, science says it’s a matter of choosing the right type of workout and finding the right workout schedule for you.\n" +
                        "\n" +
                        "People who reported greater exertion before bed were actually more efficient sleepers, according to research published in the September 2014 issue of the Journal of Clinical Sleep Medicine; they also fell asleep faster, slept deeper, and woke up less during the night. (4) Another study, published in the journal Sleep Medicine, found that moderate-intensity workouts before bed helped soothe pre-sleep anxiety. (5)\n" +
                        "\n" +
                        "That said, you’re probably better off sticking to low-intensity workouts like yoga, pilates, or barre, if you plan to sweat close to bedtime. Research published in the September 2014 issue of the European Journal of Applied Physiology found that high-intensity exercise has been shown to delay sleep onset, probably because of an increased heart rate post-gym time. (6)",
                R.drawable.tips_sleep
            )
        )
        mealList.add(
            Meal(
                "Reduce stress",
                "By: Mayoclinic",
                "You know that exercise does your body good, but you're too busy and stressed to fit it into your routine. Hold on a second — there's good news when it comes to exercise and stress.\n" +
                        "\n" +
                        "Virtually any form of exercise, from aerobics to yoga, can act as a stress reliever. If you're not an athlete or even if you're out of shape, you can still make a little exercise go a long way toward stress management. Discover the connection between exercise and stress relief — and why exercise should be part of your stress management plan.\n" +
                        "\n" +
                        "Exercise and stress relief\n" +
                        "Exercise increases your overall health and your sense of well-being, which puts more pep in your step every day. But exercise also has some direct stress-busting benefits.\n" +
                        "\n" +
                        "It pumps up your endorphins. Physical activity helps bump up the production of your brain's feel-good neurotransmitters, called endorphins. Although this function is often referred to as a runner's high, a rousing game of tennis or a nature hike also can contribute to this same feeling.\n" +
                        "It's meditation in motion. After a fast-paced game of racquetball or several laps in the pool, you'll often find that you've forgotten the day's irritations and concentrated only on your body's movements.\n" +
                        "\n" +
                        "As you begin to regularly shed your daily tensions through movement and physical activity, you may find that this focus on a single task, and the resulting energy and optimism, can help you remain calm and clear in everything you do.\n" +
                        "\n" +
                        "It improves your mood. Regular exercise can increase self-confidence, it can relax you, and it can lower the symptoms associated with mild depression and anxiety. Exercise can also improve your sleep, which is often disrupted by stress, depression and anxiety. All of these exercise benefits can ease your stress levels and give you a sense of command over your body and your life.\n" +
                        "Put exercise and stress relief to work for you\n" +
                        "A successful exercise program begins with a few simple steps.\n" +
                        "\n" +
                        "Consult with your doctor. If you haven't exercised for some time and you have health concerns, you may want to talk to your doctor before starting a new exercise routine.\n" +
                        "Walk before you run. Build up your fitness level gradually. Excitement about a new program can lead to overdoing it and possibly even injury.\n" +
                        "\n" +
                        "For most healthy adults, the Department of Health and Human Services recommends getting at least 150 minutes a week of moderate aerobic activity (such as brisk walking or swimming) or 75 minutes a week of vigorous aerobic activity (such as running). You also can do a combination of moderate and vigorous activity.\n" +
                        "\n" +
                        "Also, incorporate strength training exercises at least twice a week.\n" +
                        "\n" +
                        "Do what you love. Virtually any form of exercise or movement can increase your fitness level while decreasing your stress. The most important thing is to pick an activity that you enjoy. Examples include walking, stair climbing, jogging, bicycling, yoga, tai chi, gardening, weightlifting and swimming.\n" +
                        "Pencil it in. Although your schedule may necessitate a morning workout one day and an evening activity the next, carving out some time to move every day helps you make your exercise program an ongoing priority.\n" +
                        "Stick with it\n" +
                        "Starting an exercise program is just the first step. Here are some tips for sticking with a new routine or reinvigorating a tired workout:\n" +
                        "\n" +
                        "Set SMART goals. Write down SMART goals — specific, measurable, attainable, relevant and time-limited goals.\n" +
                        "\n" +
                        "If your primary goal is to reduce stress in your life and recharge your batteries, your specific goals might include committing to walking during your lunch hour three times a week or, if needed, finding a baby sitter to watch your children so that you can slip away to attend a cycling class.\n" +
                        "\n" +
                        "Find a friend. Knowing that someone is waiting for you to show up at the gym or the park can be a powerful incentive. Working out with a friend, co-worker or family member often brings a new level of motivation and commitment to your workouts.\n" +
                        "Change up your routine. If you've always been a competitive runner, take a look at other less competitive options that may help with stress reduction, such as Pilates or yoga classes. As an added bonus, these kinder, gentler workouts may enhance your running while also decreasing your stress.\n" +
                        "Exercise in increments. Even brief bouts of activity offer benefits. For instance, if you can't fit in one 30-minute walk, try three 10-minute walks instead. Interval training, which entails brief (60 to 90 seconds) bursts of intense activity at almost full effort, is being shown to be a safe, effective and efficient way of gaining many of the benefits of longer duration exercise. What's most important is making regular physical activity part of your lifestyle.\n" +
                        "Whatever you do, don't think of exercise as just one more thing on your to-do list. Find an activity you enjoy — whether it's an active tennis match or a meditative meander down to a local park and back — and make it part of your regular routine. Any form of physical activity can help you unwind and become an important part of your approach to easing stress.",
                R.drawable.tips_stress
            )
        )
        mealList.add(
            Meal(
                "Get a routine",
                "By: Healthline",
                "Exercise is defined as any movement that makes your muscles work and requires your body to burn calories.\n" +
                        "\n" +
                        "There are many types of physical activity, including swimming, running, jogging, walking and dancing, to name a few.\n" +
                        "\n" +
                        "Being active has been shown to have many health benefits, both physically and mentally. It may even help you live longer (1Trusted Source).\n" +
                        "\n" +
                        "Here are the top 10 ways regular exercise benefits your body and brain.\n" +
                        "1. It Can Make You Feel Happier\n" +
                        "Exercise has been shown to improve your mood and decrease feelings of depression, anxiety and stress (2Trusted Source).\n" +
                        "\n" +
                        "It produces changes in the parts of the brain that regulate stress and anxiety. It can also increase brain sensitivity for the hormones serotonin and norepinephrine, which relieve feelings of depression (1Trusted Source).\n" +
                        "\n" +
                        "Additionally, exercise can increase the production of endorphins, which are known to help produce positive feelings and reduce the perception of pain (1Trusted Source).\n" +
                        "\n" +
                        "Furthermore, exercise has been shown to reduce symptoms in people suffering from anxiety. It can also help them be more aware of their mental state and practice distraction from their fears (1Trusted Source).\n" +
                        "\n" +
                        "Interestingly, it doesn't matter how intense your workout is. It seems that your mood can benefit from exercise no matter the intensity of the physical activity.\n" +
                        "\n" +
                        "In fact, a study in 24 women who had been diagnosed with depression showed that exercise of any intensity significantly decreased feelings of depression (3Trusted Source).\n" +
                        "\n" +
                        "The effects of exercise on mood are so powerful that choosing to exercise (or not) even makes a difference over short periods.\n" +
                        "\n" +
                        "One study asked 26 healthy men and women who normally exercised regularly to either continue exercising or stop exercising for two weeks. Those who stopped exercising experienced increases in negative mood (4Trusted Source).\n" +
                        "\n" +
                        "SUMMARY:\n" +
                        "Exercising regularly can improve your mood and reduce feelings of anxiety and depression.\n" +
                        "2. It Can Help With Weight Loss\n" +
                        "Some studies have shown that inactivity is a major factor in weight gain and obesity (5Trusted Source, 6Trusted Source).\n" +
                        "\n" +
                        "To understand the effect of exercise on weight reduction, it is important to understand the relationship between exercise and energy expenditure.\n" +
                        "\n" +
                        "Your body spends energy in three ways: digesting food, exercising and maintaining body functions like your heartbeat and breathing.\n" +
                        "\n" +
                        "While dieting, a reduced calorie intake will lower your metabolic rate, which will delay weight loss. On the contrary, regular exercise has been shown to increase your metabolic rate, which will burn more calories and help you lose weight (5Trusted Source, 6Trusted Source, 7Trusted Source, 8Trusted Source).\n" +
                        "\n" +
                        "Additionally, studies have shown that combining aerobic exercise with resistance training can maximize fat loss and muscle mass maintenance, which is essential for keeping the weight off (6Trusted Source, 8Trusted Source, 9Trusted Source, 10, 11Trusted Source).\n" +
                        "\n" +
                        "SUMMARY:\n" +
                        "Exercise is crucial to supporting a fast metabolism and burning more calories per day. It also helps you maintain your muscle mass and weight loss.\n" +
                        "3. It Is Good for Your Muscles and Bones\n" +
                        "Exercise plays a vital role in building and maintaining strong muscles and bones.\n" +
                        "\n" +
                        "Physical activity like weight lifting can stimulate muscle building when paired with adequate protein intake.\n" +
                        "\n" +
                        "This is because exercise helps release hormones that promote the ability of your muscles to absorb amino acids. This helps them grow and reduces their breakdown (12Trusted Source, 13Trusted Source).\n" +
                        "\n" +
                        "As people age, they tend to lose muscle mass and function, which can lead to injuries and disabilities. Practicing regular physical activity is essential to reducing muscle loss and maintaining strength as you age (14Trusted Source).\n" +
                        "\n" +
                        "Also, exercise helps build bone density when you're younger, in addition to helping prevent osteoporosis later in life (15Trusted Source).\n" +
                        "\n" +
                        "Interestingly, high-impact exercise, such as gymnastics or running, or odd-impact sports, such as soccer and basketball, have been shown to promote a higher bone density than non-impact sports like swimming and cycling (16Trusted Source).\n" +
                        "\n" +
                        "SUMMARY:\n" +
                        "Physical activity helps you build muscles and strong bones. It may also help prevent osteoporosis.\n" +
                        "4. It Can Increase Your Energy Levels\n" +
                        "Exercise can be a real energy booster for healthy people, as well as those suffering from various medical conditions (17Trusted Source, 18Trusted Source).\n" +
                        "\n" +
                        "One study found that six weeks of regular exercise reduced feelings of fatigue for 36 healthy people who had reported persistent fatigue (19Trusted Source).\n" +
                        "\n" +
                        "Furthermore, exercise can significantly increase energy levels for people suffering from chronic fatigue syndrome (CFS) and other serious illnesses (20Trusted Source, 21Trusted Source).\n" +
                        "\n" +
                        "In fact, exercise seems to be more effective at combating CFS than other treatments, including passive therapies like relaxation and stretching, or no treatment at all (20Trusted Source).\n" +
                        "\n" +
                        "Additionally, exercise has been shown to increase energy levels in people suffering from progressive illnesses, such as cancer, HIV/AIDS and multiple sclerosis (21Trusted Source).\n" +
                        "\n" +
                        "SUMMARY:\n" +
                        "Engaging in regular physical activity can increase your energy levels. This is true even in people with persistent fatigue and those suffering from serious illnesses.\n" +
                        "5. It Can Reduce Your Risk of Chronic Disease\n" +
                        "Lack of regular physical activity is a primary cause of chronic disease (22Trusted Source).\n" +
                        "\n" +
                        "Regular exercise has been shown to improve insulin sensitivity, cardiovascular fitness and body composition, yet decrease blood pressure and blood fat levels (23Trusted Source, 24Trusted Source, 25Trusted Source, 26Trusted Source).\n" +
                        "\n" +
                        "In contrast, a lack of regular exercise — even in the short term — can lead to significant increases in belly fat, which increases the risk of type 2 diabetes, heart disease and early death (23Trusted Source).\n" +
                        "\n" +
                        "Therefore, daily physical activity is recommended to reduce belly fat and decrease the risk of developing these diseases (27Trusted Source, 28Trusted Source).\n" +
                        "\n" +
                        "SUMMARY:\n" +
                        "Daily physical activity is essential to maintaining a healthy weight and reducing the risk of chronic disease.\n" +
                        "6. It Can Help Skin Health\n" +
                        "Your skin can be affected by the amount of oxidative stress in your body.\n" +
                        "\n" +
                        "Oxidative stress occurs when the body's antioxidant defenses cannot completely repair the damage that free radicals cause to cells. This can damage their internal structures and deteriorate your skin.\n" +
                        "\n" +
                        "Even though intense and exhaustive physical activity can contribute to oxidative damage, regular moderate exercise can increase your body's production of natural antioxidants, which help protect cells (29Trusted Source, 30Trusted Source).\n" +
                        "\n" +
                        "In the same way, exercise can stimulate blood flow and induce skin cell adaptations that can help delay the appearance of skin aging (31Trusted Source).\n" +
                        "\n" +
                        "SUMMARY:\n" +
                        "Moderate exercise can provide antioxidant protection and promote blood flow, which can protect your skin and delay signs of aging.\n" +
                        "7. It Can Help Your Brain Health and Memory\n" +
                        "Exercise can improve brain function and protect memory and thinking skills.\n" +
                        "\n" +
                        "To begin with, it increases your heart rate, which promotes the flow of blood and oxygen to your brain.\n" +
                        "\n" +
                        "It can also stimulate the production of hormones that can enhance the growth of brain cells.\n" +
                        "\n" +
                        "Moreover, the ability of exercise to prevent chronic disease can translate into benefits for your brain, since its function can be affected by these diseases (32).\n" +
                        "\n" +
                        "Regular physical activity is especially important in older adults since aging — combined with oxidative stress and inflammation — promotes changes in brain structure and function (33Trusted Source, 34Trusted Source).\n" +
                        "\n" +
                        "Exercise has been shown to cause the hippocampus, a part of the brain that's vital for memory and learning, to grow in size. This serves to increase mental function in older adults (33Trusted Source, 34Trusted Source, 35Trusted Source).\n" +
                        "\n" +
                        "Lastly, exercise has been shown to reduce changes in the brain that can cause Alzheimer's disease and schizophrenia (36Trusted Source).\n" +
                        "\n" +
                        "SUMMARY:\n" +
                        "Regular exercise improves blood flow to the brain and helps brain health and memory. Among older adults, it can help protect mental function.\n" +
                        "8. It Can Help With Relaxation and Sleep Quality\n" +
                        "Regular exercise can help you relax and sleep better (37Trusted Source, 38Trusted Source).\n" +
                        "\n" +
                        "In regards to sleep quality, the energy depletion that occurs during exercise stimulates recuperative processes during sleep (38Trusted Source).\n" +
                        "\n" +
                        "Moreover, the increase in body temperature that occurs during exercise is thought to improve sleep quality by helping it drop during sleep (39Trusted Source).\n" +
                        "\n" +
                        "Many studies on the effects of exercise on sleep have reached similar conclusions.\n" +
                        "\n" +
                        "One study found that 150 minutes of moderate-to-vigorous activity per week can provide up to a 65% improvement in sleep quality (40).\n" +
                        "\n" +
                        "Another showed that 16 weeks of physical activity increased sleep quality and helped 17 people with insomnia sleep longer and more deeply than the control group. It also helped them feel more energized during the day (41Trusted Source).\n" +
                        "\n" +
                        "What's more, engaging in regular exercise seems to be beneficial for the elderly, who tend to be affected by sleep disorders (41Trusted Source, 42Trusted Source, 43Trusted Source).\n" +
                        "\n" +
                        "You can be flexible with the kind of exercise you choose. It appears that either aerobic exercise alone or aerobic exercise combined with resistance training can equally help sleep quality (44Trusted Source).\n" +
                        "\n" +
                        "SUMMARY:\n" +
                        "Regular physical activity, regardless of whether it is aerobic or a combination of aerobic and resistance training, can help you sleep better and feel more energized during the day.\n" +
                        "9. It Can Reduce Pain\n" +
                        "Chronic pain can be debilitating, but exercise can actually help reduce it (45Trusted Source).\n" +
                        "\n" +
                        "In fact, for many years, the recommendation for treating chronic pain was rest and inactivity. However, recent studies show that exercise helps relieve chronic pain (45Trusted Source).\n" +
                        "\n" +
                        "A review of several studies indicates that exercise helps participants with chronic pain reduce their pain and improve their quality of life (45Trusted Source).\n" +
                        "\n" +
                        "Several studies show that exercise can help control pain that's associated with various health conditions, including chronic low back pain, fibromyalgia and chronic soft tissue shoulder disorder, to name a few (46Trusted Source).\n" +
                        "\n" +
                        "Additionally, physical activity can also raise pain tolerance and decrease pain perception (47Trusted Source, 48Trusted Source).\n" +
                        "\n" +
                        "SUMMARY:\n" +
                        "Exercise has favorable effects on the pain that's associated with various conditions. It can also increase pain tolerance.\n" +
                        "10. It Can Promote a Better Sex Life\n" +
                        "Exercise has been proven to boost sex drive (49Trusted Source, 50, 51).\n" +
                        "\n" +
                        "Engaging in regular exercise can strengthen the cardiovascular system, improve blood circulation, tone muscles and enhance flexibility, all of which can improve your sex life (49Trusted Source, 51).\n" +
                        "\n" +
                        "Physical activity can improve sexual performance and sexual pleasure, as well as increase the frequency of sexual activity (50, 52Trusted Source).\n" +
                        "\n" +
                        "A group of women in their 40s observed that they experienced orgasms more frequently when they incorporated more strenuous exercise, such as sprints, boot camps and weight training, into their lifestyles (53Trusted Source).\n" +
                        "\n" +
                        "Also, among a group of 178 healthy men, the men that reported more exercise hours per week had higher sexual function scores (50).\n" +
                        "\n" +
                        "One study found that a simple routine of a six-minute walk around the house helped 41 men reduce their erectile dysfunction symptoms by 71% (54Trusted Source).\n" +
                        "\n" +
                        "Another study performed in 78 sedentary men revealed how 60 minutes of walking per day (three and a half days per week, on average) improved their sexual behavior, including frequency, adequate functioning and satisfaction (55Trusted Source).\n" +
                        "\n" +
                        "What's more, a study demonstrated that women suffering from polycystic ovary syndrome, which can reduce sex drive, increased their sex drive with regular resistance training for 16 weeks (56Trusted Source).\n" +
                        "\n" +
                        "SUMMARY:\n" +
                        "Exercise can help improve sexual desire, function and performance in men and women. It can also help decrease the risk of erectile dysfunction in men.\n" +
                        "The Bottom Line\n" +
                        "Exercise offers incredible benefits that can improve nearly every aspect of your health from the inside out.\n" +
                        "\n" +
                        "Regular physical activity can increase the production of hormones that make you feel happier and help you sleep better.\n" +
                        "\n" +
                        "It can also improve your skin's appearance, help you lose weight and keep it off, lessen the risk of chronic disease and improve your sex life.\n" +
                        "\n" +
                        "Whether you practice a specific sport or follow the guideline of 150 minutes of activity per week, you will inevitably improve your health in many ways (57Trusted Source).",
                R.drawable.tips_training_routine
            )
        )
        mealList.add(
            Meal(
                "Track your progress",
                "By: Amanda Bireline - NIFS",
                "We are all on a fitness journey in one way or another. With life’s hectic schedule, it’s easy to lose track of where you are and where you want to be regarding your fitness. No matter whether you are trying to lose weight, put on mass, or maintain where you are, tracking fitness progress is an essential piece of your ongoing success.\n" +
                        "\n" +
                        "While some people track every single workout, all gains, and all food consumed in their fitness journal, others just want to get it done and go by how they feel. But with the constant change in technology, specifically in the fitness industry, tracking progress becomes easier and easier; and in fact, it can add some benefits to your training.\n" +
                        "\n" +
                        "he Benefits of Logging and Tracking\n" +
                        "For those who regularly log and track their progress, you may not need to be convinced why you should be tracking it. But keep reading—this is still for you! And for those who don’t normally track progress, take a quick look at why it might be important to start.\n" +
                        "\n" +
                        "Makes it more likely to reach and surpass your goal.\n" +
                        "Allows you to be more efficient in your time and workouts.\n" +
                        "Lends accountability to yourself and your goals.\n" +
                        "Allows for easier modifications and shows when and where changes need to be made.\n" +
                        "It can be motivating and reinforcing to remind you why you are doing what you are.\n" +
                        "Helps to drive the focus and direction of your programming.\n" +
                        "Keeps you committed to your plan.",
                R.drawable.tips_track
            )
        )
        mealList.add(
            Meal(
                "Eat fibers",
                "By: Muscleandfitness",
                "You may have been told that all calories are created equally; however, that’s not always the case.\n" +
                        "\n" +
                        "For example, people often class grams of fiber as just another gram of carbohydrate, when in fact, its health-promoting and disease-reducing benefits are quite unique.\n" +
                        "\n" +
                        "If you’ve been ignoring fiber and just focusing on calories or the 3 main macros, then this three-part article series is for you. In this first installment we’ll cover the correlative research around fiber benefiting weight loss.\n" +
                        "\n" +
                        "Over the course of this three-part series on fiber, we will break down the different types of fibers and explain how they can help with fat loss, fighting disease, improving gut health, controlling blood sugar levels and much more.\n" +
                        "\n" +
                        "Fiber Can Help You Lose Weight\n" +
                        "\n" +
                        "Numerous studies have shown that fiber is a key nutrient which can help you lose weight as it aids in slowing down digestion, reduces the spike in blood sugar and insulin while aiding in general satiety.\n" +
                        "\n" +
                        "In addition to this, high fiber foods often have a low calorie density. Which means you will naturally eat less, even if you aren’t consciously trying to diet, control your portion-size or count calories. Several studies have shown less calories are consumed when eating high fiber or low calorie dense foods; in one study participants who were provided with low calorie, high fiber foods ate a whopping 50% less.\n" +
                        "While most people assume these benefits are only present when one consumes their fiber from natural sources such as vegetables or grains this isn’t the case. One study actually tested all the main types of fiber in supplement form over a 5 week diet.\n" +
                        "\n" +
                        "They added these fibers to a 1200 calorie controlled diet, finding all the fiber groups resulted in significantly greater weight loss than the normal diet without the fiber. This equaled about 1.5LB extra weight loss per week, which equaled 7.5LB of extra weight loss over the 5 week period. If these figures were to be extended over a 6 month diet, it would equate to a drastic 39LB of weight loss just from fiber supplementation (1).\n" +
                        "\n" +
                        "Interestingly, although we are still waiting on human trials, one study in rodents found that the use of Soluble Corn Fiber, (the fiber used in Quest Bars), even decreased the fat gain during over-feeding while reducing levels of fat in the blood by an astounding 30% (2).\n" +
                        "\n" +
                        "Several other studies have shown fiber and fiber supplements, including some newer data on Soluble Corn Fiber, to be effective at improving satiety and increasing weight loss over a lower fiber diet (3,4,5).\n" +
                        "\n" +
                        "That wraps up the first part in our series on fiber. Already we’re starting to see more and more researching being done to truly understand the importance of fiber in our daily nutrition. For bodybuilders especially, as satiety is a huge factor come competition time when meals are calculated and every calorie counts.",
                R.drawable.tips_fiber
            )
        )
        mealList.add(
            Meal(
                "Reduce fat intake",
                "By: Healthline",
                "Calculate your calorie intake\n" +
                        "Fat loss occurs when you consistently eat fewer calories than you burn.\n" +
                        "\n" +
                        "The number of calories you should eat per day to lose weight depends on your weight, height, lifestyle, gender, and exercise levels.\n" +
                        "\n" +
                        "In general, an average woman needs around 2,000 calories per day to maintain her weight but 1,500 calories to lose 1 pound (0.45 kg) of fat per week, whereas an average man needs around 2,500 calories to maintain his weight or 2,000 calories to lose the same amount (5Trusted Source).\n" +
                        "\n" +
                        "A slow, even rate of weight loss — such as 1 pound (0.45 kg) or 0.5–1% of your body weight per week — is best for a cutting diet (4Trusted Source).\n" +
                        "\n" +
                        "Although a larger calorie deficit may help you lose weight faster, research has shown that it increases your risk of losing muscle, which is not ideal for this diet (4Trusted Source, 6Trusted Source).\n" +
                        "\n" +
                        "Determine your protein intake\n" +
                        "Maintaining adequate protein intake is important on a cutting diet.\n" +
                        "\n" +
                        "Numerous studies have found that high protein intake can aid fat loss by boosting your metabolism, reducing your appetite, and preserving lean muscle mass (7Trusted Source, 8Trusted Source, 9Trusted Source).\n" +
                        "\n" +
                        "If you’re on a cutting diet, you need to eat more protein than if you’re merely trying to maintain weight or build muscle mass. That’s because you’re getting fewer calories but exercising routinely, which increases your protein needs (10Trusted Source).\n" +
                        "\n" +
                        "Most studies suggest that 0.7–0.9 grams of protein per pound of body weight (1.6–2.0 grams per kg) is sufficient for conserving muscle mass on a cutting diet (4Trusted Source, 10Trusted Source).\n" +
                        "\n" +
                        "For example, a 155-pound (70-kg) person should eat 110–140 grams of protein per day.\n" +
                        "\n" +
                        "Determine your fat intake\n" +
                        "Fat plays a key role in hormone production, which makes it crucial for a cutting diet (11Trusted Source).\n" +
                        "\n" +
                        "While it’s common to reduce fat intake on a cutting diet, not eating enough can affect the production of hormones like testosterone and IGF-1, which help preserve muscle mass.\n" +
                        "\n" +
                        "For example, studies demonstrate that reducing fat intake from 40% to 20% of total calories lowers testosterone levels by a modest but significant amount (4Trusted Source, 12Trusted Source).\n" +
                        "\n" +
                        "However, some evidence suggests that a drop in testosterone levels does not always lead to muscle loss — as long as you eat enough protein and carbs (5Trusted Source, 13Trusted Source).\n" +
                        "\n" +
                        "Experts suggest that, on this diet, 15–30% of your calories should come from fat (4Trusted Source).\n" +
                        "\n" +
                        "One gram of fat contains 9 calories, so anyone on a 2,000-calorie regimen should eat 33–67 grams of fat per day on a cutting diet.\n" +
                        "\n" +
                        "If you do intense exercise, the lower end of that fat range may be best because it allows for higher carb intake.\n" +
                        "\n" +
                        "Determine your carb intake\n" +
                        "Carbs play a key role in preserving muscle mass while on a cutting diet.\n" +
                        "\n" +
                        "Because your body prefers to use carbs for energy instead of protein, eating an adequate number of carbs may combat muscle loss (14Trusted Source).\n" +
                        "\n" +
                        "Additionally, carbs can help fuel your performance during workouts (15Trusted Source).\n" +
                        "\n" +
                        "On a cutting diet, carbs should comprise the remaining calories after you subtract protein and fat.\n" +
                        "\n" +
                        "Protein and carbs both provide 4 calories per gram, while fat stands at 9 per gram. After subtracting your protein and fat needs from your total calorie intake, divide the remaining number by 4, which should tell you how many carbs you can eat per day.\n" +
                        "\n" +
                        "For example, a 155-pound (70-kg) person on a 2,000-calorie cutting diet may eat 110 grams of protein and 60 grams of fat. The remaining 1,020 calories (255 grams) can be taken up by carbs.\n" +
                        "\n" +
                        "SUMMARY\n" +
                        "To plan a cutting diet, you should calculate your calorie, protein, fat, and carb needs based on your weight and lifestyle factors.\n" +
                        "\n" +
                        "HEALTHLINE RESOURCES\n" +
                        "Find the diet that’s right for you with our free diet quiz\n" +
                        "Our free assessment ranks the best diets for you based on your answers to 3 quick questions.\n" +
                        "\n" +
                        "Does meal timing matter? \n" +
                        "Meal timing is a strategy used for muscle growth, fat loss, and performance.\n" +
                        "\n" +
                        "Although it may benefit competitive athletes, it isn’t as important for fat loss (15Trusted Source).\n" +
                        "\n" +
                        "For example, many studies note that endurance athletes can boost their recovery by timing their meals and carb intake around exercise (15Trusted Source, 16, 17Trusted Source).\n" +
                        "\n" +
                        "That said, this isn’t necessary for the cutting diet.\n" +
                        "\n" +
                        "Instead, you should focus on eating whole foods and getting sufficient calories, protein, carbs, and fat throughout the day.\n" +
                        "\n" +
                        "If you’re hungry frequently, a high-calorie breakfast may keep you fuller later in the day (18Trusted Source, 19Trusted Source, 20).\n" +
                        "\n" +
                        "SUMMARY\n" +
                        "Timing your meals isn’t necessary on the cutting diet but may assist endurance athletes with their training.\n" +
                        "\n" +
                        "Cheat meals and refeed days \n" +
                        "Cheat meals and/or refeed days are commonly incorporated into cutting diets.\n" +
                        "\n" +
                        "Cheat meals are occasional indulgences meant to ease the strictness of a given diet, whereas refeed days boost your carb intake once or twice per week.\n" +
                        "\n" +
                        "A higher carb intake has several benefits, such as restoring your body’s glucose stores, improving exercise performance, and balancing several hormones (21Trusted Source, 22Trusted Source).\n" +
                        "\n" +
                        "For example, studies show that a higher-carb day can increase levels of the fullness hormone leptin and temporarily raise your metabolism (23Trusted Source, 24Trusted Source, 25Trusted Source).\n" +
                        "\n" +
                        "Although you may gain weight after a cheat meal or refeed day, this tends to be water weight that’s usually lost over the next few days (26Trusted Source).\n" +
                        "\n" +
                        "Still, it’s easy to overeat on these days and sabotage your weight loss efforts. Moreover, these routines may promote unhealthy habits, especially if you’re prone to emotional eating (27Trusted Source, 28Trusted Source, 29Trusted Source).\n" +
                        "\n" +
                        "Thus, cheat meals and refeed days aren’t required and should be planned out carefully.\n" +
                        "\n" +
                        "SUMMARY\n" +
                        "Cheat meals and refeed days may boost your morale, exercise performance, and hormone levels but aren’t necessary for a cutting diet. They can hinder your progress if improperly planned.\n" +
                        "\n" +
                        "Helpful tips for a cutting diet\n" +
                        "Here are some helpful tips to keep fat loss on track on a cutting diet:\n" +
                        "\n" +
                        "Choose more fiber-rich foods. Fiber-rich carb sources like non-starchy vegetables tend to contain more nutrients and can help you stay fuller for longer while on a calorie deficit (30Trusted Source).\n" +
                        "Drink plenty of water. Staying hydrated may help curb your appetite and temporarily speed up your metabolism (31Trusted Source, 32Trusted Source).\n" +
                        "Try meal prepping. Preparing meals ahead of schedule can help save time, keep you on track with your diet, and avoid the temptation of unhealthy foods.\n" +
                        "Avoid liquid carbs. Sports drinks, soft drinks, and other sugar-rich beverages lack micronutrients, may increase your levels of hunger, and aren’t as filling as fiber-rich, whole foods (33Trusted Source).\n" +
                        "Consider cardio. When used alongside weight lifting, aerobic exercise — especially high-intensity cardio — may further your fat loss (34Trusted Source).\n" +
                        "SUMMARY\n" +
                        "To optimize a cutting diet, try drinking lots of water, eating fiber-rich foods, and doing cardio, among several other tips.\n" +
                        "\n" +
                        "The bottom line\n" +
                        "A cutting diet is meant to maximize fat loss while maintaining muscle mass.\n" +
                        "\n" +
                        "This diet involves calculating your calorie, protein, fat, and carb needs based on your weight and lifestyle. You’re only meant to follow it for a few months preceding an athletic event and should combine it with weightlifting.\n" +
                        "\n" +
                        "If you’re interested in this weight loss diet for athletes, consult your trainer or a medical professional to see if it’s right for you.",
                R.drawable.tips_fats
            )
        )
        mealList.add(
            Meal(
                "Enjoy the process",
                "By: Rehband",
                "The human body was made to move. Yet today many of us find ourselves at jobs sitting in front of screens, hardly moving at all in our everyday lives. Tight schedules and high stress levels means some of us choose to down-prioritize exercise.\n" +
                        "\n" +
                        "The truth is, working out is about much more than looking good or loosing fat. It’s also about feeling good, being healthy and finding the energy to go through the rest of your life happily. Studies suggest that regular exercise can even make us perform better at work – by increasing our productivity and helping us make better decisions.\n" +
                        "\n" +
                        "Just like our community, we love an active lifestyle. Here are 6 benefits of working out that will hopefully make you want to make get out, and get moving!\n" +
                        "\n" +
                        " \n" +
                        "\n" +
                        "1. PEOPLE THAT EXERCISE ARE HAPPIER\n" +
                        "People that exercise regularly are happier. Both cardio and strength training can increase your mood, with research suggesting that working out around 30 minutes, 3 – 5 days a week, can have the best positive mood boosting effect. University of Bristol found that exercising at work helps increase happiness, and that people who worked out felt re-energized and keener on networking.\n" +
                        "2. WORKING OUT DECREASES STRESS LEVELS\n" +
                        "Working out helps reduce stress. Endurance exercise reduces adrenaline and cortisol in the body, also known as stress hormones.  At the same time, working out releases more endorphins which helps boost our mood and also makes us feel more relaxed. Clinical trials with endurance athletes have shown that exercise can be used to help treat anxiety and depression.\n" +
                        "3. PEOPLE THAT WORK OUT SLEEP BETTER\n" +
                        "Do you have a hard time falling asleep, or do you tend to wake up several times during the night? Research has shown that people who exercise at least 150 minutes a week sleep significantly better, and also feel more energized during the day. According to the same study, between 35 to 40 percent of the US population have problems with falling asleep or with feeling tired during the day. Exercise could help!\n" +
                        "4. EXERCISE BOOSTS OUR IMMUNE SYSTEM\n" +
                        "We become healthier when we work out, not just because of muscle gain and fat loss, but also because we boost our immune system. Studies have found a link between regular exercise and a stronger immune system. Some studies suggest that working out can even help us prevent catching a cold.\n" +
                        "5. WORKING OUT MAKES US SMARTER\n" +
                        "Studies show that working out can actually make us smarter. Regular exercise helps us focus better and improves our memory. Some studies suggest that tough exercise benefits our brains by producing more brain-derived protein that is believed to help us learn better and make better decisions.\n" +
                        "6. EXERCISE GIVES US A CONFIDENCE BOOST!\n" +
                        "Setting and sticking to your goals can help anyone feel better about themselves and their accomplishments. There’s a lot of research that suggests working out makes us more confident about ourselves and our abilities, which of course also reflects in all other aspects of life. A study from the University of Essex suggests that our self-confidence gets an extra boost from working out outside."
                ,
                R.drawable.tips_cheat
            )
        )
        mealList.add(
            Meal(
                "Avoid alcohol",
                "By: Indi",
                        "Whether you exercise casually or competitively it is important to understand the effect alcohol can have on your performance. Taking too much alcohol could prevent you reaping the rewards from all the work you have put in to exercise.\n" +
                                "\n" +
                                "Effects of alcohol on sports performance\n" +
                                "Alcohol can alter your sports performance because of how it affects the body during exercise. It does this in several ways:\n" +
                                "\n" +
                                "Alcohol dehydrates you. This is because it is a diuretic, which means it makes your kidneys produce more urine. Therefore drinking too much alcohol can lead to dehydration. Exercising soon after drinking alcohol can make dehydration worse because you also sweat during exercise. Dehydration leads to reduced exercise performance. You need to be well hydrated when you exercise to maintain the flow of blood through your body, which is essential for carrying oxygen and nutrients to your muscles, thus maximising performance.\n" +
                                "\n" +
                                "Alcohol can interfere with the way your body makes energy. Alcohol is broken down in the liver. When you are breaking down alcohol, all other functions of the liver are secondary, one function involves glucose production, we need glucose for energy. If your liver is not producing enough glucose, your body will become tired as it works to expel the alcohol, making it even more of a struggle to keep up the pace.\n" +
                                "\n" +
                                "Alcohol slows down the nerves that pass messages around the body, causing a relaxed feeling. This effect can take time to wear off and this can result in your reactions, coordination, accuracy and balance being slower than usual during exercise and competition.\n" +
                                "\n" +
                                "Exercising the day after the night before\n" +
                                "Drinking alcohol the night before exercise could have a negative influence on your performance the following day. It is not possible to perform at your best if you are feeling any of the effects normally associated with a hangover such as dehydration or headache.\n" +
                                "\n" +
                                "During exercise, your muscles burn glucose for energy. This produces lactic acid. Too much lactic acid leads to muscle fatigue and cramps. If you exercise after drinking your liver will be working harder to get rid of the toxins from the alcohol so it will be slower to clear out the lactic acid thus, increasing the likelihood of cramps. You will also lack strength or power and are more likely to feel tired quicker.\n" +
                                "\n" +
                                "For these reasons it is advisable to avoid alcohol the night before exercise, especially if it is due to be moderate or intense activity. However, if you do decide to drink, ideally limit the number of drinks and take alcohol with food. \n" +
                                "\n" +
                                "Drinking directly after exercise is also not advisable if you have not consumed enough water to replace the fluids you lost through sweating.\n" +
                                "\n" +
                                "Other health effects\n" +
                                "Weight gain\n" +
                                "\n" +
                                "Alcohol is high in calories: seven calories per gram, almost as many as pure fat. If you exercise to help manage your weight you could be taking in unnecessary ‘empty’ calories through alcohol and preventing weight loss. In addition to this after a few drinks you may be tempted to eat high calorie foods which will further hamper your efforts and progress.\n" +
                                "\n" +
                                "Poor muscle growth\n" +
                                "\n" +
                                "Alcohol can disrupt sleep patterns. Growth hormones which we need for muscle growth are released during deep sleep. Therefore disrupted sleep from alcohol can lead to slow muscle gain.\n" +
                                "\n" +
                                "Altered heart rate\n" +
                                "\n" +
                                "Most worryingly, drinking can increase the potential for unusual heart rhythms. This is a risk which significantly increases during exercise up to two days after heavy alcohol consumption. This risk varies between individuals. The physical activity itself increases your heart rate and with a lot of alcohol in your system your heart is put under further stress.\n" +
                                "\n" +
                                "Slow healing after injury:\n" +
                                "\n" +
                                "Alcohol causes the blood vessels to the skin, arms and legs to open up which results in an increased blood supply making an injury bleed and swell even more, slowing down the recovery process.",
                R.drawable.tips_alcohol
            )
        )
        mealList.add(
            Meal(
                "Eat proteins",
                "By: Eattrek",
                "When people hear “protein” and “exercise” in the same sentence, they’re most likely to picture images of bodybuilders eating tons of chicken and drinking protein shakes in order to maximise their gains. Protein is important for everyone who is hitting the gym, playing sports, going for runs or doing any other form of exercise, not just the pros.\n" +
                        "\n" +
                        "Protein is a macronutrient. To put it simply, protein is one of the main nutrients that every person needs to maintain a healthy body. It helps to repair any internal or external damage, supports the immune system and contributes to an overall feeling of wellbeing. At a cellular level, proteins are used for just about everything, from transporting messages, carry out the instructions of DNA and defending, preserving and repairing essential life functions.\n" +
                        "\n" +
                        "PROTEIN, NUTRITION AND EXERCISE\n" +
                        "It doesn’t matter which way you look at it, protein is essential for exercise. Anyone undertaking any kind of exercise routine is definitely going to need more protein than someone who doesn’t. This is because when you exercise, you are effectively tearing and breaking muscle fibres apart, which then need to be repaired by the body, which requires protein.\n" +
                        "\n" +
                        "HOW TO GET ENOUGH PROTEIN ON A PLANT BASED DIET\n" +
                        "From a dietary point of view, you can get enough day to day protein from eating food such as beans, soy protein products, nuts and other such foods. If you are exercising, it is beneficial to supplement this normal intake of protein with additional food items such as protein bars, powders or shakes. Trek Protein Energy Bars come in a variety of delicious flavours. They’re vegan, gluten free and are packed with 10g of protein.\n" +
                        "\n" +
                        "REPAIR, MAINTAIN, GROW\n" +
                        "Protein is especially important to consume after a workout, as during exercise you are effectively breaking your muscles down. That is why it’s common to see people at the gym eating protein bars or drinking whey shakes when they have finished their routine. It helps to increase the impact of their exercise. It’s also important to mix this protein with carbohydrates as they helps your body to absorb the protein and turn it into more muscle mass.\n" +
                        "\n" +
                        "If you are exercising but find yourself with low energy or feel that you are not building any muscle, it may be down to not having enough protein in your diet. Make an effort to eat more protein through your meals and supplement your intake with protein-rich snacks like Trek Protein Flapjacks and Protein Nut Bars. You should start to feel better and get better results for all of your hard work.",
                R.drawable.tips_protein
            )
        )
        mealList.add(
            Meal(
                "Avoid soda",
                "By: Chucktownfitness",
                "10 Reasons to Avoid Drinking Soda\n" +
                        "The average American drinks approximately 56 GALLONS of soda a year. Some people even admit to drinking more soda than water each day, or no water at all. Soft drinks are a multi-billion dollar product, and they account for a quarter of all drinks consumed in the United States.\n" +
                        "\n" +
                        "With Americans consuming this large amount of soft drinks each year, it becomes important to evaluate how soft drinks can influence a person’s health. With a little research, it becomes clear that even moderate consumption of soda can be dangerous.\n" +
                        "\n" +
                        "10 Reasons to Avoid Drinking Soda\n" +
                        "\n" +
                        "The Sugar! – A single can of soda contains the equivalent of 10 teaspoons of sugar. This amount of sugar, especially in liquid form, skyrockets the blood sugar and causes an insulin reaction in the body. Over time, this can lead to diabetes or insulin resistance, not to mention weight gain and other health problems. Soft drink companies are the largest user of sugar in the country.\n" +
                        "Phosphoric Acid -Soda contains phosphoric acid, which interferes with the body’s ability to absorb calcium and can lead to osteoporosis, cavities and bone softening. Phosphoric Acid also interacts with stomach acid, slowing digestion and blocking nutrient absorption.\n" +
                        "Artificial Sweeteners– In diet sodas, aspartame is used as a substitute for sugar, and can actually be more harmful. It has been linked to almost a hundred different health problems including seizures, multiple sclerosis, brain tumors, diabetes, and emotional disorders. It converts to methanol at warm temperatures and methanol breaks down to formaldehyde and formic acid. Diet sodas also increase the risk of metabolic syndrome, which causes belly fat, high blood sugar and raised cholesterol.\n" +
                        "Caffeine– Most sodas contain caffeine, which has been linked to certain cancers, breast lumps, irregular heart beat, high blood pressure, and other problems.\n" +
                        "The Water– The water used in soda is just simple tap water and can contain chemicals like chlorine, fluoride and traces of heavy metals.\n" +
                        "Obesity– Harvard researchers have recently positively linked soft drinks to obesity. The study found that 12 year olds who drank soda were more likely to be obese than those who didn’t, and for each serving of soda consumed daily, the risk of obesity increased 1.6 times.\n" +
                        "Extra Fructose– Sodas contain High Fructose Corn Syrup, which obviously comes from corn. Most of this corn has been genetically modified, and there are no long term studies showing the safety of genetically modified crops, as genetic modification of crops has only been around since the 1990s. Also, the process of making High Fructose Corn Syrup involves traces of mercury, which causes a variety of long term health problems.\n" +
                        "Lack of Nutrients– There is absolutely no nutritional value in soda whatsoever. Not only are there many harmful effects of soda, but there are not even any positive benefits to outweigh them.  Soda is an unnatural substance that harms the body.\n" +
                        "Dehydration– Because of the high sugar, sodium and caffeine content in soda, it dehydrates the body and over a long period of time can cause chronic dehydration.\n" +
                        "Bad for the teeth– Drinking soda regularly causes plaque to build up on the teeth and can lead to cavities and gum disease.\n" +
                        "Healthy Alternatives to Soda\n" +
                        "\n" +
                        "I’ve heard that it can be really difficult for those who consume soda daily to just stop drinking it. This actually makes sense when you think about how soda affects the body and creates positive feedback loops thanks to its sugar content.\n" +
                        "\n" +
                        "If you’re a soda drinker and are looking for some healthy alternatives, here are a few suggestions:\n" +
                        "\n" +
                        "Club soda flavored with a squeeze of fresh fruit juice or some fresh ginger root\n" +
                        "Homemade water kefir– this has the same fizz and sweetness but with a boost of probiotics!\n" +
                        "Kombucha– Like kefir, you can make this at home and it is naturally fizzy and packed with vitamins\n" +
                        "Bottom Line\n" +
                        "\n" +
                        "Soda may taste good and it certainly seem to have some addictive properties (ask any self-proclaimed Diet Coke addict!) but the bottom line is that soda isn’t healthy in any amount and there is absolutely no reason or justification for consuming it.\n" +
                        "\n" +
                        "Sure, some will argue that anything in moderation is ok, and in most cases, I’d agree. When it comes to soda, the amount of sugar and additives is so high that I don’t think “moderation” even exists, and it certainly doesn’t exist in the 20 ounce or larger bottles that are so common.\n" +
                        "\n" +
                        "We are talking about a substance that is often consumed in place of water (which most of us don’t drink enough of), contains no nutrients or beneficial properties whatsoever, and is packed with sugar and artificial additives that make it harmful.\n" +
                        "\n" +
                        "Just stop drinking soda…your health with thank you!",
                R.drawable.tips_replace_juice
            )
        )
    }

    interface OnItemClickListener {
        fun onItemClick(meal: Meal)
    }

}

