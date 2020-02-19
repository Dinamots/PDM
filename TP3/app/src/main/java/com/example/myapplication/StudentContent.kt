package fr.clerc.myapplication.kotlin

object StudentContent {
    val ITEMS: ArrayList<Student> = ArrayList()

    init { // Add some sample items.
        ITEMS.add(Student(0, "Robert", "Brown", "M", 21))
        ITEMS.add(Student(1, "Jason", "Jones", "M", 22))
        ITEMS.add(Student(2, "Jane", "Smith", "F", 21))
        ITEMS.add(Student(3, "Theodore", "Putman", "M", 23))
        ITEMS.add(Student(4, "Karen", "Martinez", "F", 20))
        ITEMS.add(Student(5, "Jame", "Chase", "M", 21))
        ITEMS.add(Student(6, "Louise", "Lee", "F", 21))
        ITEMS.add(Student(7, "Claire", "Green", "F", 21))
        ITEMS.add(Student(8, "Helen", "Cranberry", "F", 22))
        ITEMS.add(Student(9, "Jacob", "Graves", "M", 21))
        ITEMS.add(Student(10, "Elliot", "Edwards", "M", 20))
        ITEMS.add(Student(11, "Lily", "Richard", "F", 21))
        ITEMS.add(Student(12, "Violet", "Miles", "F", 22))
        ITEMS.add(Student(13, "John", "Farell", "M", 22))
        ITEMS.add(Student(14, "Rachel", "Caine", "F", 23))
    }
}