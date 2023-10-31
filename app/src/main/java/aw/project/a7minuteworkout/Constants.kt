package aw.project.a7minuteworkout

object Constants {
    fun defaultExerciseList():ArrayList<ExerciseModel>{
        val exerciseList=ArrayList<ExerciseModel>()
        val jumpingJacks=ExerciseModel(
            1,
            "JumpingJacks",
            R.drawable.ic_jumping_jacks,
            false,
        false
        )
        exerciseList.add(jumpingJacks)

        val wallSit=ExerciseModel(
            2,
            "Wall Sit",
            R.drawable.ic_wall_sit,
            false,
            false
        )
        exerciseList.add(wallSit)
        val pushup=ExerciseModel(
            3,
            "Push Up",
            R.drawable.ic_push_up,
            false,
            false
        )
        exerciseList.add(pushup)
        val abdominalCrunch=ExerciseModel(
            4,
            "Abdominal Crunch",
            R.drawable.ic_abdominal_crunch,
            false,
            false
        )
        exerciseList.add(abdominalCrunch)
        val highkneesrunningonthespot=ExerciseModel(
            5,
            "High Knees Running on the spot",
            R.drawable.ic_high_knees_running_in_place,
            false,
            false
        )
        exerciseList.add(highkneesrunningonthespot)
        val lunges=ExerciseModel(
            6,
            "Lunges",
            R.drawable.ic_lunge,
            false,
            false
        )
        exerciseList.add(lunges)
        val plank = ExerciseModel(
            7,
            "plank",
            R.drawable.ic_plank,
            false,
            false
        )
        exerciseList.add(plank)
        val sideplank=ExerciseModel(
            8,
            "sideplank",
            R.drawable.ic_side_plank,
            false,
            false
        )
        exerciseList.add(sideplank)
        val squats=ExerciseModel(
            9,
            "squats",
            R.drawable.ic_squat,
            false,
            false
        )
        exerciseList.add(squats)
        val steponchair=ExerciseModel(
            10,
            "Step on Chair",
            R.drawable.ic_step_up_onto_chair,
            false,
            false
        )
        exerciseList.add(steponchair)
        val tricepchairdips=ExerciseModel(
            11,
            "Triceps Chair Dips",
            R.drawable.ic_triceps_dip_on_chair,
            false,
            false
        )
        exerciseList.add(tricepchairdips)

        return exerciseList
    }
}